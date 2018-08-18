package com.telstra.dptsync.demo.config;

import org.springframework.batch.item.ItemProcessor;

public class ProductItemProcessor implements ItemProcessor {

    @Override
    public Object process(Object o) throws Exception {
        System.out.println("#######################");
        return o;
    }
}
