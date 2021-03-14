package me.letssee.staffmode.enums;

public enum ItemCType {

    CURRENT("Current"),
    CURSOR("Cursor"),
    BOTH("Both");

    private String name;
    private ItemCType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
