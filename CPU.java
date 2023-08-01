
public class CPU {
	private int utilization;
	private Process running;
	
	public CPU() {
		this.utilization = 0;
		this.running = new Process();
	}
	
	/**
	 * parametrized constructor for CPU
	 * @param utilization
	 * @param running
	 */
	public CPU(int utilization, Process running) {
		this.utilization = utilization;
		this.running = running;
	}
	
	/**
	 * mutator method for utilization
	 * @param utilization
	 */
	public void setUtilization(int utilization){
		this.utilization = utilization;
	}
	
	/**
	 * mutator method for running
	 * @param running
	 */
	public void setRunning(Process running) {
		this.running = running;
	}
	
	/**
	 * acessor method for utilization
	 * @return utilization
	 */
	public int getUtilization() {
		return utilization;
	}
	
	/**
	 * accessor method for running
	 * @return running
	 */
	public Process getRunning() {
		return running;
	}
	
	public String toString() {
		return "utilization: " + utilization + "\nProcess id Running: " + running.getProcessID();
	}
}
