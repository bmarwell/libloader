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

package io.github.java_native.libloader.systems;

/**
 * Abstract System Properties class with a default implementation, which matches the normalized OS name,
 * architecture and bitness.
 */
public abstract class AbstractOsNameArchBitnessMatchingSystem extends AbstractSystemProperties implements SystemDefinition {

    @Override
    public boolean matches(final SystemProperties currentSystemProperties) {
        return getNormalizedOsName().equals(currentSystemProperties.getNormalizedOsName())
                && getMatchingArchitectures().contains(currentSystemProperties.getArchitecture())
                && getBitness().equals(currentSystemProperties.getBitness());
    }

}
