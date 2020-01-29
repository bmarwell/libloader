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

import io.github.java_native.libloader.internal.Nullable;
import java.util.Locale;

/**
 * Stores the current system properties in a singleton.
 *
 * <p>This class does some basic normalization.<br></p>
 *
 * <p>Examples:</p>
 * <pre>
 *     AIX 7.2 ppc64 @ openj9
 *     Name    [os.name]             = AIX
 *     Version [os.version]          = 7.2
 *     arch    [os.arch]             = ppc64
 *     bitness [sun.arch.data.model] = 64
 *     bitness [com.ibm.vm.bitmode]  = 64
 *     endian  [sun.cpu.endian]      = big
 *
 *     Raspbian stretch arm6l @ oracle-hotspot
 *     Name    [os.name]             = Linux
 *     Version [os.version]          = 4.14.50+
 *     arch    [os.arch]             = arm
 *     bitness [sun.arch.data.model] = 32
 *     bitness [com.ibm.vm.bitmode]  = null
 *     endian  [sun.cpu.endian]      = little
 *
 *     Ubuntu 18.04 arm64/aarch64 (cortex-a57) @ OpenJDK 1.8.0_191 64-Bit Server arm64
 *     Name    [os.name]             = Linux
 *     Version [os.version]          = 4.15.0-45-generic
 *     arch    [os.arch]             = aarch64
 *     bitness [sun.arch.data.model] = 64
 *     bitness [com.ibm.vm.bitmode]  = null
 *     endian  [sun.cpu.endian]      = little
 * </pre>
 */
public enum CurrentSystemProperties implements SystemProperties {
    INSTANCE;

    private final String lowercaseOsName;

    private final String osVersion;

    private final String architecture;

    private final Bitness bitness;

    private final Endianess endianess;

    private final @Nullable String abi;

    CurrentSystemProperties() {
        this.lowercaseOsName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH).trim();
        this.osVersion = System.getProperty("os.version");
        this.architecture = System.getProperty("os.arch");
        this.bitness = determineBitness();
        this.endianess = determineEndianess();
        this.abi = determineAbi();
        /* nullable  sun.arch.abi, can be gnueabihf or gnueabi, or find file /lib/arm-linux-gnueabihf */
        /* or, on linux, use `file java.home/bin/java`. */
    }

    private @Nullable
    String determineAbi() {
        final @Nullable
        String sunArchAbi = System.getProperty("sun.arch.abi");
        if (sunArchAbi != null) {
            if (sunArchAbi.trim().endsWith("hf")) {
                return "hf";
            }
        }

        return null;
    }

    private Endianess determineEndianess() {
        final String sunCpuEndian = System.getProperty("sun.cpu.endian");

        if (sunCpuEndian != null) {
            return Endianess.fromString(sunCpuEndian.toLowerCase(Locale.ENGLISH));
        }

        if (this.architecture.endsWith("le")) {
            return Endianess.LITTLE;
        }

        return Endianess.BIG;
    }

    private Bitness determineBitness() {
        final String sunArchDataModel = System.getProperty("sun.arch.data.model");
        if (sunArchDataModel != null) {
            return Bitness.fromString(sunArchDataModel);
        }

        final String ibmBitMode = System.getProperty("com.ibm.vm.bitmode");
        if (ibmBitMode != null) {
            return Bitness.fromString(ibmBitMode);
        }

        if (this.architecture.contains("16")) {
            return Bitness._16;
        }

        if (this.architecture.contains("32")) {
            return Bitness._32;
        }

        if (this.architecture.contains("128")) {
            return Bitness._128;
        }

        return Bitness._64;
    }

    /* Implementing */

    @Override
    public String getNormalizedOsName() {
        return this.lowercaseOsName;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    @Override
    public String getArchitecture() {
        return this.architecture;
    }

    @Override
    public Bitness getBitness() {
        return this.bitness;
    }

    @Override
    public Endianess getEndianess() {
        return this.endianess;
    }

    @Override
    public String getQualifier() {
        return this.abi;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CurrentSystemProperties{");
        sb.append("lowercaseOsName='").append(this.lowercaseOsName).append('\'');
        sb.append(", osVersion='").append(this.osVersion).append('\'');
        sb.append(", architecture='").append(this.architecture).append('\'');
        sb.append(", bitness=").append(this.bitness);
        sb.append(", endianess=").append(this.endianess);
        sb.append(", abi='").append(this.abi).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
