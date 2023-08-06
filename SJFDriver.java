package COMP346a2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJFDriver {

	public static void main(String[] args) {
		 try {
	            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("samplerunSJF.txt")));
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
        double averageWaitTime = 0;
		
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
        
        // compare total execution time of each process
        Comparator<Process> totalExecTimeComparator = Comparator.comparingInt(Process::getTotalExecTime);
        
        // sort ready queue with shortest total execution time first
        Collections.sort(readyQueue, totalExecTimeComparator);
         
        int counter = readyQueue.size();
        
        if(CPUs.size() > counter) // if CPUs size bigger than processes size
        {
        	for(int i =0; i < counter; i++) 
        	{
        		CPUs.get(i).setRunning(readyQueue.get(0));
        		readyQueue.get(0).setState(RUNNING);
        		readyQueue.remove(0);
        	}
        } 
        else if(CPUs.size() < counter) // if processes size bigger than CPUs size
        {
        	for(int i =0; i < CPUs.size(); i++) 
        	{
        		CPUs.get(i).setRunning(readyQueue.get(i));
        		readyQueue.get(0).setState(RUNNING);
        		readyQueue.remove(0);
        	}
        }
        else { // if both CPUs size and processes size are equal
        	for(int i =0; i < CPUs.size(); i++) 
        	{
        		CPUs.get(i).setRunning(readyQueue.get(i));
        		readyQueue.get(0).setState(RUNNING);
        		readyQueue.remove(0);
        	}
        }

        // start running the program
        while (processesInProgram.size() > 0) 
        {        
        	// add all the processes that arrive at the current time unit to the ready queue
        	for(int i = 0; i < processesInProgram.size(); i++) {
        		if(processesInProgram.get(i).getArrivalTime() == timeUnit && processesInProgram.get(i).getState() == NEW) {
        			readyQueue.add(processesInProgram.get(i));
        			processesInProgram.get(i).setState(READY);
        			processesInProgram.get(i).setTurnAroundTime(timeUnit);
        		}
        	}
        	
        	// sort ready queue with shortest total execution time first
            Collections.sort(readyQueue, totalExecTimeComparator);
        	
        	// add the processes in the readyQueue to the CPUs
        	for(int i= 0; i < CPUs.size(); i++) {
        		// if a CPU is idle and the ready queue is not empty then add a process to that CPU and remove it from the ready queue
        		if(CPUs.get(i).getRunning().getProcessID() == -1 && readyQueue.size() > 0) {
        			if(readyQueue.get(0).getProgramCounter() == 0) { // if it's the first response for a process (it's first instruction being executed)
        				readyQueue.get(0).setResponseTime(timeUnit - readyQueue.get(0).getArrivalTime());
        			}
        			CPUs.get(i).setRunning(readyQueue.get(0));
        			readyQueue.get(0).setState(RUNNING);
        			readyQueue.remove(0);
        		}
        	}
        	
        	printSampleRun(timeUnit, CPUs, readyQueue, io, processes);
        	
        	for(int i =0; i < readyQueue.size(); i++) {
        		readyQueue.get(i).setWaitTime(readyQueue.get(i).getWaitTime() + 1);
        	}
        	
        	if(io.size() > 0) { // check if there is a process in the io queue
        		io.get(0).setIoTimeRequest(io.get(0).getIoTimeRequest() +1);
        		if(io.get(0).getIoTimeRequest() == 2) { // if the first process in the queue spent 2 time cycles then send it back to the ready queue
        			if(io.get(0).getProgramCounter() == io.get(0).getTotalExecTime()) {
        				io.get(0).setState(TERMINATED);
        				io.get(0).setTurnAroundTime(timeUnit - io.get(0).getTurnaroundTime());
                        processesInProgram.remove(io.get(0));
                        io.remove(0);
        			}
        			else 
        			{
            			readyQueue.add(io.get(0)); // send the process to the ready queue
            			io.get(0).setState(READY); // set the process state to ready
            			io.get(0).setProgramCounter(io.get(0).getProgramCounter() + 1); // increase the program counter
            			io.remove(0); // remove the process from the io queue
        			}
        		}
        	}
        	
        	for(int i =0; i < CPUs.size(); i++) {
        		if(CPUs.get(i).getRunning().getProcessID() != -1) { // if a CPU is running a process increase CPU utilization by 1
        			CPUs.get(i).setUtilization(CPUs.get(i).getUtilization() + 1);
        		}
        		//check for IO
        		if(CPUs.get(i).getRunning().getIoRequestAtTimes().size() > 0 && CPUs.get(i).getRunning().getProgramCounter() == CPUs.get(i).getRunning().getIoRequestAtTimes().get(0)) {
        			CPUs.get(i).getRunning().getIoRequestAtTimes().remove(0);
        			CPUs.get(i).getRunning().setState(WAITING); // set the process state to waiting
        			CPUs.get(i).getRunning().setIoTimeRequest(0);
        			
        			io.add(CPUs.get(i).getRunning()); // send process to io queue
        			CPUs.get(i).setRunning(new Process()); // remove the process from the CPU that was running it
        		}
        	}
        	
        	// terminate process if program counter is the same as number of instructions
        	for(int i =0; i < CPUs.size(); i++) {
                if(CPUs.get(i).getRunning().getProcessID() !=-1 && CPUs.get(i).getRunning().getProgramCounter() == CPUs.get(i).getRunning().getTotalExecTime())
                {
                	CPUs.get(i).getRunning().setState(TERMINATED);
                	CPUs.get(i).getRunning().setTurnAroundTime(timeUnit - CPUs.get(i).getRunning().getTurnaroundTime());
                    processesInProgram.remove(CPUs.get(i).getRunning());
                    CPUs.get(i).setRunning(new Process());     
                }
        	}
        	
        	// increase program counter for each process running in a CPU
        	for(int i= 0; i < CPUs.size(); i++) {
        		if(CPUs.get(i).getRunning().getProcessID() != -1) {
        			CPUs.get(i).getRunning().setProgramCounter(CPUs.get(i).getRunning().getProgramCounter() + 1); // increase PC
        		}
        	}
        	timeUnit++; // increase time unit
        }
        
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("samplerunSJF.txt", true)));
            
            for(int i=0;i < CPUs.size(); i++) {
            	CPUs.get(i).setUtilization((CPUs.get(i).getUtilization()/timeUnit) * 100);
            	pw.write("CPU " + CPUs.get(i).getCpuId() + " utilization : " + CPUs.get(i).getUtilization() + "%\n");
            }
            
        	pw.write("\n");
            
            for(int i =0; i < processes.size(); i++) {
            	averageWaitTime = averageWaitTime + processes.get(i).getWaitTime();
            	pw.write("Process " + processes.get(i).getProcessID() + ":\n----------\nTurnaround Time : " + processes.get(i).getTurnaroundTime() +"\nWait Time : " + processes.get(i).getWaitTime() + "\nResponse Time : " + processes.get(i).getResponseTime() + "\n\n");
            }
                    
            averageWaitTime = averageWaitTime/processes.size();
            pw.write("Average wait time : " + averageWaitTime + "\n\n");
            
            pw.close();
			}catch(IOException e) {
				System.out.println("Error: terminating program");
			 	System.exit(0);
			}
	}
        public static void printSampleRun(int timeUnit, ArrayList<CPU> CPUs, ArrayList<Process> readyQueue, ArrayList<Process> io, ArrayList<Process> processes) {
        	try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("samplerunSJF.txt", true)));

                pw.write("******************* System Informations *******************\n");
                pw.write("Time unit: " + timeUnit + "\n\n");
                
                for(int i =0; i < CPUs.size(); i++) 
                {  	
                	pw.write(CPUs.get(i).toString());
                	pw.write("\n\n");
                }            

                pw.write("Ready Queue: [");
                
                for(int i =0; i < readyQueue.size(); i++) 
                {  	
                	pw.write(Integer.toString(readyQueue.get(i).getProcessID()));
                	if(i != readyQueue.size() -1) {
                 		pw.write(", ");
                 	}
                }   

                pw.write("] \nIO: [");
                for(int i = 0; i < io.size(); i++)
                {
               	 pw.write(Integer.toString(io.get(i).getProcessID()));
                	if(i != io.size() -1) {
                		pw.write(", ");
                	}
                }
                
                pw.write("]\n\n======================== Processes ========================\n");                
                for(int i = 0; i < processes.size(); i++){
               	 pw.write(processes.get(i).toString() + "\n\n");
                }          
                
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
