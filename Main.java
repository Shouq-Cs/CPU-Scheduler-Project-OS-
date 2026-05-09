import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Process> processes;

        processes = JobReader.loadProcesses("job.txt");

 Scanner input = new Scanner(System.in);

        System.out.println("Choose Scheduling Algorithm:");
        System.out.println("1. Shortest Job First (SJF)");
        System.out.println("2. Round Robin (RR)");
        System.out.println("3. Priority Scheduling");

        int choice = input.nextInt();

        // Temporary schedule results for testing
        ArrayList<ScheduleResult> results = new ArrayList<>();

       // results.add(new ScheduleResult(processes.get(0), 0, 25));
        //results.add(new ScheduleResult(processes.get(1), 25, 38));
        //results.add(new ScheduleResult(processes.get(2), 38, 58));
        //results.add(new ScheduleResult(processes.get(3), 58, 68));

switch (choice) {

    case 1:
    System.out.println("\nRunning SJF...\n");

    SystemManager.jobQueue.addAll(processes);

    DispatcherThread dispatcherSJF = new DispatcherThread();
    dispatcherSJF.start();

    try {
        Thread.sleep(20);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    results = SchedulerLogic.runSJF();

    dispatcherSJF.interrupt();

    break;

    case 2:
        System.out.println("\nRunning Round Robin...\n");

        // Add all processes to job queue
        SystemManager.jobQueue.addAll(processes);

        // Start dispatcher thread
        DispatcherThread dispatcher = new DispatcherThread();
        dispatcher.start();
try {
    Thread.sleep(20);
} catch (InterruptedException e) {
    e.printStackTrace();
}
        // Run Round Robin scheduler
        results = new ArrayList<>(SchedulerLogic.runRoundRobin(5));

        dispatcher.interrupt();

        break;


   case 3:
    System.out.println("\nRunning Priority Scheduling...\n");

    SystemManager.jobQueue.addAll(processes);

    DispatcherThread dispatcherPriority = new DispatcherThread();
    dispatcherPriority.start();

    try {
        Thread.sleep(20);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    results = SchedulerLogic.runPriority();

    dispatcherPriority.interrupt();

    break;

    default:
        System.out.println("Invalid choice");
        return;
}

        // Print scheduler results
      OutputManager.printResults(results);

              input.close();

    }

   
}