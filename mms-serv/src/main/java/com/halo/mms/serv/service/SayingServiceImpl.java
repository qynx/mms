package com.halo.mms.serv.service;

import com.halo.mms.serv.service.api.SayingService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SayingServiceImpl implements SayingService {

    private static final List<String> SAYINGS = Arrays.asList(
        "什么都无法舍弃的人什么都得不到",
        "上善若水",
        "陆舟"
    );

    @Override
    public String randSaying() {
        return SAYINGS.get((int) (System.currentTimeMillis() % SAYINGS.size()));
    }
}
