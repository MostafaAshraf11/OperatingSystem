package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class Process {

	private int ID;
	private ArrayList<String> Instructions; //that to get its size and set CodeLines variable,M2 we Are exec from MEm not from that list
//	private ArrayList<Word> Variables; //temp variables in case of pre-emption in middle of assign a input
	private ArrayList<Word> PCB;  
	private State state;
	private int minBoundary;
	private int maxBoundary;
	private int PC;
	private int CodeLines;

	public Process(int ID) { // String path was argument
		this.ID = ID;
		// this.Instructions = Parser.readProgram(path);
		this.Instructions = new ArrayList<String>();
//		this.Variables = new ArrayList<Word>();
		this.PCB = new ArrayList<Word>();
		this.state = State.New;
	}

	public void semWaitB(Mutex m, Scheduler s) {
		if (m.getValue() == MutexValue.One) {
			m.setOwnerID(this.getID());
			m.setValue(MutexValue.Zero);
		} else {
			/* place this process in m.queue */
			m.getBlockedQueue().add(this);
			s.getBlockedQueue().add(this); // general queue -------------------------------
			/* block this process */
			this.setState(State.Blocked);
			if(this.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
				OS.memory.MainMemory[1].setData(State.Blocked);
			}else if(this.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
				OS.memory.MainMemory[21].setData(State.Blocked);
			}
	}
	}
//	public void removeFromQueue(Process p, Queue<Process> Q) {		
//		for(Process x:Q) {
//			if(x.getID()==Q.peek().getID()) {
//				Q.remove();
//			}
//			Q.add(Q.remove());
//		}
//	}
	public void semSignalB(Mutex m, Scheduler s) throws NumberFormatException, IOException {
		/* check if this process is the owner */
		if (m.getOwnerID() == this.getID()) {
			if (m.getBlockedQueue().isEmpty())
				m.setValue(MutexValue.One);
			else {
				/* remove a process P from m.queue */
				Process p = m.getBlockedQueue().remove();
				for (Process p1 : s.getBlockedQueue()) {
					if (p1.getID() == p.getID()) {
						s.getBlockedQueue().remove(p);
//						removeFromQueue(p,Scheduler.getBlockedQueue());
						s.getReadyQueue().add(p);
					}
				}
				/* place process P on ready list */
				// general queue-----------------------------------------
				
				p.setState(State.Ready);
				if(p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
					OS.memory.MainMemory[1].setData(State.Ready);
				}else if(p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
					OS.memory.MainMemory[21].setData(State.Ready);
				}
				if(p.getID()==(int)Integer.parseInt(""+(SystemCalls.readDisk()[0].split(" ")[1]))) {
					String []temp = SystemCalls.readDisk();
					String []x = temp[1].split(" ");
					x[1]=State.Ready.toString();
					temp[1]=helper2(x);
					SystemCalls.writeDisk(helper(temp));
				}
				/* update ownerID to be equal to Process P’s ID */
				m.setOwnerID(p.getID());
			}
		}
	}

	public ArrayList<String> getInstructions() {
		return Instructions;
	}

	public void setInstructions(ArrayList<String> instructions) {
		Instructions = instructions;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

//	public ArrayList<Word> getVariables() {
//		return Variables;
//	}
//
//	public void setVariables(ArrayList<Word> word) {
//		Variables = word;
//	}

	public ArrayList<Word> getPCB() {
		return PCB;
	}

	public void setPCB(ArrayList<Word> pCB) {
		PCB = pCB;
	}

	public String toString() {
		return "" + this.getID();
	}

	public int getMinBoundary() {
		return minBoundary;
	}

	public void setMinBoundary(int minBoundary) {
		this.minBoundary = minBoundary;
	}

	public int getMaxBoundary() {
		return maxBoundary;
	}

	public void setMaxBoundary(int maxBoundary) {
		this.maxBoundary = maxBoundary;
	}

	public int getPC() {
		return PC;
	}

	public void setPC(int pC) {
		PC = pC;
	}

	public int getCodeLines() {
		return CodeLines;
	}

	public void setCodeLines(int codeLines) {
		CodeLines = codeLines;
	}
	public static String helper(String []content) {
		String x="";
		for(int i=0;i < content.length;i++) {
			if(i==content.length-1) {
				x+=content[i];
			}else {
				x+=content[i]+"\n";
			}

		}
		return x;
	}
	public static String helper2(String []content) {
		String x="";
		for(int i=0;i < content.length;i++) {
			if(i==content.length-1) {
				x+=content[i];
			}else {
				x+=content[i]+" ";
			}

		}
		return x;
	}
}
