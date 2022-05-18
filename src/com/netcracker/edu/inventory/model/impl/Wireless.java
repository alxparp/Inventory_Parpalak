package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.OneToManyConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 08.12.2016.
 */
public class Wireless<A extends Device, B extends Device> extends AbstractConnection<A, B> implements OneToManyConnection<A,B> {

    static protected Logger LOGGER = Logger.getLogger(Wireless.class.getName());

    private String technology;
    private String protocol;
    private int version;
    private A deviceAPoint;
    private List<B> devicesBPoint;
    private int bCapacity;

    public Wireless(Integer bCapacity, String technology) {
        this.technology = technology;
        devicesBPoint = new ArrayList<B>();
        this.bCapacity = bCapacity;
        setStatus(Connection.PLANED);
        initBPoints();
    }

    public Wireless() {
        setStatus(Connection.PLANED);
        devicesBPoint = new ArrayList<B>();
    }

    private void initBPoints() {
        devicesBPoint.clear();
        for(int i = 0; i < bCapacity; i++) {
            devicesBPoint.add(null);
        }
    }

    public String getTechnology() {
        return technology;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public ConnectorType getAPointConnectorType() {
        return ConnectorType.Wireless;
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return ConnectorType.Wireless;
    }

    @Override
    public A getAPoint() {
        return deviceAPoint;
    }

    @Override
    public void setAPoint(A device) {
        this.deviceAPoint = device;
    }

    @Override
    public List<B> getBPoints() {
        List<B> copyDevicesBPoint = new ArrayList<B>(devicesBPoint);
        return copyDevicesBPoint;
    }

    @Override
    public void setBPoints(List devices) {
        List devicesCopy = new ArrayList(devices);
        this.devicesBPoint = devicesCopy;
    }

    @Override
    public int getBCapacity() {
        return bCapacity;
    }

    @Override
    public B getBPoint(int deviceNumber) {
        if(devicesBPoint.size() > deviceNumber && deviceNumber >= 0)
            return devicesBPoint.get(deviceNumber);
        else {
            IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Invalid device number");
            LOGGER.log(Level.WARNING,ex.getMessage());
            return null;
        }
    }

    @Override
    public void setBPoint(Device device, int deviceNumber) {
        if(device == null) {
            DeviceValidationException ex = new DeviceValidationException(device, "Device is not valid for operation setBPoint");
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }
        if(deviceNumber < bCapacity && deviceNumber >= 0) {
            devicesBPoint.set(deviceNumber, (B)device);
        }
        else {
            IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Invalid device number");
            LOGGER.log(Level.WARNING,ex.getMessage());
        }
    }

    @Override
    public void fillAllFields(List<Field> fields) {

        super.fillAllFields(fields.subList(0, fields.size()-6));

        // setTechnology
        if(fields.get(fields.size()-6).getValue() != null && technology == null)
            technology = ((String)fields.get(fields.size()-6).getValue());

        // setSecurityProtocol
        if(fields.get(fields.size()-5).getValue() != null)
            setProtocol((String)fields.get(fields.size()-5).getValue());

        // setVersion
        if(fields.get(fields.size()-4).getValue() != null)
            setVersion((Integer)fields.get(fields.size()-4).getValue());

        //setAPoint
        if(fields.get(fields.size()-3).getValue() != null)
            setAPoint((A)fields.get(fields.size()-3).getValue());

        // setBPoints
        if(fields.get(fields.size()-2).getValue() != null)
            setBPoints((List<B>)fields.get(fields.size()-2).getValue());

        // setBCapacity
        if(fields.get(fields.size()-1).getValue() != null && bCapacity == 0)
            bCapacity = (Integer)fields.get(fields.size()-1).getValue();
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> list = super.getAllFieldsList();
        list.add(new Field(String.class, getTechnology()));
        list.add(new Field(String.class, getProtocol()));
        list.add(new Field(Integer.class, getVersion()));
        list.add(new Field(Device.class, getAPoint()));
        list.add(new Field(List.class, getBPoints()));
        list.add(new Field(Integer.class, getBCapacity()));
        return list;
    }
}
