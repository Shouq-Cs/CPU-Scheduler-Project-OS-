public class Process {

    int id;
    int burstTime;
    int remainingTime;
    int priority;
    int memory;

    int startTime = -1;
    int finishTime;
    int waitingTime;
    int turnaroundTime;

    public Process(int id, int burstTime, int priority, int memory) {

        this.id = id;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;

        this.priority = priority;
        this.memory = memory;
    }
}