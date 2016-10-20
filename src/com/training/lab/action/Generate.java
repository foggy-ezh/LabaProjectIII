package com.training.lab.action;

import com.training.lab.constant.Constant;

import java.util.Random;

/**
 * Created by USER on 12.10.2016.
 */
public class Generate {
    private Generate(){}
    private static Random random = new Random();

    public static int generateTerminalCapacity(){
        return random.nextInt(Constant.MAX_TERMINAL_CAPACITY);
    }

    public static int generateArrivingTime(){
        return random.nextInt(Constant.MAX_ARRIVING_TIME);
    }

    public static int generateWaitingTime(){
        return random.nextInt(Constant.MAX_WAITING_TIME);
    }

    public static int generateCargoCapacity(){
        return random.nextInt(Constant.MAX_TRUCK_CAPACITY);
    }

    public static int generateCargoActionType(){ return random.nextInt(Constant.CARGO_ACTION_TYPE_LENGTH);}

    public static boolean generateIsPerishable(){ return random.nextBoolean();}

}
