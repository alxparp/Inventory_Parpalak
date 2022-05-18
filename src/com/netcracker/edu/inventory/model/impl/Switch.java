/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Alex
 */
public class Switch extends Router implements Device, Serializable {

    protected int numberOfPorts;

    protected ConnectorType portsType;

    protected List<Connection> portsConnections = new ArrayList<Connection>();

    public Switch() {
        portsType = ConnectorType.need_init;
    }

    public Switch(ConnectorType connectorType) {
        this.portsType = connectorType;
    }

    public Switch(ConnectorType portsType, int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
        this.portsType = portsType;
        initPorts();
    }

    private void initPorts() {
        portsConnections.clear();
        for(int i = 0; i < numberOfPorts; i++) {
            portsConnections.add(null);
        }
    }

    public List<Connection> getAllPortConnections() {
        List<Connection> copyPortsConnection = new ArrayList<Connection>(portsConnections);
        return copyPortsConnection;
    }

    public Connection getPortConnection(int portNumber) {
        if(portNumber >= 0 && portNumber < numberOfPorts) {
            return portsConnections.get(portNumber);
        } else {
            IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Invalid portNumber");
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }
    }

    public void setPortConnection(Connection connection, int portNumber) {
        if(connection == null) {
            IllegalArgumentException ex = new IllegalArgumentException("Connection should not be null");
            LOGGER.log(Level.SEVERE, ex.getMessage(),ex);
            throw ex;
        }
        if(portNumber >=0 && portNumber < numberOfPorts) {
            //if(getPortConnection(portNumber) == null) {
                portsConnections.set(portNumber, connection);
            //}
        } else {
            IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Invalid portNumber");
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }
    }

    public ConnectorType getPortsType() {
        return portsType;
    }

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.portsConnections = new ArrayList<Connection>();
        this.numberOfPorts = numberOfPorts;
        initPorts();
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(super.getAllFieldsList());
        fields.add(new Field(Integer.class, getNumberOfPorts()));
        fields.add(new Field(Enum.class, getPortsType()));
        fields.add(new Field(List.class, portsConnections));
        return fields;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        super.fillAllFields(fields.subList(0, fields.size()-3));
        if(fields.get(fields.size()-3).getValue() != null)
            setNumberOfPorts((Integer)fields.get(fields.size()-3).getValue());
        if(fields.get(fields.size()-2).getValue() != null && portsType == ConnectorType.need_init)
            this.portsType = ConnectorType.valueOf(fields.get(fields.size()-2).getValue().toString());
        if(fields.get(fields.size()-1).getValue() != null)
            portsConnections = (List<Connection>)fields.get(fields.size()-1).getValue();
    }
}
