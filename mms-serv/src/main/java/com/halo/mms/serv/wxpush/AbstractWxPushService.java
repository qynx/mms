package com.halo.mms.serv.wxpush;

import com.halo.mms.out.dto.MessageDTO;
import com.halo.mms.serv.configs.PushConfig;
import com.zjiecode.wxpusher.client.WxPusher;
import com.zjiecode.wxpusher.client.bean.Message;
import com.zjiecode.wxpusher.client.bean.MessageResult;
import com.zjiecode.wxpusher.client.bean.Result;

import java.util.List;

public abstract class AbstractWxPushService {

    protected Message getMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setUid(messageDTO.getUid());
        message.setUrl(messageDTO.getUrl());
        message.setContent(messageDTO.getContent());
        message.setSummary(messageDTO.getSummary());
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setAppToken(getPushConfig().getPushProperties().getAppToken());
        return message;
    }

    protected Result<List<MessageResult>> sendMessage(Message message) {
        Result<List<MessageResult>> result = WxPusher.send(message);
        return result;
    }

    abstract protected PushConfig getPushConfig();
}
