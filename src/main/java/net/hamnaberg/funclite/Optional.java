/*
 * Copyright 2013 Erlend Hamnaberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.hamnaberg.funclite;

import java.util.Iterator;

public abstract class Optional<A> implements Iterable<A> {
    public static None<Object> NONE = new None<Object>();

    Optional() {
    }

    public abstract A get();

    public abstract boolean isSome();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    public final boolean isNone() {
        return !isSome();
    }

    public final <B> Optional<B> map(Function<A, B> f) {
        if (isNone()) {
            return none();
        }
        else {
            return fromNullable(f.apply(get()));
        }
    }

    public final <B> Optional<B> flatMap(Function<A, Optional<B>> f) {
        if (isNone()) {
            return none();
        }
        else {
            return Preconditions.checkNotNull(f.apply(get()));
        }
    }

    public final void foreach(Effect<A> e) {
        CollectionOps.foreach(this, e);
    }

    public final Optional<A> filter(Predicate<A> input) {
        if (isSome() && input.apply(get())) {
            return this;
        }
        else {
            return none();
        }
    }

    public boolean forall(Predicate<A> input) {
        return CollectionOps.forall(this, input);
    }

    public boolean exists(Predicate<A> input) {
        return CollectionOps.exists(this, input);
    }

    public static <A> Optional<A> fromNullable(A value) {
        return value != null ? some(value) : Optional.<A>none();
    }

    public static <A> Optional<A> some(A value) {
        return new Some<A>(Preconditions.checkNotNull(value));
    }

    public com.google.common.base.Optional<A> toGuava() {
        return com.google.common.base.Optional.fromNullable(orNull());
    }

    public static <A> Optional<A> fromGuava(com.google.common.base.Optional<A> opt) {
        return fromNullable(opt.orNull());
    }

    @SuppressWarnings("unchecked")
    public static <A> Optional<A> none() {
        return (Optional<A>) NONE;
    }

    public A orNull() {
        return isSome() ? get() : null;
    }

    public A getOrElse(A orElse) {
        return isSome() ? get() : orElse;
    }

    public A getOrElse(Supplier<A> orElse) {
        return isSome() ? get() : orElse.get();
    }

    public Optional<A> or(Optional<A> orElse) {
        return isSome() ? this : orElse;
    }

    public <B> B fold(Supplier<B> noneF, Function<A, B> someF) {
        return isNone() ? noneF.get() : someF.apply(get());
    }

    @Override
    public final Iterator<A> iterator() {
        return new Iterator<A>() {
            private volatile boolean used = false;
            @Override
            public boolean hasNext() {
                return !used && isSome();
            }

            @Override
            public A next() {
                A value = get();
                used = true;
                return value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported");
            }
        };
    }
}

final class Some<A> extends Optional<A> {

    private final A value;

    Some(A value) {
        this.value = value;
    }

    @Override
    public A get() {
        return value;
    }

    @Override
    public boolean isSome() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Some some = (Some) o;

        if (value != null ? !value.equals(some.value) : some.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("Some{%s}", value);
    }
}

final class None<A> extends Optional<A> {
    @Override
    public A get() {
        throw new UnsupportedOperationException("Cannot get from None");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof None;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public boolean isSome() {
        return false;
    }

    @Override
    public String toString() {
        return "None";
    }
}
