package Algorithms;

import java.util.*;

import Models.Process;
import Models.ProcessManager;


public class FCFS implements SchedulingAlgorithm{
    ProcessManager processManager;

    public FCFS(ProcessManager processManager) {
        this.processManager = processManager;
    }

    public void schedule() {
        List<Process> processes = new ArrayList<>(processManager.getReadyQueue());

        int currentTime = 0;


        for (Process process : processes) {
            process.setStartTime(currentTime);
            currentTime += process.getBurstTime();
            process.setEndTime(currentTime);
            process.setTurnAroundTime(process.getEndTime() - process.getArrivalTime());
            process.setWaitingTime(process.getTurnAroundTime() - process.getBurstTime());
            process.setResponseTime(process.getStartTime() - process.getArrivalTime());
            process.setRemainingTime(0);
            processManager.removeProcess(process);
            processManager.addFinishedProcess(process);
        }
    }
}