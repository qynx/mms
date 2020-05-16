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

        Consumer<MessageDTO> dealOneMessage = new Consumer<MessageDTO>() {
            @Override
            public void accept(MessageDTO messageDTO) {
                Message message = getMessage(messageDTO);
                sendMessage(message);
            }
        };

        request.getMessages().forEach(dealOneMessage);

        return BaseResult.getSucc();
    }

    protected PushConfig getPushConfig() {
        return this.pushConfig;
    }

}
