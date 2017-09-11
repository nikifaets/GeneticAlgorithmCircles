
public class Individual {

	
	int x,y;
	double r = 0;
	int id;
	
	Individual(int x,int y, int id){
		
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	Individual(int x, int y, double r, int id){
		
		this.x = x;
		this.y = y;
		this.r = r;
		this.id = id;
	}
	
	Individual(int x, int y){
		
		this.x = x;
		this.y =y;
	}
	
	
	
	public boolean hits(Circle circ) {
		
		int dx = Math.abs(x - circ.x);
		int dy = Math.abs(y - circ.y);
		
		
		double dist = Math.sqrt(dx*dx + dy*dy);
		
		if(dist <= r+circ.r) {
			return true;
		}
		
		return false;
	}
	
	
}
