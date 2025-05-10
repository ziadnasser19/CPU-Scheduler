package Algorithms;

import Models.Process;
import Models.ProcessManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityPreemptive implements SchedulingAlgorithm {
    private final ProcessManager manager;
    public final List<ExecutionSegment> timeline = new ArrayList<>();

    public PriorityPreemptive(ProcessManager manager) {
        this.manager = manager;
    }

    @Override
    public void schedule() {
        // قائمة العمليات مرتبة حسب Arrival
        List<Process> processes = new ArrayList<>(manager.getReadyQueue());
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        manager.resetFinished();
        timeline.clear();

        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
        int currentTime = 0, i = 0, total = processes.size(), finished = 0;

        while (finished < total) {
            while (i < processes.size() && processes.get(i).getArrivalTime() <= currentTime) {
                pq.add(processes.get(i));
                i++;
            }

            if (pq.isEmpty()) {
                currentTime++;
                continue;
            }

            Process p = pq.poll();
            if (p.getRemainingTime() == p.getBurstTime()) {
                p.setStartTime(currentTime);
                p.setResponseTime(currentTime - p.getArrivalTime());
            }

            int start = currentTime;
            p.setRemainingTime(p.getRemainingTime() - 1);
            currentTime++;
            timeline.add(new ExecutionSegment(p.getProcessNumber(), start, currentTime));

            if (p.getRemainingTime() == 0) {
                p.setEndTime(currentTime);
                p.setTurnAroundTime(p.getEndTime() - p.getArrivalTime());
                p.setWaitingTime(p.getTurnAroundTime() - p.getBurstTime());
                manager.addFinishedProcess(p);
                finished++;
            } else {
                pq.add(p);
            }
        }
    }

    public static class ExecutionSegment {
        public final int pid, start, end;
        public ExecutionSegment(int pid, int start, int end) {
            this.pid = pid;
            this.start = start;
            this.end = end;
        }
    }
}
