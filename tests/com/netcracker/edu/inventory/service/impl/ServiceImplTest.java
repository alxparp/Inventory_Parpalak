package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.inventory.service.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 05.10.16.
 */
public class ServiceImplTest {

    Service service;

    @Before
    public void before() throws Exception {
        service = new ServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getDeviceService() throws Exception {
        DeviceService deviceService = service.getDeviceService();

        assertNotNull(deviceService);
    }

    @Test
    public void getRackService() throws Exception {
        RackService rackService = service.getRackService();

        assertNotNull(rackService);
    }

    @Test
    public void sortByIN() throws Exception {
        Battery b0 = new Battery();
        Battery b1 = new Battery();
        b1.setIn(1);
        Battery b2 = new Battery();
        b2.setIn(2);
        Battery b3 = new Battery();
        b3.setIn(3);
        Device[] devices = new Device[] {null, b2, b1, b0, null, b3, b2, b0, null};
        Device[] expResult = new Device[] {b1, b2, b2, b3, b0, b0, null, null, null};

        service.sortByIN(devices);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType() throws Exception {
        Battery b = new Battery();
        Router r = new Router();
        Switch s = new Switch();
        WifiRouter wr = new WifiRouter();
        Device tn = new Battery() {
            @Override
            public String getType() {
                return null;
            }
        };
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        service.filtrateByType(devices, Router.class.getSimpleName());

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType_TypeNull() throws Exception {
        Battery b = new Battery();
        Router r = new Router();
        Switch s = new Switch();
        WifiRouter wr = new WifiRouter();
        Device tn = new Battery() {
            @Override
            public String getType() {
                return null;
            }
        };
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        service.filtrateByType(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void getIndependentCopy() throws Exception {
        WifiRouter device = CreateUtilities.createWifiRouterWithConnections();
        Rack rack = new RackArrayImpl(5, Router.class);
        Wireless connection = CreateUtilities.createWirelessWithDevices();

        WifiRouter expDevice = CreateUtilities.createWifiRouterWithConnections();
        Rack expRack = new RackArrayImpl(5, Router.class);
        Wireless expConnection = CreateUtilities.createWirelessWithDevices();

        Router routerRack = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouterWithConnections();
        rack.insertDevToSlot(routerRack, 0);
        rack.insertDevToSlot(aSwitch, 2);
        rack.insertDevToSlot(wifiRouter, 4);
        expRack.insertDevToSlot((Device) service.getIndependentCopy(routerRack), 0);
        expRack.insertDevToSlot((Device) service.getIndependentCopy(aSwitch), 2);
        expRack.insertDevToSlot((Device) service.getIndependentCopy(wifiRouter), 4);
        expDevice.setWireConnection((Connection) device.getWireConnection().getPrimaryKey());
        expDevice.setWirelessConnection((Connection) device.getWirelessConnection().getPrimaryKey());
        expConnection.setAPoint(connection.getAPoint().getPrimaryKey());
        expConnection.setBPoint(connection.getBPoint(0).getPrimaryKey(), 0);
        expConnection.setBPoint(connection.getBPoint(2).getPrimaryKey(), 2);

        Device result1 = (Device) service.getIndependentCopy(device);
        Rack result2 = (Rack) service.getIndependentCopy(rack);
        Connection result3 = (Connection) service.getIndependentCopy(connection);

        AssertUtilities.assertSomeDevice(expDevice, result1);
        AssertUtilities.assertRack(expRack, result2);
        AssertUtilities.assertSomeConnection(expConnection, result3);
    }

}