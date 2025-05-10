package Algorithms;

import Models.Process;
import Models.ProcessManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJFNonPreemptive implements SchedulingAlgorithm {
    private final ProcessManager manager;

    public SJFNonPreemptive(ProcessManager manager) {
        this.manager = manager;
    }

    @Override
    public void schedule() {
        List<Process> processes = new ArrayList<>(manager.getReadyQueue());
        processes.sort(Comparator.comparingInt(Process::getBurstTime));

        manager.resetFinished();

        int currentTime = 0;
        for (Process p : processes) {
            p.setState(Process.ProcessState.RUNNING);
            p.setStartTime(currentTime);
            currentTime += p.getBurstTime();
            p.setEndTime(currentTime);

            p.setResponseTime(p.getStartTime() - p.getArrivalTime());
            p.setTurnAroundTime(p.getEndTime() - p.getArrivalTime());
            p.setWaitingTime(p.getTurnAroundTime() - p.getBurstTime());

            manager.addFinishedProcess(p);
        }
    }
}