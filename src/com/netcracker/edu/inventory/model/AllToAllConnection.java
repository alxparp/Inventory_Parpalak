package com.netcracker.edu.inventory.model;

import java.util.Set;

/**
 * Created by oleksandr on 10.11.16.
 */
public interface AllToAllConnection<T extends Device> extends Connection<T, T> {

    ConnectorType getConnectorType();
    boolean addDevice(T device);
    boolean removeDevice(T device);
    boolean containDevice(T device);
    Set<T> getAllDevices();
    int getCurSize();
    int getMaxSize();
    
}
