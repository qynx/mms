package com.halo.mms.serv.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TodoListQuery implements Serializable {

    private Integer page;
    private Integer size;

    private String doingDay;
}
