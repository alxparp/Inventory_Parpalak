package com.netcracker.edu.inventory.model;

/**
 * Created by makovetskyi on 16.11.2016.
 */
public interface RackPrimaryKey<T extends Device> extends Unique.PrimaryKey, Rack<T> {
}
