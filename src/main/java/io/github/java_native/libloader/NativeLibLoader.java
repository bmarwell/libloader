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

package io.github.java_native.libloader;

public interface NativeLibLoader {

    /**
     * Will try to load a library with the given name.
     *
     * @param libraryName
     *         the library name without '{@code lib} prefix on posix systems and without a file suffix like {@code .so} or similar.
     * @return the result containing information about loading errors and the temporary path.
     * @throws NullPointerException
     *         if libraryName is {@code null}.
     * @throws IllegalArgumentException
     *         if libraryName is empty or contains a path separator char.
     */
    LibLoaderResult loadLibrary(final String libraryName);
}
