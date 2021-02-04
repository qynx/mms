package com.halo.mms.repo.model;

import io.ebean.Model;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Data
//@Entity
//@Table(name = "day_event", uniqueConstraints = {@UniqueConstraint(columnNames={"day", "tag", "nick_name"})})
public class DayEventDO extends Model {

    @Id
    private Long id;

    @Column(name = "tag")
    private String tag;

    @Column(name = "start")
    private Date start;

    @Column(name = "end")
    private Date end;

    @Column(name = "day")
    private String day;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
