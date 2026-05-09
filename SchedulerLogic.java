import java.util.ArrayList;
import java.util.List;

public class SchedulerLogic {

	    public static Process getNextSJF(List<Process> readyQueue) {
	        if (readyQueue.isEmpty()) {
	            return null; // No ready processes
	        }

	        Process shortestJob = readyQueue.get(0);

	        for (Process p : readyQueue) {
	            if (p.burstTime < shortestJob.burstTime) {
	                shortestJob = p;
	            } 
	            else if (p.burstTime == shortestJob.burstTime) {
	                if (p.id < shortestJob.id) {
	                    shortestJob = p;
	                }
	            }
	        }
	        return shortestJob;
	    }

	    public static Process getNextPriority(List<Process> readyQueue) {
	        if (readyQueue.isEmpty()) {
	            return null; 
	        }

	        Process highestPriorityJob = readyQueue.get(0);

	        for (Process p : readyQueue) {
	            if (p.priority < highestPriorityJob.priority) {
	                highestPriorityJob = p;
	            } 
	            else if (p.priority == highestPriorityJob.priority) {
	                if (p.id < highestPriorityJob.id) {
	                    highestPriorityJob = p;
	                }
	            }
	        }
	        return highestPriorityJob;
	    }

	 
	 public static void applyAging(List<Process> readyQueue, int currentTime) {

	     int n = readyQueue.size();

	     if (n == 0)
	         return;

	     // Starvation threshold = N * 5 ms
	     int starvationThreshold = n * 5;

	     for (Process p : readyQueue) {

	         int currentWaitTime = currentTime - p.timeEnteredReadyQueue;

	         if (currentWaitTime > starvationThreshold) {

	             p.isStarved = true;

	             // Apply aging every 4 ms
	             if ((currentTime - p.lastAgingTime) >= 4) {

	                 if (p.priority > 1) {
	                     p.priority--;
	                 }

	                 p.lastAgingTime = currentTime;
	             }
	         }
	     } 
	     } 

		 public static List<ScheduleResult> runRoundRobin(int quantum) {
	        // Time simulation variable to track CPU time
	        int currentTime = 0; 
	        
	        // List to record execution intervals for Gantt Chart generation
	        List<ScheduleResult> executionTimeline = new ArrayList<>(); 

	        System.out.println("\n--- Starting Round Robin Simulation (q=" + quantum + "ms) ---");

	        while (true) {
	            Process p = null;

	            synchronized (SystemManager.lock) {
	                // Break the loop if all processes are finished
	                if (SystemManager.jobQueue.isEmpty() && SystemManager.readyQueue.isEmpty()) 
	                    break;

	                // Dequeue the first process from the ready queue
	                p = SystemManager.readyQueue.poll();
	            }

	            if (p != null) {
	                // Set start time for the first execution of the process
	                if (p.startTime == -1) p.startTime = currentTime;

	                // Determine execution time: either the Time Quantum or remaining time
	                int timeToRun = Math.min(p.remainingTime, quantum);
	                
	                // Record exactly when this process executed
	                executionTimeline.add(new ScheduleResult(p, currentTime, currentTime + timeToRun));

	                // --- Time Simulation Update ---
	                currentTime += timeToRun;
	                p.remainingTime -= timeToRun;

	                if (p.remainingTime > 0) {
	                    // If process is not finished, add it back to the ready queue
	                    synchronized (SystemManager.lock) {
	                        SystemManager.readyQueue.add(p);
	                    }
	                } else {
	                    // Process is finished! Record finish time for metrics calculation
	                    p.finishTime = currentTime;
	                    
	                    // Release memory back to the system
	                    synchronized (SystemManager.lock) {
	                        SystemManager.currentMemoryUsed -= p.memory;
	                    }
	                }
	            } else {
	                try {
        Thread.sleep(1);
    } catch (InterruptedException e) {
        break;
    }
	            }
	        }
	        
	        // Return the execution timeline for Gantt chart and metrics processing
	        return executionTimeline; 
	    }
	 

		public static ArrayList<ScheduleResult> runSJF() {

    int currentTime = 0;

    ArrayList<ScheduleResult> results = new ArrayList<>();

    while (true) {

        Process p = null;

        synchronized (SystemManager.lock) {

            if (SystemManager.jobQueue.isEmpty() && SystemManager.readyQueue.isEmpty()) {
                break;
            }

            p = getNextSJF(new ArrayList<>(SystemManager.readyQueue));

            if (p != null) {
                SystemManager.readyQueue.remove(p);
            }
        }

        if (p != null) {

            p.startTime = currentTime;

            int finish = currentTime + p.burstTime;

            p.finishTime = finish;
            p.waitingTime = p.startTime;
            p.turnaroundTime = p.finishTime;

            results.add(new ScheduleResult(p, p.startTime, p.finishTime));

            currentTime = finish;

            synchronized (SystemManager.lock) {
                SystemManager.currentMemoryUsed -= p.memory;
            }

        } else {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    return results;
}

public static ArrayList<ScheduleResult> runPriority() {

    int currentTime = 0;

    ArrayList<ScheduleResult> results = new ArrayList<>();

    while (true) {

        Process p = null;

        synchronized (SystemManager.lock) {

            if (SystemManager.jobQueue.isEmpty() && SystemManager.readyQueue.isEmpty()) {
                break;
            }

            // Apply aging before selecting the next process
            applyAging(new ArrayList<>(SystemManager.readyQueue), currentTime);

            p = getNextPriority(new ArrayList<>(SystemManager.readyQueue));

            if (p != null) {
                SystemManager.readyQueue.remove(p);
            }
        }

        if (p != null) {

            p.startTime = currentTime;

            int finish = currentTime + p.burstTime;

            p.finishTime = finish;
            p.waitingTime = p.startTime;
            p.turnaroundTime = p.finishTime;

            results.add(new ScheduleResult(p, p.startTime, p.finishTime));

            currentTime = finish;

            synchronized (SystemManager.lock) {
                SystemManager.currentMemoryUsed -= p.memory;
            }

        } else {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    return results;
}
	 }
	 