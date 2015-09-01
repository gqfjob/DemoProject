package com.andaily.infrastructure.grouper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractGrouper<K, E> implements Grouper<K, E> {

    protected List<E> elements = new ArrayList<E>();

    protected AbstractGrouper() {
    }

    protected AbstractGrouper(List<E> elements) {
        this.elements = elements;
    }

    /**
     * Set new elements of grouper
     *
     * @param elements elements
     * @return Current grouper
     */
    public AbstractGrouper<K, E> setElements(List<E> elements) {
        this.elements = elements;
        return this;
    }

    @Override
    public GroupResults<K, E> group() {
        GroupResults<K, E> results = createGroupResults();
        for (E element : elements) {
            K key = groupKey(element);
            GroupResult<K, E> groupResult = results.getGroupResult(key);
            if (groupResult == null) {
                GroupResult<K, E> groupedResult = createGroupResult(key).add(element);
                results.add(groupedResult);
            } else {
                groupResult.add(element);
            }
        }

        return results;
    }

    /**
     * Create a new   GroupResults instance
     *
     * @return GroupResults
     */
    protected GroupResults<K, E> createGroupResults() {
        return new GroupResults<K, E>();
    }

    /**
     * Create a new  GroupResult instance
     *
     * @param key K
     * @return GroupResult
     */
    protected GroupResult<K, E> createGroupResult(K key) {
        return new GroupResult<K, E>(key);
    }


    /**
     * Return the group factor.
     *
     * @param element The element of group
     * @return Group key
     */
    public abstract K groupKey(E element);
}
