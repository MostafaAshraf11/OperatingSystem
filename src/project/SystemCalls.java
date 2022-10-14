package project;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.Parser;

public class SystemCalls {

	public SystemCalls() {
		// TODO Auto-generated constructor stub
	}

	public static Word ReadFromMemory(int x) throws IOException {
		return OS.memory.MainMemory[x];
	}
	
	public static void ClearDisk() throws IOException {
		String fileContent = "";
		FileWriter writer = new FileWriter("Disk.txt");
		writer.write(fileContent);
		writer.close(); 	
	}
	public static String[] readDisk() throws IOException {
        FileReader fr = new FileReader("Disk.txt");
        BufferedReader br = new BufferedReader(fr);
        String s[] = new String[20];
        int i = 0;
        while (i != 20) {
            String temp = br.readLine();
            if ((temp != null || temp != "null") && temp != "") {
                s[i] = temp;
            } else {
                s[i] = null;
            } 
            i++;
        }
        br.close();
        fr.close();
        return s;
	}

	public static void writeDisk(String content) throws IOException {
		String fileContent = content;
		FileWriter writer = new FileWriter("Disk.txt");
		writer.write(fileContent);
		writer.close(); 	
	}
	public static void WriteDataToMemory(Process p, Word var) throws IOException {
//		p.getVariables().add(var); //temp
		   if(OS.memory.MainMemory[0]!=null && p.getID() == (int)Integer.parseInt(""+OS.memory.MainMemory[0].getData())) {
			   if(OS.memory.MainMemory[4 + p.getCodeLines() + 1].getData()==null || OS.memory.MainMemory[4 + p.getCodeLines() + 1].getData().equals("null") ) {
			   OS.memory.MainMemory[4 + p.getCodeLines() + 1]=var;
			   }
			   else if(OS.memory.MainMemory[4 + p.getCodeLines() + 2].getData()==null || OS.memory.MainMemory[4 + p.getCodeLines() + 2].getData().equals("null")) {
			   OS.memory.MainMemory[4 + p.getCodeLines() + 2]=var;
			   }
			   else if(OS.memory.MainMemory[4 + p.getCodeLines() + 3].getData()==null || OS.memory.MainMemory[4 + p.getCodeLines() + 3].getData().equals("null")) {
			   OS.memory.MainMemory[4 + p.getCodeLines() + 3]=var;
			   }
			}
		   else if(OS.memory.MainMemory[20]!=null && p.getID()==(int)Integer.parseInt(""+OS.memory.MainMemory[20].getData())) {
			   if(OS.memory.MainMemory[24 + p.getCodeLines() + 1].getData()==null || OS.memory.MainMemory[24 + p.getCodeLines() + 1].getData().equals("null")) {
			   OS.memory.MainMemory[24 + p.getCodeLines() + 1]=var;
			   }
			   else if(OS.memory.MainMemory[24 + p.getCodeLines() + 2].getData()==null || OS.memory.MainMemory[24 + p.getCodeLines() + 2].getData().equals("null")) {
			   OS.memory.MainMemory[24 + p.getCodeLines() + 2]=var;
			   }
			   else if(OS.memory.MainMemory[24 + p.getCodeLines() + 3].getData()==null || OS.memory.MainMemory[24 + p.getCodeLines() + 3].getData().equals("null")) {
			   OS.memory.MainMemory[24 + p.getCodeLines() + 3]=var;
			   }
		   }
	}

	public static void printData(String a) {
		System.out.println("Output : "+a);
	}

	public static void input(Process p,String varName) {
		System.out.println("Please enter input:");
		Scanner sc = new Scanner(System.in);
		Object varData =(Object)sc.nextLine();
		p.getPCB().add(new Word(varName,varData));
	}

	public static void printFromTo(int start, int end) {
		while (start <= end) {
			System.out.println(start);
			start++;
		}
	}

	public static String readFileFromDisk(Process p,String varName,String path) throws FileNotFoundException {
		File x = new File(path + ".txt");
		Scanner scan = new Scanner(x); // not sure
		String result = "";
		while (scan.hasNextLine()) {
			result +=scan.nextLine()+"\n";
		}
		p.getPCB().add(new Word(varName,result));
		return result;
	}

	public static void writeFileToDisk(String filename, String content) throws IOException {
		String fileContent = content;
		FileWriter writer = new FileWriter(filename + ".txt");
		writer.write(fileContent);
		writer.close();
	}
}
