package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.ConcurrentIOService;
import com.netcracker.edu.location.impl.LocationStubImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * Created by makovetskyi on 01.12.16.
 */
public class ConcurrentIOServiceImplTest {

    private static final int PIPED_BUFER_SIZE = 1024*4;
    private static final String TEXT_FILE_NAME = "concurrentOut.txt";

    protected ConcurrentIOService service = new ConcurrentIOServiceImpl();
    protected PipedOutputStream pipedOutputStream;
    protected PipedInputStream pipedInputStream;
    protected PipedWriter pipedWriter;
    protected PipedReader pipedReader;
    protected Battery battery;
    protected TwistedPair twistedPair;
    protected WifiRouter wifiRouter;
    protected ThinCoaxial thinCoaxial;
    protected Rack emptyRack;
    protected Rack partlyRack;
    protected Collection<FeelableEntity> elements;
    protected Collection<Rack> racks;

    @Before
    public void setUp() throws Exception {
        pipedOutputStream = new PipedOutputStream();
        pipedInputStream = new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);
        pipedWriter = new PipedWriter();
        pipedReader = new PipedReader(pipedWriter, PIPED_BUFER_SIZE);

        elements =  new ArrayList<FeelableEntity>(5);
        elements.add(battery = CreateUtilities.createBattery());
        elements.add(twistedPair = CreateUtilities.createTwistedPair());
        elements.add(null);
        elements.add(wifiRouter = CreateUtilities.createWifiRouter());
        elements.add(thinCoaxial = CreateUtilities.createThinCoaxial());

        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        emptyRack = new RackArrayImpl(0, Device.class);
        partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);
        racks = new ArrayList<Rack>(3);
        racks.add(emptyRack);
        racks.add(null);
        racks.add(partlyRack);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void parallelOutputInputElements() throws Exception {
        Future<Collection<FeelableEntity>> future = service.parallelOutputElements(elements, pipedOutputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        pipedOutputStream.close();

        future = service.parallelInputElements(3, pipedInputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        FeelableEntity[] result1 = future.get().toArray(new FeelableEntity[0]);
        future = service.parallelInputElements(3, pipedInputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        FeelableEntity[] result2 = future.get().toArray(new FeelableEntity[0]);
        pipedInputStream.close();

        assertEquals(result1.length, 3);
        for (FeelableEntity entity : result1) {
            if (battery.getClass() == entity.getClass()) {
                AssertUtilities.assertBattery(battery, (Battery) entity);
            } else if (twistedPair.getClass() == entity.getClass()) {
                AssertUtilities.assertTwistedPair(twistedPair, (TwistedPair) entity);
            } else if (wifiRouter.getClass() == entity.getClass()) {
                AssertUtilities.assertWifiRouter(wifiRouter, (WifiRouter) entity);
            } else assert false;
        }
        assertEquals(result2.length, 1);
        FeelableEntity entity = result2[0];
        if (thinCoaxial.getClass() == entity.getClass()) {
            AssertUtilities.assertThinCoaxial(thinCoaxial, (ThinCoaxial) entity);
        } else assert false;
    }

    @Test
    public void parallelWriteReadElements() throws Exception {
        Future<Collection<FeelableEntity>> future = service.parallelWriteElements(elements, pipedWriter);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        pipedWriter.close();

        future = service.parallelReadElements(3, pipedReader);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        FeelableEntity[] result1 = future.get().toArray(new FeelableEntity[0]);
        future = service.parallelReadElements(3, pipedReader);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        FeelableEntity[] result2 = future.get().toArray(new FeelableEntity[0]);
        pipedReader.close();

        assertEquals(result1.length, 3);
        for (FeelableEntity entity : result1) {
            if (battery.getClass() == entity.getClass()) {
                AssertUtilities.assertBattery(battery, (Battery) entity);
            } else if (twistedPair.getClass() == entity.getClass()) {
                AssertUtilities.assertTwistedPair(twistedPair, (TwistedPair) entity);
            } else if (wifiRouter.getClass() == entity.getClass()) {
                AssertUtilities.assertWifiRouter(wifiRouter, (WifiRouter) entity);
            } else assert false;
        }
        assertEquals(result2.length, 1);
        FeelableEntity entity = result2[0];
        if (thinCoaxial.getClass() == entity.getClass()) {
            AssertUtilities.assertThinCoaxial(thinCoaxial, (ThinCoaxial) entity);
        } else assert false;
    }

    @Test
    public void parallelOutputInputRacks() throws Exception {
        Future<Collection<Rack>> future = service.parallelOutputRacks(racks, pipedOutputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        pipedOutputStream.close();

        future = service.parallelInputRacks(2, pipedInputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        Rack[] result1 = future.get().toArray(new Rack[0]);
        future = service.parallelInputRacks(2, pipedInputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        Rack[] result2 = future.get().toArray(new Rack[0]);
        pipedInputStream.close();

        assertEquals(result1.length, 2);
        for (Rack rack : result1) {
            if (rack.getSize() == 0) {
                AssertUtilities.assertRack(emptyRack, rack);
            } else if (rack.getSize() == 3) {
                AssertUtilities.assertRack(partlyRack, rack);
            } else assert false;
        }
        assertEquals(result2.length, 0);
    }

    @Test
    public void parallelWriteReadRacks() throws Exception {
        Future<Collection<Rack>> future = service.parallelWriteRacks(racks, pipedWriter);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        pipedWriter.close();

        future = service.parallelReadRacks(2, pipedReader);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        Rack[] result1 = future.get().toArray(new Rack[0]);
        future = service.parallelReadRacks(2, pipedReader);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        Rack[] result2 = future.get().toArray(new Rack[0]);
        pipedReader.close();

        assertEquals(result1.length, 2);
        for (Rack rack : result1) {
            if (rack.getSize() == 0) {
                AssertUtilities.assertRack(emptyRack, rack);
            } else if (rack.getSize() == 3) {
                AssertUtilities.assertRack(partlyRack, rack);
            } else assert false;
        }
        assertEquals(result2.length, 0);
    }

    @Test
    public void concurrency() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        CharArrayWriter charArrayWriter1 = new CharArrayWriter();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        CharArrayWriter charArrayWriter2 = new CharArrayWriter();
        ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
        CharArrayWriter charArrayWriter3 = new CharArrayWriter();

        Collection<FeelableEntity> entities1 = new ArrayList<FeelableEntity>();
        for (int i = 0; i < 100; i++) {
            entities1.add(CreateUtilities.createBattery());
            entities1.add(CreateUtilities.createThinCoaxial());
            entities1.add(CreateUtilities.createRouter());
        }
        Collection<FeelableEntity> entities2 = new ArrayList<FeelableEntity>(entities1);
        for (int i = 0; i < 100; i++) {
            entities2.add(CreateUtilities.createTwistedPair());
            entities2.add(CreateUtilities.createSwitch());
            entities2.add(CreateUtilities.createOpticFiber());
        }
        Collection<FeelableEntity> entities3 = new ArrayList<FeelableEntity>(entities2);
        for (int i = 0; i < 100; i++) {
            entities3.add(CreateUtilities.createWifiRouter());
            entities3.add(CreateUtilities.createWireless());
        }

        Future[] futures = new Future[6];
        List<String> log = new ArrayList<String>();
        int finished = 0;
        futures[0] = service.parallelWriteElements(entities3, charArrayWriter1);
        futures[1] = service.parallelOutputElements(entities3, byteArrayOutputStream1);
        futures[2] = service.parallelWriteElements(entities2, charArrayWriter2);
        futures[3] = service.parallelOutputElements(entities2, byteArrayOutputStream2);
        futures[4] = service.parallelWriteElements(entities1, charArrayWriter3);
        futures[5] = service.parallelOutputElements(entities1, byteArrayOutputStream3);

        while (finished < 6) {
            log.add("-tik-");
            for (int i = 0; i < 6; i++) {
                if ((futures[i] != null) && (futures[i].isDone())) {
                    finished++;
                    log.add("Task " + i + " finish!");
                    futures[i] = null;
                }
            }
            Thread.sleep(3);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(TEXT_FILE_NAME));
        for (String line : log) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

}