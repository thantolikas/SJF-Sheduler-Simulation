package sjf;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcessGenerator {

	private File inputFile;
	private int counter;
	private Random random;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public ProcessGenerator(String filename,boolean readFile) throws FileNotFoundException, IOException {
		counter = 0;
		random = new Random();
		inputFile = new File(filename);
		if (readFile==false) {
			out = new ObjectOutputStream(new FileOutputStream(inputFile));
		}
		else {
			in = new ObjectInputStream(new FileInputStream(inputFile));
		}
	}


	public Process createProcess() {
		counter++;
		int arrivalTime = random.nextInt(10) + Main.clock.showTime();
		int burstTime = random.nextInt(10);
		while (burstTime == 0) {
			burstTime = random.nextInt(10);
		}
		Process pro = new Process(counter,arrivalTime,burstTime);
		storeProcessToFile(pro);
		return pro;

	}

	public void storeProcessToFile(Process process) {
		try {
			out.writeObject(process);
		}catch (IOException e) {

		}
	}

	public List<Process> parseProcessFile() throws ClassNotFoundException, FileNotFoundException, IOException {
		List<Process> list = new ArrayList<>();

		try {
			while (true) {
				Process process = new Process(0,0,0);
				process = (Process) in.readObject();
				list.add(process);
			}
		}catch (EOFException e) {
			in.close();
		}
		return list;
	}

}