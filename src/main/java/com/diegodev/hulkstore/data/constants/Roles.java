package com.diegodev.hulkstore.data.constants;

public enum Roles {
    USER("USER"),
    ADMIN("ADMIN"),
    SUPPORT("SUPPORT"),
    CUSTOMER("CUSTOMER"),
    SELLER("SELLER");

    private final String name;

    private Roles(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
