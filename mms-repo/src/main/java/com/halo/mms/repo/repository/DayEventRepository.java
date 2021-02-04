package com.halo.mms.repo.repository;

import com.halo.mms.repo.model.DayEventDO;
import io.ebean.EbeanServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DayEventRepository {

    private static final String sql = "insert into day_event(day, tag, nick_name, start, end) "
            + " values(:day, :tag, :nickName, :start, :end) on "
            + " duplicate key update start = IF(values(start) is not null, values(start), start), "
            + "end = IF(values(end) is not null, values(end), end)";

    //@Autowired
    private EbeanServer ebeanServer;

     public Object updateByUK(DayEventDO dayEventDO) {
        return ebeanServer.sqlUpdate(sql)
                .setGetGeneratedKeys(true)
                .setParameter("day", dayEventDO.getDay())
                .setParameter("tag", dayEventDO.getTag())
                .setParameter("nickName", dayEventDO.getNickName())
                .setParameter("start", dayEventDO.getStart())
                .setParameter("end", dayEventDO.getEnd())
                .executeGetKey();
    }
}
