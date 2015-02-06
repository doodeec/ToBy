package com.doodeec.tobycommon.model;

import com.doodeec.tobycommon.model.interfaces.IShopCategory;

import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author dusan.bartos
 */
public class ShopUnitTest {

    Shop shop;
    IShopCategory mockCategory;

    @Before
    public void setUp() {
        this.mockCategory = new IShopCategory() {
            @Override
            public void setId(int id) {}

            @Override
            public Integer getId() {
                return 12345;
            }

            @Override
            public String getName() {
                return "Potraviny";
            }
        };
        this.shop = new Shop("Billa", mockCategory);
    }

    @Test
    public void testGetName() {
        assertThat(shop.getName(), is("Billa"));
    }

    @Test
    public void testGetSetId() {
        assertThat(shop.getId(), is(IsNull.nullValue()));
        shop.setId(123456789);
        assertThat(shop.getId(), is(123456789));
        shop.setId(123);
        assertThat(shop.getId(), is(123));
    }

    @Test
    public void testGetCategory() {
        assertThat(shop.getCategory(), is(mockCategory));
    }

    @After
    public void tearDown() {
        shop = null;
    }
}
