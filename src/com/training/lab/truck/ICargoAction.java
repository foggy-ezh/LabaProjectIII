package com.training.lab.truck;

import com.training.lab.base.WorkingTerminal;

/**
 * Created by USER on 19.10.2016.
 */
public interface ICargoAction {
    int doAction(WorkingTerminal currentTerminal, int cargo);
    int load(WorkingTerminal currentTerminal, int cargo);
    int unload(WorkingTerminal currentTerminal, int cargo);
}
