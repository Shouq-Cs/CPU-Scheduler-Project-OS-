public class Process {

    int id;               // Process ID
    int burstTime;       // CPU burst time
    int remainingTime;  // Remaining execution time
    int priority;      // Process priority
    int memory;       // Memory required

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