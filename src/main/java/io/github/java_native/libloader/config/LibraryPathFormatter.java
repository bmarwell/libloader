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
import java.io.File;
import java.util.Locale;

public class LibraryPathFormatter {

    private static final String NATIVES_ROOT_FOLDER = "natives";
    private static final String SEPARATOR = "-";
    private static final String OS_NAME = "%1$s";
    private static final String ARCH = "%2$s";
    private static final String BITNESS = "%3$s";
    public static final String DEFAULT_QUALIFIER_PATH = OS_NAME + SEPARATOR + ARCH + SEPARATOR + BITNESS;
    private static final String QUALIFIER = "%4$s";
    private static final String PREFIX_LIBNAME_SUFFIX = "%5$s%6$s%7$s";
    private static final String FORMATTED_PATH_WITH_QUALIFIER = NATIVES_ROOT_FOLDER
            + "/" + DEFAULT_QUALIFIER_PATH + SEPARATOR + QUALIFIER
            + "/" + PREFIX_LIBNAME_SUFFIX;
    private static final String FORMATTED_PATH_WITHOUT_QUALIFIER = NATIVES_ROOT_FOLDER
            + "/" + DEFAULT_QUALIFIER_PATH
            + "/" + PREFIX_LIBNAME_SUFFIX;

    public String getFormattedPath(final SystemDefinition systemDefinition, final String libName) {
        if (libName.isEmpty()) {
            throw new IllegalArgumentException("Empty library name provided");
        }

        if (libName.contains(File.separator) || libName.contains(File.pathSeparator)) {
            throw new IllegalArgumentException(
                    "library name contains illegal characters: [" + libName + "]. "
                            + "Do not use '" + File.separator + "' or '" + File.pathSeparator + "'.");
        }

        final String pathTemplate = getPathTemplate(systemDefinition);

        return String.format(Locale.ENGLISH,
                pathTemplate,
                systemDefinition.getNormalizedOsName(),
                systemDefinition.getArchitecture(),
                systemDefinition.getBitness().getBitness(),
                systemDefinition.getQualifier(),
                systemDefinition.getLibraryPrefix(),
                libName,
                systemDefinition.getLibrarySuffix()
        );
    }

    private String getPathTemplate(final SystemDefinition systemDefinition) {
        if (systemDefinition.getQualifier() != null) {
            return FORMATTED_PATH_WITH_QUALIFIER;
        }

        return FORMATTED_PATH_WITHOUT_QUALIFIER;
    }

}
