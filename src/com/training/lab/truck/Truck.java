package com.training.lab.truck;

import com.training.lab.action.Generate;
import com.training.lab.base.LogisticBase;
import com.training.lab.base.WorkingTerminal;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by USER on 11.10.2016.
 */
public class Truck implements Runnable {

    private final Logger LOGGER = LogManager.getLogger();
    private String truckName;
    private int cargo;
    private CargoActionType cargoActionType;
    private boolean isPerishable;
    private LogisticBase logisticBase;
    private WorkingTerminal terminal;

    public Truck(String truckName, int cargo, CargoActionType cargoActionType, Boolean isPerishable) {
        this.truckName = truckName;
        this.cargo = cargo;
        this.cargoActionType = cargoActionType;
        this.isPerishable = isPerishable;
    }

    @Override
    public void run() {
        System.out.println("Was created — " + this);
        logisticBase = LogisticBase.getInstance();
        try {
            moveToBase();
            logisticBase.registerAndWait(isPerishable, truckName);
            while(terminal == null){
                terminal = logisticBase.getTerminal(truckName);
                TimeUnit.MILLISECONDS.sleep(Generate.generateWaitingTime());
            }
            cargo = cargoActionType.doAction(terminal, cargo);
            TimeUnit.SECONDS.sleep(5);
            logisticBase.releaseTerminal(terminal);
            terminal=null;
            logisticBase.deregister();
            System.out.println("Moved away — " + this);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    private void moveToBase() throws InterruptedException {
        TimeUnit.SECONDS.sleep(Generate.generateArrivingTime());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Truck{" +
                "truckName='" + truckName + '\'' +
                ", cargo=" + cargo +
                ", cargoActionType=" + cargoActionType +
                ", isPerishable=" + isPerishable +
                '}');
        return stringBuilder.toString();
    }
}
