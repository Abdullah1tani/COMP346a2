package COMP346a2;

public class CPU {
	private int cpuId;
	private int utilization;
	private Process running;
	private static int CPUsCounter = 1; 
	
	public CPU() {
		this.utilization = 0;
		this.running = new Process();
		this.cpuId = CPUsCounter;
		CPUsCounter++;
	}
	
	/**
	 * parametrized constructor for CPU
	 * @param utilization
	 * @param running
	 */
	public CPU(int utilization, Process running) {
		this.utilization = utilization;
		this.running = running;
		this.cpuId = CPUsCounter;
		CPUsCounter++;
	}
	
	/**
	 * mutator method for utilization
	 * @param utilization
	 */
	public void setUtilization(int utilization){
		this.utilization = utilization;
	}
	
	/**
	 * mutator method to set the CPUsCounter
	 * @param counter
	 */
	public void setCPUsCounter(int counter) {
		CPUsCounter = counter;
	}
	
	/**
	 * mutator method for running
	 * @param running
	 */
	public void setRunning(Process running) {
		this.running = running;
	}
	
	/**
	 * accessor method for cpuId
	 * @return cpuId
	 */
	public int getCpuId() {
		return this.cpuId;
	}
	
	/**
	 * acessor method for utilization
	 * @return utilization
	 */
	public int getUtilization() {
		return utilization;
	}
	
	/**
	 * accessor method for CPUsCounter
	 * @return CPUs counter
	 */
	public int getCPUsCounter()	{
		return CPUsCounter;
	}
	
	/**
	 * accessor method for running
	 * @return running
	 */
	public Process getRunning() {
		return running;
	}
	
	public String toString() 
	{
		if(running.getProcessID() == -1)
		{
			return "CPU ID : " + cpuId + "\nProcess id Running: NONE";
		}
		else 
		{
			return "CPU ID : " + cpuId + "\nProcess id Running: " + running.getProcessID();
		}
	}
}
