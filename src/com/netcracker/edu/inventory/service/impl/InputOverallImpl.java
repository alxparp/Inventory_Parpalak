package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.impl.ConnectionPK;
import com.netcracker.edu.inventory.model.impl.DevicePK;
import com.netcracker.edu.inventory.service.impl.factorymethod.EntitySelector;
import com.netcracker.edu.location.Trunk;
import com.netcracker.edu.location.impl.TrunkStubImpl;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 23.01.2017.
 */
class InputOverallImpl {

    protected static Logger LOGGER = Logger.getLogger(InputOverallImpl.class.getName());

    private EntitySelector selector = new EntitySelector();

    FeelableEntity inputOverall(InputStream inputStream) throws IOException, ClassNotFoundException {
        if(inputStream == null) {
            IllegalArgumentException ex = new IllegalArgumentException("InputStream should not be null");
            LOGGER.log(Level.SEVERE, "InputStream should not be null", ex);
            throw ex;
        }

        DataInputStream dataInput = new DataInputStream(inputStream);

        if(dataInput.available() == 0) {
            LOGGER.log(Level.INFO, "Запрашиваемое устройство или соединение отсутствует!");
            return null;
        }

        Class clazz = Class.forName(dataInput.readUTF());
        List<FeelableEntity.Field> fields = new ArrayList<FeelableEntity.Field>();
        FeelableEntity feelableEntity = selector.getEntity(clazz);

        if(feelableEntity instanceof Connection) {
            setConnectionProperties(dataInput, fields, (Connection) feelableEntity);
        } else  {
            setDeviceProperties(dataInput, fields, (Device)feelableEntity);
        }

        feelableEntity.fillAllFields(fields);
        return feelableEntity;
    }

    private void setConnectionProperties(DataInputStream dataInput, List<FeelableEntity.Field> fields, Connection connection) throws IOException {
        List<FeelableEntity.Field> emptyFields = connection.getAllFieldsList();
        String temp = null;

        for(int i = 0; i < emptyFields.size(); i++) {

            inputConnectionByType(i, temp, dataInput, fields, emptyFields);

        }
    }

    private void setDeviceProperties(DataInputStream dataInput, List<FeelableEntity.Field> fields, Device device) throws IOException {
        // In
        int in = dataInput.readInt();
        fields.add(new FeelableEntity.Field(Integer.class, in));

        // холостой вызов, type инициализируется автоматически
        String temp = dataInput.readUTF();
        fields.add(null);
        List<FeelableEntity.Field> emptyFields = device.getAllFieldsList();

        for(int i = 2; i < emptyFields.size(); i++) {

            inputConnectionByType(i, temp, dataInput, fields, emptyFields);

        }
    }

    private void inputConnectionByType(int i, String temp, DataInputStream dataInput, List<FeelableEntity.Field> fields, List<FeelableEntity.Field> emptyFields) throws IOException {
        // String
        if(emptyFields.get(i).getType().equals(String.class)) {
            temp = dataInput.readUTF();
            if(!temp.equals("\n"))
                fields.add(new FeelableEntity.Field(String.class,temp));
            else
                fields.add(new FeelableEntity.Field(String.class, null)); // or null for manufacturer
        }

        // Date
        if(emptyFields.get(i).getType().equals(Date.class)) {
            long productionDate = dataInput.readLong();
            if(productionDate != -1)
                fields.add(new FeelableEntity.Field(Date.class, new Date(productionDate)));
            else
                fields.add(new FeelableEntity.Field(Date.class, null));
        }

        // Integer
        if(emptyFields.get(i).getType().equals(Integer.class)) {
            fields.add(new FeelableEntity.Field(Integer.class, dataInput.readInt()));
        }

        // Device
        if(emptyFields.get(i).getType().equals(Device.class)) {
            temp = dataInput.readUTF();
            if(!temp.equals("\n")) {
                String parameters[] = temp.split(" ");
                int numberIn = Integer.parseInt(parameters[1]);
                Device device = new DevicePK(numberIn);
                fields.add(new FeelableEntity.Field(emptyFields.get(i).getType(), device));
            }
            else
                fields.add(new FeelableEntity.Field(emptyFields.get(i).getType(), null));
        }

        // Connection
        if(emptyFields.get(i).getType().equals(Connection.class)) {
            temp = dataInput.readUTF();
            if(!temp.equals("\n")) {
                String parameters[] = temp.split(" ");
                int serialNumber = Integer.parseInt(parameters[1]);
                String route = parameters[2];
                String alias = "";
                for(int j = 3; j < parameters.length; j++)
                    alias += parameters[j] + " ";
                alias = alias.trim();
                Connection connection = new ConnectionPK(new TrunkStubImpl(route, alias), serialNumber);
                fields.add(new FeelableEntity.Field(emptyFields.get(i).getType(), connection));
            }
            else
                fields.add(new FeelableEntity.Field(emptyFields.get(i).getType(), null));
        }

        // Enum
        if(emptyFields.get(i).getType().equals(Enum.class)) {
            temp = dataInput.readUTF();
            if(!temp.equals("\n"))
                fields.add(new FeelableEntity.Field(Enum.class, temp));
            else
                fields.add(new FeelableEntity.Field(Enum.class, null));
        }

        // Trunk
        if(emptyFields.get(i).getType().equals(Trunk.class)) {
            temp = dataInput.readUTF();
            if(!temp.equals("\n")) {
                String parameters[] = temp.split(" ");
                String route = parameters[0];
                String alias = parameters[1];
                fields.add(new FeelableEntity.Field(emptyFields.get(i).getType(), new TrunkStubImpl(route, alias)));
            }
            else
                fields.add(new FeelableEntity.Field(emptyFields.get(i).getType(), null));
        }

        // List
        if(emptyFields.get(i).getType().equals(List.class)) {
            List list = new ArrayList();
            inputCollection(dataInput, list);
            fields.add(new FeelableEntity.Field(List.class, list));
        }

        // Set
        if(emptyFields.get(i).getType().equals(Set.class)) {
            Set<Device> set = new HashSet<Device>();
            List list = new ArrayList(set);
            inputCollection(dataInput, list);
            fields.add(new FeelableEntity.Field(Set.class, new HashSet(list)));
        }

    }

    private void inputCollection(DataInputStream dataInput, List list) throws IOException {
        int tempInt = dataInput.readInt();
        for(int j = 0; j < tempInt; j++) {
            String temp = dataInput.readUTF();
            if(!temp.equals("\n")) {
                String parameters[] = temp.split(" ");
                int number = Integer.parseInt(parameters[1]);
                if(parameters[0].equals("DPK:")) {
                    list.add(new DevicePK(number));
                } else if(parameters[0].equals("CPK:")) {
                    String route = parameters[2];
                    String alias = "";
                    for(int k = 3; k < parameters.length; k++)
                        alias += parameters[k]+" ";
                    alias = alias.trim();
                    list.add(new ConnectionPK(new TrunkStubImpl(route, alias), number));
                } else
                    list.add(null);
            } else {
                list.add(null);
            }
        }
    }

}
