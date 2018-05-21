package com.concurrent.service.tree;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TreeServiceTest {

    private TreeMap<Long, String> treeMap = null;
    private TreeService treeService = null;
    private ExecutorService readExecutorService = null;
    private ExecutorService writeExecutorService = null;
    private Long keyValue = 1L;

    @Before
    public void setup() {
        treeMap = new TreeMap<>();
        treeService = new TreeService(treeMap);
    }

    @Test
    public void testConcurrentReadWriteTask() {
        readExecutorService = Executors.newFixedThreadPool(5);
        writeExecutorService = Executors.newFixedThreadPool(1);

        Assert.assertTrue(treeMap.isEmpty());
        long startTimeMillis = System.currentTimeMillis();
        while (getTimeElapsedInMillis(startTimeMillis) < 10000) {
            readExecutorService.submit(getReadTask(treeService));
            writeExecutorService.submit(getWriteTask(treeService, keyValue++));
        }
        readExecutorService.shutdownNow();
        writeExecutorService.shutdownNow();
        Assert.assertFalse(treeMap.isEmpty());
    }

    @Test
    public void testConcurrentWriteTask() {
        writeExecutorService = Executors.newFixedThreadPool(2);

        Assert.assertTrue(treeMap.isEmpty());
        long startTimeMillis = System.currentTimeMillis();
        while (getTimeElapsedInMillis(startTimeMillis) < 10000) {
            writeExecutorService.submit(getWriteTask(treeService, keyValue++));
        }
        writeExecutorService.shutdownNow();

        Assert.assertFalse(treeMap.isEmpty());
    }

    private long getTimeElapsedInMillis(long startTimeMillis) {
        return System.currentTimeMillis() - startTimeMillis;
    }

    private static Runnable getReadTask(TreeService treeService) {
        return () -> {
            try {
                treeService.readAndPrintValues();
                TimeUnit.MILLISECONDS.sleep((int) (100 * Math.random() + 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private static Runnable getWriteTask(TreeService treeService, Long value) {
        return () -> {
            try {
                treeService.writeValue(new AbstractMap.SimpleEntry<>(value, String.valueOf(value)));
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

}
