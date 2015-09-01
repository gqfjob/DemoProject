package com.andaily.domain.user;

import com.andaily.domain.AbstractDomain;

import javax.persistence.*;

/**
 * @author Shengzhao Li
 */
@Entity
@Table(name = "user_privilege")
public class UserPrivilege extends AbstractDomain {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "privilege")
    @Enumerated(value = EnumType.STRING)
    private Privilege privilege;

    public UserPrivilege() {
    }

    public UserPrivilege(User user, Privilege privilege) {
        this.user = user;
        this.privilege = privilege;
    }

    public Privilege privilege() {
        return privilege;
    }

    public User user() {
        return user;
    }
}