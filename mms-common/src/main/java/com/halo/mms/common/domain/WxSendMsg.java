package com.halo.mms.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class WxSendMsg implements Serializable {

    private String wxPusherId;
    private String title;
    private String content;
    private String uid;
}
