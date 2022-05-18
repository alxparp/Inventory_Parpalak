package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.OpticFiber;
import com.netcracker.edu.inventory.model.impl.ThinCoaxial;
import com.netcracker.edu.inventory.model.impl.TwistedPair;
import com.netcracker.edu.inventory.model.impl.Wireless;
import com.netcracker.edu.inventory.service.ConnectionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 13.11.16.
 */
public class ConnectionServiceImplTest {

    private static final int PIPED_BUFER_SIZE = 1024*4;
    private static final String BINARY_FILE_NAME = "testOutConnection.bin";
    private static final String TEXT_FILE_NAME = "testOutConnection.txt";
    private static final String OBJECT_FILE_NAME = "testOutConnection.obj";

    ConnectionService connectionService;

    @Before
    public void setUp() throws Exception {
        connectionService = new ConnectionServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isValidConnectionForWriteToStream() throws Exception {
        Wireless wireless = CreateUtilities.createWireless();

        boolean result = connectionService.isValidConnectionForWriteToStream(wireless);

        assertTrue(result);
    }

    @Test
    public void isValidConnectionForWriteToStream_DeviceNull() throws Exception {
        boolean result = connectionService.isValidConnectionForWriteToStream(null);

        assertFalse(result);
    }

    @Test
    public void isValidConnectionForWriteToStream_ConnectionAttributeInvalid() throws Exception {
        Wireless wireless = CreateUtilities.createWireless();
        wireless.setProtocol("| WAP |");

        boolean result = connectionService.isValidConnectionForWriteToStream(wireless);

        assertFalse(result);
    }

    @Test
    public void writeReadConnection() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter, PIPED_BUFER_SIZE);
        TwistedPair twistedPair = CreateUtilities.createTwistedPairWithDevices();
        OpticFiber opticFiber = CreateUtilities.createOpticFiberWithDevices();
        Wireless wireless = CreateUtilities.createWirelessWithDevices();
        ThinCoaxial thinCoaxial = CreateUtilities.createThinCoaxialWithDevices();

        connectionService.writeConnection(twistedPair, pipedWriter);
        connectionService.writeConnection(opticFiber, pipedWriter);
        connectionService.writeConnection(wireless, pipedWriter);
        connectionService.writeConnection(thinCoaxial, pipedWriter);
        pipedWriter.close();

        twistedPair.setAPoint(twistedPair.getAPoint().getPrimaryKey());
        opticFiber.setBPoint(opticFiber.getBPoint().getPrimaryKey());
        wireless.setAPoint(wireless.getAPoint().getPrimaryKey());
        wireless.setBPoint(wireless.getBPoint(0).getPrimaryKey(), 0);
        wireless.setBPoint(wireless.getBPoint(2).getPrimaryKey(), 2);
        Set<Device> set = thinCoaxial.getAllDevices();
        for (Device device : set) {
            if (device != null) {
                Device pk = device.getPrimaryKey();
                thinCoaxial.removeDevice(device);
                thinCoaxial.addDevice(pk);
            }
        }

        Connection result1 = connectionService.readConnection(pipedReader);
        Connection result2 = connectionService.readConnection(pipedReader);
        Connection result3 = connectionService.readConnection(pipedReader);
        Connection result4 = connectionService.readConnection(pipedReader);
        pipedReader.close();

        assertEquals(TwistedPair.class, result1.getClass());
        AssertUtilities.assertTwistedPair(twistedPair, (TwistedPair) result1);
        assertEquals(OpticFiber.class, result2.getClass());
        AssertUtilities.assertOpticFiber(opticFiber, (OpticFiber) result2);
        assertEquals(Wireless.class, result3.getClass());
        AssertUtilities.assertWireless(wireless, (Wireless) result3);
        assertEquals(ThinCoaxial.class, result4.getClass());
        AssertUtilities.assertThinCoaxial(thinCoaxial, (ThinCoaxial) result4);
    }

    @Test
    public void writeConnectionNull() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);

        connectionService.writeConnection(null, pipedWriter);
        pipedWriter.close();

        assertEquals(-1, pipedReader.read());
        pipedReader.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeConnectionStreamNull() throws Exception {
        connectionService.writeConnection(CreateUtilities.createTwistedPair(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readConnectionStreamNull() throws Exception {
        connectionService.readConnection(null);
    }

    @Test
    public void outputInputConnection() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);
        TwistedPair twistedPair = CreateUtilities.createTwistedPairWithDevices();
        OpticFiber opticFiber = CreateUtilities.createOpticFiberWithDevices();
        Wireless wireless = CreateUtilities.createWirelessWithDevices();
        ThinCoaxial thinCoaxial = CreateUtilities.createThinCoaxialWithDevices();

        connectionService.outputConnection(twistedPair, pipedOutputStream);
        connectionService.outputConnection(opticFiber, pipedOutputStream);
        connectionService.outputConnection(wireless, pipedOutputStream);
        connectionService.outputConnection(thinCoaxial, pipedOutputStream);
        pipedOutputStream.close();

        twistedPair.setAPoint(twistedPair.getAPoint().getPrimaryKey());
        opticFiber.setBPoint(opticFiber.getBPoint().getPrimaryKey());
        wireless.setAPoint(wireless.getAPoint().getPrimaryKey());
        wireless.setBPoint(wireless.getBPoint(0).getPrimaryKey(), 0);
        wireless.setBPoint(wireless.getBPoint(2).getPrimaryKey(), 2);
        Set<Device> set = thinCoaxial.getAllDevices();
        for (Device device : set) {
            if (device != null) {
                Device pk = device.getPrimaryKey();
                thinCoaxial.removeDevice(device);
                thinCoaxial.addDevice(pk);
            }
        }

        Connection result1 = connectionService.inputConnection(pipedInputStream);
        Connection result2 = connectionService.inputConnection(pipedInputStream);
        Connection result3 = connectionService.inputConnection(pipedInputStream);
        Connection result4 = connectionService.inputConnection(pipedInputStream);
        pipedInputStream.close();

        assertEquals(TwistedPair.class, result1.getClass());
        AssertUtilities.assertTwistedPair(twistedPair, (TwistedPair) result1);
        assertEquals(OpticFiber.class, result2.getClass());
        AssertUtilities.assertOpticFiber(opticFiber, (OpticFiber) result2);
        assertEquals(Wireless.class, result3.getClass());
        AssertUtilities.assertWireless(wireless, (Wireless) result3);
        assertEquals(ThinCoaxial.class, result4.getClass());
        AssertUtilities.assertThinCoaxial(thinCoaxial, (ThinCoaxial) result4);
    }

    @Test
    public void outputConnectionNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        connectionService.outputConnection(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void outputConnectionStreamNull() throws Exception {
        connectionService.outputConnection(CreateUtilities.createTwistedPair(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputConnectionStreamNull() throws Exception {
        connectionService.inputConnection(null);
    }

    @Test
    public void outputToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(BINARY_FILE_NAME);
        TwistedPair twistedPair = CreateUtilities.createTwistedPairWithDevices();
        OpticFiber opticFiber = CreateUtilities.createOpticFiberWithDevices();
        Wireless wireless = CreateUtilities.createWirelessWithDevices();
        ThinCoaxial thinCoaxial = CreateUtilities.createThinCoaxialWithDevices();

        connectionService.outputConnection(twistedPair, fileOutputStream);
        connectionService.outputConnection(opticFiber, fileOutputStream);
        connectionService.outputConnection(wireless, fileOutputStream);
        connectionService.outputConnection(thinCoaxial, fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void writeToFile() throws Exception {
        FileWriter fileWriter = new FileWriter(TEXT_FILE_NAME);
        TwistedPair twistedPair = CreateUtilities.createTwistedPairWithDevices();
        OpticFiber opticFiber = CreateUtilities.createOpticFiberWithDevices();
        Wireless wireless = CreateUtilities.createWirelessWithDevices();
        ThinCoaxial thinCoaxial = CreateUtilities.createThinCoaxialWithDevices();

        connectionService.writeConnection(twistedPair, fileWriter);
        connectionService.writeConnection(opticFiber, fileWriter);
        connectionService.writeConnection(wireless, fileWriter);
        connectionService.writeConnection(thinCoaxial, fileWriter);
        fileWriter.close();
    }

    @Test
    public void serializeToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(OBJECT_FILE_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        TwistedPair twistedPair = CreateUtilities.createTwistedPairWithDevices();
        OpticFiber opticFiber = CreateUtilities.createOpticFiberWithDevices();
        Wireless wireless = CreateUtilities.createWirelessWithDevices();
        ThinCoaxial thinCoaxial = CreateUtilities.createThinCoaxialWithDevices();

        objectOutputStream.writeObject(twistedPair);
        objectOutputStream.writeObject(opticFiber);
        objectOutputStream.writeObject(wireless);
        objectOutputStream.writeObject(thinCoaxial);
        fileOutputStream.close();
    }

}