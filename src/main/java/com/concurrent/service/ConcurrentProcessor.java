package com.concurrent.service;

/**
 * Interface defining the concurrent processes for object of type T containing values V
 * <T> object to be processed concurrently
 * <V> type of values for T
 */
public interface ConcurrentProcessor<T, V> {
    /**
     * Read and print values for object of Type T after locking the resource for read
     * i.e concurrent reads will be allowed for the object
     *
     * @param object Object of type T to be read and printed
     */
    void readAndPrintValues(T object);

    /**
     * Add value V to object of T after locking the resource for write
     * i.e concurrent write will not be allowed for the object
     *
     * @param object Object of type T to be added
     * @param value Value of type V to be added
     */
    void addValue(T object, V value);


//Todo : implement them afterwards
    /**
     * Modify value V of object of T after locking the resource for write
     * i.e concurrent write will not be allowed for the object
     *
     * @param object Object of type T to be modified
     * @param oldValue Value to be modified
     * @param newValue Value to be set
     * @return true of value is modified
     */
//    boolean modifyValue(T object, V oldValue, V newValue);

    /**
     * Delete value V from object of T after locking the resource for write
     * i.e concurrent write will not be allowed for the object
     *
     * @param object object of type T to me modified
     * @param value value to be deleted≈
     */
//    boolean deleteValue(T object, V value);
}
