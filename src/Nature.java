import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class Nature {
	
	int indsNum;
	int bestFit = 0;
	int bestRes = 0;
	int indsToFindForCrossing = 20;
	double maxRad = 0;
	int maxRadX;
	int maxRadY;
	int iterations = 0;
	
	//array with individs
	Individual inds[];
	
	//array to see if an individual has been crossed, indexed by id
	boolean indIsUsed[];
	
	//list to quickly remove crossed individs, indexed by id
	List <Integer>indsList = new ArrayList<Integer> ();
	
	Playground pg = new Playground(0,0,0);
	
	
	Nature(int individs, Playground pg){
		
		indsNum = individs;
		this.pg = pg;
		inds = new Individual[indsNum];
		createFirstGeneration();
	}
	
	public void live() {
		
		iterations++;
		
		if(iterations%10 == 0) {
			
			System.out.println("best r: " + maxRad);
		}
		
		while(true) {
			
			grow();
			System.out.println("GROW NO BUGS");
			newGeneration();
		}
		
		
	}
	
	
	
	//create the first generation
	public void createFirstGeneration() {
		
		int maxR = 20;
		
		for(int i=0; i<indsNum; i++) {
			
			
			int x = pg.randomWithRange(0, pg.len);
			int y = pg.randomWithRange(0, pg.h);
			int r = pg.randomWithRange(1, maxR);
			
			inds[i] = new Individual(x,y,0,i);
			indsList.add(i);
			//System.out.println("During the creation: " + inds[i].r);
			
			//System.out.println("x,y,r: " + x + " " + y +" " + r);
			
		}
		
		//System.out.println("Individuals created: " + indsNum);
	}
	
	
	public void newGeneration() {
		
		Individual[] newInds = new Individual[indsNum];
		
		newInds = findAndCross();
		
		inds = newInds;
		
		for(int i=0; i<indsNum; i++) {
			
			inds[i].id = i;
			indsList.add(i);
		}
		
	}
		
	//cross two individuals
	public Individual[] crossInds (Individual ind1, Individual ind2) {
			
			//if ind is in the top 50% of fitness, child = 1.5*ind + ind1
			// child1 is circ1+ind
			
			//else child = ind1+ind
		
			Individual indBig = (ind1.r > ind2.r) ? ind1 : ind2;
			
			Individual indSmall = (ind1.r > ind2.r) ? ind2 : ind1;
			Individual[] childs = new Individual [2];
			
			
			if(ind1.r >= 0.5*bestRes || ind2.r >= 0.5*bestRes) {
							
			
				
				int x1 = (2*indBig.x + indSmall.x)/2;
				int y1 = (2*indBig.y + indSmall.y)/2;
				
				int x2 = (indBig.x + indSmall.x)/2;
				int y2 = (indBig.y + indSmall.y)/2;
				
				childs[0] = new Individual(x1, y1);
				childs[1] = new Individual(x2, y2);
		
			}
			
			else {
				
				int x1 = (2*indBig.x + indSmall.x)/2;
				int y1 = (2*indBig.y + indSmall.y)/2;
				
				int x2 = (indBig.x + 2*indSmall.x)/2;
				int y2 = (indBig.y + 2*indSmall.y)/2;
				
				childs[0] = new Individual(x1, y1);
				childs[1] = new Individual(x2, y2);
	
			}
			
			return childs;
		}
	
	//find individuals to cross and replace the new ones in the array with inds
	public Individual[] findAndCross() {
		
		Individual newInds[] = new Individual[indsNum];
		int newIdx = 0;
		int s = indsList.size();
		//System.out.println("indsNum and list size: " + indsNum + " " + s);
		//System.out.println("in the array: " + inds[0].r);
		for(int i=0; i<s; i++) {
			
			Individual ind1 = inds[i];
			//System.out.println("at the beginning: " + inds[i].r);
			Individual indsForCross[] = new Individual[indsToFindForCrossing];
			List <Integer>tempList = indsList;
			int ss = s;
			
			//get a few individs to cross with
			for(int j=0; j<indsToFindForCrossing; j++) {
				
				int idx = tempList.get(pg.randomWithRange(0, ss-1));
				//System.out.println("indsForCross accepting: " + inds[idx].r);
				indsForCross[j] = inds[idx]; 				
				//System.out.println("found ind r: " + inds[j].r);
				
				//System.out.println("trying to remove " + idxToRemove + " element, size is " + tempList.size());
				tempList.remove(idx);
				
				ss--;
				
			}
			
			tempList.clear();
			
			Individual best = findBestMatch(ind1, indsForCross);
			System.out.println(best.id);
			//cross the best match
			Individual[] childs = new Individual[2];
			
			childs = crossInds(ind1, best);
			
			int idxToRemove = indsList.indexOf(best);
			indsList.remove(idxToRemove);
			indsList.remove(ind1.id);
			s-=2;
			
			newInds[newIdx] = childs[0];
			newInds[newIdx+1] = childs[1];
			newIdx+=2;

		}
		
		return newInds;
	}
	
	public Individual findBestMatch(Individual ind, Individual indsForCross[]) {
		
		//find the best match: koeff = 1/dist + size/maxR
		
		double koeffBest = 0;
		Individual best = new Individual(0,0);
		
		for(int j=0; j<indsToFindForCrossing; j++) {
			
			Individual ind2 = indsForCross[j];
			
			int dx = Math.abs(ind.x - ind2.x);
			int dy = Math.abs(ind.y - ind2.y);
			
			double dist = (double)(Math.sqrt( dx*dx + dy*dy));
			double koeff = (double)(1/dist) + (double)(ind2.r/pg.maxR);
			
			if(dist < 0.001) {
				dist = 0.01;
			}
			//System.out.println("dist and koeff: " + (double)(1/dist) + " " + ind2.r);
			
			if(koeff > koeffBest) {
				
				best = ind2;
				koeffBest = koeff;
			}
			
		}
		
		return best;
	}
	
	public void grow() {
		
		for(int i=0; i<indsNum; i++) {
			
			inds[i].r = pg.findMaxRad(inds[i]);
			//System.out.println("found new max: " + inds[i].r);
			if(inds[i].r > maxRad) {
				maxRad = inds[i].r;
				maxRadX = inds[i].x;
				maxRadY = inds[i].y;
			}
		}
	}
	
	//print current individuals
	public void showInds() {
		
		for(int i=0; i<indsNum; i++) {
			
			//System.out.println(inds[i].x + " " + inds[i].y + " " + inds[i].r);
		}
	}
	
	
		
		
	}
