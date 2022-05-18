/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.*;
import com.netcracker.edu.inventory.service.impl.factorymethod.EntitySelector;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.netcracker.edu.inventory.model.FeelableEntity.*;

/**
 *
 * @author Alex
 */
public class ServiceImpl implements Service {

    private Utilities utilities = new Utilities();
    EntitySelector selector = new EntitySelector();
    FeelableEntity entity;

    static protected Logger LOGGER = Logger.getLogger(ServiceImpl.class.getName());

    @Override
    public DeviceService getDeviceService() {
        return new DeviceServiceImpl();
    }

    @Override
    public RackService getRackService() {
        return new RackServiceImpl();
    }

    @Override
    public ConnectionService getConnectionService() {
        ConnectionServiceImpl connectionService = new ConnectionServiceImpl();
        return connectionService;
    }

    @Override
    public ConcurrentIOService getConcurrentIOService() {
        return new ConcurrentIOServiceImpl();
    }

    @Override
    public void sortByIN(Device[] devices) {
        utilities.sortByIN(devices);
    }

    @Override
    public void filtrateByType(Device[] devices, String type) {
        utilities.filtrateByType(devices, type);
    }

    @Override
    public <T extends Unique.PrimaryKey> Unique<T> getIndependentCopy(Unique<T> element) {

        if(element instanceof Rack) {
            return getIndependentCopyOfRack(element);
        } else if (element instanceof Connection || element instanceof Device) {
            return getIndependentCopyOfDeviceOrConnection(element);
        }
        return null;

    }

    private <T extends Unique.PrimaryKey> Unique<T> getIndependentCopyOfDeviceOrConnection(Unique<T> element) {
        FeelableEntity feelableEntity = (FeelableEntity)element;
        List<Field> list = feelableEntity.getAllFieldsList();
        List<Field> fillList = new ArrayList<Field>();
        for(int i = 0; i < list.size(); i++) {
            Field field = list.get(i);
            // Connection
            if(field.getType() == Connection.class || field.getType() == Device.class) {
                if (field.getValue() != null) {
                    fillList.add(new Field(field.getType(), ((Unique) field.getValue()).getPrimaryKey()));
                } else {
                    fillList.add(new Field(field.getType(), null));
                }
            }
            // List
            else if(field.getType() == List.class) {
                List<Unique> connList = ((List)field.getValue());
                List<Unique.PrimaryKey> newConnList = new ArrayList<Unique.PrimaryKey>();
                for(int j = 0; j < connList.size(); j++) {
                    if(connList.get(j)!=null) {
                        newConnList.add((connList.get(j)).getPrimaryKey());
                    } else {
                        newConnList.add(null);
                    }
                }
                fillList.add(new Field(field.getType(), newConnList));
            }
            // Set
            else if (field.getType() == Set.class) {
                Set<Unique> devSet = ((Set)field.getValue());
                Set<Unique.PrimaryKey> newDevSet = new HashSet<Unique.PrimaryKey>();
                for(Unique dev : devSet) {
                    newDevSet.add(dev.getPrimaryKey());
                }
                fillList.add(new Field(field.getType(), newDevSet));
            }
            // Primitive
            else {
                fillList.add(list.get(i));
            }
        }

        entity = selector.getEntity(feelableEntity.getClass());

        entity.fillAllFields(fillList);
        return (Unique<T>) entity;
    }

    private <T extends Unique.PrimaryKey> Unique<T> getIndependentCopyOfRack(Unique<T> element) {
        Rack rack = (Rack)element;
        Rack newRack = new RackArrayImpl(rack.getSize(),rack.getTypeOfDevices());
        for(int i = 0; i < rack.getSize(); i++) {
            if(rack.getDevAtSlot(i) != null) {
                newRack.insertDevToSlot((Device) getIndependentCopyOfDeviceOrConnection(rack.getDevAtSlot(i)), i);
            }
        }
        newRack.setLocation(rack.getLocation());
        return newRack;
    }

}
