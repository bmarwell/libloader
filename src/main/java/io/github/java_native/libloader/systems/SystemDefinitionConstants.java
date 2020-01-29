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

import java.util.Arrays;
import java.util.List;

public final class SystemDefinitionConstants {

    private static final List<String> ARCH_X86_32 = Arrays.asList(
            "x86_32",
            "x8632",
            "x86",
            "i386",
            "i486",
            "i586",
            "i686",
            "ia32",
            "x32"
    );

    private static final List<String> ARCH_X86_64 = Arrays.asList(
            "x86_64",
            "x8664",
            "amd64",
            "ia32e",
            "em64t",
            "x64"
    );

    private static final List<String> ARCH_ITANIUM_32 = Arrays.asList(
            "itanium_32",
            "ia64n"
    );

    private static final List<String> ARCH_ITANIUM_64 = Arrays.asList(
            "itanium_64", "ia64", "ia64w", "itanium64"
    );

    private SystemDefinitionConstants() {
        // util class.
    }

    public static List<String> getArchX8632() {
        return ARCH_X86_32;
    }

    public static List<String> getArchX8664() {
        return ARCH_X86_64;
    }

    protected static List<String> getArchItanium32() {
        return ARCH_ITANIUM_32;
    }

    protected static List<String> getArchItanium64() {
        return ARCH_ITANIUM_64;
    }

}
