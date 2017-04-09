package types;

public class MutableInteger {
	private int val;
	
	public MutableInteger(int val) {
		this.val = val;
	}
	
	public int get() {
		return val;
	}
	
	public void set(int val) {
		this.val = val;
	}
	
	public boolean equals(int val) {
		if(this.val == val) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return Integer.toString(val);
	}
}
