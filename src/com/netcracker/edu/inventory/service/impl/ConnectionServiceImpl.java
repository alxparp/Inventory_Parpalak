package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.service.ConnectionService;

import java.io.*;

/**
 * Created by User on 11.12.2016.
 */
class ConnectionServiceImpl implements ConnectionService {

    private Validator connectionValidator = new Validator();
    private InputOutputOperations in = new InputOutputOperations();

    @Override
    public boolean isValidConnectionForWriteToStream(Connection connection) {
        return connectionValidator.isValidConnectionForWriteToStream(connection);
    }

    @Override
    public void writeConnection(Connection connection, Writer writer) throws IOException {
        in.writeConnection(connection, writer);
    }

    @Override
    public Connection readConnection(Reader reader) throws IOException, ClassNotFoundException {
        return in.readConnection(reader);
    }

    @Override
    public void outputConnection(Connection connection, OutputStream outputStream) throws IOException {
        in.outputConnection(connection, outputStream);
    }

    @Override
    public Connection inputConnection(InputStream inputStream) throws IOException, ClassNotFoundException {
        return in.inputConnection(inputStream);
    }
}
