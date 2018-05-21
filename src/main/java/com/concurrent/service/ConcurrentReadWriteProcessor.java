package com.concurrent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Concurrent read write from object of type T containing values V
 * <T> object to be processed concurrently
 * <V> type of values for T
 */
public abstract class ConcurrentReadWriteProcessor<T, V> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConcurrentReadWriteProcessor.class.getName());

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private T object;

    public ConcurrentReadWriteProcessor(T object) {
        this.object = object;
    }

    /**
     * Read and print values for object of Type T after locking the resource for read
     * i.e concurrent reads will be allowed for the object
     */
    public void readAndPrintValues() {
        readWriteLock.readLock().lock();
        try {
            LOGGER.debug("Printing Values");
            printValues();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * Add value V to object of T after locking the resource for write
     * i.e concurrent write will not be allowed for the object
     */
    public void writeValue(V v) {
        readWriteLock.writeLock().lock();
        try {
            LOGGER.debug("Writing Value !!");
            addValue(v);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * Getter for property 'object'.
     *
     * @return Value for property 'object'.
     */
    public T getObject() {
        if (object == null) {
            object = init();
        }
        return object;
    }

    /*
     * Initialises T if its null
     */
    protected abstract T init();

    /**
     * Print values of object T after locking the resource for read
     */
    protected abstract void printValues();

    /**
     * Add value V to object of T after locking the resource for write
     *
     * @param value value to be added to object T
     */
    protected abstract void addValue(V value);
}
