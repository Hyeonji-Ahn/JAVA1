package pakage;

public class Operation_worksheet {
	public void Integer_to_character(int x) {
		System.out.println((char)x);
	}
	
	public void Print_last_digit(int x) {
		int lastdigit = x%10;
		System.out.println(lastdigit);
	}
	
	public void Exact_Average(int x, int y) {
		double dx =  x;
		double dy = y;
		double avg = (dx+dy)/2;
		System.out.println(avg);
	}
	
	public static void main(String[] args) {
		Operation_worksheet runner = new Operation_worksheet();
		runner.Integer_to_character(98);  
		runner.Print_last_digit(1234);
		runner.Exact_Average(54,23);
		
	}
}
