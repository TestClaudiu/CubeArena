import java.util.ArrayList;
import java.util.Random;

public class MapChooser { // this class will randomize the order of the maps

	ArrayList<Integer> myList = new ArrayList<Integer>();
	int i = 0;
	Animation A;
	Random rand = new Random();
	
	public MapChooser(Animation A) {
		this.A = A;
		
		myList.add(1);myList.add(2);myList.add(3);
		randomizeArray();
		
	}
	public void next() {
		
	
		if (i >= myList.size()) {
			
			int lastMap = myList.get(i-1);
			i = 0;
			randomizeArray();
			while(myList.get(0)== lastMap){
				randomizeArray();
			}
			
			next();
			
		} else {
			A.generateTileMap(myList.get(i));
			i++;
		}
	}
	public void randomizeArray(){
		ArrayList<Integer> temporary = new ArrayList<Integer>();
		int[] checked = new int[myList.size()];
		for(int i=0;i<checked.length;i++){
			checked[i]=0;
		}
		
		
		while(temporary.size()!=myList.size()){
			int position = rand.nextInt(myList.size());
			if(checked[position]!=1){
				checked[position]=1;
				temporary.add(myList.get(position));
			}
		}
		
		myList.clear();
		myList.addAll(temporary);
		temporary = null;
		
	}
}
