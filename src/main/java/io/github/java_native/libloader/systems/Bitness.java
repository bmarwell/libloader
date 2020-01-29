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

public enum Bitness {
    UNKNOWN(0),
    _8(8),
    _16(16),
    _32(32),
    _64(64),
    _128(128);


    private final int bitness;

    Bitness(final int bitness) {
        this.bitness = bitness;
    }

    public static Bitness fromString(final String bitnessString) {
        if (bitnessString == null || "".equals(bitnessString)) {
            return UNKNOWN;
        }

        for (final Bitness knownValue : values()) {
            if (("" + knownValue.getBitness()).equals(bitnessString)) {
                return knownValue;
            }
        }
        return UNKNOWN;
    }

    public int getBitness() {
        return this.bitness;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bitness{");
        sb.append("bitness=").append(this.bitness);
        sb.append('}');
        return sb.toString();
    }
}
