package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.OneToOneConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 08.12.2016.
 */
abstract class AbstractOneToOneConnector<A extends Device, B extends Device> extends AbstractConnection<A, B> implements OneToOneConnection<A, B> {

    private int length;
    private A aPoint;
    private B bPoint;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public A getAPoint() {
        return aPoint;
    }

    @Override
    public void setAPoint(A device) {
        this.aPoint = device;
    }

    @Override
    public B getBPoint() {
        return bPoint;
    }

    @Override
    public void setBPoint(B device) {
        this.bPoint = device;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        super.fillAllFields(fields.subList(0, fields.size()-3));
        if(fields.get(fields.size()-3) != null)
            setAPoint((A)fields.get(fields.size()-3).getValue());
        if(fields.get(fields.size()-2) != null)
            setBPoint((B)fields.get(fields.size()-2).getValue());
        if(fields.get(fields.size()-1) != null)
            setLength((Integer)fields.get(fields.size()-1).getValue());
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> list = super.getAllFieldsList();
        list.add(new Field(Device.class, getAPoint()));
        list.add(new Field(Device.class, getBPoint()));
        list.add(new Field(Integer.class, getLength()));
        return list;
    }

}
