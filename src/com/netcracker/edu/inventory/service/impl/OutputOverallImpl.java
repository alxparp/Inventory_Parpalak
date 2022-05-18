package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.impl.ConnectionPK;
import com.netcracker.edu.location.Trunk;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 24.01.2017.
 */
class OutputOverallImpl {

    protected static Logger LOGGER = Logger.getLogger(OutputOverallImpl.class.getName());
    private ServiceImpl service = new ServiceImpl();

    void outputOverall(FeelableEntity feelableEntity, OutputStream outputStream) throws IOException {
        if(feelableEntity != null) {
            if(outputStream == null) {
                IllegalArgumentException ex = new IllegalArgumentException("OutputStream should not be null");
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                throw ex;
            }
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            List<FeelableEntity.Field> fields = feelableEntity.getAllFieldsList();

            dataOutputStream.writeUTF(feelableEntity.getClass().getName());

            outputDeviceOrConnectionFields(dataOutputStream, fields);
        }
    }

    private void outputDeviceOrConnectionFields(DataOutputStream dataOutputStream, List<FeelableEntity.Field> fields) throws IOException {

        for(int i = 0; i < fields.size(); i++) {

            // String
            if(fields.get(i).getType().equals(String.class)) {
                if (fields.get(i).getValue() != null)
                    dataOutputStream.writeUTF((String)fields.get(i).getValue());
                else
                    dataOutputStream.writeUTF("\n");
            }

            // Date
            if(fields.get(i).getType().equals(Date.class)) {
                if (fields.get(i).getValue() != null)
                    dataOutputStream.writeLong(((Date)fields.get(i).getValue()).getTime());
                else
                    dataOutputStream.writeLong(-1);
            }

            // Integer
            if(fields.get(i).getType().equals(Integer.class)) {
                if(fields.get(i).getValue() != null)
                    dataOutputStream.writeInt((Integer)fields.get(i).getValue());
            }

            // Device
            if(fields.get(i).getType() == Device.class) {
                Device dev = (Device) fields.get(i).getValue();
                if(dev != null) {
                    dataOutputStream.writeUTF("DPK: " + service.getIndependentCopy(dev).getPrimaryKey().getIn());
                } else {
                    dataOutputStream.writeUTF("\n");
                }
            }

            // Connection
            if(fields.get(i).getType() == Connection.class) {
                Connection connection = (Connection) fields.get(i).getValue();
                if(connection != null) {
                    ConnectionPK primaryKey = (ConnectionPK)service.getIndependentCopy(connection).getPrimaryKey();
                    dataOutputStream.writeUTF("CPK: " + primaryKey.getSerialNumber() +
                            " " + primaryKey.getTrunk().getRoute() +
                            " " + primaryKey.getTrunk().getAlias());
                } else {
                    dataOutputStream.writeUTF("\n");
                }
            }

            // Enum
            if(fields.get(i).getType() == Enum.class) {
                if(fields.get(i).getValue() != null) {
                    dataOutputStream.writeUTF(fields.get(i).getValue().toString());
                } else {
                    dataOutputStream.writeUTF("\n");
                }
            }

            // Trunk
            if(fields.get(i).getType() == Trunk.class) {
                Trunk trunk = (Trunk) fields.get(i).getValue();
                if(trunk != null) {
                    dataOutputStream.writeUTF(trunk.getRoute() + " " + trunk.getAlias());
                } else {
                    dataOutputStream.writeUTF("\n");
                }
            }

            // Set
            if(fields.get(i).getType() == Set.class) {
                Set set = (HashSet) fields.get(i).getValue();
                List list = new ArrayList(set);
                outputCollection(dataOutputStream, list);
            }

            // List
            if(fields.get(i).getType() == List.class) {
                List<Device> list = (List<Device>)fields.get(i).getValue();
                outputCollection(dataOutputStream, list);
            }

        }
    }

    private void outputCollection(DataOutputStream dataOutputStream, List list) throws IOException {
        dataOutputStream.writeInt(list.size());
        if(list != null) {
            for(int j = 0; j < list.size(); j++) {
                if(list != null) {
                    if(list.get(j) instanceof Device)
                        dataOutputStream.writeUTF("DPK: " + service.getIndependentCopy((Device)list.get(j)).getPrimaryKey().getIn());
                    else if(list.get(j) instanceof Connection) {
                        ConnectionPK connectionPK = (ConnectionPK)service.getIndependentCopy((Connection) list.get(j)).getPrimaryKey();
                        dataOutputStream.writeUTF("CPK: " + connectionPK.getSerialNumber() +
                                " " + connectionPK.getTrunk().getRoute() +
                                " " + connectionPK.getTrunk().getAlias());
                    } else
                        dataOutputStream.writeUTF("\n");
                } else {
                    dataOutputStream.writeUTF("\n");
                }
            }
        }
    }


}
