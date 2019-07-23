package com.xinmy.entity;

import com.xinmy.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User extends AbstractEntity implements UserDetails {
    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "T_ROLE";

    /**
     * 用户类型枚举
     */
    public enum UserType {
        /**
         * 管理员
         */
        ADMIN("管理员");

        private String desc;

        UserType(String desc) {
            this.desc = desc;
        }

        /**
         * 获取枚举的真实值
         */
        public String getName() {
            return this.name();
        }

        /**
         * 获取枚举的描述值
         */
        public String getDesc() {
            return this.desc;
        }
    }

    @Id
    @TableGenerator(name = TABLE_NAME, table = SEQUENCE_TABLE, pkColumnValue = TABLE_NAME)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = TABLE_NAME)
    private Long id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
