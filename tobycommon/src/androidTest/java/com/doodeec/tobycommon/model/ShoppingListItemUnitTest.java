package com.doodeec.tobycommon.model;

import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author dusan.bartos
 */
public class ShoppingListItemUnitTest {
    ShoppingListItem listItem;

    @Before
    public void setUp() {
        this.listItem = new ShoppingListItem("Rozky");
        this.listItem.setAmount(5);
        this.listItem.setUnitType(UnitType.Units);
    }

    @Test
    public void testGetName() {
        assertThat(listItem.getName(), is("Rozky"));
    }

    @Test
    public void testGetSetId() {
        assertThat(listItem.getId(), is(IsNull.nullValue()));
        listItem.setId(123456789);
        assertThat(listItem.getId(), is(123456789));
        listItem.setId(123);
        assertThat(listItem.getId(), is(123));
    }

    @Test
    public void testGetSetAmount() {
        assertThat(listItem.getAmount(), is(5));
        listItem.setAmount(10);
        assertThat(listItem.getAmount(), is(10));
    }

    @Test
    public void testGetSetUnitType() {
        assertThat(listItem.getUnit(), is(UnitType.Units));
        listItem.setUnitType(UnitType.CentiM);
        assertThat(listItem.getUnit(), is(UnitType.CentiM));
    }

    @After
    public void tearDown() {
        listItem = null;
    }
}
