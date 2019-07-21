package br.com.brunoxkk0.syrxontime.rewards;

import java.util.concurrent.TimeUnit;

public interface IReward {
    TimeUnit timeUnit();
    int time();

    String[] commands();
    double money();
    double xp();
}
