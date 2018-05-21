package com.concurrent.service.lock;

import com.concurrent.service.ConcurrentObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.StampedLock;

/**
 * Concurrent read write from object of type T containing values V
 * <T> object to be processed concurrently
 * <V> type of values for T
 */
public class StampedLockService<T extends ConcurrentObject<?, V>, V> extends LockService<T, V> {

    private final static Logger LOGGER = LoggerFactory.getLogger(StampedLockService.class.getName());

    private StampedLock stampedLock = new StampedLock();

    public StampedLockService(T concurrentObject) {
        super(concurrentObject);
    }

    @Override
    public void readObject(T object) {
        long stamp = stampedLock.tryOptimisticRead();

        if (!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock();
            try {
                printNow(object);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        } else {
            printNow(object);
        }
    }

    private void printNow(T object) {
        if (object != null) {
            LOGGER.debug("Printing Values");
            object.printValues();
        } else {
            LOGGER.info("Unable to print values. Object is null !!");
        }
    }

    @Override
    public void modifyObject(T object, V value) {
        long stamp = stampedLock.writeLock();
        try {
            if (object != null) {
                LOGGER.debug("Adding Value ({})", value);
                object.addValue(value);
            } else {
                LOGGER.info("Unable to add value. Object is null !!");
            }
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
}
