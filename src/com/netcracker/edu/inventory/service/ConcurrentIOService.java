package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.Rack;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.concurrent.Future;

/**
 * Created by makovetskyi on 29.11.2016.
 */
public interface ConcurrentIOService {

    Future parallelOutputElements(Collection<FeelableEntity> elements, OutputStream outputStream);

    Future<Collection<FeelableEntity>> parallelInputElements(int number, InputStream inputStream);

    Future parallelWriteElements(Collection<FeelableEntity> elements, Writer writer);

    Future<Collection<FeelableEntity>> parallelReadElements(int number, Reader reader);

    Future parallelOutputRacks(Collection<Rack> racks, OutputStream outputStream);

    Future<Collection<Rack>> parallelInputRacks(int number, InputStream inputStream);

    Future parallelWriteRacks(Collection<Rack> racks, Writer writer);

    Future<Collection<Rack>> parallelReadRacks(int number, Reader reader);

}
