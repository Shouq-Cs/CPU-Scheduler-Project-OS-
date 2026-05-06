import java.io.*;
import java.util.ArrayList;

public class JobReader {

    public static ArrayList<Process> loadProcesses(String fileName) {

        ArrayList<Process> processList = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(":"); // Split line data using :

                int id = Integer.parseInt(parts[0]);
                int burst = Integer.parseInt(parts[1]);
                int priority = Integer.parseInt(parts[2]);
                int memory = Integer.parseInt(parts[3]);

                Process p = new Process(id, burst, priority, memory);

                processList.add(p);
            }

            br.close();

        } catch (Exception e) {

            System.out.println("Error reading file");
        }

        return processList;
    }
}