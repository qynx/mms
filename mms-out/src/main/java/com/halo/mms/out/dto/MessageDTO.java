package com.halo.mms.out.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MessageDTO implements Serializable {

    private String uid;
    private String url;
    private String content;
}
