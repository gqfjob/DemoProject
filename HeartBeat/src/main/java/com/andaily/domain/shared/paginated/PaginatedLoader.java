package com.andaily.domain.shared.paginated;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface PaginatedLoader<T> {

    List<T> loadList();

    int loadTotalSize();

}