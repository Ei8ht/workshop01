package com.icd.wksh.simple;

import org.springframework.stereotype.Component;

//@Component
public class Canon implements Printer {

    @Override
    public String print() {
        return "Canon !";
    }
}
