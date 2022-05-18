package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.WrongPKMethodException;
import com.netcracker.edu.inventory.model.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.location.Trunk;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 29.12.2016.
 */
public class ConnectionPK<A extends Device, B extends Device> implements ConnectionPrimaryKey<A, B> {

    private final Trunk trunk;
    private final int serialNumber;

    private final WrongPKMethodException exWrong;

    protected static Logger LOGGER = Logger.getLogger(ConnectionPK.class.getName());

    public ConnectionPK(Trunk trunk, int serialNumber) {
        this.trunk = trunk;
        this.serialNumber = serialNumber;
        exWrong = new WrongPKMethodException("Not implemented");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectionPK<?, ?> that = (ConnectionPK<?, ?>) o;

        if (serialNumber != that.serialNumber) return false;
        return trunk.equals(that.trunk);
    }

    @Override
    public Trunk getTrunk() {
        return trunk;
    }

    @Override
    public void setTrunk(Trunk trunk) {
        LOGGER.log(Level.SEVERE, "Method setTrunk should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        LOGGER.log(Level.SEVERE, "Method setSerialNumber should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public String getStatus() {
        LOGGER.log(Level.SEVERE, "Method getStatus should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public void setStatus(String status) {
        LOGGER.log(Level.SEVERE, "Method setStatus should not be implemented", exWrong);
        throw exWrong;
    }

    @Override
    public void feelAllFields(Field[] fields) {
        LOGGER.log(Level.SEVERE, "Method feelAllFields should not be implemented", exWrong);
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
    public ConnectionPrimaryKey getPrimaryKey() {
        return this;
    }

    @Override
    public int compareTo(Object o) {
        if(o.getClass() == ConnectionPK.class) {
            return this.getSerialNumber() - ((ConnectionPK) o).getSerialNumber();
        } else {
            return -1;
        }
    }
}
