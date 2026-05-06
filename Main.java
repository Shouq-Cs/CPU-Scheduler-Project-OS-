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
        printResults(results);
    }

    // Display Gantt chart and scheduling statistics
    public static void printResults(ArrayList<ScheduleResult> results) {

        // Print Gantt Chart
        System.out.println("\nGantt Chart:");

        for (ScheduleResult r : results) {
            System.out.print("| P" + r.process.id + " ");
        }

        System.out.println("|");

        for (ScheduleResult r : results) {
            System.out.print(r.startTime + "    ");
        }

        System.out.println(results.get(results.size() - 1).finishTime);

        // Print results table
        System.out.println("\nResults Table:");

        System.out.println("-------------------------------------------------------------");

        System.out.printf("%-10s %-10s %-10s %-10s %-15s%n",
                "Process", "Start", "Finish", "Waiting", "Turnaround");

        System.out.println("-------------------------------------------------------------");

        // Print each process result
        for (ScheduleResult r : results) {

            // Calculate waiting and turnaround time
            int waiting = r.startTime;

            int turnaround = r.finishTime;

            System.out.printf("%-10s %-10d %-10d %-10d %-15d%n",
                    "P" + r.process.id,
                    r.startTime,
                    r.finishTime,
                    waiting,
                    turnaround);
        }

        System.out.println("-------------------------------------------------------------");
    }
}