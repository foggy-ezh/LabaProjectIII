package com.training.lab.action;

import com.training.lab.constant.Constant;
import com.training.lab.truck.CargoActionType;
import com.training.lab.truck.Truck;

/**
 * Created by USER on 19.10.2016.
 */
public class TrucksCreator {
    public void createAndLaunchTrucks(){
        for(int i = 0; i < Constant.TRUCKS_COUNT; i++){
            new Thread(new Truck(Constant.TRUCK_NAME+i,
                    Generate.generateCargoCapacity(),
                    CargoActionType.values()[Generate.generateCargoActionType()],
                    Generate.generateIsPerishable())).start();
        }
    }
}
