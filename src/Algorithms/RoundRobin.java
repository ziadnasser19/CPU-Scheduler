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
        Queue<Process> readyQueue = new LinkedList<>(manager.getReadyQueue());
        int currentTime = 0;
        timeline.clear();
        manager.resetFinished();

        while (!readyQueue.isEmpty()) {
            Process p = readyQueue.poll();
            p.setState(Process.ProcessState.RUNNING);

            if (p.getRemainingTime() == p.getBurstTime()) {
                p.setStartTime(currentTime);
                p.setResponseTime(currentTime - p.getArrivalTime());
            }

            if (p.getRemainingTime() > timeQuantum) {
                p.setRemainingTime(p.getRemainingTime() - timeQuantum);
                currentTime += timeQuantum;
                p.setState(Process.ProcessState.READY);
                readyQueue.add(p);

                timeline.add(new ExecutionSegment(p.getProcessNumber(), currentTime - timeQuantum, currentTime));
            }
            else {
                currentTime += p.getRemainingTime();
                p.setEndTime(currentTime);

                p.setTurnAroundTime(p.getEndTime() - p.getArrivalTime());
                p.setWaitingTime(p.getTurnAroundTime() - p.getBurstTime());

                timeline.add(new ExecutionSegment(p.getProcessNumber(), currentTime - p.getRemainingTime(), currentTime));
                p.setRemainingTime(0);

                manager.addFinishedProcess(p);
            }

            Queue<Process> finishedQueue = new LinkedList<>(manager.getFinishedQueue().stream()
                    .sorted(Comparator.comparingInt(Process::getProcessNumber))
                    .toList());

            manager.setFinishedQueue(finishedQueue);
        }
    }
}
