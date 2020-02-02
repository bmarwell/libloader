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
import io.github.java_native.libloader.systems.linux.LinuxArm32HardFloat;
import io.github.java_native.libloader.systems.linux.LinuxArm32SoftFloat;
import io.github.java_native.libloader.systems.linux.LinuxX8632;
import io.github.java_native.libloader.systems.linux.LinuxX8664;
import io.github.java_native.libloader.systems.osx.MacOsxX8664;
import io.github.java_native.libloader.systems.windows.WindowsX8632;
import io.github.java_native.libloader.systems.windows.WindowsX8664;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ExtensibleNativeLibLoaderVersionDefaultPathTest {

    private static final Logger LOG = Logger.getLogger(ExtensibleNativeLibLoaderVersionDefaultPathTest.class.getName());

    private final SystemDefinition systemDefinition;
    private final String libName;
    private final String version;
    private final String expectedPath;

    public ExtensibleNativeLibLoaderVersionDefaultPathTest(final SystemDefinition systemDefinition,
                                                           final String libName,
                                                           final String version,
                                                           final String expectedPath) {
        this.systemDefinition = systemDefinition;
        this.libName = libName;
        this.version = version;
        this.expectedPath = expectedPath;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new LinuxX8664(), "jssc", "1.0.0", "natives/linux-x86_64-64/libjssc-1.0.0.so"},
                {new LinuxX8632(), "jssc", "2.0.0", "natives/linux-x86_32-32/libjssc-2.0.0.so"},
                {new LinuxArm32SoftFloat(), "jssc", "2.0.1", "natives/linux-arm_32-32/libjssc-2.0.1.so"},
                {new LinuxArm32HardFloat(), "jssc", "2.2.0", "natives/linux-arm_32-32-hf/libjssc-2.2.0.so"},
                {new MacOsxX8664(), "jssc", "3.0.0", "natives/osx-x86_64-64/libjssc-3.0.0.dylib"},
                {new WindowsX8664(), "jssc", "", "natives/windows-x86_64-64/jssc.dll"},
                {new WindowsX8632(), "jssc", "", "natives/windows-x86_32-32/jssc.dll"},
        });
    }

    @Test
    public void testLibLoaderPath() {
        final ExtensibleNativeLibLoader nativeLibLoader = new ExtensibleNativeLibLoader(new DefaultLibLoaderConfig());
        nativeLibLoader.setDetectedSystem(this.systemDefinition);

        final List<String> libraryPackagePath = nativeLibLoader.getLibraryPackagePath(this.libName, this.version);
        LOG.log(Level.INFO, "assert e[" + this.expectedPath + "] == a[" + libraryPackagePath + "]");

        Assert.assertEquals(this.expectedPath, libraryPackagePath.get(0));
    }

}
