package project;

import java.util.LinkedList;
import java.util.Queue;

public class Mutex {
	
	private MutexValue value;
	private int ownerID;
	private Queue<Process> BlockedQueue;
	
	public Mutex(int ownerID) {
		this.value = MutexValue.One;
		this.ownerID = ownerID;
		this.BlockedQueue = new LinkedList();
	}

	public MutexValue getValue() {
		return value;
	}

	public void setValue(MutexValue value) {
		this.value = value;
	} 

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public Queue<Process> getBlockedQueue() {
		return BlockedQueue;
	}

	public void setBlockedQueue(Queue<Process> blockedQueue) {
		BlockedQueue = blockedQueue;
	}
	
}
