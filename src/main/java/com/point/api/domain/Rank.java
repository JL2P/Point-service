package com.point.api.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ranks")
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId; // 유저 아이디
    private int total;        // 계획을 완료함으로써 얻는 총 포인트(서버에서 계산)
}
