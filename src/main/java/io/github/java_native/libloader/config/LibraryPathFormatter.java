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

package io.github.java_native.libloader.config;

import io.github.java_native.libloader.systems.SystemDefinition;
import java.util.List;

/**
 * A formatter takes a system definition and a library and will output a formatted path to load
 * a library from. The path is to be loaded from within a jar file.
 */
public interface LibraryPathFormatter {

    /**
     * Returns a formatted path for the given library name and supplied system.<br>
     *
     * @param systemDefinition
     *         the system to generate the library load path for.
     * @param libName
     *         the library name to load, without prefix or suffix or file extension.
     * @return the formatted path which can be loaded from a jar file.
     * @throws IllegalArgumentException
     *         if libName is empty or contains path separators or directory separators.
     * @throws NullPointerException
     *         if either parameter is {@code null}.
     */
    List<String> getFormattedPaths(SystemDefinition systemDefinition, String libName);

    /**
     * Returns a formatted path for the given library name, version and supplied system.<br>
     *
     * <p>If version is empty, it will not be appended. In this case the output
     * is equal to {@link #getFormattedPaths(SystemDefinition, String)}.<br></p>
     *
     * <p>Will <strong>not</strong> return anything which {@link #getFormattedPaths(SystemDefinition, String)} returns.<br></p>
     *
     * @param systemDefinition
     *         the system to generate the library load path for.
     * @param libName
     *         the library name to load, without prefix or suffix or file extension.
     * @param version
     *         the version number as string.
     * @return the formatted path which can be loaded from a jar file.
     * @throws IllegalArgumentException
     *         if libName is empty or contains path separators or directory separators.
     * @throws NullPointerException
     *         if either parameter is {@code null}.
     */
    List<String> getFormattedPaths(SystemDefinition systemDefinition, String libName, String version);

}
