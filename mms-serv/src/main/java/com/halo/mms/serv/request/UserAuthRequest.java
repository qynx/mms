package com.halo.mms.serv.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAuthRequest implements Serializable {

    private String nickName;
    private String pwd;
}
