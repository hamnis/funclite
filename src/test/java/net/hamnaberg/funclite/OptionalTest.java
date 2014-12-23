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

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class OptionalTest {

    @Test(expected = IllegalArgumentException.class)
    public void failWhenAddingNullToSome() {
        Optional.some(null);
    }

    @Test
    public void iterableIsWorkingCorrectly() {
        int count = 0;
        for (String s : Optional.some("hello")) {
            assertEquals("hello", s);
            count++;
        }

        assertEquals("Iterator of Some did not produce values", 1, count);
    }
    @Test
    public void iterableOfNoneIsWorkingCorrectly() {
        for (String s : Optional.<String>none()) {
            fail("Iterator of none produced values");
        }
    }

    @Test
    public void foreach(){
        final AtomicReference<String> value = new AtomicReference<String>();
        Optional<String> some = Optional.some("Foreach all the things!");
        some.foreach(new Effect<String>() {
            @Override
            public void exec(String s) {
                value.set(s);
            }
        });
        assertEquals(some.get(), value.get());
    }

    @Test
    public void fold() {
        Optional<String> opt = Optional.some("3");
        Integer a = opt.fold(new Supplier<Integer>() {
            @Override
            public Integer get() {
                fail("Some called noneF");
                return null;
            }
        }, new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return Integer.valueOf(input);
            }
        });
        assertEquals(3, a.intValue());
        Optional<String> opt2 = Optional.none();
        Integer b = opt2.fold(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 0;
            }
        }, new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                fail("None called someF");
                return null;
            }
        });
        assertEquals(0, b.intValue());
    }
}
