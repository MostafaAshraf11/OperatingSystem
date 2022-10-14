package project;
import java.util.*;
import project.SystemCalls;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Parser {

	public Parser() { 

	}

	public static Process readProgram(String pathToProgram, int ID) { //return type ArrayList<String>
		Process P = new Process(ID);
		try {
			P.setInstructions((ArrayList<String>) Files.readAllLines(Paths.get(pathToProgram)));
			P.setCodeLines(P.getInstructions().size());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return P;
	}

	public static void execute(Process p, Scheduler s) throws IOException {
//		String[] portionOfCode = 
//				p.getInstructions().remove(0).split(" ");
		String[] portionOfCode = (""+OS.memory.MainMemory[p.getPC()].getData()).split(" "); //need to handle nesting like the comment in the add 2nd clock cycle code?
		switch (portionOfCode[0]) {

			case "assign": 
				/* check on portionOfCode[2] */
				/* if input --> call input */
				/* else if readFile --> call readFile */
				/* else --> assign */
				if(portionOfCode[2].equals("input")) {
					 SystemCalls.input(p,portionOfCode[1]);
				}else {
					String path = "";
					for(int i = p.getMinBoundary(); i <= p.getMaxBoundary() && OS.memory.MainMemory[i]!=null; i++) {
						 if(portionOfCode[3].equals(OS.memory.MainMemory[i].getName())) {
							  path = "" + OS.memory.MainMemory[i].getData();
						  }
					}
//					for(int i = 0; i < p.getVariables().size(); i++) {
//						  if(portionOfCode[3].equals(p.getVariables().get(i).getName())) {
//							  path = "" + p.getVariables().get(i).getData();
//						  }
//					  }
//						System.out.println("testing readfile system call " +path); 
//						System.out.println("testing readfile system call " +portionOfCode[1]); 
						SystemCalls.readFileFromDisk(p,portionOfCode[1],path);
				}
				addSecondClockCycleNestedCode(p,portionOfCode,0);
				 break;
			case "input": 
						   SystemCalls.WriteDataToMemory(p,p.getPCB().remove(0));
						   p.setPC(p.getPC()+1);
						   if(OS.memory.MainMemory[0]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
							   OS.memory.MainMemory[2].setData(p.getPC());
						   }
						   else if(OS.memory.MainMemory[20]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
							   OS.memory.MainMemory[22].setData(p.getPC());
						   } 
				break;
			case "print": 
				for(int i = p.getMinBoundary(); i <= p.getMaxBoundary() && OS.memory.MainMemory[i]!=null; i++) {
					 if(portionOfCode[1].equals(OS.memory.MainMemory[i].getName())) {
						 SystemCalls.printData((String)OS.memory.MainMemory[i].getData());
					  }
				}
//						for(int i = 0; i < p.getVariables().size(); i++) {
//							if(portionOfCode[1].equals(p.getVariables().get(i).getName())) {
//								SystemCalls.printData((String)p.getVariables().get(i).getData());		
//							}
//						}
						p.setPC(p.getPC()+1);
						   if(OS.memory.MainMemory[0]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
							   OS.memory.MainMemory[2].setData(p.getPC());
						   }
						   else if(OS.memory.MainMemory[20]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
							   OS.memory.MainMemory[22].setData(p.getPC());
						   } 
						break;
			case "printFromTo": 
							  int start = 0, end = 0;
								for(int i = p.getMinBoundary(); i <= p.getMaxBoundary() && OS.memory.MainMemory[i]!=null ; i++) {
									 if(portionOfCode[1].equals(OS.memory.MainMemory[i].getName())) {
										  start =Integer.parseInt((String) OS.memory.MainMemory[i].getData());
									  }
									  if(portionOfCode[2].equals(OS.memory.MainMemory[i].getName())) {
										  end =Integer.parseInt((String) OS.memory.MainMemory[i].getData());
									  }
								}
//							  for(int i = 0; i < p.getVariables().size(); i++) {
//								  if(portionOfCode[1].equals(p.getVariables().get(i).getName())) {
//									  start =Integer.parseInt((String)p.getVariables().get(i).getData());
//								  }
//								  if(portionOfCode[2].equals(p.getVariables().get(i).getName())) {
//									  end =Integer.parseInt((String)p.getVariables().get(i).getData());
//								  }
//							  }
							  
							  SystemCalls.printFromTo(start, end);
							  p.setPC(p.getPC()+1);
							   if(OS.memory.MainMemory[0]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
								   OS.memory.MainMemory[2].setData(p.getPC());
							   }
							   else if(OS.memory.MainMemory[20]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
								   OS.memory.MainMemory[22].setData(p.getPC());
							   } 
							  break;
			case "readFile":
						   SystemCalls.WriteDataToMemory(p,p.getPCB().remove(0));
						   p.setPC(p.getPC()+1);
						   if(OS.memory.MainMemory[0]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
							   OS.memory.MainMemory[2].setData(p.getPC());
						   }
						   else if(OS.memory.MainMemory[20]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
							   OS.memory.MainMemory[22].setData(p.getPC());
						   } 
						   break;
			case "writeFile": 
							String filename = "", content = "";
							
							for(int i = p.getMinBoundary(); i <= p.getMaxBoundary() && OS.memory.MainMemory[i]!=null; i++) {
								  if( portionOfCode[1].equals(OS.memory.MainMemory[i].getName())) {
									  filename = "" + OS.memory.MainMemory[i].getData();
								  }
								  if(portionOfCode[2].equals( OS.memory.MainMemory[i].getName())) {
									  content = "" +OS.memory.MainMemory[i].getData();
								  }
							  }
//							for(int i = 0; i < p.getVariables().size(); i++) {
//								  if(portionOfCode[1].equals(p.getVariables().get(i).getName())) {
//									  filename = "" + p.getVariables().get(i).getData();
//								  }
//								  if(portionOfCode[2].equals( p.getVariables().get(i).getName())) {
//									  content = "" + p.getVariables().get(i).getData();
//								  }
//							  }
				            SystemCalls.writeFileToDisk(filename, content);
				            p.setPC(p.getPC()+1);
							   if(OS.memory.MainMemory[0]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
								   OS.memory.MainMemory[2].setData(p.getPC());
							   }
							   else if(OS.memory.MainMemory[20]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
								   OS.memory.MainMemory[22].setData(p.getPC());
							   } 
				            break;
				            
			case "semWait": 
							switch(portionOfCode[1]) {
								case "userInput": p.semWaitB(OS.userInput, s);
								break;
								
								case "userOutput": p.semWaitB(OS.userOutput, s);
								break;
								
								case "file": p.semWaitB(OS.file, s);
								break;
										}
							p.setPC(p.getPC()+1);
							   if(OS.memory.MainMemory[0]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
								   OS.memory.MainMemory[2].setData(p.getPC());
							   }
							   else if(OS.memory.MainMemory[20]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
								   OS.memory.MainMemory[22].setData(p.getPC());
							   } 
							break;
		
			case "semSignal": 
								switch(portionOfCode[1]) {
									case "userInput": p.semSignalB(OS.userInput, s);
									break;
			
									case "userOutput": p.semSignalB(OS.userOutput, s);
									break;
			
									case "file": p.semSignalB(OS.file, s);
								    break;
					}
								p.setPC(p.getPC()+1);
								   if(OS.memory.MainMemory[0]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
									   OS.memory.MainMemory[2].setData(p.getPC());
								   }
								   else if(OS.memory.MainMemory[20]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
									   OS.memory.MainMemory[22].setData(p.getPC());
								   } 
								break;
		}

	}

	public static void addSecondClockCycleNestedCode(Process p,String remainingCode[], int start) {
		String restOfCode = "";
		for(; start < remainingCode.length; start++) {
			restOfCode += remainingCode[start];
			if (start != remainingCode.length - 1)
				restOfCode += " ";
		}
		String s;
		if(restOfCode.contains("input")) {
			s="input";
		}
		else {
			s="readFile";
		}
		String x=s +" "+ restOfCode;
//		p.getInstructions().add(0, x);
		OS.memory.MainMemory[p.getPC()].setData(x);
	}
}
