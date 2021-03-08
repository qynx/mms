package com.halo.mms.common.service;

import com.halo.mms.common.domain.ListPageResult;

/**
 * 实现类 需要无状态 或 自行确保线程安全性
 */
public abstract class AbstractNovelCrawService {

    public abstract String parseChapterContent(String contentPageHtml);

    public abstract ListPageResult parsePageUrl(String listPageHtml);
}
