package Models;

import java.util.*;

public class ProcessManager {

    private static int nextPID = 1;
    private Queue<Process> readyQueue;

    private Queue<Process> finishedQueue;

    public ProcessManager() {
        readyQueue = new LinkedList<>();
        finishedQueue = new LinkedList<>();
    }




    public void addProcess(Process process) {
        readyQueue.add(process);
    }



    public void createProcess(int burstTime, int priority) {
        Process p = new Process(nextPID++, burstTime, priority, 0);
        addProcess(p);
    }

    public Queue<Process> getReadyQueue() {
        return readyQueue;
    }

    public void removeProcess(Process process) {
        if (getReadyQueue().contains(process)) {
            this.readyQueue.remove(process);
        }
    }

    public boolean isReadyQueueEmpty() {
        return readyQueue.isEmpty();
    }

    public void addFinishedProcess(Process process) {
        finishedQueue.add(process);
    }
    public Queue<Process> getFinishedQueue() {
        return finishedQueue;
    }
}
