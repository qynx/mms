package com.halo.mms.serv.controller;

import com.halo.mms.out.result.BaseResult;
import com.halo.mms.serv.core.SelContextHolder;
import com.halo.mms.serv.request.UserAuthRequest;
import com.halo.mms.serv.result.UserInfo;
import com.halo.mms.serv.service.api.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/api/mms/api")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    //@GetMapping("/user/get")
    public Object getUser(Model model) {
        model.addAttribute("qrcodeUrl", userInfoService.generateQrCode());
        return "user/index";
    }

    @GetMapping("/user")
    @ResponseBody
    public Object user() {
        Map<String, String> r = new HashMap<>();
        r.put("uuid", SelContextHolder.getContext().getUserInfo().getUuid());
        return BaseResult.getSucc(r);
    }

    @PostMapping("/user/auth")
    @ResponseBody
    public Object authUser(@RequestBody UserAuthRequest userAuthRequest) {
        return userInfoService.authUser(userAuthRequest);
    }

    @PostMapping("/user/get")
    @ResponseBody
    public Object getUser() {
        return
            BaseResult.getSucc(
            new UserInfo(userInfoService.queryUser(SelContextHolder.getContext().getUserInfo().getUuid())));
    }
}
