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

import io.github.java_native.libloader.config.DefaultLibLoaderConfig;
import io.github.java_native.libloader.provider.BogoOs;
import io.github.java_native.libloader.systems.SystemDefinition;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class AbstractExtensibleNativeLibLoaderTest {

    @Test
    public void testLoadSpi() {
        final AbstractExtensibleNativeLibLoader libLoader = new AbstractExtensibleNativeLibLoader(
                new DefaultLibLoaderConfig()) {

        };

        final List<SystemDefinition> systemDefinitions = libLoader.getSystemDefinitions();

        for (final SystemDefinition def : systemDefinitions) {
            if (def instanceof BogoOs) {
                return;
            }
        }

        Assert.fail("BogoOs instance not found.");
    }

}
