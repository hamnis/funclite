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
        return new Iterable<A>() {
            @Override
            public Iterator<A> iterator() {
                return it;
            }
        };
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
            if (f.apply(a)) {
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
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        sb.append(start);
        for (V v : iterable) {
            if (v != null) {
                if(first) {
                    sb.append(v.toString());
                    first = false;
                } else {
                    sb.append(separator);
                    sb.append(v);
                }    
            }            
        }
        sb.append(end);
        return sb.toString();
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

    public static <K, V> Map<K, Collection<V>> groupBy(Iterable<V> iterable, Function<V, K> grouper) {
        Map<K, Collection<V>> map = new LinkedHashMap<>();
        for (V v : iterable) {
            K key = grouper.apply(v);
            Collection<V> value = map.get(key);
            if (value == null) {
                value = newArrayList();
                map.put(key, value);
            }
            value.add(v);
        }
        return map;
    }

    public static <A> boolean forall(final Iterable<A> iterable, Predicate<A> pred) {
        for (A a : iterable) {
            if (!pred.apply(a)) {
                return false;
            }
        }
        return true;
    }

    public static <A> boolean exists(final Iterable<A> iterable, Predicate<A> pred) {
        for (A a : iterable) {
            if (pred.apply(a)) {
                return true;
            }
        }
        return false;
    }

    public static <A> Optional<A> find(final Collection<A> coll, final Predicate<A> f) {
        for (A a : coll) {
            if (f.apply(a)) {
                return Optional.fromNullable(a);
            }
        }
        return Optional.none();
    }

    public static <A> Optional<A> headOption(final Iterable<A> coll) {
        return isEmpty(coll) ? Optional.<A>none() : Optional.fromNullable(coll.iterator().next());
    }

    public static <A> int size(Iterable<A> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection)iterable).size();
        }
        int size = 0;
        for (A ignore : iterable) {
            size++;
        }
        return size;
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

    public static <A> void foreach(Iterable<A> iterable, Effect<A> effect) {
        for (A a : iterable) {
            effect.exec(a);
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
        Map<B, Collection<A>> bCollectionMap = groupBy(iterable, f);
        Set<B> keySet = bCollectionMap.keySet();
        HashMap<B, Integer> map = new HashMap<>();
        for (B key : keySet) {
            map.put(key, bCollectionMap.get(key).size());
        }

        return map;
    }

    private static class StringArrayIterator implements Iterator<String> {
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
