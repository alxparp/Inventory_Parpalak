package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 05.10.16.
 */
public class WifiRouterTest {

    WifiRouter defaultWifiRouter;
    WifiRouter wifiRouter;
    WifiRouter wifiRouterWithConnections;
    String technologyVersion = "802.11g";

    String securityProtocol = "";

    @Before
    public void before() throws Exception {
        defaultWifiRouter = new WifiRouter();
        wifiRouter = CreateUtilities.createWifiRouter();
        wifiRouterWithConnections = CreateUtilities.createWifiRouterWithConnections();
    }

    @After
    public void after() throws Exception {
        wifiRouter = null;
    }

    @Test
    public void getTechnologyVersion() throws Exception {
        assertNull(defaultWifiRouter.getTechnologyVersion());
        assertEquals(technologyVersion, wifiRouter.getTechnologyVersion());
    }

    @Test
    public void setGetSecurityProtocol() throws Exception {
        wifiRouter.setSecurityProtocol(securityProtocol);
        String result = wifiRouter.getSecurityProtocol();

        assertEquals(securityProtocol, result);
    }

    @Test
    public void setGetWirelessConnection() throws Exception {
        Wireless wireless = CreateUtilities.createWireless();
        wireless.setVersion(1);

        wifiRouter.setWireConnection(wireless);
        Wireless result = (Wireless) wifiRouter.getWireConnection();

        AssertUtilities.assertWireless(wireless, result);
    }

    @Test
    public void getWirePortType() throws Exception {
        assertEquals(ConnectorType.need_init, defaultWifiRouter.getWirePortType());
        assertEquals(ConnectorType.RJ45, wifiRouter.getWirePortType());
    }

    @Test
    public void setGetWireConnection() throws Exception {
        TwistedPair twistedPair = CreateUtilities.createTwistedPair();
        twistedPair.setLength(10);

        wifiRouter.setWireConnection(twistedPair);
        TwistedPair result = (TwistedPair) wifiRouter.getWireConnection();

        AssertUtilities.assertTwistedPair(twistedPair, result);
    }

    @Test
    public void testGetAndFeelAllFieldsArray() throws Exception {
        Device result1 = new WifiRouter();
        result1.feelAllFields(wifiRouterWithConnections.getAllFields());

        AssertUtilities.assertDevice(wifiRouterWithConnections, result1);
    }

    @Test
    public void testGetAndFeelAllFieldsArray_EmptyDevice() throws Exception {
        Device result1 = new WifiRouter();
        result1.feelAllFields(wifiRouterWithConnections.getAllFields());

        AssertUtilities.assertDevice(wifiRouterWithConnections, result1);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        WifiRouter result1 = new WifiRouter();
        result1.fillAllFields(wifiRouterWithConnections.getAllFieldsList());

        AssertUtilities.assertWifiRouter(wifiRouterWithConnections, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyDevice() throws Exception {
        WifiRouter result1 = new WifiRouter();
        result1.fillAllFields(wifiRouterWithConnections.getAllFieldsList());

        AssertUtilities.assertWifiRouter(wifiRouterWithConnections, result1);
    }

    @Test
    public void isPrimaryKey() throws Exception {
        assertFalse(defaultWifiRouter.isPrimaryKey());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        DevicePrimaryKey expDevicePK = new DevicePK(wifiRouter.getIn());

        Unique.PrimaryKey primaryKey = wifiRouter.getPrimaryKey();

        AssertUtilities.assertSomePK(expDevicePK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = defaultWifiRouter.getPrimaryKey();

        assertNull(primaryKey);
    }

}