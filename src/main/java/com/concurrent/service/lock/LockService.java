package com.concurrent.service.lock;

import com.concurrent.service.ConcurrentObject;
import com.concurrent.service.ConcurrentProcessTask;

public abstract class LockService<T extends ConcurrentObject<?, V>, V> implements ConcurrentProcessTask<T, V> {

    private T concurrentObject;

    public LockService(T concurrentObject) {
        this.concurrentObject = concurrentObject;
    }

    public void readObject() {
        readObject(this.getConcurrentObject());
    }

    public void modifyObject(V value) {
        modifyObject(getConcurrentObject(), value);
    }

    /**
     * Getter for property 'concurrentObject'.
     *
     * @return Value for property 'concurrentObject'.
     */
    public T getConcurrentObject() {
        return concurrentObject;
    }
}
