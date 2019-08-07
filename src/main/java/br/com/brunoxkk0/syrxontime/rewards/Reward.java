package br.com.brunoxkk0.syrxontime.rewards;

import java.util.concurrent.TimeUnit;

public class Reward implements IReward {

    private IReward reward;
    private int Reward_UUID;

    Reward(IReward reward, int Reward_UUID){
        this.reward = reward;
        this.Reward_UUID = Reward_UUID;
    }

    @Override
    public TimeUnit timeUnit() {
        return reward.timeUnit();
    }

    @Override
    public int time() {
        return reward.time();
    }

    @Override
    public String[] commands() {
        return reward.commands();
    }

    @Override
    public double money() {
        return reward.money();
    }

    @Override
    public double xp() {
        return reward.xp();
    }

    int Reward_UUID() {
        return Reward_UUID;
    }
}
