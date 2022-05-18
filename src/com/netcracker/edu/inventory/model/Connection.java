package com.netcracker.edu.inventory.model;

import com.netcracker.edu.location.Trunk;

import java.io.Serializable;
import java.util.List;

/**
 * The interface Connection describe contract of mutable POJO,
 * witch represent connecting equipment-entity as object.
 *
 * Created by makovetskyi on 10.11.16.
 */
public interface Connection<A extends Device, B extends Device> extends FeelableEntity, Unique<ConnectionPrimaryKey>, Serializable, Comparable {

    String PLANED = "Planed"; // must be default
    String ON_BUILD = "On build";
    String READY = "Ready";
    String USED = "Used";
    String ON_DISASSEMBLING = "On disassembling";
    String DISASSEMBLED = "Disassembled";

    Trunk getTrunk();

    void setTrunk(Trunk trunk);

    int getSerialNumber();

    void setSerialNumber(int serialNumber);

    String getStatus();

    void setStatus(String status);

    interface HaveAPoint {
        ConnectorType getAPointConnectorType();
    }

    interface HaveBPoint {
        ConnectorType getBPointConnectorType();
    }

    interface OneAPoint<A extends Device, B extends Device> extends Connection<A, B>, HaveAPoint {
        A getAPoint();
        void setAPoint(A device);
    }

    interface OneBPoint<A extends Device, B extends Device> extends Connection<A, B>, HaveBPoint {
        B getBPoint();
        void setBPoint(B device);
    }

    interface MultipleAPoint<A extends Device, B extends Device> extends Connection<A, B>, HaveAPoint {
        List<A> getAPoints();
        void setAPoints(List<A> devices);
        int getACapacity();
        A getAPoint(int deviceNumber);
        void setAPoint(A device, int deviceNumber);
    }

    interface MultipleBPoint<A extends Device, B extends Device> extends Connection<A, B>, HaveBPoint {
        List<B> getBPoints();
        void setBPoints(List<B> devices);
        int getBCapacity();
        B getBPoint(int deviceNumber);
        void setBPoint(B device, int deviceNumber);
    }

}
