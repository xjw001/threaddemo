package com.xjw.finalClass;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
public class FinalClass {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                synchronized (sdf){
                    try {
                        log.debug("{}",sdf.parse("1951-04-21"));
                    }catch (Exception e){
                        log.error("{}",e);
                    }
                }

            }).start();
        }
    }
}
