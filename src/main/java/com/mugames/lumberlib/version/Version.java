package com.mugames.lumberlib.version;

public enum Version {

    V_1_12("1.12", "1_12"),
    V_1_13("1.13", "1_13"),
    V_1_14("1.14", "1_14"),
    V_1_15("1.15", "1_15"),
    V_1_16("1.16", "1_16"),
    V_1_17("1.17", "1_17"),
    V_1_18("1.18", "1_18"),
    V_1_19("1.19", "1_19")
    ;

    private final String version;
    private final String spigotVersion;

    Version(String version, String spigotVersion) {
        this.version = version;
        this.spigotVersion = spigotVersion;
    }

    public String getVersion() {
        return version;
    }

    public String getSpigotVersion() {
        return spigotVersion;
    }

    public static Version getVersion(String version) {
        for (Version value : values()) {
            if (version.contains(value.getSpigotVersion()))
                return value;
        }
        return V_1_12;
    }

    public static boolean isOver(Version version1, Version version2) {
        return version1.ordinal() > version2.ordinal();
    }

    public static boolean isUnder(Version version1, Version version2) {
        return version1.ordinal() < version2.ordinal();
    }

}
