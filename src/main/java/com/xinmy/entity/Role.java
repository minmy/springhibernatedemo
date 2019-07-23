package com.xinmy.entity;

import com.xinmy.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色实体
 * 
 * @author 杨海彬
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = Role.TABLE_NAME)
public class Role extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "T_ROLE";

	/** ID */
	@Id
	@TableGenerator(name = TABLE_NAME, table = SEQUENCE_TABLE, pkColumnValue = TABLE_NAME)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = TABLE_NAME)
	@Column(name = "ID")
	private Long id;

	/** 名称 */
	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	/** 备注 */
	@Column(name = "REMARK")
	private String remark;

	/** 拥有的权限集合 */
	@OrderBy("sortWeight,id")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "T_AUTHORITY_ROLE", joinColumns = @JoinColumn(name = "FK_ROLE"), inverseJoinColumns = @JoinColumn(name = "FK_AUTHORITY"))
	private Set<Authority> authoritys = new HashSet<>();

	/** 拥有该角色的用户集合 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "T_USER_ROLE", joinColumns = @JoinColumn(name = "FK_ROLE"), inverseJoinColumns = @JoinColumn(name = "FK_USER"))
	private Set<User> users = new HashSet<>();
}