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

import io.github.java_native.libloader.config.LibLoaderConfig;
import io.github.java_native.libloader.internal.Nullable;
import io.github.java_native.libloader.provider.SystemDefinitionProvider;
import io.github.java_native.libloader.systems.CurrentSystemProperties;
import io.github.java_native.libloader.systems.SystemDefinition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a lazy service loader loading mechanism.
 */
abstract class AbstractExtensibleNativeLibLoader {

    private final LibLoaderConfig config;

    private @Nullable SystemDefinition detectedSystem;

    public AbstractExtensibleNativeLibLoader(final LibLoaderConfig config) {
        this.config = config;
    }

    /**
     * A provider for system definitions which will also trigger the lazy initialzation of the
     * ServiceLoader mechanism.
     *
     * @return a List of SystemDefinitions including those supplied by ServiceLoaders.
     */
    protected List<SystemDefinition> getSystemDefinitions() {
        final List<SystemDefinition> providedSystems = ServiceLoaderProvidedSystems.providedSystemDefinitions;
        final List<SystemDefinition> systemDefinitions = getConfig().getSystemDefinitions();

        final ArrayList<SystemDefinition> allDefinitions =
                new ArrayList<SystemDefinition>(providedSystems.size() + systemDefinitions.size());

        allDefinitions.addAll(providedSystems);
        allDefinitions.addAll(systemDefinitions);

        return Collections.unmodifiableList(allDefinitions);
    }

    protected void ensureSystemDetected() {
        if (this.detectedSystem != null) {
            return;
        }

        final CurrentSystemProperties currentSystemProperties = CurrentSystemProperties.INSTANCE;

        for (final SystemDefinition system : getSystemDefinitions()) {
            if (system.matches(currentSystemProperties)) {
                this.detectedSystem = system;
                return;
            }
        }
    }

    protected LibLoaderConfig getConfig() {
        return this.config;
    }


    protected SystemDefinition getDetectedSystem() {
        return this.detectedSystem;
    }

    protected void setDetectedSystem(final SystemDefinition detectedSystem) {
        this.detectedSystem = detectedSystem;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractExtensibleNativeLibLoader{");
        sb.append("config=").append(this.config);
        sb.append(", detectedSystem=").append(this.detectedSystem);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Value holding class with lazy initialization.
     */
    private static class ServiceLoaderProvidedSystems {

        static final List<SystemDefinition> providedSystemDefinitions = doLoadFromServiceLoaders();

        private static List<SystemDefinition> doLoadFromServiceLoaders() {
            final String className = ServiceLoaderProvidedSystems.class.getName();
            final Logger logger = Logger.getLogger(className);

            logger.log(Level.INFO, "Loading additional system definitions.");

            final ServiceLoader<SystemDefinitionProvider> providers = ServiceLoader.load(SystemDefinitionProvider.class);
            final Iterator<SystemDefinitionProvider> providerIterator = providers.iterator();

            final List<SystemDefinition> collectedSystemDefinitions = new ArrayList<SystemDefinition>();

            while (providerIterator.hasNext()) {
                final SystemDefinitionProvider provider = providerIterator.next();
                final Collection<SystemDefinition> providerSystemDefinitions = provider.getSystemDefinitions();

                if (null == providerSystemDefinitions) {

                    logger.log(Level.WARNING,
                            "Provider " + provider.getClass().getName() + " returned 'null' for getSystemDefinitions.");

                    // this result must be ignored.
                    continue;
                }
                collectedSystemDefinitions.addAll(providerSystemDefinitions);
            }

            if (!collectedSystemDefinitions.isEmpty()) {
                logger.log(Level.INFO, "Loaded " + collectedSystemDefinitions.size() + " additional definitions.");
            }

            return Collections.unmodifiableList(collectedSystemDefinitions);
        }
    }
}
