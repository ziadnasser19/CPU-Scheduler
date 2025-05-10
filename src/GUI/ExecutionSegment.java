package GUI;

public class ExecutionSegment {
    public final int pid, start, end;
    public ExecutionSegment(int pid, int start, int end) {
        this.pid = pid;
        this.start = start;
        this.end = end;
    }
}