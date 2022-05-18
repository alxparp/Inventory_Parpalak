package com.netcracker.edu.inventory;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.location.impl.TrunkStubImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by oleksandr on 13.11.16.
 */
public class CreateUtilities {

    public static Battery createBattery() {
        Battery battery = new Battery();
        battery.setIn(4);
        battery.setManufacturer("");
        battery.setModel("qwerty");
        battery.setProductionDate(new Date());
        battery.setChargeVolume(5000);
        return battery;
    }

    public static Router createRouter() {
        Router router = new Router();
        router.setIn(2);
        router.setManufacturer("D-link");
        router.setDataRate(1000);
        return router;
    }

    public static Switch createSwitch() {
        return createSwitch(7);
    }

    public static Switch createSwitch(int in) {
        Switch aSwitch = new Switch(ConnectorType.RJ45);
        aSwitch.setIn(in);
        aSwitch.setModel("null");
        aSwitch.setManufacturer("Cisco");
        aSwitch.setDataRate(1000000);
        aSwitch.setNumberOfPorts(16);
        return aSwitch;
    }

    public static WifiRouter createWifiRouter() {
        return createWifiRouter(5);
    }

    public static WifiRouter createWifiRouter(int in) {
        WifiRouter wifiRouter = new WifiRouter("802.11g", ConnectorType.RJ45);
        wifiRouter.setIn(in);
        wifiRouter.setModel(null);
        wifiRouter.setManufacturer("D-link");
        wifiRouter.setSecurityProtocol(" ");
        return wifiRouter;
    }

    public static Switch createSwitchWithConnections() {
        Switch aSwitch = createSwitch();
        aSwitch.setPortConnection(createTwistedPair(), 4);
        return aSwitch;
    }

    public static WifiRouter createWifiRouterWithConnections() {
        WifiRouter wifiRouter = createWifiRouter();
        wifiRouter.setWireConnection(createOpticFiber());
        wifiRouter.setWirelessConnection(createWireless());
        return wifiRouter;
    }

    public static TwistedPair createTwistedPair() {
        return createTwistedPair(4);
    }

    public static TwistedPair createTwistedPair(int serialNumber) {
        TwistedPair twistedPair = new TwistedPair(TwistedPair.Type.UTP, 100);
        twistedPair.setTrunk(new TrunkStubImpl("ua.od.onpu.ics.607.east_wall-608.west_wall", "NC TC Internet to 608"));
        twistedPair.setSerialNumber(serialNumber);
        twistedPair.setStatus(Connection.READY);
        return twistedPair;
    }

    public static OpticFiber createOpticFiber() {
        OpticFiber opticFiber = new OpticFiber(OpticFiber.Mode.single, 1000);
        opticFiber.setStatus(Connection.ON_BUILD);
        opticFiber.setSerialNumber(12);
        opticFiber.setTrunk(new TrunkStubImpl("Test", "test"));
        return opticFiber;
    }

    public static Wireless createWireless() {
        return createWireless(3);
    }

    public static Wireless createWireless(int serialNumber) {
        Wireless wireless = new Wireless(3, "802.11g");
        wireless.setSerialNumber(serialNumber);
        wireless.setTrunk(new TrunkStubImpl("ua.od.onpu.ics.607.east_wall-area", "NC TC Wifi"));
        wireless.setStatus(Connection.USED);
        wireless.setProtocol("WPA");
        wireless.setVersion(2);
        return wireless;
    }

    public static ThinCoaxial createThinCoaxial() {
        return createThinCoaxial(5);
    }

    public static ThinCoaxial createThinCoaxial(int serialNumber) {
        ThinCoaxial thinCoaxial = new ThinCoaxial(5);
        thinCoaxial.setStatus(Connection.USED);
        thinCoaxial.setSerialNumber(serialNumber);
        thinCoaxial.setTrunk(new TrunkStubImpl("Test", "test"));
        return thinCoaxial;
    }

    public static TwistedPair createTwistedPairWithDevices() {
        TwistedPair twistedPair = createTwistedPair();
        twistedPair.setAPoint(createRouter());
        return twistedPair;
    }

    public static OpticFiber createOpticFiberWithDevices() {
        OpticFiber opticFiber = createOpticFiber();
        opticFiber.setBPoint(createWifiRouter());
        return opticFiber;
    }

    public static Wireless createWirelessWithDevices() {
        Wireless wireless = createWireless();
        wireless.setAPoint(createWifiRouter());
        wireless.setBPoint(createWifiRouter(), 0);
        wireless.setBPoint(createWifiRouter(), 2);
        return wireless;
    }

    public static ThinCoaxial createThinCoaxialWithDevices() {
        ThinCoaxial thinCoaxial = createThinCoaxial();
        thinCoaxial.addDevice(createRouter());
        thinCoaxial.addDevice(createSwitch());
        return thinCoaxial;
    }

    public static Collection<Unique> createSegment() {
        Wireless<WifiRouter, Device> wireless = createWireless(1);
        WifiRouter wifiRouter1 = createWifiRouter(1);
        ThinCoaxial<Device> thinCoaxial1 = createThinCoaxial(2);
        Switch switch1 = createSwitch(2);
        Switch switch2 = createSwitch(3);
        ThinCoaxial<Router> thinCoaxial2 = createThinCoaxial(3);
        TwistedPair<Device, WifiRouter> twistedPair = createTwistedPair(4);
        WifiRouter wifiRouter2 = createWifiRouter(4);
        DevicePK devicePK11 = new DevicePK(11);
        DevicePK devicePK12 = new DevicePK(12);
        DevicePK devicePK13 = new DevicePK(13);
        ConnectionPK connectionPK14 = new ConnectionPK(new TrunkStubImpl("1 - 2", "RedLine"), 14);
        ConnectionPK connectionPK15 = new ConnectionPK(new TrunkStubImpl("1 - 2", "GreenLine"), 15);
        ConnectionPK connectionPK16 = new ConnectionPK(new TrunkStubImpl("1 - 2", "YellowLine"), 16);

        wireless.setAPoint(wifiRouter1);
        wireless.setBPoint(devicePK11, 0);
        wireless.setBPoint(devicePK12, 2);

        wifiRouter1.setWirelessConnection(wireless);
        wifiRouter1.setWireConnection(thinCoaxial1);

        thinCoaxial1.addDevice(wifiRouter1);
        thinCoaxial1.addDevice(switch1);
        thinCoaxial1.addDevice(switch2);
        thinCoaxial1.addDevice(devicePK13);

        switch1.setPortConnection(thinCoaxial1, 0);
        switch1.setPortConnection(thinCoaxial2, 5);

        switch2.setPortConnection(thinCoaxial1, 1);
        switch2.setPortConnection(thinCoaxial2, 10);
        switch2.setPortConnection(connectionPK14, 4);
        switch2.setPortConnection(connectionPK15, 15);

        thinCoaxial2.addDevice(switch1);
        thinCoaxial2.addDevice(switch2);

        twistedPair.setBPoint(wifiRouter2);

        wifiRouter2.setWireConnection(twistedPair);
        wifiRouter2.setWirelessConnection(connectionPK16);

        Collection result = new ArrayList(8);
        result.add(wireless);
        result.add(thinCoaxial1);
        result.add(thinCoaxial2);
        result.add(twistedPair);
        result.add(switch1);
        result.add(switch2);
        result.add(wifiRouter1);
        result.add(wifiRouter2);
        return result;
    }

}
