package com.halo.mms.serv.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.mms.repo.model.ToDoListDO;
import com.halo.mms.serv.request.TodoListQuery;

public interface TodoListService extends AbstractCrudService<ToDoListDO> {

    Page<ToDoListDO> list(TodoListQuery todoListQuery);
}
