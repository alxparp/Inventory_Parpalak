package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test only methods are presents
 *
 * Created by makovetskyi on 11/4/2016.
 */
public class UtilitiesTest {

    Utilities utilities;

    @Before
    public void before() throws Exception {
        utilities = new Utilities();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void sortByIN() throws Exception {
        utilities.sortByIN(new Device[0]);
    }

    @Test
    public void filtrateByType() throws Exception {
        utilities.filtrateByType(null, null);
    }

}