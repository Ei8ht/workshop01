package com.icd.wksh.simple;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class Brother implements Printer {

    @Override
    public String print() {
        return "Brother !";
    }
}
