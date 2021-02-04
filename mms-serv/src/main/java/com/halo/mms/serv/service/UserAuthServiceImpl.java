package com.halo.mms.serv.service;

import com.halo.mms.repo.model.UserContactInfoDO;
import com.halo.mms.serv.exception.LoginInvalidException;
import com.halo.mms.serv.service.api.UserAuthService;
import com.halo.mms.serv.store.AbstractCache;
import com.halo.mms.serv.store.CacheFactory;
import com.halo.mms.serv.store.StoreData;
import com.halo.mms.serv.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@Service
public class UserAuthServiceImpl implements UserAuthService {

    private static final String TOKEN_STORE_PREFIX = "token_";

    @Resource
    private Environment environment;

    private static AbstractCache store = CacheFactory.buildFileCache("token");

    @Override
    public String generateToken(UserContactInfoDO userContactInfoDO) {
        String token = UUID.randomUUID().toString().replace("-", "");

        store.putData(buildTokenKey(token),
            StoreData.builder().value(JsonUtil.toJson(userContactInfoDO))
                .invalidTimeInMs(System.currentTimeMillis() + ttl()).build());

        return token;
    }

    private long ttl() {
        return Long.parseLong(environment.getProperty("mms.token.ttl"));
    }

    @Override
    public UserContactInfoDO getUidByToken(String token) {
        return store.getData(buildTokenKey(token))
            .map(StoreData::getValue)
            .map(v -> JsonUtil.fromJson(v, UserContactInfoDO.class))
            .orElseThrow(() -> new LoginInvalidException(401, "invalid token"));
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
