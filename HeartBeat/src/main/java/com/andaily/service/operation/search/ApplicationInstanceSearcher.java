package com.andaily.service.operation.search;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.application.ApplicationInstanceRepository;
import com.andaily.domain.dto.HBSearchDto;
import com.andaily.domain.dto.HBSearchResultDto;
import com.andaily.domain.shared.BeanProvider;
import com.andaily.domain.shared.paginated.PaginatedLoader;

import java.util.List;
import java.util.Map;

/**
 * 15-3-13
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceSearcher implements HBSearcher {

    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);

    public ApplicationInstanceSearcher() {
    }

    @Override
    public HBSearchDto search(HBSearchDto searchDto) {

        final Map<String, Object> map = searchDto.queryMap();
        return searchDto.load(new PaginatedLoader<HBSearchResultDto>() {
            @Override
            public List<HBSearchResultDto> loadList() {
                List<ApplicationInstance> instances = instanceRepository.findHBSearchInstances(map);
                return HBSearchResultDto.toInstanceDtos(instances);
            }

            @Override
            public int loadTotalSize() {
                return instanceRepository.totalHBSearchInstances(map);
            }
        });

    }
}
