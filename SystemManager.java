
import java.util.LinkedList;
import java.util.Queue;
public class SystemManager {
	
    public static final Queue<Process> jobQueue = new LinkedList<>();
    public static final Queue<Process> readyQueue = new LinkedList<>();
    public static int currentMemoryUsed = 0;
    public static final int MAX_MEMORY = 2048;
    public static int globalTime=0;
    public static final Object lock = new Object();
}
