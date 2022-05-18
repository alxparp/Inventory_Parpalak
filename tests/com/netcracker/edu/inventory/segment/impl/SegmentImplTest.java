package com.netcracker.edu.inventory.segment.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.segment.Segment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 27.11.16.
 */
public class SegmentImplTest {

    Segment segment;

    @Before
    public void setUp() throws Exception {
        segment = new SegmentImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void add() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        TwistedPair innerTwistedPair = (TwistedPair) aSwitch.getPortConnection(4);
        OpticFiber opticFiber = CreateUtilities.createOpticFiberWithDevices();
        WifiRouter innerwifiRouter = (WifiRouter) opticFiber.getBPoint();

        assertFalse(segment.contain(aSwitch.getPrimaryKey()));
        assertFalse(segment.contain(innerTwistedPair.getPrimaryKey()));
        assertFalse(segment.contain(opticFiber.getPrimaryKey()));
        assertFalse(segment.contain(innerwifiRouter.getPrimaryKey()));

        assertTrue(segment.add(aSwitch));
        assertTrue(segment.add(opticFiber));
        assertFalse(segment.add(aSwitch));
        assertFalse(segment.add(opticFiber));

        assertTrue(segment.contain(aSwitch.getPrimaryKey()));
        assertFalse(segment.contain(innerTwistedPair.getPrimaryKey()));
        assertTrue(segment.contain(opticFiber.getPrimaryKey()));
        assertFalse(segment.contain(innerwifiRouter.getPrimaryKey()));

        Switch returnedSwitch = (Switch) segment.get(aSwitch.getPrimaryKey());
        OpticFiber returnedOpticFiber = (OpticFiber) segment.get(opticFiber.getPrimaryKey());
        aSwitch.setPortConnection((Connection) aSwitch.getPortConnection(4).getPrimaryKey(), 4);
        opticFiber.setBPoint(opticFiber.getBPoint().getPrimaryKey());
        AssertUtilities.assertSwitch(aSwitch, returnedSwitch);
        AssertUtilities.assertOpticFiber(opticFiber, returnedOpticFiber);
    }

    @Test
    public void add_elementNull() throws Exception {
        assertFalse(segment.add(null));
    }

    @Test
    public void add_elementRack() throws Exception {
        assertFalse(segment.add(new RackArrayImpl(6, Device.class)));
    }

    @Test
    public void add_elementIsPK() throws Exception {
        assertFalse(segment.add(CreateUtilities.createBattery().getPrimaryKey()));
    }

    @Test
    public void add_elementHasNoPK() throws Exception {
        Battery battery = new Battery();
        assertFalse(segment.add(battery));
    }

    @Test
    public void set() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        TwistedPair innerTwistedPair = (TwistedPair) aSwitch.getPortConnection(4);
        OpticFiber opticFiber = CreateUtilities.createOpticFiberWithDevices();
        WifiRouter innerwifiRouter = (WifiRouter) opticFiber.getBPoint();
        assertTrue(segment.add(aSwitch));
        assertTrue(segment.add(opticFiber));
        assertTrue(segment.contain(aSwitch.getPrimaryKey()));
        assertFalse(segment.contain(innerTwistedPair.getPrimaryKey()));
        assertTrue(segment.contain(opticFiber.getPrimaryKey()));
        assertFalse(segment.contain(innerwifiRouter.getPrimaryKey()));

        Switch newSwitch = CreateUtilities.createSwitchWithConnections();
        newSwitch.setDataRate(100);
        OpticFiber newOpticFiber = CreateUtilities.createOpticFiberWithDevices();
        newOpticFiber.setLength(50);

        assertFalse(segment.set(innerTwistedPair));
        assertFalse(segment.set(innerwifiRouter));
        assertTrue(segment.set(newSwitch));
        assertTrue(segment.set(newOpticFiber));

        assertTrue(segment.contain(aSwitch.getPrimaryKey()));
        assertFalse(segment.contain(innerTwistedPair.getPrimaryKey()));
        assertTrue(segment.contain(opticFiber.getPrimaryKey()));
        assertFalse(segment.contain(innerwifiRouter.getPrimaryKey()));

        Switch returnedSwitch = (Switch) segment.get(aSwitch.getPrimaryKey());
        OpticFiber returnedOpticFiber = (OpticFiber) segment.get(opticFiber.getPrimaryKey());
        newSwitch.setPortConnection((Connection) newSwitch.getPortConnection(4).getPrimaryKey(), 4);
        newOpticFiber.setBPoint(newOpticFiber.getBPoint().getPrimaryKey());
        AssertUtilities.assertSwitch(newSwitch, returnedSwitch);
        AssertUtilities.assertOpticFiber(newOpticFiber, returnedOpticFiber);
    }

    @Test
    public void set_elementNull() throws Exception {
        assertFalse(segment.set(null));
    }

    @Test
    public void set_elementRack() throws Exception {
        assertFalse(segment.set(new RackArrayImpl(6, Device.class)));
    }

    @Test
    public void set_elementIsPK() throws Exception {
        segment.add(CreateUtilities.createBattery());
        assertFalse(segment.set(CreateUtilities.createBattery().getPrimaryKey()));
    }

    @Test
    public void set_elementHasNoPK() throws Exception {
        Battery battery = new Battery();
        assertFalse(segment.set(battery));
    }

    @Test
    public void put() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        TwistedPair innerTwistedPair = (TwistedPair) aSwitch.getPortConnection(4);
        OpticFiber opticFiber = CreateUtilities.createOpticFiberWithDevices();
        WifiRouter innerwifiRouter = (WifiRouter) opticFiber.getBPoint();
        assertTrue(segment.put(aSwitch));
        assertTrue(segment.put(opticFiber));
        assertTrue(segment.contain(aSwitch.getPrimaryKey()));
        assertFalse(segment.contain(innerTwistedPair.getPrimaryKey()));
        assertTrue(segment.contain(opticFiber.getPrimaryKey()));
        assertFalse(segment.contain(innerwifiRouter.getPrimaryKey()));

        Switch newSwitch = CreateUtilities.createSwitchWithConnections();
        newSwitch.setDataRate(100);
        OpticFiber newOpticFiber = CreateUtilities.createOpticFiberWithDevices();
        newOpticFiber.setLength(50);

        assertTrue(segment.put(newSwitch));
        assertTrue(segment.put(newOpticFiber));

        assertTrue(segment.contain(aSwitch.getPrimaryKey()));
        assertFalse(segment.contain(innerTwistedPair.getPrimaryKey()));
        assertTrue(segment.contain(opticFiber.getPrimaryKey()));
        assertFalse(segment.contain(innerwifiRouter.getPrimaryKey()));

        Switch returnedSwitch = (Switch) segment.get(aSwitch.getPrimaryKey());
        OpticFiber returnedOpticFiber = (OpticFiber) segment.get(opticFiber.getPrimaryKey());
        newSwitch.setPortConnection((Connection) newSwitch.getPortConnection(4).getPrimaryKey(), 4);
        newOpticFiber.setBPoint(newOpticFiber.getBPoint().getPrimaryKey());
        AssertUtilities.assertSwitch(newSwitch, returnedSwitch);
        AssertUtilities.assertOpticFiber(newOpticFiber, returnedOpticFiber);
    }

    @Test
    public void put_elementNull() throws Exception {
        assertFalse(segment.put(null));
    }

    @Test
    public void put_elementRack() throws Exception {
        assertFalse(segment.put(new RackArrayImpl(6, Device.class)));
    }

    @Test
    public void put_elementIsPK() throws Exception {
        assertFalse(segment.put(CreateUtilities.createBattery().getPrimaryKey()));
    }

    @Test
    public void put_elementHasNoPK() throws Exception {
        Battery battery = new Battery();
        assertFalse(segment.put(battery));
    }

    @Test
    public void contain_PKNull() throws Exception {
        assertFalse(segment.contain(null));
    }

    @Test
    public void contain_RackPK() throws Exception {
        assertFalse(segment.contain(new RackArrayImpl(6, Device.class).getPrimaryKey()));
    }

    @Test
    public void getGraph() throws Exception {
        Collection<Unique> baseGraph = CreateUtilities.createSegment();
        Collection expGraph = CreateUtilities.createSegment();
        Segment segment = new SegmentImpl();
        for (Unique element : baseGraph) {
            segment.add(element);
        }
        for (Unique element : baseGraph) {
            if (element instanceof Device) {
                ((Device) element).setModel("T");
                if (element instanceof Switch) {
                    ((Switch) element).setNumberOfPorts(0);
                }
            } else {
                ((Connection) element).setStatus(Connection.ON_DISASSEMBLING);
            }
        }

        Collection<Unique> result = segment.getGraph();

        AssertUtilities.assertGraph(expGraph, result);
    }

}