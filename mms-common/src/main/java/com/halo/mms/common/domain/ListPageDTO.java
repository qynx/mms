package com.halo.mms.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ListPageDTO implements Serializable {

    private String host;

    private Integer index;

    private String url;
}
