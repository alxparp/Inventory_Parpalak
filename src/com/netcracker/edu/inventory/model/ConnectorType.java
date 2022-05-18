package com.netcracker.edu.inventory.model;

/**
 * Created by oleksandr on 10.11.16.
 */
public enum ConnectorType {
    need_init("<need_init>"),
    PowerPS_C("PowerP/S(C)"),
    PowerPS_E("PowerP/S(E)"),
    PowerPS_F("PowerP/S(F)"),
    USB("USB"),
    RJ45("RJ-45"),
    RJ14("RJ-14"),
    TConnector("T-Connector"),
    VampireTap("Vampire tap"),
    Wireless("Wireless"),
    FiberConnector_FC("Fiber-Connector(FC)"),
    FiberConnector_SC("Fiber-Connector(SC)");

    private String fullName;

    ConnectorType(String fullName) {
        this.fullName = fullName;
    }

    String getFullName() {
        return fullName;
    }
}
