package guauaEventBus.listener;

import com.google.common.eventbus.Subscribe;

public class MultipleListeners {

	@Subscribe
    public void task1(String s) {
        System.out.println("do task1(" + s +")");
    }
     
    @Subscribe
    public void task2(String s) {
        System.out.println("do task2(" + s +")");
    }
     
    @Subscribe
    public void intTask(Integer i) {
        System.out.println("do intTask(" + i +")");
    }
}
