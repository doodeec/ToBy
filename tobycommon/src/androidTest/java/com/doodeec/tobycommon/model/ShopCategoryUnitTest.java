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
public class ShopCategoryUnitTest {

    ShopCategory category;

    @Before
    public void setUp() {
        this.category = new ShopCategory("Potraviny");
    }

    @Test
    public void testGetName() {
        assertThat(category.getName(), is("Potraviny"));
    }

    @Test
    public void testGetSetId() {
        assertThat(category.getId(), is(IsNull.nullValue()));
        category.setId(123456789);
        assertThat(category.getId(), is(123456789));
        category.setId(123);
        assertThat(category.getId(), is(123));
    }

    @After
    public void tearDown() {
        category = null;
    }
}
