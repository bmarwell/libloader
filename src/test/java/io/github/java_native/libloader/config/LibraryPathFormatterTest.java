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

import io.github.java_native.libloader.systems.linux.LinuxX8664;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LibraryPathFormatterTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testFormat_empty() {
        final LibraryPathFormatter formatter = new DefaultLibraryPathFormatter();
        final LinuxX8664 linuxX8664 = new LinuxX8664();
        this.expectedException.expect(IllegalArgumentException.class);
        formatter.getFormattedPaths(linuxX8664, "");
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testFormat_null() {
        final LibraryPathFormatter formatter = new DefaultLibraryPathFormatter();
        final LinuxX8664 linuxX8664 = new LinuxX8664();
        this.expectedException.expect(NullPointerException.class);
        formatter.getFormattedPaths(linuxX8664, null);
    }

    @Test
    public void testFormat_pathSeparator() {
        final LibraryPathFormatter formatter = new DefaultLibraryPathFormatter();
        final LinuxX8664 linuxX8664 = new LinuxX8664();
        this.expectedException.expect(IllegalArgumentException.class);
        formatter.getFormattedPaths(linuxX8664, ":;\\/");
    }
}
