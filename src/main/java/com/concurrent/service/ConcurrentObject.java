package com.concurrent.service;

public interface ConcurrentObject<T, V> {

    /**
     * Print values of object T
     *
     */
    void printValues();

    /**
     * Add value V to object of T after locking the resource for write
     *
     * @param value value to be added to object T
     */
    void addValue(V value);
}
