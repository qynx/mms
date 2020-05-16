package com.halo.mms.serv.service.api;

import com.halo.mms.out.result.BaseResult;
import com.halo.mms.serv.request.UserAuthRequest;
import com.halo.mms.serv.result.UserAuthResult;

public interface UserInfoService {

    String generateQrCode();

    BaseResult<UserAuthResult> authUser(UserAuthRequest request);

}
