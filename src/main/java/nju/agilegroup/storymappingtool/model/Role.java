package nju.agilegroup.storymappingtool.model;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
    @EmbeddedId
    private RolePK pk;

    @Column(name = "avatar")
    private String avatar;

    public Role(){

    }

    public RolePK getPk() {
        return pk;
    }

    public void setPk(RolePK pk) {
        this.pk = pk;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
