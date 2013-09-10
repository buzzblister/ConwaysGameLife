import java.awt.Color;

public class GameOfLife {
	private Cell[][] table;	//Table that contains all cells

	GameOfLife(int r, int col){
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
	
	public int getRow(){
		return table.length;
	}
	
	public int getColumn(){
		return table[0].length;
	}
	
	public void setLifeByColor(int row, int col, Color life){
		table[row][col].setBackground(life);
		table[row][col].setColorToIsAlive();
	}
	
	public void evolve(){ //Evolve to next generation
		for(int i=0; i<getRow(); i++){ //For each row
			for(int j=0; j<getColumn(); j++){ //For each column
				newGeneration(calculations(i,j), i, j); //Decides if cell will live in next generation
			}
		}
		
		for(int i=0; i<getRow(); i++){			//For each
			for(int j=0; j<getColumn(); j++){	//cell in table
				table[i][j].setNextGenToCurrentGen();
			}
		}
	}
	
	public int calculations(int i, int j){ //Calculates neighbours
		int r = 0;
		int c = 0;
		int countNeighbours = 0;
		for(int rowMod = -1; rowMod <= 1; rowMod++){ 		//Neighbours of cell [i  j]
			for(int colMod = -1; colMod <= 1; colMod++){	//Neighbours of cell [i  j]
				r=i+rowMod;
				c=j+colMod;
				//If cell exists and it is alive..
				if (r >= 0 && c >= 0 && r < getRow() && c < getColumn() && true == this.table[r][c].getIsAlive()){
					if(i != r || j != c) //..and it's not the cell itself (i, j)
						countNeighbours += 1; //count this neighbour
				}
		    }
		}
		return countNeighbours;
	}
	
	public void newGeneration(int countNeighbours, int r, int c){ //Decides if cell will live in next generation
		switch(countNeighbours){ //Switch alive neighbours of cel [r c]
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
}