package com.halo.mms.common.halo;

public class Gakki {

    public static void expectTrue(boolean expression, RuntimeException e) {
        if (!expression) {
            throw e;
        }
    }
}
