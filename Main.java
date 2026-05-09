import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Process> processes;

        processes = JobReader.loadProcesses("job.txt");

        // Temporary schedule results for testing
        ArrayList<ScheduleResult> results = new ArrayList<>();

        results.add(new ScheduleResult(processes.get(0), 0, 25));
        results.add(new ScheduleResult(processes.get(1), 25, 38));
        results.add(new ScheduleResult(processes.get(2), 38, 58));
        results.add(new ScheduleResult(processes.get(3), 58, 68));

        // Print scheduler results
      OutputManager.printResults(results);
    }

   
}