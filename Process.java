import java.util.ArrayList;
import java.util.Random;

public class Process {
	private int processID;
    private int totalExecTime;
    private int arrivalTime;
    private int waitTime;
    private int responseTime;
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
    public Process() {
        this.ioRequestAtTimes = new ArrayList<>();
        this.processID = -1;
        this.totalExecTime = 0;
        this.state = ProcessState.NEW;
        this.programCounter = 0;
        this.ioTimeRequest = 0;
        this.turnaroundTime = 0;
        this.waitTime = 0;
        this.arrivalTime = 0;
        this.responseTime = 0;
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
    
    /**
     * mutator method to set the response time
     * @param responseTime
     */
    public void setResponseTime(int responseTime) {
    	this.responseTime = responseTime;
    }
    
    /**
     * mutator method to set the total execution time 
     * @param totalExecTime
     */
    public void setTotalExecTime(int totalExecTime) {
    	this.totalExecTime = totalExecTime;
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
    
    /**
     * accessor method to get the response time
     * @return response time
     */
    public int getResponseTime() {
    	return responseTime;
    }
    
    // add ioRequest to ioRequestAtTimes 
    public void addIoRequestAtTimes(int ioRequest){
    	ioRequestAtTimes.add(ioRequest);
    }
    
    public String toString()
    {        
        return "Process ID: " + processID + "\nProgram Counter: " + programCounter + "\nState: " + state;
    }
}
