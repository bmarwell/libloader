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
import io.github.java_native.libloader.systems.SystemDefinition;
import io.github.java_native.libloader.systems.linux.LinuxX8632;
import io.github.java_native.libloader.systems.linux.LinuxX8664;
import io.github.java_native.libloader.systems.osx.MacOsxX8664;
import io.github.java_native.libloader.systems.windows.WindowsX8632;
import io.github.java_native.libloader.systems.windows.WindowsX8664;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ExtensibleNativeLibLoaderTest {

    private final SystemDefinition systemDefinition;
    private final String libName;
    private final String expectedPath;

    public ExtensibleNativeLibLoaderTest(final SystemDefinition systemDefinition,
                                         final String libName,
                                         final String expectedPath) {
        this.systemDefinition = systemDefinition;
        this.libName = libName;
        this.expectedPath = expectedPath;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new LinuxX8664(), "jssc", "natives/linux-x86_64-64/libjssc.so"},
                {new LinuxX8632(), "jssc", "natives/linux-x86_32-32/libjssc.so"},
                {new MacOsxX8664(), "jssc", "natives/osx-x86_64-64/libjssc.dylib"},
                {new WindowsX8664(), "jssc", "natives/windows-x86_64-64/jssc.dll"},
                {new WindowsX8632(), "jssc", "natives/windows-x86_32-32/jssc.dll"},
        });
    }

    @Test
    public void testLibLoaderPath() {
        final ExtensibleNativeLibLoader nativeLibLoader = new ExtensibleNativeLibLoader(new DefaultLibLoaderConfig());
        nativeLibLoader.setDetectedSystem(this.systemDefinition);

        final String jssc = nativeLibLoader.getLibraryPackagePath(this.libName);

        Assert.assertEquals(this.expectedPath, jssc);
    }

}
