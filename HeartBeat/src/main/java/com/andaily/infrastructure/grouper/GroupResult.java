package com.andaily.infrastructure.grouper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class GroupResult<K, E> {

    protected K key;
    protected List<E> results = new ArrayList<E>();

    public GroupResult(K key) {
        this.key = key;
    }


    public K getKey() {
        return key;
    }

    public List<E> getResults() {
        return Collections.unmodifiableList(results);
    }

    /**
     * Get per group result data size
     *
     * @return Data size
     */
    public int getResultsSize() {
        return results.size();
    }

    public GroupResult<K, E> add(E element) {
        results.add(element);
        return this;
    }
}
