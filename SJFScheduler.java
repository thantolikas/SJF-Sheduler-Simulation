package sjf;

public class SJFScheduler {

	private boolean isPreemptive;

	public SJFScheduler(boolean isPreemptive) {
		this.isPreemptive = isPreemptive;
	}

	public void addProcessToReadyList(Process process) {
		Main.readyProcList.addProcess(process);
	}

	public void SJF() {

		while (Main.newProcTempList.getFirstWithoutRemove() != null) {
			if (Main.newProcTempList.getFirstWithoutRemove().getArrival() == Main.clock
					.showTime()) {
				addProcessToReadyList(Main.newProcTempList.getFirst());
			} else {
				break;
			}
		}
		Main.stats.updateMaximumListLength();

		if (!Main.readyProcList.isEmpty()) {
			if (!Main.cpu.containsProcess()) {
				Main.cpu.addProcess(Main.readyProcList.getProcessToRunInCPU(),
						Main.clock.showTime());
			} else if (isPreemptive) {
				if (Main.cpu.getRunningProcess().getCpuRemainingTime() > Main.readyProcList
						.getList().get(0).getCpuRemainingTime()) {
					Main.readyProcList.addProcess(Main.cpu
							.removeProcessFromCpu());
					Main.cpu.addProcess(
							Main.readyProcList.getProcessToRunInCPU(),
							Main.clock.showTime());
				}

			}
			if (Main.cpu.getRunningProcess().cpuFirstTime) {
				Main.stats.updateResponseTime(Main.clock.showTime()
						- Main.cpu.getRunningProcess().getArrival());
				Main.cpu.getRunningProcess().cpuFirstTime = false;
			}
		}

		Main.stats.updateTotalWaitingTime(Main.readyProcList.getSize());

		Main.cpu.execute();

		if (Main.cpu.containsProcess()) {
			if (Main.cpu.getRunningProcess().getCpuRemainingTime() == 0) {
				Process temp = Main.cpu.removeProcessFromCpu();
				Main.stats.updateTotalBurstTime(temp.getCpuTotal());
			}
		}

	}
}
