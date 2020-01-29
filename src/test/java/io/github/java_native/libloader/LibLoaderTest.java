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

import static org.junit.Assert.assertTrue;

import io.github.java_native.libloader.config.DefaultLibLoaderConfig;
import io.github.java_native.libloader.config.LibLoaderConfig;
import io.github.java_native.libloader.systems.SystemDefinition;
import java.util.Collections;
import org.junit.Test;

public class LibLoaderTest {

    @Test
    public void testGetLibLoader_noConfig() {
        final NativeLibLoader libLoader = LibLoader.getLibLoader();

        assertTrue(libLoader instanceof ExtensibleNativeLibLoader);
        final ExtensibleNativeLibLoader extensibleNativeLibLoader = (ExtensibleNativeLibLoader) libLoader;

        final LibLoaderConfig defaultConfig = extensibleNativeLibLoader.getConfig();
        assertTrue(defaultConfig instanceof DefaultLibLoaderConfig);
    }

    @Test
    public void testGetLibLoader_emptyConfig() {
        final DefaultLibLoaderConfig defaultConfig = new DefaultLibLoaderConfig(Collections.<SystemDefinition>emptyList());
        final NativeLibLoader libLoader = LibLoader.getLibLoader(defaultConfig);

        assertTrue(libLoader instanceof ExtensibleNativeLibLoader);
        final ExtensibleNativeLibLoader extensibleNativeLibLoader = (ExtensibleNativeLibLoader) libLoader;

        final LibLoaderConfig actualConfig = extensibleNativeLibLoader.getConfig();
        assertTrue(actualConfig instanceof DefaultLibLoaderConfig);
    }

}
