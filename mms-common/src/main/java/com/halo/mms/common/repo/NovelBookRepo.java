package com.halo.mms.common.repo;

import com.halo.mms.common.plus.model.NovelBookDO;

public interface NovelBookRepo {

    NovelBookDO getByUrl(String url);

    NovelBookDO save(NovelBookDO novelBookDO);
}
