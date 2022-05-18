package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Unique;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 13.11.16.
 */
public class OpticFiberTest {

    OpticFiber<Switch, WifiRouter> defaultOpticFiber;
    OpticFiber<Switch, WifiRouter> opticFiber;
    String defaultStatus = Connection.PLANED;
    String status = Connection.READY;
    int defaultLength = 0;
    int length = 50;
    OpticFiber.Mode defaultMode = OpticFiber.Mode.need_init;
    OpticFiber.Mode mode = OpticFiber.Mode.single;

    @Before
    public void setUp() throws Exception {
        defaultOpticFiber = new OpticFiber();
        opticFiber = CreateUtilities.createOpticFiber();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getMode() throws Exception {
        assertEquals(defaultMode, defaultOpticFiber.getMode());
        assertEquals(mode, opticFiber.getMode());
    }

    @Test
    public void defaultLength() throws Exception {
        int result = defaultOpticFiber.getLength();

        assertEquals(defaultLength, result);
    }

    @Test
    public void setGetLength() throws Exception {
        defaultOpticFiber.setLength(length);
        int result = defaultOpticFiber.getLength();

        assertEquals(length, result);
    }

    @Test
    public void getAPointConnectorType() throws Exception {
        assertEquals(ConnectorType.FiberConnector_FC, defaultOpticFiber.getAPointConnectorType());
        assertEquals(ConnectorType.FiberConnector_FC, opticFiber.getAPointConnectorType());
    }

    @Test
    public void getBPointConnectorType() throws Exception {
        assertEquals(ConnectorType.FiberConnector_FC, defaultOpticFiber.getBPointConnectorType());
        assertEquals(ConnectorType.FiberConnector_FC, opticFiber.getBPointConnectorType());
    }

    @Test
    public void setGetAPoint() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitch();

        defaultOpticFiber.setAPoint(aSwitch);
        Switch result = defaultOpticFiber.getAPoint();

        AssertUtilities.assertSwitch(aSwitch, result);
    }

    @Test
    public void setGetBPoint() throws Exception {
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();

        defaultOpticFiber.setBPoint(wifiRouter);
        WifiRouter result = defaultOpticFiber.getBPoint();

        AssertUtilities.assertWifiRouter(wifiRouter, result);
    }

    @Test
    public void defaultStatus() throws Exception {
        String result = defaultOpticFiber.getStatus();

        assertEquals(defaultStatus, result);
    }

    @Test
    public void setGetStatus() throws Exception {
        defaultOpticFiber.setStatus(status);
        String result = defaultOpticFiber.getStatus();

        assertEquals(status, result);
    }

    @Deprecated
    @Test
    public void testGetAndFeelAllFieldsArray() throws Exception {
        OpticFiber result1 = new OpticFiber();
        result1.feelAllFields(opticFiber.getAllFields());

        AssertUtilities.assertOpticFiber(opticFiber, result1);
    }

    @Deprecated
    @Test
    public void testGetAndFeelAllFieldsArray_EmptyConnection() throws Exception {
        OpticFiber result1 = new OpticFiber();
        result1.feelAllFields(opticFiber.getAllFields());

        AssertUtilities.assertOpticFiber(opticFiber, result1);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        OpticFiber result1 = new OpticFiber();
        result1.fillAllFields(opticFiber.getAllFieldsList());

        AssertUtilities.assertOpticFiber(opticFiber, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyConnection() throws Exception {
        OpticFiber result1 = new OpticFiber();
        result1.fillAllFields(opticFiber.getAllFieldsList());

        AssertUtilities.assertOpticFiber(opticFiber, result1);
    }

    @Test
    public void isPrimaryKey() throws Exception {
        assertFalse(defaultOpticFiber.isPrimaryKey());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        ConnectionPrimaryKey expConnectionPK = new ConnectionPK(opticFiber.getTrunk(), opticFiber.getSerialNumber());

        Unique.PrimaryKey primaryKey = opticFiber.getPrimaryKey();

        AssertUtilities.assertSomePK(expConnectionPK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = defaultOpticFiber.getPrimaryKey();

        assertNull(primaryKey);
    }

    @Test
    public void compareTo() throws Exception {
        Connection connection1 = new OpticFiber();
        connection1.setSerialNumber(1);
        Connection connection2 = new OpticFiber();
        connection2.setSerialNumber(2);
        Connection connection3 = new OpticFiber();
        connection3.setSerialNumber(3);
        Connection connection4 = new OpticFiber();
        connection4.setSerialNumber(2);

        int result1 = connection2.compareTo(connection1);
        int result2 = connection2.compareTo(connection3);
        int result3 = connection2.compareTo(connection4);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
        assertTrue(result3 == 0);
    }

}