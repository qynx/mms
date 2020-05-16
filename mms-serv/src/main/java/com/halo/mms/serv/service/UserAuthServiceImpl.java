package com.halo.mms.serv.service;

import com.halo.mms.serv.exception.BadRequestException;
import com.halo.mms.serv.service.api.UserAuthService;
import com.halo.mms.serv.store.MemoryCache;
import com.halo.mms.serv.store.StoreData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

@Slf4j
@Service
public class UserAuthServiceImpl implements UserAuthService {

    private static final String TOKEN_STORE_PREFIX = "token_";

    @Override
    public String generateToken(String uid) {
        String token = UUID.randomUUID().toString().replace("-", "");

        MemoryCache.put(buildTokenKey(token), StoreData.builder().value(uid).build());

        return token;
    }

    @Override
    public String getUidByToken(String token) {

        return MemoryCache.get(buildTokenKey(token))
                        .map(StoreData::getValue)
                        .orElseThrow(() -> new BadRequestException(400, "invalid token"));
    }

    @Override
    public boolean auth(String authInfo, String pwd) {
        return authInfo.equals(pwd);
    }

    private String buildTokenKey(String token) {
        Assert.hasText(token, "token must not null");
        return TOKEN_STORE_PREFIX + token;
    }
}
