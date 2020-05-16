package com.halo.mms.serv.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddEventRequest extends ApiBaseRequest implements Serializable {

    private String tag;
    private Long start;
    private Long end;
    private String day;
}
