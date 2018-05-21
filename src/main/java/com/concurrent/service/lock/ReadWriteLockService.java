package com.concurrent.service.lock;

import com.concurrent.service.ConcurrentObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Concurrent read write from object of type T containing values V
 * <T> object to be processed concurrently
 * <V> type of values for T
 */
public class ReadWriteLockService<T extends ConcurrentObject<?, V>, V> extends LockService<T, V> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReadWriteLockService.class.getName());

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public ReadWriteLockService(T concurrentObject) {
        super(concurrentObject);
    }

    @Override
    public void readObject(T object) {
        readWriteLock.readLock().lock();
        try {
            if (object != null) {
                LOGGER.debug("Printing Values");
                getConcurrentObject().printValues();
            } else {
                LOGGER.info("Unable to print values. Object is null !!");
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public void modifyObject(T object, V value) {
        readWriteLock.writeLock().lock();
        try {
            if (object != null) {
                LOGGER.debug("Adding Value ({})", value);
                getConcurrentObject().addValue(value);
            } else {
                LOGGER.info("Unable to add value. Object is null !!");
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
