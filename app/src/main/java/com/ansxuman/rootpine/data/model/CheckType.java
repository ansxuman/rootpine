package com.ansxuman.rootpine.data.model;

public enum CheckType {
    SU_BINARY("SU Binary Check"),
    MAGISK("Magisk Check"),
    ROOT_MANAGEMENT("Root Management Apps"),
    DANGEROUS_APPS("Dangerous Apps"),
    CUSTOM_ROM("Custom ROM Check"),
    DANGEROUS_PROPS("System Properties"),
    BUSYBOX("Busybox Binary");

    private final String title;

    CheckType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}