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

import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

public class FunctionalMapTest {
    @Test
    public void construct() {
        FunctionalMap<Object, Object> objects = FunctionalMap.create(Collections.emptyMap());
        assertNotNull(objects);
    }

    @Test
    public void wrappingOfSelfReturnsSame() {
        FunctionalMap<Object, Object> empty = FunctionalMap.empty();
        FunctionalMap<Object, Object> map = FunctionalMap.create(empty);
        assertSame(empty, map);
    }

    @Test
    public void getOrElse() {
        Map<String,Integer> map = MapOps.newHashMap();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        FunctionalMap<String, Integer> fMap = FunctionalMap.create(map);

        Integer foo = fMap.getOrElse("foo", 3);
        assertNotNull(foo);
        foo = fMap.get("foo");
        assertNull(foo);

        FunctionalMap<String, Integer> withDefaultValue = fMap.withDefaultValue(23);
        assertNotNull(withDefaultValue.get("foo"));
        assertEquals(Integer.valueOf(23), withDefaultValue.get("foo"));
    }

    @Test
    public void mapValues() {
        Map<String, String> map = MapOps.newHashMap();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");
        FunctionalMap<String, String> wrapper = FunctionalMap.create(map);
        FunctionalMap<String, Integer> mappedValues = wrapper.mapValues(Integer::valueOf);

        assertNotSame(wrapper, mappedValues);

        assertEquals(Integer.valueOf(3), mappedValues.get("three"));
    }
}
