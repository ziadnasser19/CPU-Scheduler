package Algorithms;

import Models.Process;
import Models.ProcessManager;

import java.util.*;

public class SJFPreemptive implements SchedulingAlgorithm{

    ProcessManager processManager;

    public SJFPreemptive(ProcessManager processManager) {
        this.processManager = processManager;
    }

    public void schedule() {
        List<Process> processes = new ArrayList<>(processManager.getReadyQueue());
        Queue<Process> availableProcess = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));
        int currentTime = 0;
        int i = 0;
        int finishedCount = 0;
        int total = processManager.getReadyQueue().size();
        while (finishedCount < total) {
            while (i < processes.size() && processes.get(i).getArrivalTime() <= currentTime) {
                availableProcess.add(processes.get(i));
                i++;
            }

            if (availableProcess.isEmpty()) {
                currentTime++;
                continue;
            }

            Process process = availableProcess.poll();

            if (process.getRemainingTime() == process.getBurstTime()) {
                process.setStartTime(currentTime);
                process.setResponseTime(process.getStartTime() - process.getArrivalTime());
            }

            process.setRemainingTime(process.getRemainingTime() - 1);
            currentTime++;

            if (process.getRemainingTime() == 0) {
                process.setEndTime(currentTime);
                process.setTurnAroundTime(process.getEndTime() - process.getArrivalTime());
                process.setWaitingTime(process.getTurnAroundTime() - process.getBurstTime());

                processManager.removeProcess(process);
                processManager.addFinishedProcess(process);
                finishedCount++;
            }
            else {
                availableProcess.add(process);
            }
        }
    }
}
