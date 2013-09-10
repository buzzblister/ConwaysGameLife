public class GameOfLife {
	public Cell[][] table;	//Table that contains all cells
	public int row, column; //Size of the table
	
	GameOfLife(){
		row = 0; column = 0;
		table = new Cell [row][column];
	}
	
	GameOfLife(int r, int col){
		row = r; column = col;
		table = new Cell [r][col];
		for(int i=0; i<r; i++){ //Initialize
			for(int j=0; j<col; j++){
				table[i][j] = new Cell();
			}
		}
	}
	
	public Cell[][] getTable(){
		return table;
	}
	
	public void population(){ //Defines how many alive neighbours each cell have
		int r, c;
		int countNeighbours;
		for(int i=0; i<row; i++){ //For each row
			for(int j=0; j<column; j++){ //For each column
				countNeighbours = 0;
				for(int rowMod = -1; rowMod <= 1; rowMod++){ 		//Neighbours of cell [i  j]
					for(int colMod = -1; colMod <= 1; colMod++){	//Neighbours of cell [i  j]
						r=i+rowMod;
						c=j+colMod;
						//If cell exists and it is alive..
						if (r >= 0 && c >= 0 && r < row && c < column && true == this.table[r][c].getIsAlive()){
							if(i != r || j != c) //..and it's not the cell itself (i, j)
								countNeighbours += 1; //count this neighbour
						}
				    }
				}
				newGeneration(countNeighbours, i, j); //Decides if cell will live in next generation
			}
		}
	}
	
	public void newGeneration(int countNeighbours, int r, int c){ //Decides if cell will live in next generation
		switch(countNeighbours){ //Switch alive neighbours of cel [r c]
			case 0: case 1: 
				table[r][c].setNextGenLife(false);	//Cell dies in next generation
			break;
						
			case 2: //Alive cell live, dead cell - dead in next generation
				if(table[r][c].getIsAlive())
					table[r][c].setNextGenLife(true);
				else
					table[r][c].setNextGenLife(false);
				break;
						
			case 3: //Cell lives in next generation
				table[r][c].setNextGenLife(true);
			break;
						
			default: //Cell dies in next generation
				table[r][c].setNextGenLife(false); 
			break;
		}
	}
	
	public void enterNextGeneration(){	//Set life status of next generation to current
		for(int i=0; i<row; i++){			//For each
			for(int j=0; j<column; j++){	//cell in table
				table[i][j].setNextGenToCurrentGen();
			}
		}
	}
}