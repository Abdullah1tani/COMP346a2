import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		Process.ProcessState NEW = Process.ProcessState.NEW;
		Process.ProcessState READY = Process.ProcessState.READY;
		Process.ProcessState RUNNING = Process.ProcessState.RUNNING;
		Process.ProcessState WAITING = Process.ProcessState.WAITING;
		Process.ProcessState TERMINATED = Process.ProcessState.TERMINATED;
    	
		ArrayList<Process> processesInProgram = new ArrayList<>();
        ArrayList<Process> readyQueue = new ArrayList<>();
        ArrayList<Process> processes = new ArrayList<>();
        ArrayList<Process> io = new ArrayList<>();
        ArrayList<CPU> CPUs = new ArrayList<>();
        
        int numOfCPUs = 0, q = 0, timeUnit = 0;
		
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String line;
                
            // split the first line and add the value of numOfCPUs
            line = reader.readLine();
            String[] firstLine = line.split("=");
            numOfCPUs = Integer.parseInt(firstLine[1]);
            
            // creating all the CPUs and adding them to an arraylist
            for(int i = 0; i < numOfCPUs; i++) {
            	CPU cpu = new CPU();
            	CPUs.add(cpu);
            }

            // split the second line and add the value of q
            line = reader.readLine();
            String[] secondLine = line.split("=");
            q = Integer.parseInt(secondLine[1]);
            
            // skip the 2 comment lines from the input file
            reader.readLine();
            reader.readLine();
           
            while ((line = reader.readLine()) != null) 
            {
                
                //create a processor
            	Process process = new Process();
            	
            	// split the values in the input file
                String[] values = line.split("	");
                
                // set the processID of the process
                String processID = values[0].substring(1);
                int processId = Integer.parseInt(processID);
                process.setProcessID(processId);
                
                // set the arrival time of the process
                process.setArrivalTime(Integer.parseInt(values[1]));
                
                // set the total execution time of the process
                process.setTotalExecTime(Integer.parseInt(values[2]));
                 
                // add ioRequestAtTimes values to process
                String[] ioRequestAtTimes = values[3].substring(1, values[3].length() - 1).split(",");
                if(ioRequestAtTimes[0] == "") // if the values are empty just push -1
                {
                	process.addIoRequestAtTimes(-1);
                }
                else
                {
                	for (int i =0; i < ioRequestAtTimes.length; i++) {
                		process.addIoRequestAtTimes(Integer.parseInt(ioRequestAtTimes[i]));
                	}
                }

                processes.add(process);
                processesInProgram.add(process);
            }
        } catch (IOException e) {
        	System.out.println("Error: terminating program");
        	System.exit(0);
        }
        
        for(int i = 0; i < processes.size(); i++) {
        	System.out.println(processes.get(i).getProcessID() + "	" + processes.get(i).getArrivalTime() + "	" + processes.get(i).getTotalExecTime() + "	" + processes.get(i).getIoRequestAtTimes()); 
        }
        
	}

}
