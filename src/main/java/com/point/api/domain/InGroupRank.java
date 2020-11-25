package com.point.api.domain;

import lombok.*;

import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "in_group_ranks")
public class InGroupRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String accountId;
    private String groupId;
    private int inGroupTotal;
}
