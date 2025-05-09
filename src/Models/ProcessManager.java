package Models;

import java.util.*;

public class ProcessManager {
    private int nextPID = 1;
    private final Queue<Process> readyQueue = new LinkedList<>();
    private final Queue<Process> finishedQueue = new LinkedList<>();

    // الآن نستقبل arrivalTime أيضاً
    public void createProcess(int burstTime, int priority, int arrivalTime) {
        Process p = new Process(nextPID++, burstTime, priority, arrivalTime);
        readyQueue.add(p);
    }

    public int nextPID() {
        return nextPID;
    }

    public Queue<Process> getReadyQueue() {
        return readyQueue;
    }

    public Queue<Process> getFinishedQueue() {
        return finishedQueue;
    }

    public void addFinishedProcess(Process p) {
        finishedQueue.add(p);
    }

    public void removeProcess(Process p) {
        readyQueue.remove(p);
    }

    public void resetFinished() {
        finishedQueue.clear();
    }

    public void resetAll() {
        readyQueue.clear();
        finishedQueue.clear();
        nextPID = 1;
    }
}
