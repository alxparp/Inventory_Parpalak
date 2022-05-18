package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.WrongPKMethodException;
import com.netcracker.edu.inventory.model.FeelableEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by admin on 23.11.2016.
 */
public class DevicePKTest {

    DevicePK devicePK;

    @Before
    public void setUp() throws Exception {
        devicePK = new DevicePK(7);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getIn() throws Exception {
        int expIN = 6;
        DevicePK pk = new DevicePK(expIN);

        int in = pk.getIn();

        assertEquals(expIN, in);
    }

    @Test
    public void equals() throws Exception {
        DevicePK pk1 = new DevicePK(1);
        DevicePK pk2 = new DevicePK(2);
        DevicePK pk3 = new DevicePK(2);

        assertFalse(pk1.equals(pk2));
        assertFalse(pk2.equals(pk1));
        assertTrue(pk2.equals(pk3));
        assertTrue(pk3.equals(pk2));
    }

    @Test
    public void compareTo() throws Exception {
        DevicePK pk1 = new DevicePK(1);
        DevicePK pk2 = new DevicePK(2);
        DevicePK pk3 = new DevicePK(3);
        DevicePK pk4 = new DevicePK(2);

        int result1 = pk2.compareTo(pk1);
        int result2 = pk2.compareTo(pk3);
        int result3 = pk2.compareTo(pk4);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
        assertTrue(result3 == 0);
    }

    @Test(expected = WrongPKMethodException.class)
    public void setIn() throws Exception {
        devicePK.setIn(8);
    }

    @Test(expected = WrongPKMethodException.class)
    public void getType() throws Exception {
        devicePK.getType();
    }

    @Test(expected = WrongPKMethodException.class)
    public void getManufacturer() throws Exception {
        devicePK.getManufacturer();
    }

    @Test(expected = WrongPKMethodException.class)
    public void setManufacturer() throws Exception {
        devicePK.setManufacturer("test");
    }

    @Test(expected = WrongPKMethodException.class)
    public void getModel() throws Exception {
        devicePK.getModel();
    }

    @Test(expected = WrongPKMethodException.class)
    public void setModel() throws Exception {
        devicePK.setModel("Test");
    }

    @Test(expected = WrongPKMethodException.class)
    public void getProductionDate() throws Exception {
        devicePK.getProductionDate();
    }

    @Test(expected = WrongPKMethodException.class)
    public void setProductionDate() throws Exception {
        devicePK.setProductionDate(new Date());
    }

    @Test
    public void isPrimaryKey() throws Exception {
        assertTrue(devicePK.isPrimaryKey());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        assertTrue(devicePK == devicePK.getPrimaryKey());
    }

    @Test(expected = WrongPKMethodException.class)
    public void feelAllFields() throws Exception {
        devicePK.feelAllFields(new FeelableEntity.Field[5]);
    }

    @Test(expected = WrongPKMethodException.class)
    public void getAllFields() throws Exception {
        devicePK.getAllFields();
    }

    @Test(expected = WrongPKMethodException.class)
    public void fillAllFields() throws Exception {
        devicePK.fillAllFields(new ArrayList<FeelableEntity.Field>());
    }

    @Test(expected = WrongPKMethodException.class)
    public void getAllFieldsList() throws Exception {
        devicePK.getAllFieldsList();
    }

}