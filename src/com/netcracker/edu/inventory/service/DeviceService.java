package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;

import java.io.*;

/**The interface DeviceService describe list of services of Inventory component, witch working with Device
 *
 * Created by makovetskyi on 05.10.2016.
 */
public interface DeviceService {

    /**
     * Method check device for castability to specified type
     *
     * @param device - checked device
     * @param clazz - type for cast
     * @return true - if device is castable
     *         false - if device is not castable
     */
    boolean isCastableTo(Device device, Class clazz);

    /**
     * Method check validity of device for insert to rack
     *
     * @param device - validated device
     * @return true - if device is valid
     *         false - if device is not valid
     */
    boolean isValidDeviceForInsertToRack(Device device);

    /**
     * Method check validity of device for write to char output stream
     *
     * @param device - validated device
     * @return true - if device is valid
     *         false - if device is not valid
     */
    boolean isValidDeviceForWriteToStream(Device device);

    /**
     * Write Device instance in to symbol stream
     *
     * @param device - source Device
     * @param writer - targeted symbol stream
     */
    void writeDevice(Device device, Writer writer) throws IOException;

    /**
     * Read Device instance from symbol stream
     *
     * @param reader - source symbol stream
     * @return - received Device instance
     */
    Device readDevice(Reader reader) throws IOException, ClassNotFoundException;

    /**
     * Write Device instance in to binary stream
     *
     * @param device - source Device
     * @param outputStream - targeted binary stream
     */
    void outputDevice(Device device, OutputStream outputStream) throws IOException;

    /**
     * Read Device instance from binary stream
     *
     * @param inputStream - source binary stream
     * @return - received Device instance
     */
    Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException;

    /**
     * Serialize Device instance in to binary stream
     *
     * @param device - source Device
     * @param outputStream - targeted binary stream
     */
    @Deprecated
    void serializeDevice(Device device, OutputStream outputStream) throws IOException;

    /**
     * Deserialize Device instance from binary stream
     *
     * @param inputStream - source binary stream
     * @return - received Device instance
     */
    @Deprecated
    Device deserializeDevice(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException;

}