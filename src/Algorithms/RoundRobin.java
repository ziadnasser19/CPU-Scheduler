package Algorithms;

import Models.Process;
import Models.ProcessManager;

import java.util.*;

public class RoundRobin implements SchedulingAlgorithm{
    int timeQuantum;
    ProcessManager processManager;

    public RoundRobin(int timeQuantum, ProcessManager processManager) {
        this.timeQuantum = timeQuantum;
        this.processManager = processManager;
    }

    public void schedule() {
        Queue<Process> readyQueue = new LinkedList<>(processManager.getReadyQueue());
        int currentTime = 0;

        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.poll();

            if (process.getRemainingTime() == process.getBurstTime()) {
                process.setStartTime(currentTime);
                process.setResponseTime(currentTime - process.getArrivalTime());
            }

            int timeSpent = Math.min(timeQuantum, process.getRemainingTime());
            process.setRemainingTime(process.getRemainingTime() - timeSpent);
            currentTime += timeSpent;

            if (process.getRemainingTime() == 0) {
                process.setEndTime(currentTime);
                process.setTurnAroundTime(currentTime - process.getArrivalTime());
                process.setWaitingTime(process.getTurnAroundTime() - process.getBurstTime());
                processManager.removeProcess(process);
                processManager.addFinishedProcess(process);
            } else {
                readyQueue.add(process);
            }
        }
    }
}
