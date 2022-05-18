package com.netcracker.edu.inventory.model;

/**
 * Created by makovetskyi on 16.11.2016.
 */
public interface Unique<T extends Unique.PrimaryKey> {

    boolean isPrimaryKey();

    T getPrimaryKey();

    interface PrimaryKey {
    }

}
