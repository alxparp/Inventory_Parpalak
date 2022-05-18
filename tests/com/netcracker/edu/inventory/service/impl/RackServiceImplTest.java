package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.location.impl.LocationStubImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 23.10.16.
 */
public class RackServiceImplTest {

    private static final String BINARY_FILE_NAME = "testOutRack.bin";
    private static final String TEXT_FILE_NAME = "testOutRack.txt";
    private static final String OBJECT_FILE_NAME = "testOutRack.obj";

    RackService rackService;

    @Before
    public void setUp() throws Exception {
        rackService = new RackServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void writeReadRack() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        rackService.writeRack(emptyRack, pipedWriter);
        rackService.writeRack(partlyRack, pipedWriter);
        pipedWriter.close();

        for (int i = 0; i < aSwitch.getNumberOfPorts(); i++) {
            if (aSwitch.getPortConnection(i) != null) {
                ConnectionPrimaryKey pk = (ConnectionPrimaryKey) aSwitch.getPortConnection(i).getPrimaryKey();
                aSwitch.setPortConnection(pk, i);
            }
        }

        Rack result1 = rackService.readRack(pipedReader);
        Rack result2 = rackService.readRack(pipedReader);
        pipedReader.close();

        AssertUtilities.assertRack(emptyRack, result1);
        AssertUtilities.assertRack(partlyRack, result2);
    }

    @Test
    public void writeRackRackNull() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);

        rackService.writeRack(null, pipedWriter);
        pipedWriter.close();

        assertEquals(-1, pipedReader.read());
        pipedReader.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeRackStreamNull() throws Exception {
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        rackService.writeRack(emptyRack, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readRackNull() throws Exception {
        rackService.readRack(null);
    }

    @Test
    public void outputInputRack() throws Exception {

        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        rackService.outputRack(emptyRack, pipedOutputStream);
        rackService.outputRack(partlyRack, pipedOutputStream);
        pipedOutputStream.close();

        for (int i = 0; i < aSwitch.getNumberOfPorts(); i++) {
            if (aSwitch.getPortConnection(i) != null) {
                ConnectionPrimaryKey pk = (ConnectionPrimaryKey) aSwitch.getPortConnection(i).getPrimaryKey();
                aSwitch.setPortConnection(pk, i);
            }
        }

        Rack result1 = rackService.inputRack(pipedInputStream);
        Rack result2 = rackService.inputRack(pipedInputStream);
        pipedInputStream.close();

        AssertUtilities.assertRack(emptyRack, result1);
        AssertUtilities.assertRack(partlyRack, result2);
    }

    @Test
    public void outputRackRackNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        rackService.outputRack(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void outputRackStreamNull() throws Exception {
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        rackService.outputRack(emptyRack, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputRackNull() throws Exception {
        rackService.inputRack(null);
    }

    @Deprecated
    @Test
    public void serializeDeserializeRack() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, 4096);
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        rackService.serializeRack(emptyRack, pipedOutputStream);
        rackService.serializeRack(partlyRack, pipedOutputStream);
        pipedOutputStream.close();

        Rack result1 = rackService.deserializeRack(pipedInputStream);
        Rack result2 = rackService.deserializeRack(pipedInputStream);
        pipedInputStream.close();

        AssertUtilities.assertRack(emptyRack, result1);
        AssertUtilities.assertRack(partlyRack, result2);
    }

    @Deprecated
    @Test
    public void serializeRackRackNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        rackService.serializeRack(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Deprecated
    @Test(expected = IllegalArgumentException.class)
    public void serializeRackStreamNull() throws Exception {
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        rackService.serializeRack(emptyRack, null);
    }

    @Deprecated
    @Test(expected = IllegalArgumentException.class)
    public void deserializeRackNull() throws Exception {
        rackService.deserializeRack(null);
    }

    @Test
    public void outputToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(BINARY_FILE_NAME);
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        rackService.outputRack(emptyRack, fileOutputStream);
        rackService.outputRack(partlyRack, fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void writeToFile() throws Exception {
        FileWriter fileWriter = new FileWriter(TEXT_FILE_NAME);
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        rackService.writeRack(emptyRack, fileWriter);
        rackService.writeRack(partlyRack, fileWriter);
        fileWriter.close();
    }

    @Test
    public void serializeToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(OBJECT_FILE_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        objectOutputStream.writeObject(emptyRack);
        objectOutputStream.writeObject(partlyRack);
        fileOutputStream.close();
    }

}