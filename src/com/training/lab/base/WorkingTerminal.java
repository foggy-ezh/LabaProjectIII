package com.training.lab.base;

import com.training.lab.action.Generate;
import com.training.lab.constant.Constant;

/**
 * Created by USER on 11.10.2016.
 */
public class WorkingTerminal {

    private int currentCapacity;

    public WorkingTerminal() {
        this.currentCapacity = Generate.generateTerminalCapacity();
    }

    public int getCurrentCapacity(){
        return this.currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        if(currentCapacity> Constant.MAX_TERMINAL_CAPACITY){
            currentCapacity=Constant.MAX_TERMINAL_CAPACITY;
        }
        this.currentCapacity=currentCapacity;
    }
}
