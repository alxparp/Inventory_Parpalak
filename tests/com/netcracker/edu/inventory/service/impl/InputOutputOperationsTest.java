package com.netcracker.edu.inventory.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test only methods are presents
 *
 * Created by makovetskyi on 11/4/2016.
 */
public class InputOutputOperationsTest {

    InputOutputOperations inputOutputOperations;

    @Before
    public void setUp() throws Exception {
        inputOutputOperations = new InputOutputOperations();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void writeDevice() throws Exception {
        inputOutputOperations.writeDevice(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readDevice() throws Exception {
        inputOutputOperations.readDevice(null);
    }

    @Test
    public void outputDevice() throws Exception {
        inputOutputOperations.outputDevice(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputDevice() throws Exception {
        inputOutputOperations.inputDevice(null);
    }

    @Test
    public void serializeDevice() throws Exception {
        inputOutputOperations.serializeDevice(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deserializeDevice() throws Exception {
        inputOutputOperations.deserializeDevice(null);
    }

    @Test
    public void writeRack() throws Exception {
        inputOutputOperations.writeRack(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readRack() throws Exception {
        inputOutputOperations.readRack(null);
    }

    @Test
    public void outputRack() throws Exception {
        inputOutputOperations.outputRack(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputRack() throws Exception {
        inputOutputOperations.inputRack(null);
    }

    @Test
    public void serializeRack() throws Exception {
        inputOutputOperations.serializeRack(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deserializeRack() throws Exception {
        inputOutputOperations.deserializeRack(null);
    }

    @Test
    public void writeConnection() throws Exception {
        inputOutputOperations.writeConnection(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readConnection() throws Exception {
        inputOutputOperations.readConnection(null);
    }

    @Test
    public void outputConnection() throws Exception {
        inputOutputOperations.outputConnection(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputConnection() throws Exception {
        inputOutputOperations.inputConnection(null);
    }

}