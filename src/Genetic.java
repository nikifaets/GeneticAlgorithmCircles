import java.util.Scanner;
import java.util.PriorityQueue;


public class Genetic {
	
	public static void main(String[] args) {
	
		InputOutput scan = new InputOutput();
		
		int individs = scan.getInt();
		int pgLen = scan.getInt();
		int pgH = scan.getInt();
		int circsNum = scan.getInt();
		
		
		Playground pg = new Playground(circsNum, pgLen, pgH);
		Nature nature = new Nature(individs, pg);
		//nature.showInds();
		//pg.showCircs();
		
		nature.live();
		
		
		
		
	}
	
	
	
	

}
