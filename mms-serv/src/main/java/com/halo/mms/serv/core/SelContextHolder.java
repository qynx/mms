package com.halo.mms.serv.core;

public class SelContextHolder {

    private final static ThreadLocal<ContextBO> CONTEXT_HOLDER = new ThreadLocal<>();

    public static ContextBO getContext() {
        ContextBO context = CONTEXT_HOLDER.get();

        if (context == null) {
            CONTEXT_HOLDER.set(new ContextBO());
        }

        return CONTEXT_HOLDER.get();
    }


    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }

}
