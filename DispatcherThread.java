
public class DispatcherThread extends Thread{
	     @Override
	    public void run() {
	        while (true) {
	            synchronized (SystemManager.lock) {
	                if (!SystemManager.jobQueue.isEmpty()) {
	                    Process nextProcess = SystemManager.jobQueue.peek();

	                    if (SystemManager.currentMemoryUsed + nextProcess.memory <= SystemManager.MAX_MEMORY) {
	                        Process p = SystemManager.jobQueue.poll();
	                        p.timeEnteredReadyQueue=SystemManager.globalTime;
	                        SystemManager.readyQueue.add(p);
	                        SystemManager.currentMemoryUsed += p.memory;
	                        System.out.println("Process " + p.id + " moved to Ready Queue.");
	                    }
	                }
	            }

	            try {
	                Thread.sleep(1);
	            } catch (InterruptedException e) {
	                break;
	            }
	        }
	    }
	}
