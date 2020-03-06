package com.icd.wksh.configs;

import org.springframework.stereotype.Component;

@Component("Canon1")
public class Canon implements Printer {
    @Override
    public void print() {
        System.out.println("Canon");
    }
}
