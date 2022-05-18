package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.OpticFiber;
import com.netcracker.edu.inventory.model.impl.TwistedPair;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.model.impl.Wireless;

/**
 * Created by User on 13.11.2016.
 */
class Validator {

    public boolean isValidDeviceForInsertToRack(Device device) {
        if(device == null || device.getType() == null || device.getIn() == 0) {
            return false;
        }
        return true;
    }

    public boolean isValidDeviceForWriteToStream(Device device) {
        if(device != null)
            if((device.getManufacturer() == null || !device.getManufacturer().contains("|"))
                    && (device.getModel() == null || !device.getModel().contains("|"))
                    && (device.getType() == null || !device.getType().contains("|"))) {
                if(device instanceof WifiRouter)
                    if( ((WifiRouter) device).getSecurityProtocol() == null || !((WifiRouter) device).getSecurityProtocol().contains("|"))
                        return true;
                    else
                        return false;
                return true;
            }

        return false;
    }

    public boolean isValidConnectionForWriteToStream(Connection connection) {
        if(connection != null)
            if((connection.getStatus() == null || !connection.getStatus().contains("|"))) {
                if(connection instanceof TwistedPair)
                    if( ((TwistedPair) connection).getType() != null && ((TwistedPair) connection).getType().toString().contains("|"))
                        return false;
                if(connection instanceof OpticFiber)
                    if(((OpticFiber) connection).getMode() != null && ((OpticFiber) connection).getMode().toString().contains("|"))
                        return false;
                if(connection instanceof Wireless)
                    if(((Wireless) connection).getProtocol() != null && ((Wireless) connection).getProtocol().contains("|")
                            || ((Wireless) connection).getTechnology() != null && ((Wireless) connection).getTechnology().contains("|"))
                        return false;
                return true;
            }

        return false;
    }

}
