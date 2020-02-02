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

import io.github.java_native.libloader.internal.Nullable;
import io.github.java_native.libloader.systems.SystemDefinition;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DefaultLibraryPathFormatter implements LibraryPathFormatter {

    private static final String NATIVES_ROOT_FOLDER = "natives";
    private static final String SEPARATOR = "-";
    private static final String OS_NAME = "%1$s";
    private static final String ARCH = "%2$s";
    private static final String BITNESS = "%3$s";
    private static final String DEFAULT_QUALIFIER_PATH = OS_NAME + SEPARATOR + ARCH + SEPARATOR + BITNESS;
    private static final String QUALIFIER = "%4$s";
    private static final String PREFIX = "%5$s";
    private static final String LIBRARY_NAME = "%6$s";
    private static final String FILE_EXTENSION = "%7$s";
    private static final String VERSION = "%8$s";
    private static final String PREFIX_LIBNAME_SUFFIX = PREFIX + LIBRARY_NAME + FILE_EXTENSION;

    private static final String PREFIX_LIBNAME_SUFFIX_VERSIONED = PREFIX + LIBRARY_NAME + "-" + VERSION + FILE_EXTENSION;

    private static final String FORMATTED_PATH_WITH_QUALIFIER = NATIVES_ROOT_FOLDER
            + "/" + DEFAULT_QUALIFIER_PATH + SEPARATOR + QUALIFIER
            + "/" + PREFIX_LIBNAME_SUFFIX;
    private static final String FORMATTED_PATH_WITHOUT_QUALIFIER = NATIVES_ROOT_FOLDER
            + "/" + DEFAULT_QUALIFIER_PATH
            + "/" + PREFIX_LIBNAME_SUFFIX;

    private static final String FORMATTED_PATH_WITH_VERSION_QUALIFIER = NATIVES_ROOT_FOLDER
            + "/" + DEFAULT_QUALIFIER_PATH + SEPARATOR + QUALIFIER
            + "/" + PREFIX_LIBNAME_SUFFIX_VERSIONED;
    private static final String FORMATTED_PATH_WITH_VERSION_WITHOUT_QUALIFIER = NATIVES_ROOT_FOLDER
            + "/" + DEFAULT_QUALIFIER_PATH
            + "/" + PREFIX_LIBNAME_SUFFIX_VERSIONED;

    private static final @Nullable String OVERRIDE_PATH = System.getProperty("native.libloader.systempath", null);

    private static List<String> getPathTemplate(final SystemDefinition systemDefinition) {
        final ArrayList<String> templates = new ArrayList<String>();
        if (null != OVERRIDE_PATH) {
            templates.add(getOverriddenPathTemplate(OVERRIDE_PATH));
        }

        templates.addAll(getDefaultPathTemplates(systemDefinition));
        return Collections.unmodifiableList(templates);
    }

    private static List<String> getDefaultPathTemplates(final SystemDefinition systemDefinition) {
        final ArrayList<String> templates = new ArrayList<String>();

        if (systemDefinition.getQualifier() != null) {
            templates.add(FORMATTED_PATH_WITH_QUALIFIER);
        }

        templates.add(FORMATTED_PATH_WITHOUT_QUALIFIER);
        return Collections.unmodifiableList(templates);
    }

    private Collection<String> getPathTemplatesVersioned(final SystemDefinition systemDefinition) {
        final ArrayList<String> templates = new ArrayList<String>();

        if (systemDefinition.getQualifier() != null) {
            templates.add(FORMATTED_PATH_WITH_VERSION_QUALIFIER);
        }

        templates.add(FORMATTED_PATH_WITH_VERSION_WITHOUT_QUALIFIER);
        return Collections.unmodifiableList(templates);
    }

    @Override
    public List<String> getFormattedPaths(final SystemDefinition systemDefinition, final String libName) {
        if (libName.isEmpty()) {
            throw new IllegalArgumentException("Empty library name provided");
        }

        if (libName.contains(File.separator) || libName.contains(File.pathSeparator)) {
            throw new IllegalArgumentException(
                    "library name contains illegal characters: [" + libName + "]. "
                            + "Do not use '" + File.separator + "' or '" + File.pathSeparator + "'.");
        }

        final List<String> pathTemplates = getPathTemplate(systemDefinition);
        final List<String> formattedPaths = new ArrayList<String>();

        for (final String pathTemplate : pathTemplates) {
            formattedPaths.addAll(doCreatePathForSystem(systemDefinition, libName, null, pathTemplate));
        }

        return Collections.unmodifiableList(formattedPaths);
    }

    @Override
    public List<String> getFormattedPaths(final SystemDefinition systemDefinition, final String libName, final String version) {
        if (libName.isEmpty()) {
            throw new IllegalArgumentException("Empty library name provided");
        }

        if (libName.contains(File.separator) || libName.contains(File.pathSeparator)) {
            throw new IllegalArgumentException(
                    "library name contains illegal characters: [" + libName + "]. "
                            + "Do not use '" + File.separator + "' or '" + File.pathSeparator + "'.");
        }

        if (version.trim().isEmpty()) {
            // no version supplied.
            return getFormattedPaths(systemDefinition, libName);
        }

        final ArrayList<String> formattedPaths = new ArrayList<String>();
        for (final String pathTemplate : getPathTemplatesVersioned(systemDefinition)) {
            formattedPaths.addAll(doCreatePathForSystem(systemDefinition, libName, version, pathTemplate));
        }

        // also add non-versioning paths.
        formattedPaths.addAll(getFormattedPaths(systemDefinition, libName));

        return Collections.unmodifiableList(formattedPaths);
    }

    private List<String> doCreatePathForSystem(final SystemDefinition systemDefinition,
                                               final String libName,
                                               final @Nullable String version,
                                               final String pathTemplate) {
        final List<String> formattedPaths = new ArrayList<String>();

        for (final String suffix : systemDefinition.getLibrarySuffixes()) {
            final String formattedPath = String.format(Locale.ENGLISH,
                    pathTemplate,
                    systemDefinition.getNormalizedOsName(),
                    systemDefinition.getArchitecture(),
                    systemDefinition.getBitness().getBitness(),
                    systemDefinition.getQualifier(),
                    systemDefinition.getLibraryPrefix(),
                    libName,
                    suffix,
                    version
            );
            formattedPaths.add(formattedPath);
        }

        return Collections.unmodifiableList(formattedPaths);
    }


    private static String getOverriddenPathTemplate(final String overridePath) {
        return overridePath + "/" + PREFIX_LIBNAME_SUFFIX;
    }

}
