package com.halo.mms.out.request;


import com.halo.mms.out.dto.MessageDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class WxPushRequest implements Serializable {

    private List<MessageDTO> messages;
}
