package com.training.lab.truck;

import com.training.lab.action.Generate;
import com.training.lab.base.WorkingTerminal;
import com.training.lab.constant.Constant;

/**
 * Created by USER on 19.10.2016.
 */
public enum CargoActionType implements ICargoAction {
    LOAD{
        @Override
        public int doAction(WorkingTerminal currentTerminal, int cargo) {
           return  load(currentTerminal, cargo);
        }
    },
    UNLOAD{
        @Override
        public int doAction(WorkingTerminal currentTerminal, int cargo) {
            return unload(currentTerminal, cargo);
        }
    },
    UNLOAD_AND_LOAD{
        @Override
        public int doAction(WorkingTerminal currentTerminal, int cargo) {
            unload(currentTerminal, cargo);
            return  load(currentTerminal, cargo);
        }
    };

    public int load(WorkingTerminal currentTerminal, int cargo) {
        cargo = (currentTerminal.getCurrentCapacity() > Constant.MAX_TRUCK_CAPACITY )?
                Generate.generateCargoCapacity() : currentTerminal.getCurrentCapacity();
        currentTerminal.setCurrentCapacity(currentTerminal.getCurrentCapacity() - cargo);
        return cargo;
    }

    public int unload(WorkingTerminal currentTerminal, int cargo) {
        currentTerminal.setCurrentCapacity(currentTerminal.getCurrentCapacity() + cargo);
        return 0;
    }
}
