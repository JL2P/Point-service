package com.point.api.domain.logic;

import com.point.api.domain.GroupPoint;
import com.point.api.domain.service.GroupPointService;
import com.point.api.repository.GroupPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupPointServiceImpl implements GroupPointService {
    private final GroupPointRepository groupPointRepository;

    @Override
    public List<GroupPoint> getAllGroupPoint() {
        return groupPointRepository.findAll();
    }

    @Override
    public GroupPoint addGroupPoint(GroupPoint groupPoint) {
        return groupPointRepository.save(groupPoint);
    }
}
