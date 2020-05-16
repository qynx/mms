package com.halo.mms.serv.service.api;

import com.halo.mms.out.result.BaseResult;
import com.halo.mms.serv.request.AddEventRequest;

public interface EventDayService {

    BaseResult addEvent(AddEventRequest request);
}
