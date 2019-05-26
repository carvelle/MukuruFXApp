package za.co.makuru.makurufx.utils.rx;

import io.reactivex.Scheduler;

public interface CustomScheduler {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}
