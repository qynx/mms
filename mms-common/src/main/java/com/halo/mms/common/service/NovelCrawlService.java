package com.halo.mms.common.service;

import com.halo.mms.common.plus.model.NovelBookDO;
import com.halo.mms.common.repo.NovelBookRepo;

public interface NovelCrawlService {

    NovelBookDO start(String firstListPageUrl);
}
