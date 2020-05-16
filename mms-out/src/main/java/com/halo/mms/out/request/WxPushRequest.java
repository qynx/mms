package com.halo.mms.out.request;


import com.halo.mms.out.dto.MessageDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class WxPushRequest implements Serializable {

    private List<MessageDTO> messages;
}
