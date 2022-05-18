/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.FeelableEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
class AbstractDevice implements Device, Serializable {

    private int in;
    private final String type = getClass().getSimpleName();
    private String manufacturer;
    private String model;
    private Date productionDate;

    static protected Logger LOGGER = Logger.getLogger(AbstractDevice.class.getName());

    @Override
    public int getIn() {
        return this.in;
    }

    @Override
    public void setIn(int in) {
        if(in > 0)
            this.in = in;
        else {
            IllegalArgumentException ex = new IllegalArgumentException("Field 'in' should not be negative");
            LOGGER.log(Level.SEVERE, "Field 'in' should not be negative", ex);
            throw ex;
        }
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getManufacturer() {
        return this.manufacturer;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public Date getProductionDate() {
        return this.productionDate;
    }

    @Override
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
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
        if(fields.get(0) != null && (Integer)fields.get(0).getValue() > 0) {
            setIn((Integer)fields.get(0).getValue());
        }

        setManufacturer((String)fields.get(2).getValue());
        setModel((String)fields.get(3).getValue());
        setProductionDate((Date)fields.get(4).getValue());
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = new ArrayList();
        fields.add(new Field(Integer.class, getIn()));
        fields.add(new Field(String.class, getType()));
        fields.add(new Field(String.class, getManufacturer()));
        fields.add(new Field(String.class, getModel()));
        fields.add(new Field(Date.class, getProductionDate()));
        return fields;
    }

    @Override
    public boolean isPrimaryKey() {
        return false;
    }

    @Override
    public DevicePrimaryKey getPrimaryKey() {
        if(getIn() != 0)
            return new DevicePK(getIn());
        return null;
    }

    @Override
    public int compareTo(Object o) {
        AbstractDevice dev = (AbstractDevice) o;
        int in = dev.getIn();
        if(this.in < in){
            return -1;
        } else if(this.in > in){
            return 1;
        }
        return 0;
    }
}
