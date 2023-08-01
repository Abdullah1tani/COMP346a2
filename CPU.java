
public class CPU {
	private int utilization;
	private Processor running;
	
	public CPU() {
		this.utilization = 0;
		this.running = new Processor();
	}
	
	/**
	 * parametrized constructor for CPU
	 * @param utilization
	 * @param running
	 */
	public CPU(int utilization, Processor running) {
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
	public void setRunning(Processor running) {
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
	public Processor getRunning() {
		return running;
	}
	
	public String toString() {
		return "utilization: " + utilization + "\nProcess id Running: " + running.getProcessID();
	}
}
