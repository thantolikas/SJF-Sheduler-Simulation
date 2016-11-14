package sjf;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ReadyProcessesList {
	
	private List<Process> processList;
	
	public ReadyProcessesList() {
		processList = new ArrayList<Process>();
	}
	
	public void addProcess(Process item) {
		item.setProcessState(1);
		processList.add(item);
		Collections.sort(processList, new SJFComparator());
	}
	
	public Process getProcessToRunInCPU() {
			Process temp = processList.get(0);
			processList.remove(0);
			return temp;
	}
	
	public boolean isEmpty() {
		if(processList.isEmpty()) {
			return true;
		}
		else return false;
	}
	
	public void printList() {
		for (Process pro : processList) {
			System.out.println("ID: " + pro.getPid() + " Arrival Time: " + pro.getArrival() + " Total Time: " + pro.getCpuRemainingTime() + "\n");
		}
	}
	
	public List<Process> getList() {
		return processList;
	}
	
	public int getSize() {
	 	return processList.size();
	 }
	
	static class SJFComparator implements Comparator<Process> {
		public int compare(Process p1, Process p2) {
			int result = p1.getCpuRemainingTime() - p2.getCpuRemainingTime();
		
			if (result == 0) {
			return (p1.getPid() < p2.getPid()) ? -1 : 1;
			}
			else {
			return result;
			}
		}
	}

}
