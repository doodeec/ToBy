package com.doodeec.tobycommon.model;

import com.doodeec.tobycommon.R;

/**
 * Enum for expressing amount of shopping list items
 *
 * @author Dusan Bartos
 */
public enum UnitType {
    Units("KS", R.string.unit_type_units),
    // weight
    DekaGram("DKG", R.string.unit_type_dekagram),
    Kilo("KG", R.string.unit_type_kilo),
    // length
    Meter("M", R.string.unit_type_meter),
    CentiM("CM", R.string.unit_type_centimeter),
    // volume
    Liter("L", R.string.unit_type_liter),
    DeciLiter("DCL", R.string.unit_type_deciliter),
    // other / not set
    Undefined("N/A", R.string.unit_type_unknown);

    public String typeKey;
    public int resource;

    private UnitType(String typeKey, int resource) {
        this.typeKey = typeKey;
        this.resource = resource;
    }
}
