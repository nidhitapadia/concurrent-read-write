package com.concurrent.service.tree;

import com.concurrent.service.ConcurrentReadWriteProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;


public class TreeService extends ConcurrentReadWriteProcessor<TreeMap<Long, String>, Map.Entry<Long, String>> {

    private final static Logger LOGGER = LoggerFactory.getLogger(TreeService.class.getName());

    /**
     * Constructor
     *
     * @param treeMap the treeMap to read
     */
    public TreeService(TreeMap<Long, String> treeMap) {
        super(treeMap);
    }

    @Override
    protected TreeMap<Long, String> init() {
        return new TreeMap<>();
    }

    @Override
    protected void printValues() {
        if (getObject() != null && !getObject().isEmpty()) {
            LOGGER.info("Total number of keys : {}", getObject().keySet().size());
            getObject().forEach((k, v) -> LOGGER.debug("({} --> {})", k, v));
        } else {
            LOGGER.info("Tree map is empty !!");
        }
    }

    @Override
    protected void addValue(Map.Entry<Long, String> value) {
        if (value != null) {
            LOGGER.info("Adding value ({} --> {})", value.getKey(), value.getValue());
            getObject().put(value.getKey(), value.getValue());
        }
    }
}
