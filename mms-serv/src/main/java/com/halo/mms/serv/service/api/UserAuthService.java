package com.halo.mms.serv.service.api;

import com.halo.mms.common.plus.model.UserContactInfoDO;

public interface UserAuthService {

    /**
     * for login user issue a unique token
     */
    String generateToken(UserContactInfoDO userContactInfoDO);

    /**
     * @param token the issued token
     * @return token map user
     */
    UserContactInfoDO getUidByToken(String token);

    /**
     *
     * @param authInfo secreted pwd
     * @param pwd pwd by user
     * @return is valid pwd
     */
    boolean auth(String authInfo, String pwd);
}
