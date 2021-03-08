package com.halo.mms.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class ListPageResult implements Serializable {

    private List<ListPageDTO> newListPageUrl;

    private List<ChapterDTO> chapters;
}
