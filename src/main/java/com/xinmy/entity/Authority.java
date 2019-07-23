package com.xinmy.entity;

import com.xinmy.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限实体
 * 
 * @author 杨海彬
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = Authority.TABLE_NAME)
public class Authority extends AbstractEntity implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "T_AUTHORITY";

	/** ID */
	@Id
	@TableGenerator(name = TABLE_NAME, table = SEQUENCE_TABLE, pkColumnValue = TABLE_NAME)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = TABLE_NAME)
	@Column(name = "ID")
	private Long id;

	/** 权限 */
	@Column(name = "AUTHORITY", unique = true, nullable = false)
	private String authority;

	/** 中文名称 */
	@Column(name = "NAME", nullable = false)
	private String name;

	/** 备注 */
	@Column(name = "REMARK")
	private String remark;

	/** 排序权重 */
	@Column(name = "SORT_WEIGHT", nullable = false)
	private Long sortWeight;

	/** 所属上一级权限 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_SUPER_AUTHORITY")
	private Authority superAuthority;

	/** 子权限集合 */
	@OneToMany(mappedBy = "superAuthority", fetch = FetchType.LAZY)
	private Set<Authority> subAuthoritys = new HashSet<>();

	/** 拥有该权限的角色集合 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "T_AUTHORITY_ROLE", joinColumns = @JoinColumn(name = "FK_AUTHORITY"), inverseJoinColumns = @JoinColumn(name = "FK_ROLE"))
	private Set<Role> roles = new HashSet<>();

	@Override
	public void remove(Session session) {
		for (Authority authority : this.subAuthoritys) {
			authority.remove(session);
		}
		super.remove(session);
	}
}