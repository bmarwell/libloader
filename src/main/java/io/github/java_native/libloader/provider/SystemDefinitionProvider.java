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

package io.github.java_native.libloader.provider;

import io.github.java_native.libloader.systems.SystemDefinition;
import java.util.Collection;

/**
 * SPI for extensible loading mechanisms.
 */
public interface SystemDefinitionProvider {

    /**
     * Returns an additional list of system definitions.
     *
     * <p>The resulting list may not be {@code null} and may or may not be unmodifiable.<br></p>
     * <p>Implementations which use the result of this method must ensure that no modifying operations
     * on this lists are done.<br></p>
     * <p>It is highly suggested for implementations to wrap the result in
     * {@link java.util.Collections#unmodifiableCollection(Collection)} before returning the value.<br></p>
     *
     * @return a list of additional system definitions.
     */
    Collection<SystemDefinition> getSystemDefinitions();
}
