
public class Playground {
	
	int circsNum;
	int maxR = 10;
	public int minR = 3;
	int len;
	int h;
	double step = 0.5;
	Circle circs[];
	
	Playground(int circsNum, int len, int h){
		
		this.circsNum = circsNum;
		this.len = len;
		this.h = h;
		circs = new Circle[circsNum];
		createCircles();
	}
	
	public double findMaxRad(Individual ind) {
		
		double r=0;
		boolean hit = false;
		
		while(!hit) {
			
			for(int i=0; i<circsNum; i++) {
				
				hit = ind.hits(circs[i]);
				
				
				if(hit) {
					return ind.r;
				}
			}
			
			ind.r += 0.5;
		}
		
		return 0;
			
	}
	
	
	//create the circles for the map
	void createCircles() {
		
		for(int i=0; i<circsNum; i++) {
			
			int r = randomWithRange(0, maxR);
			int x = randomWithRange(0+maxR, len-maxR);
			int y = randomWithRange(0+maxR, h-maxR);
			
			circs[i] = new Circle(x, y, r);

			
		}
		
		System.out.println("Playground created: " + circsNum);		
	}
	
	
	public void showCircs() {
		
		for(int i=0; i<circsNum; i++) {

			System.out.println(circs[i].x + " " + circs[i].y + " " + circs[i].r);
		}
	}
	
	
	
	
	public int randomWithRange(int min, int max)
	{
	   int range = Math.abs(max - min) + 1;   
	   
	   return (int)(Math.random() * range) + (min <= max ? min : max);
	}

	
	

}
