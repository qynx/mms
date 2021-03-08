package com.halo.mms.common.repo.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.halo.mms.common.plus.mapper.NovelBookMapper;
import com.halo.mms.common.plus.model.NovelBookDO;
import com.halo.mms.common.repo.NovelBookRepo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class NovelBookRepoImpl implements NovelBookRepo {

    @Resource
    private NovelBookMapper novelBookMapper;

    @Override
    public NovelBookDO getByUrl(String url) {
        Wrapper<NovelBookDO> wrapper = new QueryWrapper<NovelBookDO>()
        .lambda().eq(NovelBookDO::getStartUrl, url);
        return novelBookMapper.selectOne(wrapper);
    }

    @Override
    public NovelBookDO save(NovelBookDO novelBookDO) {
        novelBookMapper.insert(novelBookDO);
        return novelBookDO;
    }
}
