package com.ll.commons;

import java.util.UUID;

public class CommonsUtil {

    /**
     * 获取32位UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace( "-", "" );
    }
}
