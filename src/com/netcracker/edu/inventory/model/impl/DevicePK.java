package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.WrongPKMethodException;
import com.netcracker.edu.inventory.model.DevicePrimaryKey;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 29.12.2016.
 */
public class DevicePK implements DevicePrimaryKey {

    private final WrongPKMethodException exWrong;

    protected static Logger LOGGER = Logger.getLogger(DevicePK.class.getName());

    private final int in;

    public DevicePK(int in) {
        this.in = in;
        exWrong = new WrongPKMethodException("Not implemented");
    }

    @Override
    public int getIn() {
        return in;
    }

    @Override
    public void setIn(int in) {
        LOGGER.log(Level.SEVERE, "Method setTrunk should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public String getType() {
        LOGGER.log(Level.SEVERE, "Method getType should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public String getManufacturer() {
        LOGGER.log(Level.SEVERE, "Method getManufacturer should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        LOGGER.log(Level.SEVERE, "Method setManufactured should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public String getModel() {
        LOGGER.log(Level.SEVERE, "Method getModel should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public void setModel(String model) {
        LOGGER.log(Level.SEVERE, "Method setModel should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public Date getProductionDate() {
        LOGGER.log(Level.SEVERE, "Method getProductionDate should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public void setProductionDate(Date productionDate) {
        LOGGER.log(Level.SEVERE, "Method setProductionDate should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public void feelAllFields(Field[] fields) {
        LOGGER.log(Level.SEVERE, "Method fillAllFields should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public Field[] getAllFields() {
        LOGGER.log(Level.SEVERE, "Method getAllFields should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        LOGGER.log(Level.SEVERE, "Method fillAllFields should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public List<Field> getAllFieldsList() {
        LOGGER.log(Level.SEVERE, "Method getAllFieldsList should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public boolean isPrimaryKey() {
        return true;
    }

    @Override
    public DevicePrimaryKey getPrimaryKey() {
        return this;
    }

    @Override
    public int compareTo(Object o) {
        if(o.getClass() == DevicePK.class) {
            return this.getIn() - ((DevicePK) o).getIn();
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DevicePK devicePK = (DevicePK) o;

        return in == devicePK.in;

    }
}
