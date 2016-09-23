package com.javarticles.guava;

import com.google.common.eventbus.Subscribe;

public class BaseListener extends AbstractListener {
    @Subscribe
    public void baseTask(String s) {
        System.out.println("do baseTask(" + s + ")");
    }
}
