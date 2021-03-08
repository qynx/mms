package com.halo.mms.common.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.mms.common.plus.mapper.NovelContentMapper;
import com.halo.mms.common.plus.model.NovelContentDO;
import com.halo.mms.common.plus.service.IModelService;
import org.springframework.stereotype.Service;

@Service
public class NovelContentServiceImpl extends ServiceImpl<NovelContentMapper, NovelContentDO>
    implements IModelService<NovelContentDO> {

}
