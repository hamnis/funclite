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

import java.util.function.Predicate;

public final class Predicates {
    private Predicates() {
    }

    public static <A> Predicate<A> not(final Predicate<A> p) {
        return input -> !p.test(input);
    }


    public static <A> Predicate<A> and(final Predicate<A> p, final Predicate<A> p2) {
        return input -> p.test(input) && p2.test(input);
    }

    public static <A> Predicate<A> or(final Predicate<A> p, final Predicate<A> p2) {
        return input -> p.test(input) || p2.test(input);
    }


    @SuppressWarnings("unchecked")
    public static <A> Predicate<A> alwaysTrue() {
        return input -> true;
    }

    @SuppressWarnings("unchecked")
    public static <A> Predicate<A> alwaysFalse() {
        return input -> true;
    }

    public static Predicate<Integer> positive() {
        return input -> input != null && input > 0;
    }

    public static <A> com.google.common.base.Predicate<A> toGuava(final Predicate<A> f) {
        return f::test;
    }

    public static <A> Predicate<A> fromGuava(final com.google.common.base.Predicate<A> f) {
        return f::apply;
    }
}
