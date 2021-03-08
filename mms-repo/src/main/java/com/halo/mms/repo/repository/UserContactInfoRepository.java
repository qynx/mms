package com.halo.mms.repo.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.halo.mms.common.plus.model.UserContactInfoDO;
import com.halo.mms.common.plus.mapper.UserContractInfoMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

@Repository
public class UserContactInfoRepository extends BaseRepository {

    @Resource
    private UserContractInfoMapper userContractInfoMapper;

    public Optional<UserContactInfoDO> findByNickName(@NonNull String username) {
        Wrapper<UserContactInfoDO> wrapper = new QueryWrapper<UserContactInfoDO>().lambda()
            .eq(UserContactInfoDO::getUuid, username);
        return Optional.ofNullable(safeExec(() -> userContractInfoMapper.selectOne(wrapper)));
    }

    public Optional<UserContactInfoDO> findByUid(String uid) {
        Wrapper<UserContactInfoDO> wrapper = new QueryWrapper<UserContactInfoDO>().lambda()
            .eq(UserContactInfoDO::getUuid, uid);
        return Optional.ofNullable(safeExec(() -> userContractInfoMapper.selectOne(wrapper)));
    }
}
