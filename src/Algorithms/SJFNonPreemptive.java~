package Algorithms;

import Models.Process;
import Models.ProcessManager;

import java.util.*;

public class SJFNonPreemptive implements SchedulingAlgorithm{

    ProcessManager processManager;

    public SJFNonPreemptive(ProcessManager processManager) {
        this.processManager = processManager;
    }

    public void schedule() {
        List<Process> processes = new ArrayList<>(processManager.getReadyQueue());

        processes.sort(Comparator.comparingInt(Process::getBurstTime));


        int currentTime = 0;

        for (Process process : processes){
            process.setStartTime(currentTime);
            currentTime += process.getBurstTime();
            process.setResponseTime(process.getStartTime() - process.getArrivalTime());
            process.setRemainingTime(0);
            process.setEndTime(currentTime);
            process.setTurnAroundTime(process.getEndTime() - process.getArrivalTime());
            process.setWaitingTime(process.getTurnAroundTime() - process.getBurstTime());
            processManager.removeProcess(process);
            processManager.addFinishedProcess(process);
        }
    }
}
