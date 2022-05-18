package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.DeviceService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by oleksandr on 19.10.16.
 */
public class DeviceServiceImplTest {

    private static final int PIPED_BUFER_SIZE = 1024*4;
    private static final String BINARY_FILE_NAME = "testOutDevice.bin";
    private static final String TEXT_FILE_NAME = "testOutDevice.txt";
    private static final String OBJECT_FILE_NAME = "testOutDevice.obj";

    DeviceService deviceService;

    @Before
    public void setUp() throws Exception {
        deviceService = new DeviceServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isCastableTo() throws Exception {
        Battery battery = new Battery();
        Switch aSwitch = new Switch();

        boolean result1 = new DeviceServiceImpl().isCastableTo(battery, Battery.class);
        boolean result2 = new DeviceServiceImpl().isCastableTo(battery, Router.class);
        boolean result3 = new DeviceServiceImpl().isCastableTo(battery, Device.class);
        boolean result4 = new DeviceServiceImpl().isCastableTo(aSwitch, Router.class);
        boolean result5 = new DeviceServiceImpl().isCastableTo(null, Device.class);
        boolean result6 = new DeviceServiceImpl().isCastableTo(battery, null);

        assertTrue(result1);
        assertFalse(result2);
        assertTrue(result3);
        assertTrue(result4);
        assertFalse(result5);
        assertFalse(result6);
    }

    @Test
    public void isValidDeviceForInsertToRack() throws Exception {
        Battery battery = new Battery();
        battery.setIn(5);

        boolean result = deviceService.isValidDeviceForInsertToRack(battery);

        assertTrue(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_DeviceNull() throws Exception {
        boolean result = deviceService.isValidDeviceForInsertToRack(null);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_IN0() throws Exception {
        Battery battery = new Battery();

        boolean result = deviceService.isValidDeviceForInsertToRack(battery);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_TypeNull() throws Exception {
        Device deviceNoType = new Battery() {
            @Override
            public String getType() {
                return null;
            }
        };
        deviceNoType.setIn(5);

        boolean result = deviceService.isValidDeviceForInsertToRack(deviceNoType);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForWriteToStream() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("none");

        boolean result = deviceService.isValidDeviceForInsertToRack(wifiRouter);

        assertTrue(result);
    }

    @Test
    public void isValidDeviceForWriteToStream_DeviceNull() throws Exception {
        boolean result = deviceService.isValidDeviceForWriteToStream(null);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForWriteToStream_DeviceAttributeInvalid() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("Super|Puper");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("none");

        boolean result = deviceService.isValidDeviceForWriteToStream(wifiRouter);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForWriteToStream_ChildAttributeInvalid() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("Super&Puper");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("no|ne");

        boolean result = deviceService.isValidDeviceForWriteToStream(wifiRouter);

        assertFalse(result);
    }

    @Test
    public void writeReadDevice() throws Exception {

        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter, PIPED_BUFER_SIZE);
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouterWithConnections();
        WifiRouter wifiRouter2 = CreateUtilities.createWifiRouter();
        wifiRouter2.setSecurityProtocol("   ");

        deviceService.writeDevice(battery, pipedWriter);
        deviceService.writeDevice(router, pipedWriter);
        deviceService.writeDevice(aSwitch, pipedWriter);
        deviceService.writeDevice(wifiRouter, pipedWriter);
        deviceService.writeDevice(wifiRouter2, pipedWriter);
        pipedWriter.close();

        for (int i = 0; i < aSwitch.getNumberOfPorts(); i++) {
            if (aSwitch.getPortConnection(i) != null) {
                ConnectionPrimaryKey pk = (ConnectionPrimaryKey) aSwitch.getPortConnection(i).getPrimaryKey();
                aSwitch.setPortConnection(pk, i);
            }
        }
        wifiRouter.setWirelessConnection((Connection) wifiRouter.getWirelessConnection().getPrimaryKey());
        wifiRouter.setWireConnection((Connection) wifiRouter.getWireConnection().getPrimaryKey());

        Device result1 = deviceService.readDevice(pipedReader);
        Device result2 = deviceService.readDevice(pipedReader);
        Device result3 = deviceService.readDevice(pipedReader);
        Device result4 = deviceService.readDevice(pipedReader);
        Device result5 = deviceService.readDevice(pipedReader);
        pipedReader.close();

        assertEquals(Battery.class, result1.getClass());
        AssertUtilities.assertBattery(battery, (Battery) result1);
        assertEquals(Router.class, result2.getClass());
        AssertUtilities.assertRouter(router, (Router) result2);
        assertEquals(Switch.class, result3.getClass());
        AssertUtilities.assertSwitch(aSwitch, (Switch) result3);
        assertEquals(WifiRouter.class, result4.getClass());
        AssertUtilities.assertWifiRouter(wifiRouter, (WifiRouter) result4);
        assertEquals(WifiRouter.class, result5.getClass());
        AssertUtilities.assertWifiRouter(wifiRouter2, (WifiRouter) result5);
    }

    @Test
    public void writeDeviceNull() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);

        deviceService.writeDevice(null, pipedWriter);
        pipedWriter.close();

        assertEquals(-1, pipedReader.read());
        pipedReader.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeDeviceStreamNull() throws Exception {
        deviceService.writeDevice(CreateUtilities.createSwitch(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readDeviceStreamNull() throws Exception {
        deviceService.readDevice(null);
    }

    @Test
    public void outputInputDevice() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouterWithConnections();

        deviceService.outputDevice(battery, pipedOutputStream);
        deviceService.outputDevice(router, pipedOutputStream);
        deviceService.outputDevice(aSwitch, pipedOutputStream);
        deviceService.outputDevice(wifiRouter, pipedOutputStream);
        pipedOutputStream.close();

        for (int i = 0; i < aSwitch.getNumberOfPorts(); i++) {
            if (aSwitch.getPortConnection(i) != null) {
                ConnectionPrimaryKey pk = (ConnectionPrimaryKey) aSwitch.getPortConnection(i).getPrimaryKey();
                aSwitch.setPortConnection(pk, i);
            }
        }
        wifiRouter.setWirelessConnection((Connection) wifiRouter.getWirelessConnection().getPrimaryKey());
        wifiRouter.setWireConnection((Connection) wifiRouter.getWireConnection().getPrimaryKey());

        Device result1 = deviceService.inputDevice(pipedInputStream);
        Device result2 = deviceService.inputDevice(pipedInputStream);
        Device result3 = deviceService.inputDevice(pipedInputStream);
        Device result4 = deviceService.inputDevice(pipedInputStream);
        pipedInputStream.close();

        assertEquals(Battery.class, result1.getClass());
        AssertUtilities.assertBattery(battery, (Battery) result1);
        assertEquals(Router.class, result2.getClass());
        AssertUtilities.assertRouter(router, (Router) result2);
        assertEquals(Switch.class, result3.getClass());
        AssertUtilities.assertSwitch(aSwitch, (Switch) result3);
        assertEquals(WifiRouter.class, result4.getClass());
        AssertUtilities.assertWifiRouter(wifiRouter, (WifiRouter) result4);
    }

    @Test
    public void outputDeviceNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        deviceService.outputDevice(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void outputDeviceStreamNull() throws Exception {
        deviceService.outputDevice(CreateUtilities.createSwitch(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputDeviceStreamNull() throws Exception {
        deviceService.inputDevice(null);
    }

    @Deprecated
    @Test
    public void serializeDeserializeDevice() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitch();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();

        deviceService.serializeDevice(battery, pipedOutputStream);
        deviceService.serializeDevice(router, pipedOutputStream);
        deviceService.serializeDevice(aSwitch, pipedOutputStream);
        deviceService.serializeDevice(wifiRouter, pipedOutputStream);
        pipedOutputStream.close();

        Device result1 = deviceService.deserializeDevice(pipedInputStream);
        Device result2 = deviceService.deserializeDevice(pipedInputStream);
        Device result3 = deviceService.deserializeDevice(pipedInputStream);
        Device result4 = deviceService.deserializeDevice(pipedInputStream);
        pipedInputStream.close();

        assertEquals(Battery.class, result1.getClass());
        AssertUtilities.assertBattery(battery, (Battery) result1);
        assertEquals(Router.class, result2.getClass());
        AssertUtilities.assertRouter(router, (Router) result2);
        assertEquals(Switch.class, result3.getClass());
        AssertUtilities.assertSwitch(aSwitch, (Switch) result3);
        assertEquals(WifiRouter.class, result4.getClass());
        AssertUtilities.assertWifiRouter(wifiRouter, (WifiRouter) result4);
    }

    @Deprecated
    @Test
    public void serializeDeviceNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        deviceService.serializeDevice(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Deprecated
    @Test(expected = IllegalArgumentException.class)
    public void serializeDeviceStreamNull() throws Exception {
        deviceService.serializeDevice(CreateUtilities.createSwitch(), null);
    }

    @Deprecated
    @Test(expected = IllegalArgumentException.class)
    public void deserializeDeviceStreamNull() throws Exception {
        deviceService.deserializeDevice(null);
    }

    @Test
    public void outputToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(BINARY_FILE_NAME);
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouterWithConnections();

        deviceService.outputDevice(battery, fileOutputStream);
        deviceService.outputDevice(router, fileOutputStream);
        deviceService.outputDevice(aSwitch, fileOutputStream);
        deviceService.outputDevice(wifiRouter, fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void writeToFile() throws Exception {
        FileWriter fileWriter = new FileWriter(TEXT_FILE_NAME);
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouterWithConnections();

        deviceService.writeDevice(battery, fileWriter);
        deviceService.writeDevice(router, fileWriter);
        deviceService.writeDevice(aSwitch, fileWriter);
        deviceService.writeDevice(wifiRouter, fileWriter);
        fileWriter.close();
    }

    @Test
    public void serializeToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(OBJECT_FILE_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitch();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();

        objectOutputStream.writeObject(battery);
        objectOutputStream.writeObject(router);
        objectOutputStream.writeObject(aSwitch);
        objectOutputStream.writeObject(wifiRouter);
        objectOutputStream.close();
    }

}