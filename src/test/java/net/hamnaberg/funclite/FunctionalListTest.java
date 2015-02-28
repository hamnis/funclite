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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static net.hamnaberg.funclite.Optional.*;
import static org.junit.Assert.*;

public class FunctionalListTest {
    @Test
    public void construct() {
        FunctionalList<Object> objects = FunctionalList.create(new ArrayList<>());
        assertNotNull(objects);
    }

    @Test
    public void wrappingOfSelfReturnsSame() {
        FunctionalList<Object> empty = FunctionalList.empty();
        FunctionalList<Object> list = FunctionalList.create(empty);
        assertSame(empty, list);
    }

    @Test
    public void mapStringToInt() {
        FunctionalList<String> list = FunctionalList.of("1", "2", "3", "4", "5");
        FunctionalList<Integer> ints = FunctionalList.of(1, 2, 3, 4, 5);
        assertEquals(ints, list.map(Integer::valueOf));
    }

    @Test
    public void foreach(){
        final AtomicInteger sideEffectCount = new AtomicInteger(0);
        FunctionalList<Integer> ints = FunctionalList.of(1, 2, 3);
        ints.foreach(i -> sideEffectCount.incrementAndGet());
        assertEquals(ints.size(), sideEffectCount.get());
    }


    @Test
    public void flatMapStringToInt() {
        FunctionalList<String> list = FunctionalList.of("1", "2", "one", "3");
        FunctionalList<Integer> ints = FunctionalList.of(1, 2, 3);
        assertEquals(ints, list.flatMap(new Function<String, Iterable<Integer>>() {
            @Override
            public Iterable<Integer> apply(String input) {
                try {
                    return some(Integer.parseInt(input));
                }
                catch (Exception e) {
                    return Optional.none();
                }
            }
        }));

    }
}
