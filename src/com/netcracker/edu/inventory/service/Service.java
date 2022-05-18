package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Unique;

/**The interface Service describe list of services of Inventory component
 *
 * Created by makovetskyi on 05.10.2016.
 */
public interface Service {

    /**
     * Return DeviceService implementation
     *
     * @return implementation of DeviceService interface
     */
    DeviceService getDeviceService();

    /**
     * Return RackService implementation
     *
     * @return implementation of RackService interface
     */
    RackService getRackService();

    /**
     * Return ConnectionService implementation
     *
     * @return implementation of ConnectionService interface
     */
    ConnectionService getConnectionService();

    /**
     * Return ConcurrentIOService implementation
     *
     * @return implementation of ConcurrentIOService interface
     */
    ConcurrentIOService getConcurrentIOService();

    /**
     * Sort array of Device-s by identification number.
     *
     * @param devices - array of Device-s, that need to be sorted
     */
    void sortByIN(Device[] devices);

    /**
     * Filtrate array of Device-s by type
     *
     * @param devices - array of Device-s, that need to be filtrated
     * @param type - type of Devices, that will remain in the array after filtering
     */
    void filtrateByType(Device[] devices, String type);

    /**
     * Create copy of element, were all links on another elements are replaced on his primary keys.
     *
     * @param element - source object
     * @param <T> - type of primary key
     * @return - independent copy
     *         - null, if element is null, or primary key, or no clonable
     */
    <T extends Unique.PrimaryKey> Unique<T> getIndependentCopy(Unique<T> element);

}