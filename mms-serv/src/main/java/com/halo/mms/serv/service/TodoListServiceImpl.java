package com.halo.mms.serv.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.mms.repo.model.ToDoListDO;
import com.halo.mms.repo.mybatis.mapper.ToDoListMapper;
import com.halo.mms.serv.exception.BadRequestException;
import com.halo.mms.serv.request.TodoListQuery;
import com.halo.mms.serv.service.api.TodoListService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class TodoListServiceImpl extends AbstractCrudServiceImpl<ToDoListMapper, ToDoListDO> implements TodoListService {

    protected void prepare(ToDoListDO curr) {
        if (StringUtils.isEmpty(curr.getContent()) || StringUtils.isEmpty(curr.getTitle())) {
            throw new BadRequestException(400, "内容不可以为空");
        }
    }

    @Override
    public Page<ToDoListDO> list(TodoListQuery todoListQuery) {
        Page<ToDoListDO> page = new Page<>(todoListQuery.getPage(), todoListQuery.getSize());
        Wrapper<ToDoListDO> wrapper = new QueryWrapper<ToDoListDO>().lambda()
            .ge(StringUtils.isNotEmpty(todoListQuery.getDoingDay()), ToDoListDO::getEndDay, todoListQuery.getDoingDay())
            .le(StringUtils.isNotEmpty(todoListQuery.getDoingDay()), ToDoListDO::getStartDay, todoListQuery.getDoingDay())
            .orderByDesc(ToDoListDO::getId);
        getBaseMapper().selectPage(page, wrapper);
        return page;
    }
}