package sjf;


public class Clock {
	
	protected static int ticks;
	
	public Clock() {
		ticks = 0;
		
	}

	public void Time_Run() {
		ticks++;
	}
	
	public int showTime() {
		return ticks;
	}
}
