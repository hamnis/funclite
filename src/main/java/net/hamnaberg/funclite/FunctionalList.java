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

import java.util.*;

public final class FunctionalList<A> implements List<A> {
    private final List<A> delegate;

    private FunctionalList(List<A> delegate) {
        this.delegate = delegate;
    }

    public Optional<A> headOption() {
        return CollectionOps.headOption(this);
    }

    public A head() {
        return headOption().orNull();
    }

    public FunctionalList<A> tail() {
        if (isEmpty()) {
            return this;
        }
        return create(delegate.subList(1, size()));
    }

    public <B> FunctionalList<B> map(Function<A, B> f) {
        return create(CollectionOps.map(this, f));
    }

    public <B> FunctionalList<B> flatMap(Function<A, Iterable<B>> f) {
        if (isEmpty()) {
            return empty();
        }
        else {
            return create(CollectionOps.flatMap(this, f));
        }
    }

    public FunctionalList<A> filter(Predicate<A> pred) {
        return new FunctionalList<A>(CollectionOps.filter(this, pred));
    }

    public void foreach(Effect<A> effect) {
        CollectionOps.foreach(this, effect);
    }

    public boolean forall(Predicate<A> pred) {
        return CollectionOps.forall(this, pred);
    }

    public boolean exists(Predicate<A> input) {
        return CollectionOps.exists(this, input);
    }

    public Optional<A> find(Predicate<A> pred) {
        return CollectionOps.find(this, pred);
    }

    public A reduce(Semigroup<A> u, A seed) {
        return CollectionOps.reduce(this, u, seed);
    }

    public Set<A> toSet() {
        return CollectionOps.setOf(delegate);
    }

    public String mkString(String start, String sep, String end) {
        return CollectionOps.mkString(this, start, sep, end);
    }

    public String mkString(String sep) {
        return CollectionOps.mkString(this, "[", sep, "]");
    }

    public <K> Map<K, Collection<A>> groupBy(Function<A, K> grouper) {
        return CollectionOps.groupBy(this, grouper);
    }

    public String mkString(){
        return CollectionOps.mkString(this);
    }

    /** factories **/

    public static <B> FunctionalList<B> empty() {
        return new FunctionalList<B>(Collections.<B>emptyList());
    }

    public static <A> FunctionalList<A> of(A... args) {
        return new FunctionalList<A>(Arrays.asList(args));
    }

    public static <A> FunctionalList<A> create(List<A> list) {
        if (list instanceof FunctionalList) {
            return (FunctionalList<A>) list;
        }
        return new FunctionalList<A>(list);
    }

    public static <A> FunctionalList<A> copyOf(Iterable<A> list) {
        if (list instanceof FunctionalList) {
            return (FunctionalList<A>) list;
        }
        return new FunctionalList<A>(CollectionOps.newArrayList(list));
    }


    /** List boilerplate **/

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    @Override
    public Iterator<A> iterator() {
        return Collections.unmodifiableList(delegate).iterator();
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    public boolean add(A a) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    public boolean addAll(Collection<? extends A> c) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean addAll(int index, Collection<? extends A> c) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean equals(Object o) {
        return delegate.equals(o);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public A get(int index) {
        return delegate.get(index);
    }

    public A set(int index, A element) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void add(int index, A element) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public A remove(int index) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int indexOf(Object o) {
        return delegate.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return delegate.lastIndexOf(o);
    }

    @Override
    public ListIterator<A> listIterator() {
        return Collections.unmodifiableList(delegate).listIterator();
    }

    @Override
    public ListIterator<A> listIterator(int index) {
        return Collections.unmodifiableList(delegate).listIterator(index);
    }

    @Override
    public List<A> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(delegate).subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return mkString(",");
    }
}
