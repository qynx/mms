package com.halo.mms.repo.model;

import io.ebean.Model;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_contact_info")
public class UserContactInfoDO extends Model {

    @Id
    private Long id;

    @Column(name = "nick_name", length = 50)
    private String nickName;

    @Column(name = "auth_info", length = 32)
    private String authInfo;

    @Column(name = "uuid", length = 128)
    private String uuid;

    @Column(name = "email", length = 128)
    private String email;
}
