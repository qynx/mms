package com.halo.mms.out.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseResult<DATA> implements Serializable {

    private static final int SUCC_CODE = 200;

    private Integer code;
    private String msg;
    private DATA data;

    public static BaseResult build() {
        return new BaseResult();
    }

    public BaseResult buildCode(int code) {
        this.code = code;
        return this;
    }

    public BaseResult buildMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public BaseResult<DATA> buildData(DATA data) {
        this.data = data;
        return this;
    }

    public static BaseResult getSucc() {
        return new BaseResult().buildCode(SUCC_CODE);
    }

    public static <DATA> BaseResult<DATA> getSucc(DATA data) {
        return new BaseResult().buildData(data).buildCode(SUCC_CODE);
    }
}
