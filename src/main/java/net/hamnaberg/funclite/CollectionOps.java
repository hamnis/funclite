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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CollectionOps {

    public static <A> List<A> of(A... values) {
        return Arrays.asList(values);
    }

    public static <A> ArrayList<A> newArrayList() {
        return new ArrayList<A>();
    }

    public static <A> ArrayList<A> newArrayList(Iterable<A> iterable) {
        ArrayList<A> list = newArrayList();
        addAll(list, iterable);
        return list;
    }

    public static <A> Iterable<A> iterable(final Iterator<A> it) {
        return () -> it;
    }

    public static <A, B> List<B> map(final List<A> list, final Function<A, B> f) {
        List<B> toList = newArrayList();
        for (A a : list) {
            toList.add(f.apply(a));
        }
        return Collections.unmodifiableList(toList);
    }

    public static <A> void addAll(List<A> list, Iterable<A> iterable) {
        for (A a : iterable) {
            list.add(a);
        }
    }

    public static <A> void addAll(Set<A> list, Iterable<A> iterable) {
        for (A a : iterable) {
            list.add(a);
        }
    }

    public static <A, B> List<B> flatMap(final Iterable<A> list, final Function<A, Iterable<B>> f) {
        ArrayList<B> toList = newArrayList();
        for (A a : list) {
            addAll(toList, f.apply(a));
        }
        return Collections.unmodifiableList(toList);
    }

    public static <A> List<A> flatten(final Iterable<Iterable<A>> list) {
        List<A> toList = newArrayList();
        for (Iterable<A> it : list) {
            for (A a : it) {
                toList.add(a);
            }
        }
        return Collections.unmodifiableList(toList);
    }

    public static <A> List<A> filter(final Iterable<A> list, final Predicate<A> f) {
        List<A> copy = newArrayList();
        for (A a : list) {
            if (f.test(a)) {
                copy.add(a);
            }
        }
        return Collections.unmodifiableList(copy);
    }

    public static <V> String mkString(Iterable<V> iterable){
        return mkString(iterable, "");
    }

    public static <V> String mkString(Iterable<V> iterable, String separator){
        return mkString(iterable, "", separator, "");
    }

    public static <V> String mkString(Iterable<V> iterable,String start, String separator, String end){
        return StreamSupport.stream(iterable.spliterator(), false).
                filter(a -> a != null).
                map(Object::toString).
                collect(Collectors.joining(separator, start, end));
    }

    public static Iterable<String> split(String input, String separator) {
        final String[] split = input.split(separator);
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return new StringArrayIterator(split);
            }

            @Override
            public String toString() {
                return mkString(this, "[", ",", "]");
            }
        };
    }

    public static <K, V> Map<K, List<V>> groupBy(Iterable<V> iterable, Function<V, K> grouper) {
        return stream(iterable).collect(Collectors.groupingBy(grouper));
    }

    public static <A> Stream<A> stream(Iterable<A> it) {
        return StreamSupport.stream(it.spliterator(), false);
    }

    public static <A> Stream<A> parallelStream(Iterable<A> it) {
        return StreamSupport.stream(it.spliterator(), true);
    }

    public static <A> boolean forall(final Iterable<A> iterable, Predicate<A> pred) {
        return stream(iterable).allMatch(pred);
    }

    public static <A> boolean exists(final Iterable<A> iterable, Predicate<A> pred) {
        return stream(iterable).anyMatch(pred);
    }

    public static <A> Optional<A> find(final Collection<A> coll, final Predicate<A> f) {
        for (A a : coll) {
            if (f.test(a)) {
                return Optional.fromNullable(a);
            }
        }
        return Optional.none();
    }

    public static <A> Optional<A> headOption(final Iterable<A> coll) {
        return isEmpty(coll) ? Optional.<A>none() : Optional.fromNullable(coll.iterator().next());
    }

    public static <A> int size(Iterable<A> iterable) {
        return (int) stream(iterable).count();
    }

    public static <A>  boolean isEmpty(Iterable<A> iterable) {
        return !iterable.iterator().hasNext();
    }

    public static <A> Set<A> setOf(A... values) {
        LinkedHashSet<A> set = newLinkedHashSet();
        Collections.addAll(set, values);
        return set;
    }

    public static <A> LinkedHashSet<A> newLinkedHashSet() {
        return new LinkedHashSet<A>();
    }

    public static <A> Set<A> setOf(Iterable<A> values) {
        LinkedHashSet<A> set = newLinkedHashSet();
        addAll(set, values);
        return set;
    }

    public static <A> void foreach(Iterable<A> iterable, Consumer<A> effect) {
        for (A a : iterable) {
            effect.accept(a);
        }
    }

    public static <A> Set<A> difference(Set<A> left, Set<A> right) {
        Set<A> set = CollectionOps.newLinkedHashSet();
        for (A a : left) {
            if (!right.contains(a)) {
                set.add(a);
            }
        }
        return set;
    }

    public static <A> A reduce(Iterable<A> iterable, Semigroup<A> semigroup, A seed) {
        A u = seed;
        for (A a : iterable) {
            u = semigroup.append(u, a);
        }

        return u;
    }

    public static <A> A reduce(Iterable<A> iterable, Monoid<A> m) {
        A u = m.zero();
        for (A a : iterable) {
            u = m.append(u, a);
        }

        return u;
    }


    public static <A> A foldLeft(Iterable<A> iterable, Semigroup<A> semigroup, A seed) {
        return reduce(iterable, semigroup, seed);
    }

    public static <A, B> Map<B, Integer> countBy(Iterable<A> iterable, Function<A, B> f) {
        Map<B, List<A>> bCollectionMap = groupBy(iterable, f);
        Set<B> keySet = bCollectionMap.keySet();
        HashMap<B, Integer> map = new HashMap<>();
        for (B key : keySet) {
            map.put(key, bCollectionMap.get(key).size());
        }

        return map;
    }

    public static class StringArrayIterator implements Iterator<String> {
        private final String[] array;
        private int index = 0;

        public StringArrayIterator(String[] array) {
            this.array = array;
        }

        public boolean hasNext() {
            return isInRange(index);
        }

        private boolean isInRange(int idx) {
            return array.length > 0 && array.length > idx;
        }

        public String next() {
            return array[index++].trim();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not allowed");
        }
    }
}
