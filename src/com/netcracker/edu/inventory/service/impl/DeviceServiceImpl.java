package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.DeviceService;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by Alex on 22.10.2016.
 */
class DeviceServiceImpl implements DeviceService {

    private Validator deviceValidator = new Validator();
    private InputOutputOperations in = new InputOutputOperations();

    @Override
    public boolean isCastableTo(Device device, Class clazz) {
        if(device == null || clazz == null)
            return false;
        else
            return clazz.isInstance(device);
    }

    @Override
    public boolean isValidDeviceForInsertToRack(Device device) {
        return deviceValidator.isValidDeviceForInsertToRack(device);
    }

    @Override
    public boolean isValidDeviceForWriteToStream(Device device) {
        return deviceValidator.isValidDeviceForWriteToStream(device);
    }

    @Override
    public void writeDevice(Device device, Writer writer) throws IOException {
        in.writeDevice(device, writer);
    }

    @Override
    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        return in.readDevice(reader);
    }



    @Override
    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        in.outputDevice(device, outputStream);
    }

    @Override
    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        return in.inputDevice(inputStream);
    }


    @Deprecated
    @Override
    public void serializeDevice(Device device, OutputStream outputStream) throws IOException {
        in.serializeDevice(device, outputStream);
    }

    @Deprecated
    @Override
    public Device deserializeDevice(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        return in.deserializeDevice(inputStream);
    }
}
