package com.point.api.domain.service;

import com.point.api.domain.GroupPoint;

import java.util.List;

public interface GroupPointService {

    public List<GroupPoint> getAllGroupPoint();

    public GroupPoint addGroupPoint(GroupPoint groupPoint);

    //그룹에 포인트를 추가한다(그룹 기여도)
    //그룹의 모든 점수를 날짜 별로 조회
    public int getTodayCompletedCount(String accountId, String groupId);
    public List<GroupPoint> getGroupAllPoint(String groupId);
    public void deleteGroupPoint(String accountId, String groupId, String todoId);
    //특정 그룹포인트 불러 오기
    public GroupPoint getGroupPoint(String accountId, String groupId, String todoId);
}
