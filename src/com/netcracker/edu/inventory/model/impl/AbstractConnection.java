package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.location.Trunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 29.12.2016.
 */
abstract class AbstractConnection<A extends Device, B extends Device> implements Connection<A, B>{

    static protected Logger LOGGER = Logger.getLogger(ThinCoaxial.class.getName());

    private String status;
    private int serialNumber;
    private Trunk trunk;

    @Override
    public Trunk getTrunk() {
        return trunk;
    }

    @Override
    public void setTrunk(Trunk trunk) {
        this.trunk = trunk;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        if(serialNumber > 0)
            this.serialNumber = serialNumber;
        else {
            IllegalArgumentException ex = new IllegalArgumentException("Field 'serialNumber' should not be negative or equals 0");
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Deprecated
    @Override
    public void feelAllFields(Field[] fields) {
        fillAllFields(new ArrayList<Field>(Arrays.asList(fields)));
    }

    @Deprecated
    @Override
    public Field[] getAllFields() {
        return getAllFieldsList().toArray(new Field[getAllFieldsList().size()]);
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        if(fields.get(0).getValue() != null) {
            setStatus((String) fields.get(0).getValue());
            setSerialNumber((Integer) fields.get(1).getValue());
            setTrunk((Trunk) fields.get(2).getValue());
        }
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> list = new ArrayList<Field>();
        list.add(new Field(String.class, getStatus()));
        list.add(new Field(Integer.class, getSerialNumber()));
        list.add(new Field(Trunk.class, getTrunk()));
        return list;
    }

    @Override
    public boolean isPrimaryKey() {
        return false;
    }

    @Override
    public ConnectionPrimaryKey getPrimaryKey() {
        if(getTrunk() != null && getSerialNumber() != 0)
            return new ConnectionPK(getTrunk(),getSerialNumber());
        else
            return null;
    }

    @Override
    public int compareTo(Object o) {
        int serialNumber = ((Connection)o).getSerialNumber();
        if(this.serialNumber > serialNumber)
            return 1;
        else if (this.serialNumber < serialNumber)
            return -1;

        return 0;
    }
}
