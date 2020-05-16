package com.halo.mms.serv.service.api;

public interface UserAuthService {

    /**
     * for login user issue a unique token
     */
    String generateToken(String uid);

    /**
     * @param token the issued token
     * @return token map user
     */
    String getUidByToken(String token);

    /**
     *
     * @param authInfo secreted pwd
     * @param pwd pwd by user
     * @return is valid pwd
     */
    boolean auth(String authInfo, String pwd);
}
