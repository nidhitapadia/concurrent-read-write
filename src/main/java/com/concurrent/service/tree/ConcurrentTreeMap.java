package com.concurrent.service.tree;

import com.concurrent.service.ConcurrentObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

public class ConcurrentTreeMap<T, V> implements ConcurrentObject<TreeMap<T, V>, Map.Entry<T, V>> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConcurrentTreeMap.class.getName());
    private TreeMap<T, V> treeMap;

    public ConcurrentTreeMap(TreeMap<T, String> V) {
        this.treeMap = treeMap;
    }

    @Override
    public void printValues() {
        if (!getTreeMap().isEmpty()) {
            LOGGER.info("Total number of keys : {}", getTreeMap().keySet().size());
            getTreeMap().forEach((k, v) -> LOGGER.debug("({} --> {})", k, v));
        } else {
            LOGGER.info("Tree map is empty !!");
        }
    }

    @Override
    public void addValue(Map.Entry<T, V> value) {
        if (value != null) {
            LOGGER.info("Adding value ({} --> {})", value.getKey(), value.getValue());
            getTreeMap().put((T) value.getKey(), (V) value.getValue());
        }
    }

    /**
     * Getter for property 'treeMap'.
     *
     * @return Value for property 'treeMap'.
     */
    public TreeMap<T, V> getTreeMap() {
        if (treeMap == null) {
            treeMap = new TreeMap<>();
        }
        return treeMap;
    }
}
