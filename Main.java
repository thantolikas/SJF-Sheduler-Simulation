package sjf;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	static ProcessGenerator processGenerator;
	static Clock clock;
	static ReadyProcessesList readyProcList;
	static NewProcessTemporaryList newProcTempList;
	static CPU cpu;
	static SJFScheduler sjf;
	static Statistics stats;
	static boolean readfile;

	public static void main(String[] args) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		int x, y;
		int counter = 0;
		readfile = false;
		processGenerator = new ProcessGenerator("john.txt", readfile);
		clock = new Clock();
		readyProcList = new ReadyProcessesList();
		newProcTempList = new NewProcessTemporaryList();
		cpu = new CPU();
		sjf = new SJFScheduler(readfile);
		stats = new Statistics("Statistics.txt");

		if (!readfile) {
			newProcTempList.addNewProcess(processGenerator.createProcess());
			stats.updateTotalNumberOfProcesses();
			newProcTempList.addNewProcess(processGenerator.createProcess());
			stats.updateTotalNumberOfProcesses();
			newProcTempList.addNewProcess(processGenerator.createProcess());
			stats.updateTotalNumberOfProcesses();
		} else {
			for (Process process : processGenerator.parseProcessFile()) {
				newProcTempList.addNewProcess(process);
				stats.updateTotalNumberOfProcesses();
			}
		}

		while (clock.showTime() <= 40 || !readyProcList.getList().isEmpty()
				|| cpu.getRunningProcess() != null) { 
			if (!readfile) {
				if (clock.showTime() % 2 == 0 && counter < 10) {
					newProcTempList.addNewProcess(processGenerator
							.createProcess());
					stats.updateTotalNumberOfProcesses();
					counter++;
				}
			}
			System.out.println("TICK " + clock.showTime() + "\n");
			System.out.println("<---------New Temp List------------------> \n");
			newProcTempList.printList();
			System.out.println("<---------Ready List------------------> \n");
			readyProcList.printList();
			if (cpu.getRunningProcess() == null) {
				x = 0;
				y = 0;
			} else {
				x = cpu.getRunningProcess().getPid();
				y = cpu.getRunningProcess().getCpuRemainingTime();
			}
			System.out.println("<-------------CPU--------------> \n");
			System.out.println("ID: " + x + "Remaining Time: " + y);
			sjf.SJF();
			clock.Time_Run();
		}

		stats.writeStatistics2File();
	}

}
