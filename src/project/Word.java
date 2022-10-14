package project;

public class Word {
	
	private String name;
	private Object data;
	
	public Word(String name, Object data) {
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	public String toString() {
		return this.name+" "+this.data;
	}
}
