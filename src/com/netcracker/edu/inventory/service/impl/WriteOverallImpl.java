package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.impl.ConnectionPK;
import com.netcracker.edu.location.Trunk;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 23.01.2017.
 */
class WriteOverallImpl {

    protected static Logger LOGGER = Logger.getLogger(WriteOverallImpl.class.getName());
    private ServiceImpl service = new ServiceImpl();

    void writeOverall(FeelableEntity feelableEntity, Writer writer) throws IOException {
        if(feelableEntity != null) {
            if(writer == null) {
                IllegalArgumentException ex = new IllegalArgumentException("Writer should not be null");
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                throw ex;
            }
            if(feelableEntity instanceof Connection) {
                if(!new Validator().isValidConnectionForWriteToStream((Connection) feelableEntity)) {
                    IllegalArgumentException ex = new IllegalArgumentException("Connection.writeConnection can't write connection");
                    LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    throw ex;
                }
            } else {
                if (!new Validator().isValidDeviceForWriteToStream((Device) feelableEntity)) {
                    DeviceValidationException ex = new DeviceValidationException((Device) feelableEntity, "Device.writeDevice can't write device");
                    LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    throw ex;
                }
            }

            writer.write(feelableEntity.getClass().getName()+"\n");

            List<FeelableEntity.Field> field = feelableEntity.getAllFieldsList();

            if(feelableEntity instanceof Connection) {
                getConnectionProperties(writer, field);
            } else {
                getDevProp(writer, field);
            }

            writer.write('\n');
        }
    }

    private void getConnectionProperties (Writer writer, List<FeelableEntity.Field> field) throws IOException {
        String firstSplitter = " | ";
        String secondSplitter = "| ";

        for(int i = 0; i < field.size(); i++) {
            if(i == field.size()-1 && !(field.get(i).getType().equals(List.class) || field.get(i).getType().equals(Set.class))) {
                firstSplitter = " |";
                secondSplitter = "|";
            }
            writeValueByType(writer, field, firstSplitter, secondSplitter, i);
        }
    }

    private void getDevProp(Writer writer, List<FeelableEntity.Field> field) throws IOException {
        writer.write("[" + field.get(0).getValue() + "] ");
        String firstSplitter = " | ";
        String secondSplitter = "| ";

        for(int i = 1; i < field.size(); i++) {
            if(i == field.size()-1) {
                firstSplitter = " |";
                secondSplitter = "|";
            }
            writeValueByType(writer, field, firstSplitter, secondSplitter, i);
        }
    }

    private void writeValueByType(Writer writer, List<FeelableEntity.Field> field, String firstSplitter, String secondSplitter, int i) throws IOException {

        // Integer
        if(field.get(i).getType() == Integer.class) {
            if (field.get(i).getValue() != null)
                writer.write(field.get(i).getValue() + firstSplitter);
            else
                writer.write(secondSplitter);
            return;
        }

        // String
        if(field.get(i).getType() == String.class) {
            if(field.get(i).getValue() != null)
                writer.write(field.get(i).getValue().toString() + firstSplitter);
            else
                writer.write(secondSplitter);
            return;
        }

        // Date
        if(field.get(i).getType() == Date.class) {
            if(field.get(i).getValue() != null) {
                writer.write(((Date)field.get(i).getValue()).getTime() + firstSplitter);
            } else {
                writer.write("-1 " + secondSplitter);
            }
            return;
        }

        // Device
        if(field.get(i).getType() == Device.class) {
            Device dev = (Device) field.get(i).getValue();
            if(dev != null) {
                writer.write("DPK: " + service.getIndependentCopy(dev).getPrimaryKey().getIn() + firstSplitter);
            } else {
                writer.write(secondSplitter);
            }
            return;
        }

        // Connection
        if(field.get(i).getType() == Connection.class) {
            Connection conn = (Connection) field.get(i).getValue();
            if(conn != null) {
                ConnectionPK connectionPK = (ConnectionPK) service.getIndependentCopy(conn).getPrimaryKey();
                writer.write("CPK: " +  connectionPK.getSerialNumber() +
                        " " + connectionPK.getTrunk().getRoute() +
                        " " + connectionPK.getTrunk().getAlias() + firstSplitter);
            } else {
                writer.write(secondSplitter);
            }
            return;
        }

        // Enum
        if(field.get(i).getType() == Enum.class) {
            if(field.get(i).getValue() != null) {
                writer.write(field.get(i).getValue().toString() + firstSplitter);
            } else {
                writer.write(secondSplitter);
            }
            return;
        }

        // Trunk
        if(field.get(i).getType() == Trunk.class) {
            Trunk trunk = (Trunk) field.get(i).getValue();
            if(trunk != null) {
                writer.write(trunk.getRoute() + " " + trunk.getAlias() + firstSplitter);
            } else {
                writer.write(secondSplitter);
            }
        }

        // Set
        if(field.get(i).getType() == Set.class) {
            Set<Device> set = (HashSet) field.get(i).getValue();
            List list = new ArrayList(set);
            writeCollection(list, writer, firstSplitter);
            return;
        }

        // List
        if(field.get(i).getType() == List.class) {
            List list = (List)field.get(i).getValue();
            writeCollection(list, writer, firstSplitter);
            return;
        }
    }

    // writeCollection
    private void writeCollection(List list, Writer writer, String firstSplitter) throws IOException {
        if(list != null) {
            writer.write(list.size() + firstSplitter);
            for(int j = 0; j < list.size(); j++) {
                if (list.get(j) != null) {
                    if(list.get(j) instanceof Device) {
                        writer.write("DPK: " + service.getIndependentCopy((Device)list.get(j)).getPrimaryKey().getIn() + firstSplitter);
                    } else if(list.get(j) instanceof Connection) {
                        Connection connection = (Connection)list.get(j);
                        ConnectionPK connectionPK = (ConnectionPK) service.getIndependentCopy(connection).getPrimaryKey();
                        writer.write(" CPK: " +  connectionPK.getSerialNumber() +
                                " " + connectionPK.getTrunk().getRoute() +
                                " " + connectionPK.getTrunk().getAlias() + firstSplitter);
                    } else {
                        writer.write(firstSplitter);
                    }
                } else {
                    writer.write(firstSplitter);
                }
            }
        }
    }

}
