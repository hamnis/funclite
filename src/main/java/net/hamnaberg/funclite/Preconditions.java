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

public class Preconditions {
    /**
     *
     * @deprecated Use {@link java.util.Objects#requireNonNull(A)}
     */
    @Deprecated
    public static <A> A checkNotNull(A input) {
        return checkNotNull(input, "input was null");
    }

    /**
     *
     * @deprecated Use {@link java.util.Objects#requireNonNull(A, String)}
     */
    @Deprecated
    public static <A> A checkNotNull(A input, String message, Object... args) {
        if (input == null) {
            throw new IllegalArgumentException(String.format(message, args));
        }
        return input;
    }

    public static void checkArgument(boolean pred, String message, Object... args) {
        if (!pred) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }
}
