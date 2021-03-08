package com.halo.mms.common.plus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName(value = "todo_list")
public class ToDoListDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String uid;

    private String title;

    private String content;

    private Date createdAt;

    private Date updatedAt;

    private Long notifyTime;

    private Boolean notifyStatus;

    private String startDay;

    private String endDay;

    @TableLogic
    private Boolean isDeleted;
}
