import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        System.out.println("Program started");

        ArrayList<Process> processes = JobReader.loadProcesses("job.txt");

        System.out.println("Number of processes: " + processes.size());

        for (Process p : processes) {
            System.out.println(
                "ID: " + p.id +
                " Burst: " + p.burstTime +
                " Priority: " + p.priority +
                " Memory: " + p.memory
            );
        }
    }
}