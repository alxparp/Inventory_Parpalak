/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.RackPrimaryKey;
import com.netcracker.edu.location.Location;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class RackArrayImpl<T extends Device> implements Rack<T>, Serializable{

    static protected Logger LOGGER = Logger.getLogger(RackArrayImpl.class.getName());

    private final Class clazz;

    private int size;
    private  T saveObjects[];

    private Location location;

    public RackArrayImpl(int size, Class clazz) {

        if(size < 0) {
            IllegalArgumentException ex = new IllegalArgumentException("Rack size should not be negative");
            LOGGER.log(Level.SEVERE, "Rack size should not be negative",ex);
            throw ex;
        }

        if(clazz == null) {
            IllegalArgumentException ex = new IllegalArgumentException("Rack type should not be null");
            LOGGER.log(Level.SEVERE, "Rack type should not be null",ex);
            throw ex;
        }

        saveObjects = (T[]) new Device[size];
        this.clazz = clazz;
        this.size = size;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int getSize() {
       return this.size;
    }

    @Override
    public int getFreeSize() {
        int count=0;
        for(T obj : saveObjects)
            if(obj == null)
                count++;
        return count;
    }

    @Override
    public Class getTypeOfDevices() {
        return clazz;
    }

    @Override 
    public T getDevAtSlot(int index) {
        if(index >= 0 && index < size)
            return saveObjects[index];
        else {
            IndexOutOfBoundsException ex = new IndexOutOfBoundsException();
            LOGGER.log(Level.SEVERE,"Invalid index",ex);
            throw ex;
        }
    }

    @Override
    public boolean insertDevToSlot(T device, int index) throws DeviceValidationException, IndexOutOfBoundsException {
        if(index >= 0 && index < size) {
            if (device == null || device.getType() == null || device.getIn() == 0) {
                DeviceValidationException ex = new DeviceValidationException(device, "Rack.insertDevTosSlot");
                LOGGER.log(Level.SEVERE, "Device is not valid for operation Rack.insertDevTosSlot", ex);
                throw ex;
            }
            if(clazz.isInstance(device)) {
                if(getTypeOfDevices().getSimpleName() == null && !getTypeOfDevices().getSimpleName().equals(device.getType())) {
                        return false;
                }
                if (saveObjects[index] == null) {
                    saveObjects[index] = device;
                    return true;
                }
            }

        } else {
            IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Invalid index");
            LOGGER.log(Level.SEVERE, "Invalid index", ex);
            throw ex;
        }
        return false;
    }

    @Override
    public T removeDevFromSlot(int index) {
        if(index >= 0 && index < size) {
            T dev = saveObjects[index];
            if(dev != null) {
                saveObjects[index] = null;
                return dev;
            }
            LOGGER.log(Level.WARNING,"Can not remove from empty slot " + index);
        } else {
            IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Invalid index");
            LOGGER.log(Level.SEVERE, "Invalid index", ex);
            throw ex;
        }
        return null;
    }

    @Override
    public T getDevByIN(int in) {
        for (int i = 0; i < saveObjects.length; i++)
            if (saveObjects[i]!=null && saveObjects[i].getIn() == in)
                return saveObjects[i];
        return null;
    }

    @Override
    public T[] getAllDeviceAsArray() {
        T[] devArray = (T[]) new Device[getSize() - getFreeSize()];
        int sequence=0;
        for(int i = 0; i < saveObjects.length; i++)
            if(saveObjects[i] != null) {
                devArray[sequence] = saveObjects[i];
                sequence++;
            }
        return devArray;
    }

    @Override
    public boolean isPrimaryKey() {
        return false;
    }

    @Override
    public RackPrimaryKey getPrimaryKey() {
        if(location != null)
            return new RackPK(location);
        return null;
    }
}
