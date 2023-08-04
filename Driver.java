package COMP346a2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;

public class Driver {

	public static void main(String[] args) {
		 try {
	            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("samplerun.txt")));
	            pw.close();
		 }catch(IOException e) {
			 System.out.println("Error: terminating program");
			 System.exit(0);
		 }
		
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
                if(process.getArrivalTime() == 0)
                {
                	readyQueue.add(process);
                	process.setState(READY);
                }
            }
        } catch (IOException e) {
        	System.out.println("Error: terminating program");
        	System.exit(0);
        }
        
        if(CPUs.size() > readyQueue.size()) // if CPUs size bigger than processes size
        {
        	for(int i =0; i < readyQueue.size(); i++) 
        	{
        		CPUs.get(i).setRunning(readyQueue.get(0));
        		readyQueue.get(0).setState(RUNNING);
        		readyQueue.remove(0);
        	}
        } 
        else if(CPUs.size() < readyQueue.size()) // if processes size bigger than CPUs size
        {
        	for(int i =0; i < CPUs.size(); i++) 
        	{
        		CPUs.get(i).setRunning(readyQueue.get(i));
        		readyQueue.remove(0);
        	}
        }
        else { // if both CPUs size and processes size are equal
        	for(int i =0; i < CPUs.size(); i++) 
        	{
        		CPUs.get(i).setRunning(readyQueue.get(i));
        		readyQueue.remove(0);
        	}
        }
       //printSampleRun(timeUnit, CPUs, readyQueue, io, processes);


        Collections.sort(processesInProgram, (p1, p2) -> p1.getTotalExecTime() - p2.getTotalExecTime());

        // start running the program
        while (processesInProgram.size() > 0) 
        {              	
        	// add all the processes that arrive at the current time unit to the ready queue
        	for(int i = 0; i < processesInProgram.size(); i++) {
        		if(processesInProgram.get(i).getArrivalTime() == timeUnit && processesInProgram.get(i).getState() == NEW) {
        			readyQueue.add(processesInProgram.get(i));
        			processesInProgram.get(i).setState(READY);
        		}
        	}

            //(SJB)  sorting the ready queue based on the total execution time for SJB
            Collections.sort(readyQueue, (p1,p2) -> p1.getTotalExecTime() - p2.getTotalExecTime());




        	
        	// add the processes in the readyQueue to the CPUs (added RR scheduling)
        	for(int i= 0; i < CPUs.size(); i++) {
        		// if a CPU is idle and the ready queue is not empty then add a process to that CPU and remove it from the ready queue
        		if(CPUs.get(i).getRunning().getProcessID() == -1 && readyQueue.size() > 0) {
//                    Process nextProcess = readyQueue.get(0);
        			CPUs.get(i).setRunning(readyQueue.get(0));
        			readyQueue.get(0).setState(RUNNING);
        			readyQueue.remove(0);
        		}

                // If a CPU has a running process and the process has used up the quantum time (q), move it to the end of the ready queue
                if (CPUs.get(i).getRunning().getProcessID() != -1 && CPUs.get(i).getUtilization() == q) {
                    Process currentProcess = CPUs.get(i).getRunning();
                    currentProcess.setState(READY);
                    readyQueue.add(currentProcess);
                    CPUs.get(i).setRunning(new Process());
                }

                // Increment CPU utilization for the running process
                if (CPUs.get(i).getRunning().getProcessID() != -1) {
                    CPUs.get(i).setUtilization(CPUs.get(i).getUtilization() + 1);
                }
        	}



            // terminate process if program counter is the same as number of instructions
            for(int i =0; i < CPUs.size(); i++) {
                if(CPUs.get(i).getRunning().getProgramCounter() == CPUs.get(i).getRunning().getTotalExecTime())
                {
                    CPUs.get(i).getRunning().setState(TERMINATED);
                    processesInProgram.remove(CPUs.get(i).getRunning());
                    CPUs.get(i).setRunning(new Process());
                }
            }
        	
        	printSampleRun(timeUnit, CPUs, readyQueue, io, processes);
        	
        	// increase program counter for each process running in a CPU
        	for(int i= 0; i < CPUs.size(); i++) {
        		if(CPUs.get(i).getRunning().getProcessID() != -1) {
        			CPUs.get(i).getRunning().setProgramCounter(CPUs.get(i).getRunning().getProgramCounter() + 1); // increase PC
        		}
        	}
        	timeUnit++; // increase time unit
        }
    	
	}
        public static void printSampleRun(int timeUnit, ArrayList<CPU> CPUs, ArrayList<Process> readyQueue, ArrayList<Process> io, ArrayList<Process> processes) {
        	try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("samplerun.txt", true)));

                System.out.print("******************* System Informations *******************\n");
                System.out.print("Time unit: " + timeUnit + "\n\n");
                
                for(int i =0; i < CPUs.size(); i++) 
                {  	
                	System.out.print(CPUs.get(i).toString());
                	System.out.print("\n\n");
                }            

                System.out.print("Ready Queue: [");
                
                for(int i =0; i < readyQueue.size(); i++) 
                {  	
                	System.out.print(readyQueue.get(i).getProcessID());
                	if(i != readyQueue.size() -1) {
                 		System.out.print(", ");
                 	}
                }   

                System.out.print("] \nIO: [");
                for(int i = 0; i < io.size(); i++)
                {
               	 System.out.print(Integer.toString(io.get(i).getProcessID()));
                	if(i != io.size() -1) {
                		System.out.print(", ");
                	}
                }
                
                System.out.print("]\n\n====================== Processes PCB ======================\n");                
                for(int i = 0; i < processes.size(); i++){
               	 System.out.print(processes.get(i).toString() + "\n\n");
                }          
                
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
