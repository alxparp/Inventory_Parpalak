package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 08.12.2016.
 */
public class OpticFiber<A extends Device, B extends Device> extends AbstractOneToOneConnector<A,B> {

    public enum Mode {need_init, single, multi};

    private Mode mode;


    public OpticFiber() {
        mode = Mode.need_init;
        setLength(0);
        setStatus(Connection.PLANED);
    }

    public OpticFiber(Mode mode, int length) {
        this.mode = mode;
        setLength(length);
        setStatus(Connection.PLANED);
    }

    public Mode getMode() {
        return mode;
    }

    @Override
    public ConnectorType getAPointConnectorType() {
        return ConnectorType.FiberConnector_FC;
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return ConnectorType.FiberConnector_FC;
    }

    @Override
    public void fillAllFields(List fields) {
        super.fillAllFields(fields.subList(0, fields.size()-1));
        if(mode == Mode.need_init && ((Field)fields.get(fields.size()-1)).getValue() != null) {
            mode = Mode.valueOf(((Field)fields.get(fields.size()-1)).getValue().toString());
        }
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> list = super.getAllFieldsList();
        list.add(new Field(Enum.class, getMode()));
        return list;
    }
}
