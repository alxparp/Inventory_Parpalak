package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.impl.factorymethod.EntitySelector;
import com.netcracker.edu.location.Trunk;
import com.netcracker.edu.location.impl.TrunkStubImpl;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 13.11.2016.
 */
class InputOutputOperations {

    protected static Logger LOGGER = Logger.getLogger(InputOutputOperations.class.getName());

    private ReadOverallImpl readOverall = new ReadOverallImpl();
    private InputOverallImpl inputOverall = new InputOverallImpl();
    private WriteOverallImpl writeOverall = new WriteOverallImpl();
    private OutputOverallImpl outputOverall = new OutputOverallImpl();

    // writeConnection
    public void writeConnection(Connection connection, Writer writer) throws IOException {
        writeOverall.writeOverall(connection, writer);
    }

    public Connection readConnection(Reader reader) throws IOException, ClassNotFoundException {
        return (Connection) readOverall.readOverall(reader);
    }


    public void outputConnection(Connection connection, OutputStream outputStream) throws IOException {
        outputOverall.outputOverall(connection, outputStream);
    }


    public Connection inputConnection(InputStream inputStream) throws IOException, ClassNotFoundException {
        return (Connection) inputOverall.inputOverall(inputStream);
    }


    public void writeDevice(Device device, Writer writer) throws IOException {
        writeOverall.writeOverall(device, writer);
    }

    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        return (Device) readOverall.readOverall(reader);
    }

    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        outputOverall.outputOverall(device, outputStream);
    }

    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        return (Device) inputOverall.inputOverall(inputStream);
    }

    @Deprecated
    public void serializeDevice(Device device, OutputStream outputStream) throws IOException {
        if(device != null) {
            if(outputStream == null) {
                IllegalArgumentException ex = new IllegalArgumentException("OutputStream should not be null");
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                throw ex;
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(device);
        }
    }

    @Deprecated
    public Device deserializeDevice(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        if(inputStream == null) {
            IllegalArgumentException ex = new IllegalArgumentException("InputStream should not be null");
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (Device)objectInputStream.readObject();
    }


    public void writeRack(Rack rack, Writer writer) throws IOException {
        if(rack != null) {
            if(writer == null) {
                IllegalArgumentException ex = new IllegalArgumentException("Writer should not be null");
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                throw ex;
            }
            writer.write(rack.getSize() + " ");
            writer.write(rack.getTypeOfDevices().getName() + "\n");

            new com.netcracker.edu.location.impl.ServiceImpl().writeLocation(rack.getLocation(), writer);

            DeviceServiceImpl deviceService = new DeviceServiceImpl();
            for(int i = 0; i < rack.getSize(); i++) {
                if(rack.getDevAtSlot(i) != null) {
                    deviceService.writeDevice(rack.getDevAtSlot(i), writer);
                } else {
                    writer.write("\n");
                }
            }
        }
    }

    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        if(reader == null) {
            IllegalArgumentException ex = new IllegalArgumentException("Reader should not be null");
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }

        Rack rack;
        String size="";
        String className="";
        int temp = reader.read();
        int rackSize;

        while(temp != ' ') {
            size += (char)temp;
            temp = reader.read();
        }

        while(temp != '\n') {
            className += (char)temp;
            temp = reader.read();
        }

        rackSize = Integer.parseInt(size.trim());
        Class clazz = Class.forName(className.trim());

        rack = new RackArrayImpl(rackSize, clazz);

        rack.setLocation(new com.netcracker.edu.location.impl.ServiceImpl().readLocation(reader));

        DeviceServiceImpl deviceService = new DeviceServiceImpl();
        for(int i = 0; i < rackSize; i++) {
            Device dev = deviceService.readDevice(reader);
            if(dev != null)
                rack.insertDevToSlot(dev, i);
        }

        return rack;
    }

    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if(rack != null) {
            if(outputStream == null) {
                IllegalArgumentException ex = new IllegalArgumentException("OutputStream should not be null");
                LOGGER.log(Level.SEVERE, "OutputStream should not be null", ex);
                throw ex;
            }
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeInt(rack.getSize());

            dataOutputStream.writeUTF(rack.getTypeOfDevices().getName());

            //записываем местонахождение
            new com.netcracker.edu.location.impl.ServiceImpl().outputLocation(rack.getLocation(), outputStream);

            DeviceServiceImpl deviceService = new DeviceServiceImpl();
            for(int i = 0; i < rack.getSize(); i++) {
                if(rack.getDevAtSlot(i) != null) {
                    dataOutputStream.writeUTF("dev");
                    deviceService.outputDevice(rack.getDevAtSlot(i), dataOutputStream);
                } else {
                    dataOutputStream.writeUTF("\n");
                }
            }
        }

    }

    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        if(inputStream == null) {
            IllegalArgumentException ex = new IllegalArgumentException("InputStream should not be null");
            LOGGER.log(Level.SEVERE, "InputStream should not be null", ex);
            throw ex;
        }

        DataInputStream dataInputStream = new DataInputStream(inputStream);

        int size = dataInputStream.readInt();
        Class clazz = Class.forName(dataInputStream.readUTF());

        Rack rack = new RackArrayImpl(size, clazz);

        rack.setLocation(new com.netcracker.edu.location.impl.ServiceImpl().inputLocation(inputStream));

        DeviceServiceImpl deviceService = new DeviceServiceImpl();

        for(int i = 0; i < rack.getSize(); i++) {
            if(dataInputStream.readUTF().equals("dev")) {
                Device dev = deviceService.inputDevice(dataInputStream);
                rack.insertDevToSlot(dev, i);
            }
        }
        return rack;
    }

    @Deprecated
    public void serializeRack(Rack rack, OutputStream outputStream) throws IOException {
        if(rack != null) {
            if(outputStream == null) {
                IllegalArgumentException ex = new IllegalArgumentException("OutputStream should not be null");
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                throw ex;
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(rack);
        }
    }

    @Deprecated
    public Rack deserializeRack(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        if(inputStream == null) {
            IllegalArgumentException ex = new IllegalArgumentException("InputStream should not be null");
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (Rack)objectInputStream.readObject();
    }

}
