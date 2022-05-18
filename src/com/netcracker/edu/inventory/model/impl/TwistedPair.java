package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.location.Trunk;

import java.util.List;

/**
 * Created by User on 07.12.2016.
 */
public class TwistedPair<A extends Device, B extends Device> extends AbstractOneToOneConnector<A,B> {

    public enum Type {need_init, UTP, FTP, STP, SFTP, S_FTP};

    private Type type;

    public TwistedPair() {
        setStatus(Connection.PLANED);
        type = Type.need_init;
        setLength(0);
    }

    public TwistedPair(Type type, int length) {
        setStatus(Connection.PLANED);
        this.type = type;
        setLength(length);
    }

    public Type getType() {
        return type;
    }

    @Override
    public ConnectorType getAPointConnectorType() {
        return ConnectorType.RJ45;
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return ConnectorType.RJ45;
    }

    @Override
    public void fillAllFields(List fields) {
        super.fillAllFields(fields.subList(0, fields.size()-1));
        if(type == Type.need_init) {
            type = Type.valueOf(((Field)fields.get(fields.size()-1)).getValue().toString());
        }
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> list = super.getAllFieldsList();
        list.add(new Field(Enum.class, getType()));
        return list;
    }

}
