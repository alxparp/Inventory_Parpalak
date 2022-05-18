package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 08.12.2016.
 */
public class ThinCoaxial<T extends Device> extends AbstractConnection<T, T> implements AllToAllConnection<T> {

    private Integer maxSize=0;
    private Set<Device> devices;

    public ThinCoaxial() {
        setStatus(Connection.PLANED);
        devices = new HashSet<Device>();
    }

    public ThinCoaxial(int maxSize) {
        setStatus(Connection.PLANED);
        devices = new HashSet<Device>();
        this.maxSize = maxSize;
    }

    @Override
    public ConnectorType getConnectorType() {
        return ConnectorType.TConnector;
    }

    @Override
    public boolean addDevice(Device device) {
        if(devices.size() < maxSize) {
            devices.add(device);
            return true;
        }
        IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Ports are filled");
        LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        return false;
    }

    @Override
    public boolean removeDevice(Device device) {
        if(containDevice(device)) {
            devices.remove(device);
            return true;
        }
        IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Device does not exists");
        LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        return false;
    }

    @Override
    public boolean containDevice(Device device) {
        if (devices.contains(device)) {
            return true;
        }
        IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Device does not exists");
        LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        return false;
    }

    @Override
    public Set getAllDevices() {
        Set<Device> copyList = new HashSet<Device>(devices);
        return copyList;
    }

    @Override
    public int getCurSize() {
        return devices.size();
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        super.fillAllFields(fields.subList(0, fields.size()-2));
        if(fields.get(fields.size()-2).getValue() != null && maxSize == 0)
            maxSize = (Integer)fields.get(fields.size()-2).getValue();
        if(fields.get(fields.size()-1).getValue() != null)
            devices = (Set)fields.get(fields.size()-1).getValue();
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> list = super.getAllFieldsList();
        list.add(new Field(Integer.class, getMaxSize()));
        list.add(new Field(Set.class, getAllDevices()));
        return list;
    }
}
