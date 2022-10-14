package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Memory {

	Word[] MainMemory;
//	Word[] HardDisk;
	static boolean flagAlternate = false;
	static String ProcessCont;

	public Memory(int size) {
		this.MainMemory = new Word[40];
//		this.HardDisk = new Word[20];

	}

	/*
	 * process address space
	 * 
	 * PID 0: PState 1 --> PC 2: min address boundary 3: 0 , 20 max address boundary
	 * 4: 19, 39
	 * 
	 * code: start at 5 --> ends at (4 + codelines) variable: start at (4 +
	 * codelines+ 1 )-->ends at ( max process address boundary)
	 * 
	 */

	public void firstTimeLoadProcessToMem(Process p) throws IOException {
		ProcessCont = "";
		if (this.MainMemory[0] == null) { // memory is empty then load the process to the start // what if process was
											// unloaded and there was one sitting on hd?
			int j = 0;
			this.MainMemory[0] = new Word("PID", p.getID());
			this.MainMemory[1] = new Word("PState", p.getState());
			this.MainMemory[2] = new Word("PC", 5);
			this.MainMemory[3] = new Word("MinAdder", 0);
			this.MainMemory[4] = new Word("MaxAdder", 19);
			p.setMinBoundary(0);
			p.setMaxBoundary(19);
			p.setPC(5);

			for (int i = 5; i <= 4 + p.getCodeLines(); i++) {
				Word inst = new Word("inst", p.getInstructions().get(j));
				this.MainMemory[i] = inst;
				j++;
			}
			this.MainMemory[4 + p.getCodeLines() + 1] = new Word("Var1", null);
			this.MainMemory[4 + p.getCodeLines() + 2] = new Word("Var2", null);
			this.MainMemory[4 + p.getCodeLines() + 3] = new Word("Var3", null);
		} else if (this.MainMemory[20] == null) { // only one process is at memory
			int j = 0;
			this.MainMemory[20] = new Word("PID", p.getID());
			this.MainMemory[21] = new Word("PState", p.getState());
			this.MainMemory[22] = new Word("PC", 25);
			this.MainMemory[23] = new Word("MinAdder", 20);
			this.MainMemory[24] = new Word("MaxAdder", 39);
			p.setMinBoundary(20);
			p.setMaxBoundary(39);
			p.setPC(25);
			for (int i = 25; i <= 24 + p.getCodeLines(); i++) {
				Word inst = new Word("inst", p.getInstructions().get(j));
				this.MainMemory[i] = inst;
				j++;
			}
			this.MainMemory[24 + p.getCodeLines() + 1] = new Word("Var1", null);
			this.MainMemory[24 + p.getCodeLines() + 2] = new Word("Var2", null);
			this.MainMemory[24 + p.getCodeLines() + 3] = new Word("Var3", null);
		} else { // two process is at memory we need to replace according to FIFO policy so first
					// time we will empty mem locations from 0 to 19 into disk then alternate
			int j = 0;
			for (int i = 0; i <= 19; i++) {
//						this.HardDisk[i] = this.MainMemory[i];
				if(i==19) {
					ProcessCont += "" + this.MainMemory[i];
				}else {
					ProcessCont += "" + this.MainMemory[i] + "\n";
				}
				
				this.MainMemory[i] = null;
			}
			SystemCalls.writeDisk(ProcessCont);
			this.MainMemory[0] = new Word("PID", p.getID());
			this.MainMemory[1] = new Word("PState", p.getState());
			this.MainMemory[2] = new Word("PC", 5);
			this.MainMemory[3] = new Word("MinAdder", 0);
			this.MainMemory[4] = new Word("MaxAdder", 19);
			p.setMinBoundary(0);
			p.setMaxBoundary(19);
			p.setPC(5);
			for (int i = 5; i <= 4 + p.getCodeLines(); i++) {
				Word inst = new Word("inst", p.getInstructions().get(j));
				this.MainMemory[i] = inst;
				j++;
			}
			this.MainMemory[4 + p.getCodeLines() + 1] = new Word("Var1", null);
			this.MainMemory[4 + p.getCodeLines() + 2] = new Word("Var2", null);
			this.MainMemory[4 + p.getCodeLines() + 3] = new Word("Var3", null);
		}
	}

	public void swapProcessFromDiskToMem(Process p) throws IOException {
//		Word temp;
		String name = "";
		Object data = "";
		ProcessCont = "";
		String temp[] = new String[20];
		temp = SystemCalls.readDisk();
		if (flagAlternate) { // swap into the first 20 cell
			for (int i = 0; i <= 19; i++) {
				if (this.MainMemory[i] != null) {
					if(i==19) {
						ProcessCont += "" + this.MainMemory[i];
					}else {
						ProcessCont += "" + this.MainMemory[i] + "\n";
					}
				}
				if (temp[i] != null) {
					name = temp[i].split(" ")[0];
					if (i == 2) {
						data = (int) (Integer.parseInt("" + SystemCalls.readDisk()[i].split(" ")[1])) - 20;
						p.setPC(p.getPC() - 20);
					} else if (i == 3) {
						data = 0;

					} else if (i == 4) {
						data = 19;
					} else {
						for (int k = 1; k < temp[i].split(" ").length; k++) {
							if (k == temp[i].split(" ").length - 1) {
								data += temp[i].split(" ")[k];
							} else {
								data += temp[i].split(" ")[k] + " ";
							}
						}
					}
					this.MainMemory[i] = new Word(name, data);
					data = "";
				} else {
					this.MainMemory[i] = null;
				}

//					temp=this.MainMemory[i];
//					this.MainMemory[i]=this.HardDisk[i];
//					if (i==2 && (int)this.HardDisk[i].getData()>=25) {
//						this.MainMemory[i].setData((int)this.HardDisk[i].getData()-20);
//						p.setPC(p.getPC()-20);
//					}
//					this.HardDisk[i] = temp;
			}
			SystemCalls.writeDisk(ProcessCont);
			p.setMinBoundary(0);
			p.setMaxBoundary(19);
			flagAlternate = false;
		}

		else {
			int z = 0;
			for (int i = 20; i <= 39; i++) {
				if (this.MainMemory[i] != null) {
					if(i==39) {
						ProcessCont += "" + this.MainMemory[i];
					}else {
						ProcessCont += "" + this.MainMemory[i] + "\n";
					}
				}
				if (temp[z] != null) {
					name = temp[z].split(" ")[0];
					if (i == 22) {
						data = (int) (Integer.parseInt("" + SystemCalls.readDisk()[z].split(" ")[1])) + 20;
						p.setPC(p.getPC() + 20);
					} else if (i == 23) {
						data = 20;

					} else if (i == 24) {
						data = 39;
					}

					else {
						for (int k = 1; k < temp[z].split(" ").length; k++) {
							if (k == temp[z].split(" ").length - 1) {
								data += temp[z].split(" ")[k];
							} else {
								data += temp[z].split(" ")[k] + " ";
							}
						}
					}
					this.MainMemory[i] = new Word(name, data);
					data = "";
				} else {
					this.MainMemory[i] = null;
				}
//				temp=this.MainMemory[i];
//				this.MainMemory[i]=this.HardDisk[z];
//				if (i==22 && (int)this.HardDisk[z].getData()<=20) {
//					this.MainMemory[i].setData((int)this.HardDisk[z].getData()+20);
//					p.setPC(p.getPC()+20);
//				}
//				this.HardDisk[z] = temp;
				z++;
			}
			SystemCalls.writeDisk(ProcessCont);
			p.setMinBoundary(20);
			p.setMaxBoundary(39);
			flagAlternate = true;
		}
	}

	public void unLoadFinishedPrcoess(Process p, Process toBeLoaded) throws IOException { // here we must swap if there
																							// is a process sitting on
																							// disk and change
		// the flag accordingly
		String name = "";
		Object data = "";
		ProcessCont = "";
		String temp[] = new String[20];
		temp = SystemCalls.readDisk();
		if (OS.memory.MainMemory[0] != null && p.getID() == (int) Integer.parseInt("" + MainMemory[0].getData())) {
			if (SystemCalls.readDisk()[0] != null) {
				for (int i = 0; i <= 19; i++) {
					if (temp[i] != null) {
						name = temp[i].split(" ")[0];
						if (i == 2) {
							data = (int) (Integer.parseInt("" + SystemCalls.readDisk()[i].split(" ")[1])) - 20;
							toBeLoaded.setPC(toBeLoaded.getPC() - 20);
						} else {
							for (int k = 1; k < temp[i].split(" ").length; k++) {
								if (k == temp[i].split(" ").length - 1) {
									data += temp[i].split(" ")[k];
								} else {
									data += temp[i].split(" ")[k] + " ";
								}
							}
						}
						this.MainMemory[i] = new Word(name, data);
						data = "";
					} else {
						this.MainMemory[i] = null;
					}

//					MainMemory[i] = HardDisk[i];
//					if (i==2) {
//						this.MainMemory[i].setData((int)this.HardDisk[i].getData()-20);
//						toBeLoaded.setPC(toBeLoaded.getPC()-20);
//					}
//					HardDisk[i]=null;
				}
				SystemCalls.ClearDisk();
				flagAlternate = false;

			} else {
				for (int i = 0; i <= 19; i++) {
					MainMemory[i] = null;
				}
			}
		} else if (OS.memory.MainMemory[20] != null
				&& p.getID() == (int) Integer.parseInt("" + MainMemory[20].getData())) {
			int z = 0;
			if (SystemCalls.readDisk()[0] != null) {
				for (int i = 20; i <= 39; i++) {
					if (temp[z] != null) {
						name = temp[z].split(" ")[0];
						if (i == 22) {
							data = (int) (Integer.parseInt("" + SystemCalls.readDisk()[z].split(" ")[1])) + 20;
							toBeLoaded.setPC(toBeLoaded.getPC() + 20);
						} else {
							for (int k = 1; k < temp[z].split(" ").length; k++) {
								if (k == temp[z].split(" ").length - 1) {
									data += temp[z].split(" ")[k];
								} else {
									data += temp[z].split(" ")[k] + " ";
								}
							}
						}
						this.MainMemory[i] = new Word(name, data);
						data = "";
					} else {
						this.MainMemory[i] = null;
					}

//					MainMemory[i] = HardDisk[z];
//					if (i==22) {
//						this.MainMemory[i].setData((int)this.HardDisk[z].getData()+20);
//						toBeLoaded.setPC(toBeLoaded.getPC()+20);
//					}
//					HardDisk[z]=null;
					z++;
				}
				SystemCalls.ClearDisk();
				flagAlternate = true;
			} else {
				for (int i = 20; i <= 39; i++) {
					MainMemory[i] = null;
				}
			}
		}
	}
}
