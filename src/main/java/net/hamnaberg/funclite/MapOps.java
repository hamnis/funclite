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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class MapOps {
    public static <K,V> Map<K,V> newHashMap() {
        return new HashMap<>();
    }

    public static <K,V> Map<K,V> newHashMap(Map<K, V> fromMap) {
        return new HashMap<>(fromMap);
    }

    public static <K,V> Map<K,V> newHashMap(K k1, V v1) {
        HashMap<K, V> hm = new HashMap<>();
        hm.put(k1, v1);
        return hm;
    }

    public static <K,V> Map<K,V> newHashMap(K k1, V v1, K k2, V v2) {
        HashMap<K, V> hm = new HashMap<>();
        hm.put(k1, v1);
        hm.put(k2, v2);
        return hm;
    }

    public static <K,V> Map<K,V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3) {
        HashMap<K, V> hm = new HashMap<>();
        hm.put(k1, v1);
        hm.put(k2, v2);
        hm.put(k3, v3);
        return hm;
    }

    public static <K,V> Map<K,V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        HashMap<K, V> hm = new HashMap<>();
        hm.put(k1, v1);
        hm.put(k2, v2);
        hm.put(k3, v3);
        hm.put(k4, v4);
        return hm;
    }

    public static <K,V> Map<K,V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        HashMap<K, V> hm = new HashMap<>();
        hm.put(k1, v1);
        hm.put(k2, v2);
        hm.put(k3, v3);
        hm.put(k4, v4);
        hm.put(k5, v5);
        return hm;
    }

    public static <K, V> void foreach(Map<K, V> input, Consumer<Map.Entry<K, V>> effect){
        Set<Map.Entry<K,V>> entries = input.entrySet();
        for (Map.Entry<K, V> entry : entries) {
            effect.accept(entry);
        }
    }

    public static <K, V, V2> Map<K, V2> mapValues(Map<K, V> input, Function<V, V2> f) {
        Map<K, V2> map = MapOps.newHashMap();
        Set<Map.Entry<K,V>> entries = input.entrySet();
        for (Map.Entry<K, V> entry : entries) {
            map.put(entry.getKey(), f.apply(entry.getValue()));
        }
        return Collections.unmodifiableMap(map);
    }

    public static <K,V> Map<K, V> filterKeys(Map<K, V> input, final Predicate<K> predicate) {
        return filter(input, input1 -> predicate.test(input1.getKey()));
    }

    public static <K,V> Map<K, V> filter(Map<K, V> input, Predicate<Map.Entry<K, V>> predicate) {
        Map<K, V> map = MapOps.newHashMap();
        for (Map.Entry<K, V> entry : input.entrySet()) {
            if (predicate.test(entry)) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return Collections.unmodifiableMap(map);
    }
}
