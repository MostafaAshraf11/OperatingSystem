package project;

import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	static Queue<Process> BlockedQueue;
	static Queue<Process> ReadyQueue;
	static ArrayList<Process> NotFinishedYet;
	static int ClockCounter = 0;
	static int Q;
	
	public Scheduler(int Q) throws IOException {
		this.Q = Q;
		this.ReadyQueue = new LinkedList();
		this.BlockedQueue = new LinkedList();
		this.NotFinishedYet=new ArrayList<Process>();
	}
	
	public static void startScheduling(Scheduler s, String prog1, String prog2, String prog3, int t1, int t2, int t3) throws IOException {	
		Process p1=null;
		Process p2=null;
		Process p3=null;
		Process running = null;
		s.NotFinishedYet.add(p1);
		s.NotFinishedYet.add(p2);
		s.NotFinishedYet.add(p3);
		while(!s.NotFinishedYet.isEmpty()) {
			int x = ClockCounter + s.Q;
			while(ClockCounter < x) {

				if(s.NotFinishedYet.isEmpty()) {
					break;
				}
				System.out.println("---------------------");
				System.out.println("ClockCycle: " + ClockCounter);
				if(ClockCounter == t1) {
					NotFinishedYet.remove(p1);
					p1 = Parser.readProgram(prog1, 1);
					s.ReadyQueue.add(p1);
					p1.setState(State.Ready);
					NotFinishedYet.add(p1);
					System.out.println("");
					System.out.println("RAM"+Arrays.toString(OS.memory.MainMemory));
					System.out.println("HARD DISk"+Arrays.toString(SystemCalls.readDisk()));
					System.out.println("");
					OS.memory.firstTimeLoadProcessToMem(p1);
					System.out.println("        ~~~~PROCESS " + p1.getID() + " Is loaded to MEMORY~~~~");
					System.out.println("");
					System.out.println("RAM"+Arrays.toString(OS.memory.MainMemory));
					System.out.println("HARD DISk"+Arrays.toString(SystemCalls.readDisk()));
					System.out.println("");
					if((t1 < t2 && t1 < t3) || running == null) {
						p1.setState(State.Running);
						running = p1;
						if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
							OS.memory.MainMemory[1].setData(State.Running);
						}else if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
							OS.memory.MainMemory[21].setData(State.Running);
						}
						s.getReadyQueue().remove(running);
					}
				}
				
				if(ClockCounter == t2) {
					NotFinishedYet.remove(p2);
					p2 = Parser.readProgram(prog2, 2);
					s.getReadyQueue().add(p2);
					p2.setState(State.Ready);
					NotFinishedYet.add(p2);
					System.out.println("");
					System.out.println("RAM"+Arrays.toString(OS.memory.MainMemory));
					System.out.println("HARD DISk"+Arrays.toString(SystemCalls.readDisk()));
					System.out.println("");
					OS.memory.firstTimeLoadProcessToMem(p2);
					System.out.println("        ~~~~PROCESS " + p2.getID() + " Is loaded to MEMORY~~~~");
					System.out.println("");
					System.out.println("RAM"+Arrays.toString(OS.memory.MainMemory));
					System.out.println("HARD DISk"+Arrays.toString(SystemCalls.readDisk()));
					System.out.println("");
					if((t2 < t1 && t2 < t3) || running == null) {
						p2.setState(State.Running);
						running = p2;
						if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
							OS.memory.MainMemory[1].setData(State.Running);
						}else if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
							OS.memory.MainMemory[21].setData(State.Running);
						}
						s.getReadyQueue().remove(running);
					}
				}
				
				if(ClockCounter == t3) {
					NotFinishedYet.remove(p3);
					p3 = Parser.readProgram(prog3, 3);
					s.getReadyQueue().add(p3);
					p3.setState(State.Ready);
					NotFinishedYet.add(p3);
					System.out.println("");
					System.out.println("RAM"+Arrays.toString(OS.memory.MainMemory));
					System.out.println("HARD DISk"+Arrays.toString(SystemCalls.readDisk()));
					System.out.println("");
					OS.memory.firstTimeLoadProcessToMem(p3);
					System.out.println("        ~~~~PROCESS " + p3.getID() + " Is loaded to MEMORY~~~~");
					System.out.println("");
					System.out.println("RAM"+Arrays.toString(OS.memory.MainMemory));
					System.out.println("HARD DISk"+Arrays.toString(SystemCalls.readDisk()));
					System.out.println("");
					// check beta3et el null hena 3ashan bardo lw fee mesa7at been el processes ye5ally elly tewsal el running
					if((t3 < t1 && t3 < t2) || running == null) {
						p3.setState(State.Running);
						running = p3;
						if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
							OS.memory.MainMemory[1].setData(State.Running);
						}else if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
							OS.memory.MainMemory[21].setData(State.Running);
						}
						s.getReadyQueue().remove(running);
					}
//					System.out.println(4);
				}
				
				//3ashan lw ma7addesh bade2 mn t = 0 maye7salsh error
				if(running == null) { 
					ClockCounter++;
					break;
				} else {
					
					if(OS.memory.MainMemory[running.getPC()].getName().equals("inst")==false) { //if(running.getInstructions().isEmpty()) // if (OS.memory.MainMeomry[running.getPC()].getName()!="inst") || if (running.getPC()==5+running.getCodeLines() || running.getPC()==25+running.getCodeLines() )
						running.setState(State.Finished);
						System.out.println("        ~~~~PROCESS " + running.getID() + " Finished~~~~");
						s.getNotFinishedYet().remove(running);
						if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
							OS.memory.MainMemory[1].setData(State.Finished);
						}else if(OS.memory.MainMemory[20]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
							OS.memory.MainMemory[21].setData(State.Finished);
						}
						System.out.println("RAM"+Arrays.toString(OS.memory.MainMemory));
//						System.out.println("DISK"+Arrays.toString(SystemCalls.readDisk()));
//						if(SystemCalls.readDisk()[0]!=null && p1.getID()==(int)Integer.parseInt(SystemCalls.readDisk()[0].split(" ")[1])) {
//							OS.memory.unLoadFinishedPrcoess(running,p1);
//						}else if(SystemCalls.readDisk()[0]!=null && p2.getID()==(int)Integer.parseInt(SystemCalls.readDisk()[0].split(" ")[1])) {
//							OS.memory.unLoadFinishedPrcoess(running,p2);
//						}else if(SystemCalls.readDisk()[0]!=null && p3.getID()==(int)Integer.parseInt(SystemCalls.readDisk()[0].split(" ")[1])) {
//							OS.memory.unLoadFinishedPrcoess(running,p3);
//						}else {
//							OS.memory.unLoadFinishedPrcoess(running,null);
//						}
//						System.out.println("        ~~~~PROCESS " + running.getID() + " Is UNLOADED FROM MEMORY~~~~");
//						System.out.println("");
//						System.out.println("RAM"+Arrays.toString(OS.memory.MainMemory));
//						System.out.println("DISK"+Arrays.toString(SystemCalls.readDisk()));
						running = null;
						break;
					}
					System.out.println("Will Be Executing Program_" + running.getID());
//					if(running.getID()!=(int)OS.memory.MainMemory[0].getData() && running.getID()!=(int)OS.memory.MainMemory[20].getData()) {
					if(SystemCalls.readDisk()[0]!=null && running.getID()==(int)Integer.parseInt(SystemCalls.readDisk()[0].split(" ")[1])) {
						System.out.println("");
						System.out.println("RAM B4 SWAP"+Arrays.toString(OS.memory.MainMemory));
						System.out.println("DISK B4 SWAP"+Arrays.toString(SystemCalls.readDisk()));
						OS.memory.swapProcessFromDiskToMem(running);
						System.out.println("        ~~~~PROCESS " + running.getID() + " Is LOADED TO MEMORY~~~~");
						System.out.println("        ~~~~PROCESS " + SystemCalls.readDisk()[0].split(" ")[1] + " Is SWAPPED out back to DISK~~~~");
						System.out.println("");
						System.out.println("RAM After SWAP"+Arrays.toString(OS.memory.MainMemory));
						System.out.println("DISK After SWAP"+Arrays.toString(SystemCalls.readDisk()));
						System.out.println("");
						//that check may cause null pointer exception -------------------------------------------------------------------------------
					}
				
//					System.out.println("---------------------");
//					System.out.println("ClockCycle: " + ClockCounter);
//					System.out.println("Will Be Executing Program_" + running.getID());
					System.out.println("Current ReadyQueue" + ReadyQueue.toString() + " ~ Current BlockedQueue: "+BlockedQueue.toString());
//					System.out.println("");
//					System.out.println("Currently executing Program_"+ running.getID()+" Current ClockCycle: "+ClockCounter);
//					System.out.println("Current ReadyQueue"+ ReadyQueue.toString()+" Current BlockedQueue: "+BlockedQueue.toString());
//					System.out.println("");
					
//					if(running.getInstructions().get(0).split(" ")[0].equals("input") ) {
//						System.out.println("Istruction :" +"["+running.getInstructions().get(0).substring(6)+"]"+" Is being Executed ");
//					}
//					else if(running.getInstructions().get(0).split(" ")[0].equals("readFile")) {
//						System.out.println("Istruction :" +"["+running.getInstructions().get(0).substring(9)+"]"+" Is being Executed ");
//					}
//					else {
//						System.out.println("Istruction :" +"["+running.getInstructions().get(0)+"]"+" Is being Executed ");
//					}
					
					running.setState(State.Running);
					if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
						OS.memory.MainMemory[1].setData(State.Running);
					}else if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
						OS.memory.MainMemory[21].setData(State.Running);
					}
					System.out.println("Istruction :" +"["+OS.memory.MainMemory[running.getPC()].getData()+"]"+" Is being Executed ");
					System.out.println("");
					
					System.out.println("RAM Before Exec"+Arrays.toString(OS.memory.MainMemory));
					Parser.execute(running, s);
					System.out.println("");
					System.out.println("RAM After Exec"+Arrays.toString(OS.memory.MainMemory));
					
					
//					System.out.println(Arrays.toString(OS.memory.HardDisk));
					
//					System.out.println("Finished Executing Instrucion of Program " + running.getID());
//					System.out.println("");
	
					if(running.getState() == State.Blocked) {
						System.out.println("        ~~~~PROCESS " + running.getID() + " BLOCKED~~~~");
						ClockCounter++;
						break;
					}
				}				
				ClockCounter++;
			}
				if(!s.getReadyQueue().isEmpty()) {
					if(running != null) {
						if(running.getState() != State.Blocked) {
							running.setState(State.Ready);
							if(running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
								OS.memory.MainMemory[1].setData(State.Ready);
							}else if(running.getID()==(int) Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
								OS.memory.MainMemory[21].setData(State.Ready);
							}
							
							if(OS.memory.MainMemory[running.getPC()].getName().equals("inst")) {// if(running.getInstructions().isEmpty()==false) // if (OS.memory.MainMeomry[running.getPC()].getName()=="inst") || if (running.getPC()<=5+running.getCodeLines() || running.getPC()<=25+running.getCodeLines() )
								s.getReadyQueue().add(running);
								}
							else {
								s.getNotFinishedYet().remove(running);
								if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
									OS.memory.MainMemory[1].setData(State.Finished);
								}else if(OS.memory.MainMemory[20]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
									OS.memory.MainMemory[21].setData(State.Finished);
								}
								System.out.println("        ~~~~PROCESS " + running.getID() + " Finished~~~~");
								System.out.println("RAM"+Arrays.toString(OS.memory.MainMemory));
								System.out.println("DISK"+Arrays.toString(SystemCalls.readDisk()));
//								if(SystemCalls.readDisk()[0]!=null && p1.getID()==Integer.parseInt(SystemCalls.readDisk()[0].split(" ")[1])) {
//									OS.memory.unLoadFinishedPrcoess(running,p1);
//								}else if(SystemCalls.readDisk()[0]!=null && p2.getID()==Integer.parseInt(SystemCalls.readDisk()[0].split(" ")[1])) {
//									OS.memory.unLoadFinishedPrcoess(running,p2);
//								}else if(SystemCalls.readDisk()[0]!=null && p3.getID()==Integer.parseInt(SystemCalls.readDisk()[0].split(" ")[1])) {
//									OS.memory.unLoadFinishedPrcoess(running,p3);
//								}else {
//									OS.memory.unLoadFinishedPrcoess(running,null);
//								}
//								System.out.println("        ~~~~PROCESS " + running.getID() + " Is UNLOADED FROM MEMORY~~~~");
//								System.out.println("RAM"+Arrays.toString(OS.memory.MainMemory));
//								System.out.println("DISK"+Arrays.toString(SystemCalls.readDisk()));
									}
						}
					}
					running = s.getReadyQueue().remove();
					running.setState(State.Running);
					if(OS.memory.MainMemory[0]!=null && running.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
						OS.memory.MainMemory[1].setData(State.Running);
					}else if(OS.memory.MainMemory[20]!=null && running.getID()==(int) Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
						OS.memory.MainMemory[21].setData(State.Running);
					}
			    }
		}
		while(!s.getReadyQueue().isEmpty()) {
			s.getReadyQueue().remove();
		}
		System.out.println("~~~----------~~~");
		System.out.println("Final ReadyQueue" + ReadyQueue.toString() + " ~ Final BlockedQueue: "+BlockedQueue.toString());
	}
	


	public static ArrayList<Process> getNotFinishedYet() {
		return NotFinishedYet;
	}

	public static void setNotFinishedYet(ArrayList<Process> notFinishedYet) {
		NotFinishedYet = notFinishedYet;
	}

	public static Queue<Process> getBlockedQueue() {
		return BlockedQueue;
	}

	public static void setBlockedQueue(Queue<Process> blockedQueue) {
		BlockedQueue = blockedQueue;
	}

	public static Queue<Process> getReadyQueue() {
		return ReadyQueue;
	}

	public static void setReadyQueue(Queue<Process> readyQueue) {
		ReadyQueue = readyQueue;
	}

}
