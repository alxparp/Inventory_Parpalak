package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.Rack;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by User on 23.01.2017.
 */
class InputOutputConcurrent {

    InputOutputOperations inputOutputOperations = new InputOutputOperations();
    ReadOverallImpl readOverall = new ReadOverallImpl();
    InputOverallImpl inputOverall = new InputOverallImpl();
    WriteOverallImpl writeOverall = new WriteOverallImpl();
    OutputOverallImpl outputOverall = new OutputOverallImpl();


    public Future parallelOutputElements(final Collection<FeelableEntity> elements, final OutputStream outputStream) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Collection<FeelableEntity>> future = executorService.submit(new Callable<Collection<FeelableEntity>>() {
            @Override
            public Collection<FeelableEntity> call() throws Exception {
                for(FeelableEntity feelableEntity : elements) {
                    if(feelableEntity != null)
                        outputOverall.outputOverall(feelableEntity, outputStream);
                }
                return null;
            }
        });
        return future;
    }


    public Future<Collection<FeelableEntity>> parallelInputElements(final int number, final InputStream inputStream) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Collection<FeelableEntity>> future = executorService.submit(new Callable<Collection<FeelableEntity>>() {
            @Override
            public Collection<FeelableEntity> call() throws Exception {
                Collection<FeelableEntity> feelableEntities = new ArrayList<FeelableEntity>();
                while (feelableEntities.size() < number && inputStream.available() > 1) {
                    feelableEntities.add(inputOverall.inputOverall(inputStream));
                }
                return feelableEntities;
            }
        });
        return future;
    }


    public Future parallelWriteElements(final Collection<FeelableEntity> elements, final Writer writer) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Collection<FeelableEntity>> future = executorService.submit(new Callable<Collection<FeelableEntity>>() {
            @Override
            public Collection<FeelableEntity> call() throws Exception {
                for(FeelableEntity feelableEntity : elements) {
                    if(feelableEntity != null)
                        writeOverall.writeOverall(feelableEntity, writer);
                }
                return null;
            }
        });
        return future;
    }


    public Future<Collection<FeelableEntity>> parallelReadElements(final int number, final Reader reader) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Collection<FeelableEntity>> future = executorService.submit(new Callable<Collection<FeelableEntity>>() {
            @Override
            public Collection<FeelableEntity> call() throws Exception {
                Collection<FeelableEntity> feelableEntities = new ArrayList<FeelableEntity>();
                while (feelableEntities.size() < number && reader.ready()) {
                    feelableEntities.add(readOverall.readOverall(reader));
                }
                return feelableEntities;
            }
        });
        return future;
    }


    public Future parallelOutputRacks(final Collection<Rack> racks, final OutputStream outputStream) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Collection<Rack>> future = executorService.submit(new Callable<Collection<Rack>>() {
            @Override
            public Collection<Rack> call() throws Exception {
                for(Rack rack : racks) {
                    if(rack != null)
                        inputOutputOperations.outputRack(rack, outputStream);
                }
                return null;
            }
        });
        return future;
    }


    public Future<Collection<Rack>> parallelInputRacks(final int number, final InputStream inputStream) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Collection<Rack>> future = executorService.submit(new Callable<Collection<Rack>>() {
            @Override
            public Collection<Rack> call() throws Exception {
                Collection<Rack> racks = new ArrayList<Rack>();
                while (racks.size() < number && inputStream.available() > 1) {
                    racks.add(inputOutputOperations.inputRack(inputStream));
                }
                return racks;
            }
        });
        return future;
    }


    public Future parallelWriteRacks(final Collection<Rack> racks, final Writer writer) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Collection<Rack>> future = executorService.submit(new Callable<Collection<Rack>>() {
            @Override
            public Collection<Rack> call() throws Exception {
                for(Rack rack : racks) {
                    if(rack != null)
                        inputOutputOperations.writeRack(rack, writer);
                }
                return null;
            }
        });
        return future;
    }


    public Future<Collection<Rack>> parallelReadRacks(final int number, final Reader reader) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Collection<Rack>> future = executorService.submit(new Callable<Collection<Rack>>() {
            @Override
            public Collection<Rack> call() throws Exception {
                Collection<Rack> racks = new ArrayList<Rack>();
                while (racks.size() < number && reader.ready()) {
                    racks.add(inputOutputOperations.readRack(reader));
                }
                return racks;
            }
        });
        return future;
    }

}
