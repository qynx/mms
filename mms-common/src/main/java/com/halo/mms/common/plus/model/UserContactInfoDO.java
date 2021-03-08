package com.halo.mms.common.plus.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName(value = "user_contact_info")
public class UserContactInfoDO {

    @TableId
    private Long id;

    private String nickName;

    private String authInfo;

    private String uuid;

    private String email;

    @TableLogic
    private Boolean isDeleted;

    private Date createdAt;

    private Date updatedAt;

    private String wxPusherUid;
}