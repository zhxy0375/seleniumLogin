package com.javarticles.guava;

import com.google.common.eventbus.Subscribe;

public class SimpleListener {
    @Subscribe
    public void task(String s) {
        System.out.println("do task(" + s + ")");
    }
}
