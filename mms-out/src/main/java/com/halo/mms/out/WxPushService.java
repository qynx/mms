package com.halo.mms.out;

import com.halo.mms.out.request.WxPushRequest;
import com.halo.mms.out.result.BaseResult;

public interface WxPushService {

    BaseResult pushByUidList(WxPushRequest request);

    void push(String wxPusherUid, String msg);

    void push(String title, String content, String wxPusherId);
}
