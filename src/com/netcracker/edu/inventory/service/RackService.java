package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.Rack;

import java.io.*;

/**The interface RackService describe list of services of Inventory component, witch working with Rack
 *
 * Created by makovetskyi on 05.10.2016.
 */
public interface RackService {

    /**
     * Write Rack instance in to symbol stream
     *
     * @param rack - source Rack
     * @param writer - targeted symbol stream
     */
    void writeRack(Rack rack, Writer writer) throws IOException;

    /**
     * Read Rack instance from symbol stream
     *
     * @param reader - source symbol stream
     * @return - received Rack instance
     */
    Rack readRack(Reader reader) throws IOException, ClassNotFoundException;

    /**
     * Write Rack instance in to binary stream
     *
     * @param rack - source Rack
     * @param outputStream - targeted binary stream
     */
    void outputRack(Rack rack, OutputStream outputStream) throws IOException;

    /**
     * Read Rack instance from binary stream
     *
     * @param inputStream - source binary stream
     * @return - received Rack instance
     */
    Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException;

    /**
     * Serialize Rack instance in to binary stream
     *
     * @param rack - source Rack
     * @param outputStream - targeted binary stream
     */
    @Deprecated
    void serializeRack(Rack rack, OutputStream outputStream) throws IOException;

    /**
     * Deserialize Rack instance from binary stream
     *
     * @param inputStream - source binary stream
     * @return - received Rack instance
     */
    @Deprecated
    Rack deserializeRack(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException;

}