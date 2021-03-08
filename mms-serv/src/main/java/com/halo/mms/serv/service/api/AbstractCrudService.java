package com.halo.mms.serv.service.api;

import java.io.Serializable;

public interface AbstractCrudService<DOMAIN> {

    DOMAIN mmsSave(DOMAIN entity);

    DOMAIN mmsUpdate(DOMAIN entity);

    void mmsDelete(Serializable id);
}
