package com.halo.mms.repo.repository;

import com.halo.mms.repo.model.UserContactInfoDO;
import io.ebean.EbeanServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserContactInfoRepository {

    @Autowired
    private EbeanServer ebeanServer;

    public Optional<UserContactInfoDO> findByNickName(@NonNull String username) {
        return ebeanServer.find(UserContactInfoDO.class)
                .where().eq("nick_name", username)
                .findOneOrEmpty();
    }
}
