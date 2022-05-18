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

/**
 *
 * @author Alex
 */
public class WifiRouter extends Router implements Device, Serializable {
    protected String securityProtocol;

    protected String technologyVersion;

    protected Connection wirelessConnection;

    protected ConnectorType wirePortType;

    protected Connection wireConnection;

    public WifiRouter() {
        technologyVersion = null;
        wirePortType = ConnectorType.need_init;
    }

    public WifiRouter(String technologyVersion, ConnectorType wirePortType) {
        this.technologyVersion = technologyVersion;
        this.wirePortType = wirePortType;
    }

    public Connection getWireConnection() {
        return wireConnection;
    }

    public void setWireConnection(Connection wireConnection) {
        this.wireConnection = wireConnection;
    }

    public ConnectorType getWirePortType() {
        return wirePortType;
    }

    public Connection getWirelessConnection() {
        return wirelessConnection;
    }

    public void setWirelessConnection(Connection wirelessConnection) {
        this.wirelessConnection = wirelessConnection;
    }

    public String getTechnologyVersion() {
        return technologyVersion;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(super.getAllFieldsList());
        fields.add(new Field(String.class, getSecurityProtocol()));
        fields.add(new Field(String.class, getTechnologyVersion()));
        fields.add(new Field(Connection.class, getWirelessConnection()));
        fields.add(new Field(Enum.class, getWirePortType()));
        fields.add(new Field(Connection.class, getWireConnection()));
        return fields;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        super.fillAllFields(fields.subList(0, fields.size()-5));
        if(fields.get(fields.size()-5).getValue() != null)
            setSecurityProtocol((String)fields.get(fields.size()-5).getValue());
        if(fields.get(fields.size()-4).getValue() != null && technologyVersion == null)
            this.technologyVersion = (String)(fields.get(fields.size()-4)).getValue();
        if(fields.get(fields.size()-3).getValue() != null)
            setWirelessConnection((Connection)fields.get(fields.size()-3).getValue());
        if(fields.get(fields.size()-2).getValue() != null && wirePortType == ConnectorType.need_init)
            this.wirePortType = ConnectorType.valueOf(fields.get(fields.size()-2).getValue().toString());
        if(fields.get(fields.size()-1).getValue() != null)
            setWireConnection((Connection)fields.get(fields.size()-1).getValue());
    }
}
