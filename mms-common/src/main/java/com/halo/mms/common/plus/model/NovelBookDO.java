package com.halo.mms.common.plus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "novel_book")
public class NovelBookDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String startUrl;

    private String name;
}
