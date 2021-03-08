package com.halo.mms.serv.controller;

import com.halo.mms.out.result.BaseResult;
import com.halo.mms.common.plus.model.ToDoListDO;
import com.halo.mms.serv.request.TodoListQuery;
import com.halo.mms.serv.service.api.TodoListService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/mms/api")
public class TodoListController {

    @Resource
    private TodoListService todoListService;

    @RequestMapping(value = "/todo/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody ToDoListDO toDoListDO) {
        return BaseResult.getSucc(todoListService.mmsSave(toDoListDO));
    }

    @RequestMapping(value = "todo/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestBody ToDoListDO toDoListDO) {
        return BaseResult.getSucc(todoListService.mmsUpdate(toDoListDO));
    }

    @RequestMapping(value = "todo/list", method = RequestMethod.POST)
    @ResponseBody
    public Object list(@RequestBody TodoListQuery todoListQuery) {
        return BaseResult.getSucc(todoListService.list(todoListQuery));
    }

    @RequestMapping(value = "/todo/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteTodo(@RequestBody ToDoListDO toDoListDO) {
        todoListService.mmsDelete(toDoListDO.getId());
        return BaseResult.getSucc(null);
    }
}
