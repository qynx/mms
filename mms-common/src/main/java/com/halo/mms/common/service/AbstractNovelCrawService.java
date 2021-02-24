package com.halo.mms.common.service;

import com.halo.mms.common.domain.ChapterDTO;

import java.util.List;

public abstract class AbstractNovelCrawService {



    abstract void parseChapterContent(String contentPageHtml);

    abstract List<ChapterDTO> parseChapterUrl(String listPageHtml);
}
