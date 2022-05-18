package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;

/**
 * Created by User on 13.11.2016.
 */
class Utilities {

    public void sortByIN(Device[] devices) {

        if(devices == null)
            return;

        for(int i = 0; i < devices.length; i++) {
            for(int j = 0; j < devices.length-i-1;j++) {
                if(devices[j+1] == null) continue;
                if(devices[j] == null && devices[j+1].getIn() == 0 ||
                        devices[j] == null && devices[j+1].getIn() > 0 ||
                        devices[j].getIn() == 0 && devices[j+1].getIn() > 0 ||
                        devices[j].getIn() > devices[j+1].getIn() &&
                                devices[j+1].getIn()!=0 ) {
                    Device temp = devices[j];
                    devices[j] = devices[j+1];
                    devices[j+1] = temp;
                }
            }
        }
    }

    public void filtrateByType(Device[] devices, String type) {
        if(devices == null) {
            return;
        }
        if(type == null) {
            for(int i = 0; i < devices.length; i++) {
                if(devices[i] != null && (devices[i].getType() != null)) {
                    devices[i] = null;
                }
            }
        } else {
            for(int i = 0; i < devices.length; i++) {
                if (devices[i] != null && !type.equals(devices[i].getType())) {
                    devices[i] = null;
                }
            }
        }
    }

}
