package com.concurrent.service.tree;


import com.concurrent.service.lock.LockService;
import com.concurrent.service.lock.ReadWriteLockService;
import com.concurrent.service.lock.StampedLockService;
import com.concurrent.service.lock.SynchronizedService;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TreeServiceTest {

    private ExecutorService readWriteExecutor = null;

    @Test
    public void testConcurrentReadWriteTask() {

        ReadWriteLockService readWriteLockService = new ReadWriteLockService(newConcurrentTreeMap());
        SynchronizedService synchronizedService = new SynchronizedService(newConcurrentTreeMap());
        StampedLockService stampedLockService = new StampedLockService(newConcurrentTreeMap());
        long timeElapsedReadWriteLock;
        long timeElapsedSynchronizedLock;
        long timeElapsedStampedLock;
        final int NUMBER_OF_THREADS = 100;
        final int NUMBER_OF_ITERATIONS = 200;

        long startTimeMillis = System.currentTimeMillis();
        executeTask(readWriteLockService, 1L, NUMBER_OF_THREADS, NUMBER_OF_ITERATIONS);
        timeElapsedReadWriteLock = getTimeElapsedInMillis(startTimeMillis);

        System.out.println("********************* ReadWriteLock Task finished***************************");

        startTimeMillis = System.currentTimeMillis();
        executeTask(synchronizedService, 1L, NUMBER_OF_THREADS, NUMBER_OF_ITERATIONS);
        timeElapsedSynchronizedLock = getTimeElapsedInMillis(startTimeMillis);

        System.out.println("********************* Synchronised Task finished***************************");

        startTimeMillis = System.currentTimeMillis();
        executeTask(stampedLockService, 1L, NUMBER_OF_THREADS, NUMBER_OF_ITERATIONS);
        timeElapsedStampedLock = getTimeElapsedInMillis(startTimeMillis);

        System.out.println("********************* StampedLock Task finished***************************");

        System.out.println("timeElapsedReadWriteLock : " + timeElapsedReadWriteLock);
        System.out.println("timeElapsedSynchronizedLock : " + timeElapsedSynchronizedLock);
        System.out.println("timeElapsedStampedLock : " + timeElapsedStampedLock);

    }

    private void executeTask(LockService<ConcurrentTreeMap<Long, String>, Map.Entry<Long, String>> lockService,
                             Long keyValue,
                             int numberOfThreads,
                             int numberOfIterations) {
        for (int iteration = 0; iteration < numberOfIterations; iteration++) {
            readWriteExecutor = Executors.newFixedThreadPool(numberOfThreads);
            for (int j = 0; j < numberOfThreads; j += 2) {
                readWriteExecutor.execute(getReadTask(lockService));
                readWriteExecutor.execute(getWriteTask(lockService, keyValue++));
            }
            readWriteExecutor.shutdown();
            try {
                readWriteExecutor.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private long getTimeElapsedInMillis(long startTimeMillis) {
        return System.currentTimeMillis() - startTimeMillis;
    }

    private static Runnable getReadTask(LockService<ConcurrentTreeMap<Long, String>, Map.Entry<Long, String>> lockService) {
        return () -> {
            lockService.readObject();
        };
    }

    private static Runnable getWriteTask(LockService<ConcurrentTreeMap<Long, String>, Map.Entry<Long, String>> lockService, Long value) {
        return () -> {
            lockService.modifyObject(new AbstractMap.SimpleEntry<>(value, String.valueOf(value)));
        };
    }

    private static Runnable getReadTaskWithDelay(ReadWriteLockService treeService) {
        return () -> {
            try {
                treeService.readObject();
                TimeUnit.MILLISECONDS.sleep((int) (100 * Math.random() + 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private static Runnable getWriteTaskWithDelay(ReadWriteLockService treeService, Long value) {
        return () -> {
            try {
                treeService.modifyObject(new AbstractMap.SimpleEntry<>(value, String.valueOf(value)));
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private ConcurrentTreeMap<TreeMap<Long, String>, Map.Entry<Long, String>> newConcurrentTreeMap() {
        return new ConcurrentTreeMap<TreeMap<Long, String>, Map.Entry<Long, String>>(new TreeMap<>());
    }

}
