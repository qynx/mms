package com.halo.mms.common.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class KafkaConfig implements Serializable {

    private String topic;
    private String group;
    private String servers;
}
