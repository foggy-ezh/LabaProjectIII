package com.training.lab.base;

import com.training.lab.constant.Constant;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by USER on 11.10.2016.
 */
public class LogisticBase {

    private static LogisticBase instance;
    private TruckTransferControlCenter controlCenter = new TruckTransferControlCenter();
    private static Queue<WorkingTerminal> workingTerminals;
    private static ReentrantLock lock = new ReentrantLock(true);
    private static Condition terminalWaitingCondition = lock.newCondition();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static AtomicInteger countOfTrucks = new AtomicInteger(0);


    private LogisticBase() {
        initTerminals();
    }

    public static LogisticBase getInstance() {
        if (!isCreated.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new LogisticBase();
                    isCreated.getAndSet(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private void initTerminals() {
        workingTerminals = new ArrayDeque<>();
        for (int i = 0; i < Constant.MAX_TERMINALS_COUNT; i++) {
            workingTerminals.add(new WorkingTerminal());
        }

    }

    public void registerAndWait(boolean isPerishable, String truckName){
            if (isPerishable) {
                System.out.println("Perishable truck is waiting " + truckName);
                controlCenter.addUrgentTruck(truckName);
            } else {
                System.out.println("NonPerishable truck is waiting " + truckName);
                controlCenter.addNonUrgentTruck(truckName);
            }
        countOfTrucks.getAndIncrement();
    }

    public WorkingTerminal getTerminal(String truckName) throws InterruptedException {
        if (truckName.equals(controlCenter.getFirst(truckName)) || countOfTrucks.get() <= Constant.MAX_TERMINALS_COUNT) {
            controlCenter.removeTruck(truckName);
            try {
                lock.lock();
                while (workingTerminals.isEmpty()) {
                    terminalWaitingCondition.await();
                }
                System.out.println("Truck go to terminal " + truckName);

                return workingTerminals.poll();
            } finally {
                lock.unlock();
            }
        } else {
            return null;
        }
    }

    public void releaseTerminal(WorkingTerminal terminal) throws InterruptedException {
        try {
            lock.lock();
            workingTerminals.offer(terminal);
            terminalWaitingCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void deregister(){
        countOfTrucks.getAndDecrement();
    }

    private class TruckTransferControlCenter {
        private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = reentrantReadWriteLock.readLock();
        private Lock writeLock = reentrantReadWriteLock.writeLock();
        private Deque<String> trucksLine = new ArrayDeque<>();

        public void addUrgentTruck(String truckName) {
            try {
                writeLock.lock();
                System.out.println("Added to first position — "+truckName);
                trucksLine.addFirst(truckName);
            } finally {
                writeLock.unlock();
            }
        }

        public void addNonUrgentTruck(String truckName) {
            try {
                writeLock.lock();
                trucksLine.addLast(truckName);
            } finally {
                writeLock.unlock();
            }
        }

        public String getFirst(String truckName) {
            try {
                readLock.lock();
                System.out.println(truckName+" asked is he first, first is — "+trucksLine.getFirst());
                return trucksLine.getFirst();
            } finally {
                readLock.unlock();
            }
        }

        public void removeTruck(String truckName) {
            try {
                writeLock.lock();
                System.out.println("Removed — "+truckName);
                trucksLine.remove(truckName);
            } finally {
                writeLock.unlock();
            }
        }
    }
}
