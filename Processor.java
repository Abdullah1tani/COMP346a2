import java.util.ArrayList;
import java.util.Random;

public class Processor {
	private int processID;
    private int totalExecTime;
    private int arrivalTime;
    private int waitTime;
    private int turnaroundTime;
    private int programCounter;
    private int ioTimeRequest;
    private ProcessState state;
    private ArrayList<Integer> ioRequestAtTimes;
    
    // Enum to represent the state of the process
    public enum ProcessState {
        NEW,
    	READY,
        RUNNING,
        WAITING,
        TERMINATED
    }
    
    
    /**
     * processor default constructor
     */
    public Processor() {
        this.ioRequestAtTimes = new ArrayList<>();
        this.processID = 0;
        this.totalExecTime = 0;
        this.state = ProcessState.NEW;
        this.programCounter = 0;
        this.ioTimeRequest = 0;
        this.turnaroundTime = -1;
        this.waitTime = -1;
        this.arrivalTime = -1;
    }
      
    /**
     * mutator method to set the process id
     * @param processID
     */
    public void setProcessID(int processID) {
    	this.processID = processID;
    }

    /**
     * mutator method to set the state of the processor
     * @param state
     */
    public void setState(ProcessState state) {
        this.state = state;
    }

    /**
     * mutator method to set the program counter
     * @param programCounter
     */
    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }
    
    /**
     * mutator method to set the ioRequestAtTimes array list
     * @param ioRequestAtTimes
     */
    public void setIoRequestAtTimes(ArrayList<Integer> ioRequestAtTimes){
    	this.ioRequestAtTimes = ioRequestAtTimes;
    }
    
    /**
     * mutator method to set the io time request
     * @param ioTimeRequest
     */
    public void setTimeRequest(int ioTimeRequest){
    	this.ioTimeRequest = ioTimeRequest;
    }
    
    /**
     * mutator method to set the turn around time
     * @param turnAroundTime
     */
    public void setTurnAroundTime(int turnAroundTime) {
    	this.turnaroundTime = turnAroundTime;
    }
    
    /**
     * mutator method to set the wait time
     * @param waitTime
     */
    public void setWaitTime(int waitTime) {
    	this.waitTime = waitTime;
    }
    
    /**
     * mutator method to set the arrival time
     * @param arrivalTime
     */
    public void setArrivalTime(int arrivalTime) {
    	this.arrivalTime = arrivalTime;
    }
    
    // getters for Processor ===============================================================================
    
    /**
     * accessor method to get the process id
     * @return the process id
     */
    public int getProcessID() {
        return processID;
    }

    /**
     * accessor method to get the total execution time
     * @return total execution time
     */
    public int getTotalExecTime() {
        return totalExecTime;
    }

    /**
     * accessor method to get the arrival time
     * @return arrival time
     */
    public int getArrivalTime() {
        return arrivalTime;
    }
    
    /**
     * accessor method to get the wait time
     * @return wait time
     */
    public int getWaitTime() {
        return waitTime;
    }
    
    /**
     * accessor method to get the turnaround time
     * @return turnaround time
     */
    public int getTurnaroundTime() {
        return turnaroundTime;
    }
    
    
    /**
     * accessor method to get the ioRequestAtTimes array list
     * @return the ioRequestAtTimes array list
     */
    public ArrayList<Integer> getIoRequestAtTimes() {
        return ioRequestAtTimes;
    }

    /**
     * accessor method to get the process state
     * @return state of the process
     */
    public ProcessState getState() {
        return state;
    }
    
    /**
     * accessor method to get the program counter
     * @return current program counter
     */
    public int getProgramCounter() {
        return programCounter;
    }

    /**
     * accessor method to get the io time request
     * @return io time request
     */
    public int getIoTimeRequest() {
        return ioTimeRequest;
    }
    
    // add ioRequest to ioRequestAtTimes 
    public void addIoRequestAtInstruction(int ioRequest){
    	ioRequestAtTimes.add(ioRequest);
    }
    
    public String toString()
    {
    	// generate random numbers for the registers values
        Random random = new Random(); 
        int register1 = random.nextInt(500 - 1 + 1) + 1;
        int register2 = random.nextInt(500 - 1 + 1) + 1;
        
        return "Process ID: " + processID + "\nProgram Counter: " + programCounter + "\nState: " + state + "\nRegister 1: " + register1 + "\nRegister 2: " + register2;
    }
}
