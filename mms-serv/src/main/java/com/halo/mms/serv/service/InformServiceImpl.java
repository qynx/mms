package com.halo.mms.serv.service;

import com.halo.mms.out.InformService;
import org.springframework.stereotype.Service;

@Service
public class InformServiceImpl implements InformService {

    @Override
    public String emailX(String user, String content) {
        return "ok";
    }

}
