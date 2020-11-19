package com.point.api.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "points")
public class Point extends CommonDateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId; // 유저 아이디
    private String todoId;    // 완료한 TodoId
    private int likeCount;   // 완료를 누른 시점의 좋아요 갯수
    private int point;    // 계획을 완료함으로써 얻는 총 포인트(서버에서 계산)
}

