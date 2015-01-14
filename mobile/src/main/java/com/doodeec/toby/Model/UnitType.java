package com.doodeec.toby.Model;

/**
 * Enum for expressing amount of shopping list items
 *
 * @author Dusan Bartos
 */
public enum UnitType {
    Units("KS"),
    // weight
    DekaGram("DKG"),
    Kilo("KG"),
    // length
    Meter("M"),
    CentiM("CM"),
    // volume
    Liter("L"),
    DeciLiter("DCL"),
    // other / not set
    Undefined("N/A");

    public String typeKey;

    private UnitType(String typeKey) {
        this.typeKey = typeKey;
    }
}
