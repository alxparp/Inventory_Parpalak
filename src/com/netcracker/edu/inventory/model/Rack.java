package com.netcracker.edu.inventory.model;

import com.netcracker.edu.location.Location;

import java.io.Serializable;

/**
 * The interface Rack describe contract of mutable device-holder,
 * with limited volume of slots. Slots ara ordered and can fills and released randomly. *
 *
 * Created by makovetskyi on 05.10.2016.
 */
public interface Rack<D extends Device> extends Unique<RackPrimaryKey>, Serializable {

    /**
     *
     * @return current location of rack
     */
    Location getLocation();

    /**
     * Set new current location of rack
     *
     * @param location - new location
     */
    void setLocation(Location location);

    /**
     *
     * @return full capacity of rack
     */
    int getSize();

    /**
     *
     * @return free capacity of rack
     */
    int getFreeSize();

    /**
     *
     * @return type of devices, which compatible with this rack
     */
    Class getTypeOfDevices();

    /**
     * Get device object at specified slot of rack
     *
     * @param index - index of slot
     * @return device object at slot - if slot are full
     *         null - if slot are empty
     */

    D getDevAtSlot(int index);

    /**
     * Insert device object to specified slot of rack
     * Can't insert to full slot
     *
     * @param index - index of slot
     * @return true - if devicw was inserted
     *         false - if not
     */
    boolean insertDevToSlot(D device, int index);

    /**
     * Remove device object from specified slot of rack
     *
     * @param index - index of slot
     * @return device object that has been removed - if slot was full
     *         null - if slot was empty
     */
    D removeDevFromSlot(int index);

    /**
     * Get device object with specified identification number
     *
     * @param in - identification number
     * @return device object with specified identification number - if it is present in rack
     *         null - if it is absent in rack
     */
    D getDevByIN(int in);

    /**
     * Get array of devices witch are stored at current object of Rack
     * Returned array not include nulls.
     *
     * @return array of devices
     */
    D[] getAllDeviceAsArray();
}
