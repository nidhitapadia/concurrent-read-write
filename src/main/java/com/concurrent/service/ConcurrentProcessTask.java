package com.concurrent.service;

/**
 * Interface defining the concurrent processes for object of type T containing values V
 * <T> object to be processed concurrently
 * <V> type of values for T
 */
public interface ConcurrentProcessTask<T extends ConcurrentObject<?, V>, V> {
    /**
     * Read values for object of Type T after locking the resource for read
     * i.e concurrent reads will be allowed for the object
     *
     * @param object Object of type T to be read and printed
     */
    void readObject(T object);

    /**
     * Modify object of T with value V after locking the resource for write
     * i.e concurrent write will not be allowed for the object
     *
     * @param object Object of type T to be added
     * @param value Value of type V to be added
     */
    void modifyObject(T object,  V value);
}
