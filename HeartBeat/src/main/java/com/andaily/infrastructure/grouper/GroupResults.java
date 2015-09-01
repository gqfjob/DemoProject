package com.andaily.infrastructure.grouper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class GroupResults<K, E> {

    protected List<GroupResult<K, E>> groupResults = new ArrayList<GroupResult<K, E>>();

    public GroupResults() {
    }

    public List<GroupResult<K, E>> getGroupResults() {
        return Collections.unmodifiableList(groupResults);
    }

    public void add(GroupResult<K, E> groupedResult) {
        groupResults.add(groupedResult);
    }

    /**
     * Try to retrieve GroupResult by key,
     * If not found, return null.
     *
     * @param key Group key
     * @return GroupResult or null
     */
    public GroupResult<K, E> getGroupResult(K key) {
        for (GroupResult<K, E> groupResult : groupResults) {
            if (key.equals(groupResult.getKey())) {
                return groupResult;
            }
        }
        return null;
    }
}
