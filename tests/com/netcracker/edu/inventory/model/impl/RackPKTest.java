package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.WrongPKMethodException;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.impl.LocationStubImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by admin on 23.11.2016.
 */
public class RackPKTest {

    RackPK rackPK;

    @Before
    public void setUp() throws Exception {
        rackPK = new RackPK(new LocationStubImpl("Test", "test"));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getLocation() throws Exception {
        Location expLocation = new LocationStubImpl("Another", "Trunk");
        RackPK pk = new RackPK(expLocation);

        Location location = pk.getLocation();

        assertEquals(expLocation, location);
    }

    @Test
    public void equals() throws Exception {
        Location location1 = new LocationStubImpl("Location", "1");
        Location location2 = new LocationStubImpl("Location", "2");
        RackPK pk1 = new RackPK(location1);
        RackPK pk2 = new RackPK(location2);
        RackPK pk3 = new RackPK(location2);

        assertFalse(pk1.equals(pk2));
        assertFalse(pk2.equals(pk1));
        assertTrue(pk2.equals(pk3));
        assertTrue(pk3.equals(pk2));
    }

    @Test(expected = WrongPKMethodException.class)
    public void setLocation() throws Exception {
        rackPK.setLocation(new LocationStubImpl("Loca", "Loca"));
    }

    @Test(expected = WrongPKMethodException.class)
    public void getSize() throws Exception {
        rackPK.getSize();
    }

    @Test(expected = WrongPKMethodException.class)
    public void getFreeSize() throws Exception {
        rackPK.getFreeSize();
    }

    @Test(expected = WrongPKMethodException.class)
    public void getTypeOfDevices() throws Exception {
        rackPK.getTypeOfDevices();
    }

    @Test(expected = WrongPKMethodException.class)
    public void getDevAtSlot() throws Exception {
        rackPK.getDevAtSlot(3);
    }

    @Test(expected = WrongPKMethodException.class)
    public void insertDevToSlot() throws Exception {
        rackPK.insertDevToSlot(new Battery(), 3);
    }

    @Test(expected = WrongPKMethodException.class)
    public void removeDevFromSlot() throws Exception {
        rackPK.removeDevFromSlot(3);
    }

    @Test(expected = WrongPKMethodException.class)
    public void getDevByIN() throws Exception {
        rackPK.getDevByIN(7);
    }

    @Test(expected = WrongPKMethodException.class)
    public void getAllDeviceAsArray() throws Exception {
        rackPK.getAllDeviceAsArray();
    }

    @Test
    public void isPrimaryKey() throws Exception {
        assertTrue(rackPK.isPrimaryKey());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        assertTrue(rackPK == rackPK.getPrimaryKey());
    }

}
