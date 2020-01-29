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

package io.github.java_native.libloader.systems.osx;

import io.github.java_native.libloader.systems.AbstractOsNameArchBitnessMatchingSystem;
import io.github.java_native.libloader.systems.Endianess;

public abstract class AbstractMacOsxSystem extends AbstractOsNameArchBitnessMatchingSystem {

    @Override
    public String getOsName() {
        return "osx";
    }

    @Override
    public Endianess getEndianess() {
        return Endianess.LITTLE;
    }

    @Override
    public String getLibraryPrefix() {
        return "lib";
    }

    @Override
    public String getLibrarySuffix() {
        return ".dylib";
    }
}
