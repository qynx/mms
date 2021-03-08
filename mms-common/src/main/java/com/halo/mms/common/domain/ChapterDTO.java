package com.halo.mms.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ChapterDTO implements Serializable {

    private String url;
    private Integer chapterIndex;
    private String title;

    private String content;

    private ListPageDTO pageInfo;
}
