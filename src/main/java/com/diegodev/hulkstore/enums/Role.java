package com.diegodev.hulkstore.enums;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    SUPPORT("SUPPORT"),
    CUSTOMER("CUSTOMER"),
    SELLER("SELLER");

    private final String name;

    private Role(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
