/*
 * Copyright (c) 2020 the 'java-native' development team
 *         https://github.com/java-native/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.java_native.libloader.systems;

import io.github.java_native.libloader.internal.Nullable;
import java.util.Locale;

public enum Endianess {
    UNKNOWN(null),
    LITTLE("little"),
    BIG("big");

    private final @Nullable String endianess;

    Endianess(final @Nullable String endianess) {
        this.endianess = endianess;
    }

    /**
     * Try to read endianess from a string. Currently, only 'little' and 'big' are recognized.
     *
     * <p>The input will be trimmed and converted to lowercase.<br></p>
     *
     * @param endianessString the endianess as string.
     * @return the Endianess or {@link Endianess#UNKNOWN} if input was neither 'little' nor 'big'.
     */
    public static Endianess fromString(final String endianessString) {
        if (endianessString == null || "".equals(endianessString)) {
            return UNKNOWN;
        }

        final String formattedInput = endianessString.trim().toLowerCase(Locale.ENGLISH);

        for (final Endianess knownValue : values()) {
            if (formattedInput.equals(knownValue.endianess)) {
                return knownValue;
            }
        }

        return Endianess.UNKNOWN;
    }

    public String getEndianess() {
        return this.endianess;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Endianess{");
        sb.append("endianess='").append(this.endianess).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
