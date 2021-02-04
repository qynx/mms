package com.halo.mms.out.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MessageDTO implements Serializable {

    private String uid;
    private String url;
    private String content;
    private String summary;
}
