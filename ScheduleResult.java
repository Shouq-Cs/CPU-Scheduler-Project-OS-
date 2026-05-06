public class ScheduleResult {

    Process process;
    int startTime;
    int finishTime;

    public ScheduleResult(Process process, int startTime, int finishTime) {

        this.process = process;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }
}