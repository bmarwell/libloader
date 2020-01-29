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
 * Configuration provider for the main LibLoader instance.
 */
public interface LibLoaderConfig {

    /**
     * Template for default native library search path.
     *
     * <p>This method should return a string which can be formatted by {@link String#format(String, Object...)}.</p>
     *
     * <p>The following parameters are available.</p>
     *
     * <ol>
     *     <li>system name (lower case)</li>
     *     <li>normalized architecture</li>
     *     <li>bitness (e.g. 32 or 64)</li>
     *     <li>qualifier (optional, e.g. {@code hf}.</li>
     * </ol>
     */
    LibraryPathFormatter getLibraryPathFormatter();

    /**
     * Known system definitions which will be matched against the current OS.
     *
     * @return a list of known system definitions.
     */
    List<SystemDefinition> getSystemDefinitions();


}
