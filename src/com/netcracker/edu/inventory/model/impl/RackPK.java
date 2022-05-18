package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.WrongPKMethodException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.RackPrimaryKey;
import com.netcracker.edu.location.Location;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 29.12.2016.
 */
public class RackPK<T extends Device> implements RackPrimaryKey<T> {

    private final WrongPKMethodException exWrong;

    protected static Logger LOGGER = Logger.getLogger(DevicePK.class.getName());

    private final Location location;

    public RackPK(Location location) {
        this.location = location;

        exWrong = new WrongPKMethodException("Not implemented");
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        LOGGER.log(Level.SEVERE, "Method setLocation should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public int getSize() {
        LOGGER.log(Level.SEVERE, "Method getSize should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public int getFreeSize() {
        LOGGER.log(Level.SEVERE, "Method getFeeSize should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public Class getTypeOfDevices() {
        LOGGER.log(Level.SEVERE, "Method getTypeOfDevices should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public T getDevAtSlot(int index) {
        LOGGER.log(Level.SEVERE, "Method getDevAtSlot should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public boolean insertDevToSlot(T device, int index) {
        LOGGER.log(Level.SEVERE, "Method insertDevToSlot should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public T removeDevFromSlot(int index) {
        LOGGER.log(Level.SEVERE, "Method removeDevFromSlot should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public T getDevByIN(int in) {
        LOGGER.log(Level.SEVERE, "Method getDevByIN should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public T[] getAllDeviceAsArray() {
        LOGGER.log(Level.SEVERE, "Method getAllDeviceAsArray should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public boolean isPrimaryKey() {
        return true;
    }

    @Override
    public RackPrimaryKey getPrimaryKey() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RackPK<?> rackPK = (RackPK<?>) o;

        return location != null ? location.equals(rackPK.location) : false;
    }

}
