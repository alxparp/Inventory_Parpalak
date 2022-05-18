package com.netcracker.edu.inventory.exception;

import com.netcracker.edu.inventory.model.Device;

/**
 * Created by Alex on 14.10.2016.
 */
public class DeviceValidationException extends RuntimeException {
    public Device invalidObjectDevice;

    public Device getInvalidObjectDevice() {
        return invalidObjectDevice;
    }

    public void setInvalidObjectDevice(Device invalidObjectDevice) {
        this.invalidObjectDevice = invalidObjectDevice;
    }

    public DeviceValidationException(Device invalidObjectDevice, String message) {
        super("Device is not valid for operation " + message);
        this.invalidObjectDevice = invalidObjectDevice;
    }
}
