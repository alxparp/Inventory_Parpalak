package com.netcracker.edu.inventory.model;

/**
 * Created by makovetskyi on 16.11.2016.
 */
public interface ConnectionPrimaryKey<A extends Device, B extends Device> extends  Unique.PrimaryKey, Connection<A, B> {
}
