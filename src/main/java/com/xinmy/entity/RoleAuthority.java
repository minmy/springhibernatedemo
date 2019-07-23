package com.xinmy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author lijianxin
 * @date 2019/7/23 15:25
 * @desc
 */
@Getter
@Setter
@Entity
@Table(name = "TEST_ROLE_AUTHORITY")
public class RoleAuthority implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ROLE", nullable = false)
    private Role role;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_AUTHORITY", nullable = false)
    private Authority authority;
}
