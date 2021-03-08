package com.halo.mms.serv.service;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.mms.common.plus.mapper.MmsBaseMapper;
import com.halo.mms.serv.exception.BadRequestException;
import com.halo.mms.serv.service.api.AbstractCrudService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;


public abstract class AbstractCrudServiceImpl<M extends MmsBaseMapper<T>, T> extends ServiceImpl<M, T> implements AbstractCrudService<T> {

    protected void prepare(T curr) {
    }

    protected void effect(T curr, T newDomain) {

    }

    protected void preUpdate(T curr, T newDomain) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T mmsSave(T entity) {
        prepare(entity);
        super.save(entity);
        Serializable id = (Serializable) ReflectionKit.getFieldValue(entity, "id");
        T newData = super.getById(id);
        effect(null, newData);
        return newData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T mmsUpdate(T entity) {
        prepare(entity);
        Serializable id = (Serializable) ReflectionKit.getFieldValue(entity, "id");
        T curr = getById(id);
        if (null == curr) {
            throw new BadRequestException(400, "要修改的记录不存在");
        }
        preUpdate(curr, entity);
        super.updateById(entity);
        T newData = super.getById((Serializable) id);
        effect(curr, newData);
        return newData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mmsDelete(Serializable id) {
        T curr = getById(id);
        if (null == curr) {
            throw new BadRequestException(400, "要删除的记录不存在");
        }
        preUpdate(curr, null);
        super.removeById(id);
        effect(curr, null);
    }
}
