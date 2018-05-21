package com.concurrent.service.lock;

import com.concurrent.service.ConcurrentObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Concurrent read write from object of type T containing values V
 * <T> object to be processed concurrently
 * <V> type of values for T
 */
public class SynchronizedService<T extends ConcurrentObject<?, V>, V> extends LockService<T, V> {

    private final static Logger LOGGER = LoggerFactory.getLogger(SynchronizedService.class.getName());

    Object lock = new Object();

    public SynchronizedService(T concurrentObject) {
        super(concurrentObject);
    }

    @Override
    public void readObject(T object) {
        synchronized (lock) {
            if (object != null) {
                LOGGER.debug("Printing Values");
                object.printValues();
            } else {
                LOGGER.info("Unable to print values. Object is null !!");
            }
        }
    }

    @Override
    public void modifyObject(T object, V value) {
        synchronized (lock) {
            if (object != null) {
                LOGGER.debug("Adding Value ({})", value);
                object.addValue(value);
            } else {
                LOGGER.info("Unable to add value. Object is null !!");
            }
        }
    }
}
