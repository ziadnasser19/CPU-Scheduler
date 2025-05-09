package Algorithms;

import Models.Process;
import Models.ProcessManager;
import java.util.ArrayList;
import java.util.List;

public class FCFS implements SchedulingAlgorithm {
    private final ProcessManager manager;

    public FCFS(ProcessManager manager) {
        this.manager = manager;
    }

    @Override
    public void schedule() {
        List<Process> processes = new ArrayList<>(manager.getReadyQueue());
        int currentTime = 0;

        manager.resetFinished();

        for (Process p : processes) {
            p.setStartTime(currentTime);
            currentTime += p.getBurstTime();
            p.setEndTime(currentTime);

            p.setTurnAroundTime(p.getEndTime() - p.getArrivalTime());
            p.setWaitingTime(p.getTurnAroundTime() - p.getBurstTime());
            p.setResponseTime(p.getStartTime() - p.getArrivalTime());

            manager.addFinishedProcess(p);
        }
    }
}