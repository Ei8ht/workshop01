package com.icd.wksh.configs;

import org.springframework.stereotype.Component;

@Component("Brother1")
public class Brother implements Printer {
    @Override
    public void print() {
        System.out.println("Brother");
    }
}
