package com.training.lab.constant;

import com.training.lab.truck.CargoActionType;

/**
 * Created by USER on 11.10.2016.
 */
public class Constant {

    private Constant(){}

    public static final int TRUCKS_COUNT = 18;

    public static final int MAX_TERMINAL_CAPACITY=250;
    public static final int MAX_TRUCK_CAPACITY=25;
    public static final int MAX_ARRIVING_TIME=3;
    public static final int MAX_WAITING_TIME=2000;

    public static final int MAX_TERMINALS_COUNT=10;

    public static final String TRUCK_NAME = "TRUCK №";
    public static final int CARGO_ACTION_TYPE_LENGTH = CargoActionType.values().length;
}
