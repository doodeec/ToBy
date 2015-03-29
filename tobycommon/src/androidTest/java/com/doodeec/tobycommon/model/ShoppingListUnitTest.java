package com.doodeec.tobycommon.model;

import com.doodeec.tobycommon.model.interfaces.IShoppingListItem;

import org.hamcrest.core.IsNull;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author dusan.bartos
 */
public class ShoppingListUnitTest {
    ShoppingList list;
    IShoppingListItem mockItemOne;
    IShoppingListItem mockItemTwo;
    IShoppingListItem mockItemThree;
    List<IShoppingListItem> mockItems;

    @Before
    public void setUp() {
        this.list = new ShoppingList("Nakup");
        this.mockItemOne = new IShoppingListItem() {
            @Override
            public void setAmount(double amount) {

            }

            @Override
            public double getAmount() {
                return 0;
            }

            @Override
            public void setUnitType(UnitType type) {

            }

            @Override
            public UnitType getUnit() {
                return null;
            }

            @Override
            public void setChecked(boolean isChecked) {

            }

            @Override
            public boolean getChecked() {
                return false;
            }

            @Override
            public JSONObject toJSON() {
                return null;
            }

            @Override
            public int compareTo(IShoppingListItem another) {
                return 0;
            }

            @Override
            public void setId(int id) {

            }

            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public String getName() {
                return "One";
            }
        };
        this.mockItemTwo = new IShoppingListItem() {
            @Override
            public void setAmount(double amount) {

            }

            @Override
            public double getAmount() {
                return 0;
            }

            @Override
            public void setUnitType(UnitType type) {

            }

            @Override
            public UnitType getUnit() {
                return null;
            }

            @Override
            public void setChecked(boolean isChecked) {

            }

            @Override
            public boolean getChecked() {
                return false;
            }

            @Override
            public JSONObject toJSON() {
                return null;
            }

            @Override
            public int compareTo(IShoppingListItem another) {
                return 0;
            }

            @Override
            public void setId(int id) {

            }

            @Override
            public Integer getId() {
                return 2;
            }

            @Override
            public String getName() {
                return "Two";
            }
        };
        this.mockItemThree = new IShoppingListItem() {
            @Override
            public void setAmount(double amount) {

            }

            @Override
            public double getAmount() {
                return 0;
            }

            @Override
            public void setUnitType(UnitType type) {

            }

            @Override
            public UnitType getUnit() {
                return null;
            }

            @Override
            public void setChecked(boolean isChecked) {

            }

            @Override
            public boolean getChecked() {
                return false;
            }

            @Override
            public JSONObject toJSON() {
                return null;
            }

            @Override
            public int compareTo(IShoppingListItem another) {
                return 0;
            }

            @Override
            public void setId(int id) {

            }

            @Override
            public Integer getId() {
                return 3;
            }

            @Override
            public String getName() {
                return "Three";
            }
        };
        this.mockItems = new ArrayList<>();
        this.mockItems.add(mockItemOne);
        this.mockItems.add(mockItemTwo);
    }

    @Test
    public void testGetName() {
        assertThat(list.getName(), is("Nakup"));
    }

    @Test
    public void testGetSetId() {
        assertThat(list.getId(), is(IsNull.nullValue()));
        list.setId(123456789);
        assertThat(list.getId(), is(123456789));
        list.setId(123);
        assertThat(list.getId(), is(123));
    }

    @Test
    public void testItems() {
        assertThat(list.getItems(), is(IsNull.notNullValue()));
        assertThat(list.getItems().size(), is(0));
        list.setItems(mockItems);
        assertThat(list.getItems().size(), is(2));
        list.addItem(mockItemThree);
        assertThat(list.getItems().size(), is(3));
    }

    @After
    public void tearDown() {
        list = null;
    }
}
