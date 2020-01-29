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
import io.github.java_native.libloader.systems.linux.LinuxArm32HardFloat;
import io.github.java_native.libloader.systems.linux.LinuxArm32SoftFloat;
import io.github.java_native.libloader.systems.linux.LinuxX8632;
import io.github.java_native.libloader.systems.linux.LinuxX8664;
import io.github.java_native.libloader.systems.osx.MacOsxX8664;
import io.github.java_native.libloader.systems.windows.WindowsX8632;
import io.github.java_native.libloader.systems.windows.WindowsX8664;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The default config used when no other config was provided.
 *
 * <p>Contains a number of system definitions shipped with this library.<br></p>
 */
public class DefaultLibLoaderConfig implements LibLoaderConfig {

    private static final LibraryPathFormatter LIBRARY_PATH_FORMATTER = new DefaultLibraryPathFormatter();

    private static final List<SystemDefinition> DEFAULT_SYSTEMS = Arrays.<SystemDefinition>asList(
            new LinuxX8632(),
            new LinuxX8664(),
            new LinuxArm32SoftFloat(),
            new LinuxArm32HardFloat(),
            new MacOsxX8664(),
            new WindowsX8632(),
            new WindowsX8664()
    );

    private final List<SystemDefinition> systems;

    /**
     * Generates a default config.
     */
    public DefaultLibLoaderConfig() {
        this(new ArrayList<SystemDefinition>());
    }

    /**
     * Generates a default config with additional systems.
     *
     * @param additionalSystems
     *         additional systems to be added to the list of known systems.
     */
    public DefaultLibLoaderConfig(final List<SystemDefinition> additionalSystems) {
        final List<SystemDefinition> systems = new ArrayList<SystemDefinition>(DEFAULT_SYSTEMS);
        systems.addAll(additionalSystems);
        this.systems = Collections.unmodifiableList(systems);
    }

    @Override
    public LibraryPathFormatter getLibraryPathFormatter() {
        return LIBRARY_PATH_FORMATTER;
    }

    @Override
    public List<SystemDefinition> getSystemDefinitions() {
        return Collections.unmodifiableList(this.systems);
    }
}
