package com.halo.mms.serv.result;

import com.halo.mms.common.plus.model.UserContactInfoDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {

    private String uuid;
    private String nickName;
    private Long id;
    private String email;
    private String wxPusherId;

    public UserInfo(UserContactInfoDO domain) {
        this.uuid = domain.getUuid();
        this.nickName = domain.getNickName();
        this.id = domain.getId();
        this.email = domain.getEmail();
        this.wxPusherId = domain.getWxPusherUid();
    }
}
