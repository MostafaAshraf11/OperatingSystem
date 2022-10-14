package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class OS {
	static Mutex userInput=new Mutex(-1); // IS id necessary for constructing mutex ????
	static Mutex userOutput=new Mutex(-2);
	static Mutex file=new Mutex(-3);
	static Memory memory=new Memory(40);
    static int codelines1;
    static int codelines2;
    static int codelines3;
    
	public OS() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String []args ) throws IOException {
//		SystemCalls.ClearDisk();
		Scheduler s = new Scheduler(2);
		Scheduler.startScheduling(s, "Program_1.txt", "Program_2.txt", "Program_3.txt", 0, 1, 4 );

		
//		Process p1 = Parser.readProgram("Program_1.txt", 1);
//		Process p2 = Parser.readProgram("Program_2.txt", 2);
//		Process p3 = Parser.readProgram("Program_3.txt", 3);


//		codelines1=p1.getInstructions().size();
//		codelines2=p2.getInstructions().size();
//		codelines3=p3.getInstructions().size();
		
//		memory.loadProcessToMem(p1, codelines1); // should be at scheduling at arrival of each process then after that we wll be checking if running.id = either mem[0] or mem[20] if  not we must load that process b4 exec 
//		memory.loadProcessToMem(p2, codelines2); // also we should remove process from memory if it is finished?
//		System.out.println("ram"+Arrays.toString(memory.MainMemory));
//		System.out.println("---------------------");
//		
//		memory.loadProcessToMem(p3, codelines3);        //we should also update mem[1] or mem[21] in scheduler
//		System.out.println("ram"+Arrays.toString(memory.MainMemory));
//		System.out.println(Arrays.toString(memory.HardDisk));
//		System.out.println("---------------------");
//		
//		memory.loadProcessToMem(p1, codelines1);
//		System.out.println("ram"+Arrays.toString(memory.MainMemory));
//		System.out.println(Arrays.toString(memory.HardDisk));
//		System.out.println("---------------------");
//
//		memory.loadProcessToMem(p2, codelines2);
//		System.out.println("ram"+Arrays.toString(memory.MainMemory));
//		System.out.println(Arrays.toString(memory.HardDisk));
//		System.out.println("---------------------");
//		
//		memory.loadProcessToMem(p3, codelines3);
//		System.out.println("ram"+Arrays.toString(memory.MainMemory));
//		System.out.println(Arrays.toString(memory.HardDisk));
//		System.out.println("---------------------");
//		
//		memory.loadProcessToMem(p1, codelines1);
//		System.out.println("ram"+Arrays.toString(memory.MainMemory));
//		System.out.println(Arrays.toString(memory.HardDisk));
//		System.out.println("---------------------");
//		
//		memory.loadProcessToMem(p2, codelines2);
//		System.out.println("ram"+Arrays.toString(memory.MainMemory));
//		System.out.println(Arrays.toString(memory.HardDisk));
//		System.out.println("---------------------");


	}
}
