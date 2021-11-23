package org.foton.utils.nms;

import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MinecraftVersion {
    public static final VersionEnum VERSION;

    static {
        String namespace = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        VERSION = Arrays.stream(VersionEnum.values()).map(Enum::toString).collect(Collectors.toList()).contains(namespace) ? VersionEnum.valueOf(namespace) : VersionEnum.v1_17_R1;
    }

    public enum VersionEnum {
        v1_8_R1(10801),
        v1_8_R2(10802),
        v1_8_R3(10803),
        //Does this even exists?
        v1_8_R4(10804),

        v1_9_R1(10901),
        v1_9_R2(10902),

        v1_10_R1(11001),

        v1_11_R1(11101),

        v1_12_R1(11201),

        v1_13_R1(11301),
        v1_13_R2(11302),

        v1_14_R1(11401),

        v1_15_R1(11501),

        v1_16_R1(11601),
        v1_16_R2(11602),
        v1_16_R3(11603),

        v1_17_R1(11701);

        private final int index;

        VersionEnum(int index) {
            this.index = index;
        }

        public boolean newerThan(VersionEnum other) {
            return other.index < this.index;
        }

        public boolean olderThan(VersionEnum other) {
            return other.index > this.index;
        }

        public boolean equals(VersionEnum other) {
            return other.index == this.index;
        }
    }
}