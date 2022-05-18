package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.service.ConcurrentIOService;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.concurrent.Future;

/**
 * Created by User on 18.01.2017.
 */
class ConcurrentIOServiceImpl implements ConcurrentIOService {

    InputOutputConcurrent inputOutputConcurrent = new InputOutputConcurrent();

    @Override
    public Future parallelOutputElements(Collection<FeelableEntity> elements, OutputStream outputStream) {
        return inputOutputConcurrent.parallelOutputElements(elements, outputStream);
    }

    @Override
    public Future<Collection<FeelableEntity>> parallelInputElements(int number, InputStream inputStream) {
        return inputOutputConcurrent.parallelInputElements(number, inputStream);
    }

    @Override
    public Future parallelWriteElements(Collection<FeelableEntity> elements, Writer writer) {
        return inputOutputConcurrent.parallelWriteElements(elements, writer);
    }

    @Override
    public Future<Collection<FeelableEntity>> parallelReadElements(int number, Reader reader) {
        return inputOutputConcurrent.parallelReadElements(number, reader);
    }

    @Override
    public Future parallelOutputRacks(Collection<Rack> racks, OutputStream outputStream) {
        return inputOutputConcurrent.parallelOutputRacks(racks, outputStream);
    }

    @Override
    public Future<Collection<Rack>> parallelInputRacks(int number, InputStream inputStream) {
        return inputOutputConcurrent.parallelInputRacks(number, inputStream);
    }

    @Override
    public Future parallelWriteRacks(Collection<Rack> racks, Writer writer) {
        return inputOutputConcurrent.parallelWriteRacks(racks, writer);
    }

    @Override
    public Future<Collection<Rack>> parallelReadRacks(int number, Reader reader) {
        return inputOutputConcurrent.parallelReadRacks(number, reader);
    }
}
