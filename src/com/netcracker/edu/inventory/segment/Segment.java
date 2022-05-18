package com.netcracker.edu.inventory.segment;

import com.netcracker.edu.inventory.model.Unique;

import java.util.Collection;
import java.util.Set;

/**
 * Created by makovetskyi on 16.11.2016.
 */
public interface Segment {

    boolean add(Unique element);

    boolean set(Unique element);

    Unique get(Unique.PrimaryKey pk);

    boolean put(Unique element);

    boolean contain(Unique.PrimaryKey pk);

    Collection<Unique> getGraph();

}
