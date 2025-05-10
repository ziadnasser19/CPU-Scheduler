package Algorithms;

import GUI.ExecutionSegment;
import Models.Process;
import Models.ProcessManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SJFPreemptive implements SchedulingAlgorithm {
    private final ProcessManager manager;
    public final List<ExecutionSegment> timeline = new ArrayList<>();

    public SJFPreemptive(ProcessManager manager) {
        this.manager = manager;
    }

    @Override
    public void schedule() {
        List<Process> processes = new ArrayList<>(manager.getReadyQueue());
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        manager.resetFinished();
        timeline.clear();

        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));
        int currentTime = 0, i = 0, total = processes.size(), finished = 0;
        int maxArrival = processes.stream()
                .mapToInt(Process::getArrivalTime).max().orElse(0);
        int totalBurst = processes.stream()
                .mapToInt(Process::getBurstTime).sum();
        int timeLimit  = maxArrival + totalBurst;
        while (finished < total) {
            if (currentTime > timeLimit) {
                System.err.println("Warning: time exceeded limit, breaking loop.");
                break;
            }


            while (i < processes.size() && processes.get(i).getArrivalTime() <= currentTime) {
                Process p = processes.get(i);
                p.setState(Process.ProcessState.READY);
                pq.add(p);
                i++;
            }

            if (pq.isEmpty()) {
                currentTime++;
                continue;
            }

            Process p = pq.poll();
            p.setState(Process.ProcessState.RUNNING);
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
                p.setState(Process.ProcessState.READY);
                pq.add(p);
            }
        }
    }
}
