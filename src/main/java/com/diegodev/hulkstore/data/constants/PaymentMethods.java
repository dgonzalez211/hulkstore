package com.diegodev.hulkstore.data.constants;

public enum PaymentMethods {
    CASH("CASH"),
    CREDIT_CARD("CREDIT CARD"),
    DEBIT_CARD("DEBIT CARD"),
    BANK_TRANSFER("BANK TRANSFER"),
    WALLET("WALLET");

    private final String name;

    private PaymentMethods(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
