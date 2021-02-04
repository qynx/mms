package com.halo.mms.serv.wxpush;

import com.halo.mms.out.WxPushService;
import com.halo.mms.out.dto.MessageDTO;
import com.halo.mms.out.request.WxPushRequest;
import com.halo.mms.out.result.BaseResult;
import com.halo.mms.serv.configs.PushConfig;
import com.zjiecode.wxpusher.client.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class WxPushServiceImpl extends AbstractWxPushService implements WxPushService {

    @Autowired
    private PushConfig pushConfig;

    @Override
    public BaseResult pushByUidList(WxPushRequest request) {

        Consumer<MessageDTO> dealOneMessage = messageDTO -> {
            Message message = getMessage(messageDTO);
            sendMessage(message);
        };

        request.getMessages().forEach(dealOneMessage);

        return BaseResult.getSucc();
    }

    @Override
    public void push(String wxPusherUid, String msg) {
        Message message = getMessage(new MessageDTO().setContent(msg).setUid(wxPusherUid));
        sendMessage(message);
    }

    @Override
    public void push(String title, String content, String wxPusherId) {
        Message message = getMessage(new MessageDTO().setContent(content).setSummary(title)
        .setUid(wxPusherId));
        sendMessage(message);
    }

    protected PushConfig getPushConfig() {
        return this.pushConfig;
    }

}
