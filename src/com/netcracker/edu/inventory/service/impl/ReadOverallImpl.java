package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.impl.ConnectionPK;
import com.netcracker.edu.inventory.model.impl.DevicePK;
import com.netcracker.edu.inventory.service.impl.factorymethod.EntitySelector;
import com.netcracker.edu.location.Trunk;
import com.netcracker.edu.location.impl.TrunkStubImpl;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 23.01.2017.
 */
class ReadOverallImpl {

    protected static Logger LOGGER = Logger.getLogger(ReadOverallImpl.class.getName());
    private EntitySelector selector = new EntitySelector();

    FeelableEntity readOverall(Reader reader) throws IOException, ClassNotFoundException {
        if(reader == null) {
            IllegalArgumentException ex = new IllegalArgumentException("Reader should not be null");
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }

        int temp = reader.read();

        if(temp == -1) {
            LOGGER.log(Level.INFO, "Запрашиваемое устройство отсутствует!");
            return null;
        }

        if(temp == '\n')
            return null;

        String className = "", deviceType = "";
        while(temp != '\n') {
            className += (char)temp;
            temp = reader.read();
        }
        Class clazz = Class.forName(className);

        temp = reader.read();
        while(temp != '\n') {
            deviceType += (char)temp;
            temp = reader.read();
        }

        StringTokenizer st = new StringTokenizer(deviceType, "][|");

        List<FeelableEntity.Field> fields = new ArrayList<FeelableEntity.Field>();

        FeelableEntity feelableEntity = selector.getEntity(clazz);

        if(feelableEntity instanceof Connection) {
            getConnectionMethods(st, fields, (Connection) feelableEntity);
        } else {
            getDevMethods(st, fields, (Device) feelableEntity);
        }

        feelableEntity.fillAllFields(fields);
        return feelableEntity;
    }

    private void getConnectionMethods(StringTokenizer st, List<FeelableEntity.Field> fields, Connection connection) {
        String resultDevProperty;

        List<FeelableEntity.Field> emptyFields = connection.getAllFieldsList();

        for(int i = 0; i < emptyFields.size(); i++) {

            resultDevProperty = getDevProperty(st);

            readValueByType(i, resultDevProperty, st, fields, emptyFields);

        }
    }

    private String getDevProperty(StringTokenizer st) {
        if(st.hasMoreElements()) {
            String temp = ((String)st.nextElement());

            if(temp.trim().equals("") && temp.length() == 1) {
                return null;
            }

            if(temp.trim().equals("") && temp.length() > 1) {
                return temp.substring(2);
            }

            if(temp.trim().length()+2 < temp.length())
                return temp.substring(1,temp.length()-1);

            return temp.trim();
        }
        return null;
    }

    private void getDevMethods(StringTokenizer st, List<FeelableEntity.Field> fields, Device device) {
        String resultDevProperty = getDevProperty(st);
        if(resultDevProperty != "")
            fields.add(new FeelableEntity.Field(Integer.class,Integer.parseInt(resultDevProperty)));

        // холостой вызов, type инициализируется автоматически
        getDevProperty(st);
        fields.add(null);
        List<FeelableEntity.Field> emptyFields = device.getAllFieldsList();

        for(int i = 2; i < emptyFields.size(); i++) {

            resultDevProperty = getDevProperty(st);

            readValueByType(i, resultDevProperty, st, fields, emptyFields);
        }

    }

    // read
    private void readValueByType(int i, String resultDevProperty, StringTokenizer st, List<FeelableEntity.Field> fields, List<FeelableEntity.Field> emptyFields) {
        // String
        if(emptyFields.get(i).getType().equals(String.class)) {
            fields.add(new FeelableEntity.Field(String.class, resultDevProperty));
        }

        // Date
        if(emptyFields.get(i).getType().equals(Date.class)) {
            if (!resultDevProperty.equals("-1") && !resultDevProperty.equals(""))
                fields.add(new FeelableEntity.Field(Date.class, new Date(Long.parseLong(resultDevProperty))));
            else
                fields.add(new FeelableEntity.Field(Date.class, null));
        }

        // Integer
        if(emptyFields.get(i).getType().equals(Integer.class)) {
            if (resultDevProperty != null && !resultDevProperty.equals(""))
                fields.add(new FeelableEntity.Field(Integer.class,Integer.parseInt(resultDevProperty)));
            else
                fields.add(new FeelableEntity.Field(Integer.class, null));
        }

        // Device
        if(emptyFields.get(i).getType().equals(Device.class)) {
            if(resultDevProperty != null && !resultDevProperty.equals("")) {
                String dpkParameters[] = resultDevProperty.split(" ");
                int numberIn = Integer.parseInt(dpkParameters[1]);
                fields.add(new FeelableEntity.Field(Device.class, new DevicePK(numberIn)));
            } else {
                fields.add(new FeelableEntity.Field(emptyFields.get(i).getType(), null));
            }
        }

        // Connection
        if(emptyFields.get(i).getType().equals(Connection.class)) {
            if(resultDevProperty != null && !resultDevProperty.equals("")) {
                String cpkParameters[] = resultDevProperty.split(" ");
                int serialNumber = Integer.parseInt(cpkParameters[1]);
                String route = cpkParameters[2];
                String alias = "";
                for(int j = 3; j < cpkParameters.length; j++)
                    alias += cpkParameters[j] + " ";
                alias = alias.trim();
                fields.add(new FeelableEntity.Field(Connection.class, new ConnectionPK(new TrunkStubImpl(route, alias),serialNumber)));
            } else {
                fields.add(new FeelableEntity.Field(emptyFields.get(i).getType(), null));
            }
        }

        // Enum
        if(emptyFields.get(i).getType().equals(Enum.class)) {
            fields.add(new FeelableEntity.Field(Enum.class, resultDevProperty));
        }

        // Trunk
        if(emptyFields.get(i).getType().equals(Trunk.class)) {
            if(resultDevProperty != null && !resultDevProperty.equals("")) {
                String trunkParameters[] = resultDevProperty.split(" ");
                String route = trunkParameters[0];
                String alias = "";
                for(int j = 1; j < trunkParameters.length; j++)
                    alias += trunkParameters[j] + " ";
                alias = alias.trim();
                fields.add(new FeelableEntity.Field(Connection.class, new TrunkStubImpl(route,alias)));
            } else {
                fields.add(new FeelableEntity.Field(emptyFields.get(i).getType(), null));
            }
        }

        // List
        if(emptyFields.get(i).getType().equals(List.class)) {
            List list = new ArrayList();
            readCollection(list, resultDevProperty, st);
            fields.add(new FeelableEntity.Field(List.class, list));
        }

        // Set
        if(emptyFields.get(i).getType().equals(Set.class)) {
            List list = new ArrayList();
            readCollection(list, resultDevProperty, st);
            fields.add(new FeelableEntity.Field(Set.class, new HashSet(list)));
        }
    }

    private void readCollection(List list, String resultDevProperty, StringTokenizer st) {
        int count = Integer.parseInt(resultDevProperty);
        for(int j = 0; j < count; j++) {
            resultDevProperty = getDevProperty(st);
            if(resultDevProperty != null && !resultDevProperty.equals("")) {
                String parameters[] = resultDevProperty.split(" ");
                if(parameters[0].equals("DPK:")) {
                    int numberIn = Integer.parseInt(parameters[1]);
                    list.add(new DevicePK(numberIn));
                } else if (parameters[0].equals("CPK:")) {
                    int serialNumber = Integer.parseInt(parameters[1]);
                    String route = parameters[2];
                    String alias = "";
                    for(int k = 3; k < parameters.length; k++)
                        alias += parameters[k] + " ";
                    alias = alias.trim();
                    list.add(new ConnectionPK(new TrunkStubImpl(route, alias), serialNumber));
                } else {
                    list.add(null);
                }
            } else {
                list.add(null);
            }
        }
    }

}
