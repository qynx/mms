package com.halo.mms.serv.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;

public interface AbstractCrudService<DOMAIN> {

    DOMAIN mmsSave(DOMAIN entity);

    DOMAIN mmsUpdate(DOMAIN entity);

    void mmsDelete(Serializable id);
}
