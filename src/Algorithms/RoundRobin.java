package Algorithms;

import GUI.ExecutionSegment;
import Models.Process;
import Models.ProcessManager;

import java.util.*;

public class RoundRobin implements SchedulingAlgorithm {
    private final int timeQuantum;
    private final ProcessManager manager;
    public final List<ExecutionSegment> timeline = new ArrayList<>();

    public RoundRobin(int timeQuantum, ProcessManager manager) {
        this.timeQuantum = timeQuantum;
        this.manager = manager;
    }

    @Override
    public void schedule() {
        // نسخة محلية لاستهلاك
        Queue<Process> rq = new LinkedList<>(manager.getReadyQueue());
        int currentTime = 0;
        timeline.clear();
        manager.resetFinished();

        while (!rq.isEmpty()) {
            Process p = rq.poll();
            if (p.getRemainingTime() == p.getBurstTime()) {
                p.setStartTime(currentTime);
                p.setResponseTime(currentTime - p.getArrivalTime());
            }

            int exec = Math.min(timeQuantum, p.getRemainingTime());
            timeline.add(new ExecutionSegment(p.getProcessNumber(), currentTime, currentTime + exec));
            p.setRemainingTime(p.getRemainingTime() - exec);
            currentTime += exec;

            if (p.getRemainingTime() > 0) {
                rq.add(p);
            } else {
                p.setEndTime(currentTime);
                p.setTurnAroundTime(p.getEndTime() - p.getArrivalTime());
                p.setWaitingTime(p.getTurnAroundTime() - p.getBurstTime());
                manager.addFinishedProcess(p);
            }
        }
    }
}
