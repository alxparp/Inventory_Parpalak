package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.Connection;

import java.io.*;

/**
 * Created by oleksandr on 11.11.16.
 */
public interface ConnectionService {

    /**
     * Method check validity of Connection for write to char output stream
     *
     * @param connection - validated Connection
     * @return true - if Connection is valid
     *         false - if Connection is not valid
     */
    boolean isValidConnectionForWriteToStream(Connection connection);

    /**
     * Write Connection instance in to symbol stream
     *
     * @param connection - source Connection
     * @param writer - targeted symbol stream
     */
    void writeConnection(Connection connection, Writer writer) throws IOException;

    /**
     * Read Connection instance from symbol stream
     *
     * @param reader - source symbol stream
     * @return - received Connection instance
     */
    Connection readConnection(Reader reader) throws IOException, ClassNotFoundException;

    /**
     * Write Connection instance in to binary stream
     *
     * @param connection - source Connection
     * @param outputStream - targeted binary stream
     */
    void outputConnection(Connection connection, OutputStream outputStream) throws IOException;

    /**
     * Read Connection instance from binary stream
     *
     * @param inputStream - source binary stream
     * @return - received Connection instance
     */
    Connection inputConnection(InputStream inputStream) throws IOException, ClassNotFoundException;

}
