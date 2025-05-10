package Algorithms;

import Models.Process;
import Models.ProcessManager;

import java.util.*;

public class SJFPreemptive implements SchedulingAlgorithm {
    private final ProcessManager manager;

    public SJFPreemptive(ProcessManager manager) {
        this.manager = manager;
    }

    @Override
    public void schedule() {
        // نسخة محلية
        List<Process> processes = new ArrayList<>(manager.getReadyQueue());
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        manager.resetFinished();

        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));
        int currentTime = 0, i = 0, total = processes.size(), finished = 0;

        while (finished < total) {
            // أضف العمليات التي وصلت
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

            p.setRemainingTime(p.getRemainingTime() - 1);
            currentTime++;

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
}
