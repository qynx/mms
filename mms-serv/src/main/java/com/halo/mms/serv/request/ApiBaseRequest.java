package com.halo.mms.serv.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiBaseRequest implements Serializable {

    private String nickName;
    private String token;
}
