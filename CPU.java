package sjf;

public class CPU {
	
	private Process runningProcess;
	private int timeToNextContextSwitch;
	private int lastProcessStartTime;
	
    public CPU() {
		runningProcess = null;
		timeToNextContextSwitch = 0;
		lastProcessStartTime = 0 ;
	}
	
	public void addProcess(Process process,int ticks) {
			runningProcess = process;
			runningProcess.setProcessState(2);
			lastProcessStartTime = ticks;
			timeToNextContextSwitch = process.getCpuTotal();
	}
	
	public boolean containsProcess() {
		if(runningProcess == null) return false;
		else return true;
	}
	
	public Process getRunningProcess() {
		return runningProcess;
	}
	
	public Process removeProcessFromCpu() {
		Process temp = runningProcess;
		runningProcess = null;
		return temp;
	}
	
	public void execute() {
		if (containsProcess()){
			runningProcess.changeCpuRemainingTime(runningProcess.getCpuRemainingTime()-1);
		}
	}

	public int getNextSwitchTime() {
		return timeToNextContextSwitch + lastProcessStartTime;
	}
	
}
