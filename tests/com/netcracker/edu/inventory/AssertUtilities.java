package com.netcracker.edu.inventory;

import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.Service;
import com.netcracker.edu.inventory.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 13.11.16.
 */
public class AssertUtilities {

    private static final Service service = new ServiceImpl();

    public static void assertSomeDevice(Device expDevice, Device device) throws Exception {
        assertEquals(expDevice.getClass(), device.getClass());
        if (expDevice.getClass() == Battery.class) {
            assertBattery((Battery) expDevice, (Battery) device);
            return;
        }
        if (expDevice.getClass() == Router.class) {
            assertRouter((Router) expDevice, (Router) device);
            return;
        }
        if (expDevice.getClass() == Switch.class) {
            assertSwitch((Switch) expDevice, (Switch) device);
            return;
        }
        if (expDevice.getClass() == WifiRouter.class) {
            assertWifiRouter((WifiRouter) expDevice, (WifiRouter) device);
            return;
        }
    }

    public static void assertDevice(Device expDevice, Device device) throws Exception {
        assertEquals(expDevice.getIn(), device.getIn());
        assertEquals(expDevice.getType(), device.getType());
        assertEquals(expDevice.getModel(), device.getModel());
        assertEquals(expDevice.getManufacturer(), device.getManufacturer());
        assertEquals(expDevice.getProductionDate(), device.getProductionDate());
    }

    public static void assertBattery(Battery expBattery, Battery battery) throws Exception {
        assertDevice(expBattery, battery);
        assertEquals(expBattery.getChargeVolume(), battery.getChargeVolume());
    }

    public static void assertRouter(Router expRouter, Router router) throws Exception {
        assertDevice(expRouter, router);
        assertEquals(expRouter.getDataRate(), router.getDataRate());
    }

    public static void assertSwitch(Switch expSwitch, Switch aSwitch) throws Exception {
        assertRouter(expSwitch, aSwitch);
        assertEquals(expSwitch.getNumberOfPorts(), aSwitch.getNumberOfPorts());
        assertEquals(expSwitch.getPortsType(), aSwitch.getPortsType());
        assertConnectionList(expSwitch.getAllPortConnections(), aSwitch.getAllPortConnections());
    }

    public static void assertWifiRouter(WifiRouter expWifiRouter, WifiRouter wifiRouter) throws Exception {
        assertRouter(expWifiRouter, wifiRouter);
        assertEquals(expWifiRouter.getTechnologyVersion(), wifiRouter.getTechnologyVersion());
        assertEquals(expWifiRouter.getSecurityProtocol(), wifiRouter.getSecurityProtocol());
        assertEquals(expWifiRouter.getWirelessConnection(), wifiRouter.getWirelessConnection());
        assertEquals(expWifiRouter.getWirePortType(), wifiRouter.getWirePortType());
        assertEquals(expWifiRouter.getWireConnection(), wifiRouter.getWireConnection());
    }

    public static void assertRack(Rack expRack, Rack rack) throws Exception {
        if ((expRack.getLocation() == null) || (rack.getLocation() == null)) {
            assertEquals(expRack.getLocation(), rack.getLocation());
        } else {
            assertEquals(expRack.getLocation().getFullName(), rack.getLocation().getFullName());
            assertEquals(expRack.getLocation().getShortName(), rack.getLocation().getShortName());
        }
        assertEquals(expRack.getSize(), rack.getSize());
        assertEquals(expRack.getTypeOfDevices(), rack.getTypeOfDevices());
        for (int i = 0; i < expRack.getSize(); i++) {
            Device expDevice = expRack.getDevAtSlot(i);
            Device device = rack.getDevAtSlot(i);
            if (expDevice == null) {
                assertNull(device);
            } else {
                assertSomeDevice(expDevice, device);
            }
        }
    }

    public static void assertSomeConnection(Connection expConnection, Connection connection) throws Exception {
        assertEquals(expConnection.getClass(), connection.getClass());
        if (expConnection.getClass() == TwistedPair.class) {
            assertTwistedPair((TwistedPair) expConnection, (TwistedPair) connection);
            return;
        }
        if (expConnection.getClass() == OpticFiber.class) {
            assertOpticFiber((OpticFiber) expConnection, (OpticFiber) connection);
            return;
        }
        if (expConnection.getClass() == Wireless.class) {
            assertWireless((Wireless) expConnection, (Wireless) connection);
            return;
        }
        if (expConnection.getClass() == ThinCoaxial.class) {
            assertThinCoaxial((ThinCoaxial) expConnection, (ThinCoaxial) connection);
            return;
        }
    }

    public static void assertConnection(Connection expConnection, Connection connection) throws Exception {
        assertEquals(expConnection.getStatus(), connection.getStatus());
    }

    public static void assertOneToOneConnection(OneToOneConnection expOneToOneConnection, OneToOneConnection oneToOneConnection) throws Exception {
        assertConnection(expOneToOneConnection, oneToOneConnection);
        assertEquals(expOneToOneConnection.getAPointConnectorType(), oneToOneConnection.getAPointConnectorType());
        assertEquals(expOneToOneConnection.getBPointConnectorType(), oneToOneConnection.getBPointConnectorType());
        assertEquals(expOneToOneConnection.getAPoint(), oneToOneConnection.getAPoint());
        assertEquals(expOneToOneConnection.getBPoint(), oneToOneConnection.getBPoint());
    }

    public static void assertOneToManyConnection(OneToManyConnection expOneToManyConnection, OneToManyConnection oneToManyConnection) throws Exception {
        assertConnection(expOneToManyConnection, oneToManyConnection);
        assertEquals(expOneToManyConnection.getAPointConnectorType(), oneToManyConnection.getAPointConnectorType());
        assertEquals(expOneToManyConnection.getBPointConnectorType(), oneToManyConnection.getBPointConnectorType());
        assertEquals(expOneToManyConnection.getAPoint(), oneToManyConnection.getAPoint());
        assertEquals(expOneToManyConnection.getBCapacity(), oneToManyConnection.getBCapacity());
        assertEquals(expOneToManyConnection.getBPoints(), oneToManyConnection.getBPoints());
    }

    public static void assertAllToAllConnection(AllToAllConnection expAllToAllConnection, AllToAllConnection allToAllConnection) throws Exception {
        assertConnection(expAllToAllConnection, allToAllConnection);
        assertEquals(expAllToAllConnection.getConnectorType(), allToAllConnection.getConnectorType());
        assertEquals(expAllToAllConnection.getCurSize(), allToAllConnection.getCurSize());
        assertEquals(expAllToAllConnection.getMaxSize(), allToAllConnection.getMaxSize());
        assertDeviceSet(expAllToAllConnection.getAllDevices(), (allToAllConnection.getAllDevices()));
    }

    public static void assertTwistedPair(TwistedPair expTwistedPair, TwistedPair twistedPair) throws Exception {
        assertOneToOneConnection(expTwistedPair, twistedPair);
        assertEquals(expTwistedPair.getType(), twistedPair.getType());
        assertEquals(expTwistedPair.getLength(), twistedPair.getLength());
    }

    public static void assertOpticFiber(OpticFiber expOpticFiber, OpticFiber opticFiber) throws Exception {
        assertOneToOneConnection(expOpticFiber, opticFiber);
        assertEquals(expOpticFiber.getMode(), opticFiber.getMode());
        assertEquals(expOpticFiber.getLength(), opticFiber.getLength());
    }

    public static void assertWireless(Wireless expWireless, Wireless wireless) throws Exception {
        assertOneToManyConnection(expWireless, wireless);
        assertEquals(expWireless.getTechnology(), wireless.getTechnology());
        assertEquals(expWireless.getProtocol(), wireless.getProtocol());
        assertEquals(expWireless.getVersion(), wireless.getVersion());
    }

    public static void assertThinCoaxial(ThinCoaxial expThinCoaxial, ThinCoaxial thinCoaxial) throws Exception {
        assertAllToAllConnection(expThinCoaxial, thinCoaxial);
    }

    public static void assertDeviceArray(Device[] expArray, Device[] array) throws Exception {
        assertEquals(expArray.length, array.length);
        for (int i = 0; i < expArray.length; i++) {
            if (expArray[i] == null) {
                assertNull(array[i]);
            } else if (expArray[i].isPrimaryKey()) {
                assertTrue(expArray[i].equals(array[i]));
            } else {
                assertSomeDevice(expArray[i], array[i]);
            }
        }
    }

    public static void assertDeviceSet(Set<Device> expSet, Set<Device> set) throws Exception {
        Device[] expDevices = new Device[0];
        expDevices = expSet.toArray(expDevices);
        Device[] devices = new Device[0];
        devices = set.toArray(devices);
        service.sortByIN(expDevices);
        service.sortByIN(devices);
        assertDeviceArray(expDevices, devices);
    }

    public static void assertDeviceList(List<Device> expList, List<Device> list) throws Exception {
        Device[] expDevices = new Device[0];
        expDevices = expList.toArray(expDevices);
        Device[] devices = new Device[0];
        devices = list.toArray(devices);
        assertDeviceArray(expDevices, devices);
    }

    public static void assertConnectionArray(Connection[] expArray, Connection[] array) throws Exception {
        assertEquals(expArray.length, array.length);
        for (int i = 0; i < expArray.length; i++) {
            if (expArray[i] == null) {
                assertNull(array[i]);
            } else if (expArray[i].isPrimaryKey()) {
                assertTrue(expArray[i].equals(array[i]));
            } else {
                assertSomeConnection(expArray[i], array[i]);
            }
        }
    }

    public static void assertConnectionList(List<Connection> expList, List<Connection> list) throws Exception {
        Connection[] expConnections = new Connection[0];
        expConnections = expList.toArray(expConnections);
        Connection[] connections = new Connection[0];
        connections = list.toArray(connections);
        assertConnectionArray(expConnections, connections);
    }

    public static void assertSomePK(Unique.PrimaryKey expPK, Unique.PrimaryKey pk) throws Exception {
        assertEquals(expPK.getClass(), pk.getClass());
        if (expPK.getClass() == DevicePK.class) {
            assertDevicePK((DevicePrimaryKey) expPK, (DevicePrimaryKey) pk);
            return;
        }
        if (expPK.getClass() == ConnectionPK.class) {
            assertConnectionPK((ConnectionPrimaryKey) expPK, (ConnectionPrimaryKey) pk);
            return;
        }
        if (expPK.getClass() == RackPK.class) {
            assertRackPK((RackPrimaryKey) expPK, (RackPrimaryKey) pk);
            return;
        }
    }

    public static void assertDevicePK(DevicePrimaryKey expDevicePK, DevicePrimaryKey devicePK) throws Exception {
        assertEquals(expDevicePK.getIn(), devicePK.getIn());
        assertTrue(expDevicePK.equals(devicePK));
    }

    public static void assertConnectionPK(ConnectionPrimaryKey expConnectionPK, ConnectionPrimaryKey connectionPK) throws Exception {
        assertEquals(expConnectionPK.getSerialNumber(), connectionPK.getSerialNumber());
        assertTrue(expConnectionPK.getTrunk().equals(connectionPK.getTrunk()));
        assertTrue(expConnectionPK.equals(connectionPK));
    }

    public static void assertRackPK(RackPrimaryKey expRackPK, RackPrimaryKey rackPK) throws Exception {
        assertTrue(expRackPK.getLocation().equals(rackPK.getLocation()));
        assertTrue(expRackPK.equals(rackPK));
    }

    public static void assertGraph(Collection<Unique> expGraph, Collection<Unique> graph) throws Exception {
        assertEquals(expGraph.size(), graph.size());
        for (Unique expElement : expGraph) {
            Unique equal = null;
            for (Unique element : graph) {
                if (expElement.getPrimaryKey().equals(element.getPrimaryKey())) {
                    equal = element;
                    break;
                }
            }
            assertNotNull(equal);
            if (equal instanceof Device) {
                if (equal instanceof WifiRouter) {
                    WifiRouter wifiRouter = (WifiRouter) equal;
                    if (wifiRouter.getIn() == 1) {
                        assertEquals(wifiRouter.getWireConnection().getSerialNumber(), 2);
                        assertEquals(wifiRouter.getWirelessConnection().getSerialNumber(), 1);
                    } else if (wifiRouter.getIn() == 4) {
                        assertEquals(wifiRouter.getWireConnection().getSerialNumber(), 4);
                        assertEquals(wifiRouter.getWirelessConnection().getSerialNumber(), 16);
                    } else assertTrue(false);
                } else if (equal instanceof Switch) {
                    Switch aSwitch = (Switch) equal;
                    if (aSwitch.getIn() == 2) {
                        assertEquals(aSwitch.getPortConnection(0).getSerialNumber(), 2);
                        assertEquals(aSwitch.getPortConnection(5).getSerialNumber(), 3);
                    } else if (aSwitch.getIn() == 3) {
                        assertEquals(aSwitch.getPortConnection(1).getSerialNumber(), 2);
                        assertEquals(aSwitch.getPortConnection(10).getSerialNumber(), 3);
                        assertEquals(aSwitch.getPortConnection(4).getSerialNumber(), 14);
                        assertEquals(aSwitch.getPortConnection(15).getSerialNumber(), 15);
                    } else assertTrue(false);
                } else assertTrue(false);
                assertSomeDevice((Device) service.getIndependentCopy(expElement), (Device) service.getIndependentCopy(equal));
            } else if (equal instanceof Connection) {
                if (equal instanceof Wireless) {
                    Wireless wireless = (Wireless) equal;
                    assertEquals(wireless.getAPoint().getIn(), 1);
                    assertEquals(wireless.getBPoint(0).getIn(), 11);
                    assertEquals(wireless.getBPoint(2).getIn(), 12);
                } else if (equal instanceof ThinCoaxial) {
                    ThinCoaxial thinCoaxial = (ThinCoaxial) equal;
                    Set<Device> set = thinCoaxial.getAllDevices();
                    int[] ins = new int[set.size()];
                    int count = 0;
                    for (Device device : set) {
                        ins[count++] = device.getIn();
                    }
                    Arrays.sort(ins);
                    if (thinCoaxial.getSerialNumber() == 2) {
                        assertEquals(set.size(), 4);
                        assertEquals(ins[0], 1);
                        assertEquals(ins[1], 2);
                        assertEquals(ins[2], 3);
                        assertEquals(ins[3], 13);
                    } else if (thinCoaxial.getSerialNumber() == 3) {
                        assertEquals(set.size(), 2);
                        assertEquals(ins[0], 2);
                        assertEquals(ins[1], 3);
                    } else assertTrue(false);
                } else if (equal instanceof TwistedPair) {
                    TwistedPair twistedPair = (TwistedPair) equal;
                    assertEquals(twistedPair.getBPoint().getIn(), 4);
                } else assertTrue(false);
                assertSomeConnection((Connection) service.getIndependentCopy(expElement), (Connection) service.getIndependentCopy(equal));
            }
        }
    }

}
