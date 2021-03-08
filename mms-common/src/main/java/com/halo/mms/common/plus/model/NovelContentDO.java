package com.halo.mms.common.plus.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "novel_content")
public class NovelContentDO implements Serializable {

    @TableId
    private Long id;

    private String url;

    private String title;

    private String content;

    private Integer chapterIndex;

    private Long bookId;
}
