import java.util.Random;

public class TileMap {
	
	int myX;
	int mySecondX;
	public  int x = 0, y = 0;
	public Random rand = new Random();
	int myY;
	private char[][] tiles;
	char[][] temporaryTiles;
	

	public char[][] getTiles() {
		return tiles;
	}
	public void setTiles(char[][] tiles) {
		this.tiles = tiles;
	}
	public char[][] getTemporaryTiles() {
		return temporaryTiles;
	}
	public void setTemporaryTiles(char[][] temporaryTiles) {
		this.temporaryTiles = temporaryTiles;
	}



	public TileMap() {
		
		
		int count = 0;
		tiles = new char[12][12];
		temporaryTiles = new char[12][12];
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				tiles[i][j] = '-';
				temporaryTiles[i][j] = '-';
			}
		}

		// Randomized Starting Point
		int startingPoint = rand.nextInt(8);
		switch (startingPoint) {
		case 0:
			tiles[0][0] = 'S';	tiles[10][10] = 'R';
			break; // top left
		case 1:
			tiles[10][10] = 'S'; tiles[0][0] = 'R';
			break;// bottom right
		case 2:
			tiles[10][0] = 'S';	tiles[0][10] = 'R';
			break; // top right
		case 3:
			tiles[0][10] = 'S';	tiles[10][0] = 'R';
			break; // bottom left

		case 4:
			tiles[5][0] = 'S';	tiles[5][10] = 'R';
			break; // top

		case 5:
			tiles[5][10] = 'S';	tiles[5][0] = 'R';
			break; // bottom

		case 6:
			tiles[0][5] = 'S';	tiles[10][5] = 'R';
			break; // left

		case 7:
			tiles[10][5] = 'S'; tiles[0][5] = 'R';
			break;// right
		}
		for( int i = 0 ; i< tiles.length ;i++){
			for(int j=0;j< tiles.length ; j++){
				if(tiles[i][j]=='S'){
					tiles[i+1][j]='s'; 
					tiles[i][j+1] ='s';
					tiles[i+1][j+1]='s';
				}
				if(tiles[i][j]=='R'){
					tiles[i+1][j]='r'; 
					tiles[i][j+1] ='r';
					tiles[i+1][j+1]='r';
				}
			}
		}
		count = 0;
		while (count < 20) {
			x = rand.nextInt(12);
			y = rand.nextInt(12);
			if (tiles[x][y] != 'S' && tiles[x][y] != 's' && tiles[x][y] != 'R'
					&& tiles[x][y] != 'r' && tiles[x][y] != 'X') {
				tiles[x][y] = 'X';
				count++;
			} else {
				x = rand.nextInt(12);
				y = rand.nextInt(12);
			}
		}
		finalizeMap();
		rand = null;
	}
	public TileMap(int mapNumber){
		char[]firstMap = { 'X','-','-','-','-','S','s','-','-','-','-','-',
				  '-','-','X','-','-','s','s','-','-','-','X','-',
				  '-','-','X','X','X','X','X','X','-','-','X','-',
				  '-','-','-','-','-','-','-','-','X','-','-','X',
				  '-','-','X','-','X','-','-','-','-','X','-','-',
				  '-','X','-','-','X','-','-','X','-','-','X','-',
				  '-','X','-','-','X','-','-','X','-','-','X','-',
				  '-','-','X','-','-','-','-','X','-','X','-','-',
				  'X','-','-','X','-','-','-','-','-','-','-','-',
				  '-','X','-','-','X','X','X','X','X','X','-','-',
				  '-','X','-','-','-','R','r','-','-','X','-','-',
				  '-','-','-','-','-','r','r','-','-','-','-','X',
				  		
};
		char[]secondMap = { '-','-','-','-','-','-','-','-','-','-','-','-',
							'-','-','-','-','-','-','X','X','X','X','-','-',
							'-','-','X','X','-','-','-','-','-','-','-','-',
							'-','-','-','X','-','-','X','-','-','-','-','-',
							'-','-','-','X','-','-','-','-','-','-','-','-',
							'-','S','s','X','-','-','-','-','X','R','r','-',
							'-','s','s','X','-','-','-','-','X','r','r','-',
							'-','-','-','-','-','-','-','-','X','-','-','-',
							'-','-','-','-','-','X','-','-','X','-','-','-',
							'-','-','-','-','-','-','-','-','X','X','-','-',
							'-','-','X','X','X','X','-','-','-','-','-','-',
							'-','-','-','-','-','-','-','-','-','-','-','-',
				  		
};
		char[]thirdMap = { '-','-','X','-','-','-','-','-','-','X','-','-',
							'-','-','-','-','-','-','-','-','-','-','-','-',
							'X','-','X','X','X','-','-','X','X','X','-','X',
							'-','-','X','-','-','-','-','-','-','X','-','-',
							'-','-','X','-','-','-','-','-','-','X','-','-',
							'-','S','s','-','-','X','X','-','-','R','r','-',
							'-','s','s','-','-','X','X','-','-','r','r','-',
							'-','-','X','-','-','-','-','-','-','X','-','-',
							'-','-','X','-','-','-','-','-','-','X','-','-',
							'X','-','X','X','X','-','-','X','X','X','-','X',
							'-','-','-','-','-','-','-','-','-','-','-','-',
							'-','-','X','-','-','-','-','-','-','X','-','-',
	  		
};
		tiles = new char[12][12];
		temporaryTiles = new char[12][12];
		int z=0;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				switch (mapNumber) {
				
				case 1: tiles[j][i] = firstMap[z]; temporaryTiles[j][i] = firstMap[z]; break;
				case 2: tiles[j][i] = secondMap[z]; temporaryTiles[j][i] = secondMap[z]; break;
				case 3: tiles[j][i] = thirdMap[z]; temporaryTiles[j][i] = thirdMap[z]; break;
				}
				z++;
			}
		}
		finalizeMap();
		rand= null;
	}
	public void finalizeMap(){
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				if (tiles[i][j] == 'S') {
					myX = 25 + i * 50;
					myY = 25 + j * 50 ;
				}
				if (tiles[i][j] == 'R') {
					mySecondX = 25 + i * 50;
					mysecondY = 25 + j * 50;
				}
			}
		}
		int count=0;
		// code for the cherry
		while (count < 4) {
			do {
				x = rand.nextInt(12);
				y = rand.nextInt(12);
			} 

			while (tiles[x][y] == 'S' || tiles[x][y] == 's'
					|| tiles[x][y] == 'R' || tiles[x][y] == 'r' 
					|| tiles[x][y]=='X');

			if (count == 0) {
				tiles[x][y] = '1';
				count++;
			}
			if (count == 1 && tiles[x][y] != '1') {
				tiles[x][y] = '2';
				count++;
			}
			if (count == 2 && tiles[x][y] != '1' && tiles[x][y] != '2') {
				tiles[x][y] = '3';
				count++;
			}
			if (count == 3 && tiles[x][y] != '1' && tiles[x][y] != '2'
					&& tiles[x][y] != '3') {
				tiles[x][y] = '4';
				count++;
			}

		}

		// code for the solid tiles
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 1; j < tiles.length; j++) {
				if(tiles[i][j]=='X' && (tiles[i][j-1]=='X' || tiles[i][j-1]=='x')){
					tiles[i][j]='x';
				}
			}
		}

		// Temporary tile !

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				temporaryTiles[i][j] = tiles[i][j];
				if (tiles[i][j] == '2' || tiles[i][j] == '3'
						|| tiles[i][j] == '4') {
					tiles[i][j] = '-';
				}
			}
		}
	}
	
	
	public int getMySecondX() {
		return mySecondX;
	}

	public void setMySecondX(int mySecondX) {
		this.mySecondX = mySecondX;
	}

	public int getMysecondY() {
		return mysecondY;
	}

	public void setMysecondY(int mysecondY) {
		this.mysecondY = mysecondY;
	}

	int mysecondY;

	public int getMyX() {
		return myX;
	}

	public void setMyX(int myX) {
		this.myX = myX;
	}

	public int getMyY() {
		return myY;
	}

	public void setMyY(int myY) {
		this.myY = myY;
	}
}
