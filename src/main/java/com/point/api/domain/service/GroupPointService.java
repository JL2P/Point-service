package com.point.api.domain.service;

import com.point.api.domain.GroupPoint;

import java.util.List;

public interface GroupPointService {

    public List<GroupPoint> getAllGroupPoint();

    public GroupPoint addGroupPoint(GroupPoint groupPoint);
}
