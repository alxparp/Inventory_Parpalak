/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Alex
 */
public class Router extends AbstractDevice implements Device, Serializable{
    private int dataRate;

    public int getDataRate() {
        return dataRate;
    }

    public void setDataRate(int dataRate) {
        this.dataRate = dataRate;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        super.fillAllFields(fields.subList(0, fields.size()-1));
        if(fields.get(fields.size()-1).getValue() != null)
            setDataRate((Integer)fields.get(fields.size()-1).getValue());
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(super.getAllFieldsList());
        fields.add(new Field(Integer.class, getDataRate()));
        return fields;
    }
}
