package com.halo.mms.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ChapterDTO implements Serializable {

    private String url;
    private String host;
    private Integer chapterIndex;

    private String content;
}
