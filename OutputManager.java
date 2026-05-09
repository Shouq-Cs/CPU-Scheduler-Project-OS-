import java.util.ArrayList;

public class OutputManager {

    // Print all scheduler results
    public static void printResults(ArrayList<ScheduleResult> results) {

        printGanttChart(results);

        printResultsTable(results);

        printAverageTimes(results);

        printStarvedProcesses(results);
    }

    // =========================
    // Gantt Chart
    // =========================
    public static void printGanttChart(ArrayList<ScheduleResult> results) {

        System.out.println("\n=================================");
        System.out.println("            GANTT CHART");
        System.out.println("=================================");

        for (ScheduleResult r : results) {

            System.out.print("| P" + r.process.id + " ");
        }

        System.out.println("|");

        for (ScheduleResult r : results) {

            System.out.print(r.startTime + "    ");
        }

        System.out.println(results.get(results.size() - 1).finishTime);
    }

    // =========================
    // Results Table
    // =========================
    public static void printResultsTable(ArrayList<ScheduleResult> results) {

        System.out.println("\n=================================");
        System.out.println("          RESULTS TABLE");
        System.out.println("=================================");

        System.out.printf("%-10s %-10s %-10s %-10s %-12s %-15s%n",
                "Process",
                "Burst",
                "Start",
                "Finish",
                "Waiting",
                "Turnaround");

        System.out.println("---------------------------------------------------------------------");

        ArrayList<Process> printedProcesses = new ArrayList<>();

for (ScheduleResult r : results) {

    Process p = r.process;

    // Skip if process already printed
    if (printedProcesses.contains(p)) {
        continue;
    }

    printedProcesses.add(p);

    int waiting = p.finishTime - p.burstTime;

    int turnaround = p.finishTime;

    // Save values
    p.waitingTime = waiting;
    p.turnaroundTime = turnaround;

    System.out.printf("%-10s %-10d %-10d %-10d %-12d %-15d%n",
            "P" + p.id,
            p.burstTime,
            p.startTime,
            p.finishTime,
            waiting,
            turnaround);
}
    }

    // =========================
    // Average Calculations
    // =========================
   public static void printAverageTimes(ArrayList<ScheduleResult> results) {

    ArrayList<Process> calculatedProcesses = new ArrayList<>();

    double totalWaiting = 0;
    double totalTurnaround = 0;

    for (ScheduleResult r : results) {

        Process p = r.process;

        if (calculatedProcesses.contains(p)) {
            continue;
        }

        calculatedProcesses.add(p);

        totalWaiting += p.waitingTime;
        totalTurnaround += p.turnaroundTime;
    }

    double avgWaiting = totalWaiting / calculatedProcesses.size();
    double avgTurnaround = totalTurnaround / calculatedProcesses.size();

    System.out.println("\n=================================");
    System.out.println("         PERFORMANCE");
    System.out.println("=================================");

    System.out.printf("Average Waiting Time: %.2f ms%n", avgWaiting);
    System.out.printf("Average Turnaround Time: %.2f ms%n", avgTurnaround);
}

    // =========================
    // Starvation
    // =========================
    public static void printStarvedProcesses(ArrayList<ScheduleResult> results) {

        boolean found = false;

        System.out.println("\n=================================");
        System.out.println("       STARVATION CHECK");
        System.out.println("=================================");

        for (ScheduleResult r : results) {

            if (r.process.isStarved) {

                found = true;

                System.out.println("Process P" + r.process.id
                        + " suffered from starvation.");
            }
        }

        if (!found) {

            System.out.println("No starved processes detected.");
        }
    }
}