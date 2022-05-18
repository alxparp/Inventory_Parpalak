package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.WrongPKMethodException;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.location.Trunk;
import com.netcracker.edu.location.impl.TrunkStubImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by admin on 23.11.2016.
 */
public class ConnectionPKTest {

    ConnectionPK connectionPK;
    @Before
    public void setUp() throws Exception {
        connectionPK = new ConnectionPK(new TrunkStubImpl("Test", "test"), 3);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getTrunk() throws Exception {
        Trunk expTrunk = new TrunkStubImpl("Another", "Trunk");
        int expSerialNumber = 5;
        ConnectionPK pk = new ConnectionPK(expTrunk, expSerialNumber);

        Trunk trunk = pk.getTrunk();

        assertEquals(expTrunk, trunk);
    }

    @Test
    public void getSerialNumber() throws Exception {
        Trunk expTrunk = new TrunkStubImpl("Another", "Trunk");
        int expSerialNumber = 5;
        ConnectionPK pk = new ConnectionPK(expTrunk, expSerialNumber);

        int serialNumber = pk.getSerialNumber();

        assertEquals(expSerialNumber, serialNumber);
    }

    @Test
    public void equals() throws Exception {
        Trunk trunk1 = new TrunkStubImpl("Trunk", "1");
        Trunk trunk2 = new TrunkStubImpl("Trunk", "2");
        ConnectionPK pk1 = new ConnectionPK(trunk1, 1);
        ConnectionPK pk2 = new ConnectionPK(trunk1, 2);
        ConnectionPK pk3 = new ConnectionPK(trunk1, 2);
        ConnectionPK pk4 = new ConnectionPK(trunk1, 1);
        ConnectionPK pk5 = new ConnectionPK(trunk2, 1);
        ConnectionPK pk6 = new ConnectionPK(trunk2, 1);

        assertFalse(pk1.equals(pk2));
        assertFalse(pk2.equals(pk1));
        assertTrue(pk2.equals(pk3));
        assertTrue(pk3.equals(pk2));
        assertFalse(pk4.equals(pk5));
        assertFalse(pk5.equals(pk4));
        assertTrue(pk5.equals(pk6));
        assertTrue(pk6.equals(pk5));
    }

    @Test
    public void compareTo() throws Exception {
        Trunk trunk = new TrunkStubImpl("Trunk", "trunk");
        ConnectionPK pk1 = new ConnectionPK(trunk, 1);
        ConnectionPK pk2 = new ConnectionPK(trunk, 2);
        ConnectionPK pk3 = new ConnectionPK(trunk, 3);
        ConnectionPK pk4 = new ConnectionPK(trunk, 2);

        int result1 = pk2.compareTo(pk1);
        int result2 = pk2.compareTo(pk3);
        int result3 = pk2.compareTo(pk4);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
        assertTrue(result3 == 0);

    }

    @Test(expected = WrongPKMethodException.class)
    public void setTrunk() throws Exception {
        connectionPK.setTrunk(new TrunkStubImpl("Begin-End", "BE"));
    }

    @Test(expected = WrongPKMethodException.class)
    public void setSerialNumber() throws Exception {
        connectionPK.setSerialNumber(9);
    }

    @Test(expected = WrongPKMethodException.class)
    public void getStatus() throws Exception {
        connectionPK.getStatus();
    }

    @Test(expected = WrongPKMethodException.class)
    public void setStatus() throws Exception {
        connectionPK.setStatus(Connection.DISASSEMBLED);
    }

    @Test
    public void isPrimaryKey() throws Exception {
        assertTrue(connectionPK.isPrimaryKey());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        assertTrue(connectionPK == connectionPK.getPrimaryKey());
    }

    @Test(expected = WrongPKMethodException.class)
    public void feelAllFields() throws Exception {
        connectionPK.feelAllFields(new FeelableEntity.Field[5]);
    }

    @Test(expected = WrongPKMethodException.class)
    public void getAllFields() throws Exception {
        connectionPK.getAllFields();
    }

    @Test(expected = WrongPKMethodException.class)
    public void fillAllFields() throws Exception {
        connectionPK.fillAllFields(new ArrayList<FeelableEntity.Field>());
    }

    @Test(expected = WrongPKMethodException.class)
    public void getAllFieldsList() throws Exception {
        connectionPK.getAllFieldsList();
    }

}