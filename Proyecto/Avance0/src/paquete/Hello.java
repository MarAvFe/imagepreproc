package paquete;


public class Hello
{
	
	public static void main( String[] args ){
		filter();
	}
	
	public static void filter(){
		int[][] U = {
				{0,0,0,0,0,0,0},
				{0,1,1,1,1,0,0},
				{0,1,2,2,1,1,0},
				{0,1,2,2,2,1,0},
				{0,0,1,2,2,1,0},
				{0,0,1,1,1,1,0},
				{0,0,1,1,1,1,0}
		};
		int [][] gaussFilter = {
				{4,0,0},
				{0,0,0},
				{0,0,-4}
		};

		for(int i=1; i < U.length-1; i++){
			for(int j=1; j < U.length-1; j++){
				System.out.print(U[i][j] + " ");
			}
			System.out.println();
		}
	}
}