package paquete;

public class Filtros
{
	
	public static void main( String[] args ){
		/*filter();
		Filtros fl = new Filtros();
		fl.filterTest();*/
		String s = "asd$sdf";
		System.out.println(s.split("$"));
	}
	
	/*public static void filter(){
		
		int[][] U = {
				{0,0,0,0,0,0,0},
				{0,1,1,1,1,0,0},
				{0,1,2,2,1,1,0},
				{0,1,2,2,2,1,0},
				{0,0,1,2,2,1,0},
				{0,0,1,1,1,1,0},
				{0,0,1,1,1,1,0}
		};
		int[][] Ures = {
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

		for(int i=0; i < U.length; i++){			// Imprimir la matriz inicial
			for(int j=0; j < U.length; j++){
				System.out.print(U[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();

		for(int i=0; i < U.length; i++){			// Eliminar los bordes
			for(int j=0; j < U.length; j++){
				if((i == 0) | (i == U.length-1) | (j == 0) | (U[0].length-1 == 0)){
					U[i][j] = Ures[i][j] = 0;
				};
			}
		}

		for(int i=0; i < Ures.length; i++){			// Imprimir la matriz resultado
			for(int j=0; j < Ures.length; j++){
				System.out.print(Ures[i][j] + "\t");
			}
			System.out.println();
		}


		for(int i=1; i < U.length-1; i++){			// 
			for(int j=1; j < U.length-1; j++){
				int result = 
						U[i-1][j-1] * gaussFilter[0][0]  +
						U[i  ][j-1] * gaussFilter[0][1]  +
						U[i+1][j-1] * gaussFilter[0][2]  +
						U[i-1][j  ] * gaussFilter[1][0]  +
						U[i  ][j  ] * gaussFilter[1][1]  +
						U[i+1][j  ] * gaussFilter[1][2]  +
						U[i-1][j+1] * gaussFilter[2][0]  + 
						U[i  ][j+1] * gaussFilter[2][1]  +
						U[i+1][j+1] * gaussFilter[2][2];
				Ures[i][j] = result;
				/*System.out.print  (U[i-1][j-1] + "*" + gaussFilter[0][0] + " ");
				System.out.print  (U[i  ][j-1] + "*" + gaussFilter[0][1] + " ");
				System.out.println(U[i+1][j-1] + "*" + gaussFilter[0][2] + " ");
				System.out.print  (U[i-1][j  ] + "*" + gaussFilter[1][0] + " ");
				System.out.print  (U[i  ][j  ] + "*" + gaussFilter[1][1] + " ");
				System.out.println(U[i+1][j  ] + "*" + gaussFilter[1][2] + " ");
				System.out.print  (U[i-1][j+1] + "*" + gaussFilter[2][0] + " ");
				System.out.print  (U[i  ][j+1] + "*" + gaussFilter[2][1] + " ");
				System.out.println(U[i+1][j+1] + "*" + gaussFilter[2][2] + " ");
				
				System.out.print("\t\t" + result);
				System.out.println();
				
			}
			//System.out.println();
		}
		System.out.println();
		
		for(int i=0; i < Ures.length; i++){
			for(int j=0; j < Ures.length; j++){
				System.out.print(Ures[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
@Test
public void filterTest(){
		
		int[][] U = {
				{0,0,0,0,0,0,0},
				{0,1,1,1,1,0,0},
				{0,1,2,2,1,1,0},
				{0,1,2,2,2,1,0},
				{0,0,1,2,2,1,0},
				{0,0,1,1,1,1,0},
				{0,0,1,1,1,1,0}
		};
		int[][] Ures = {
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

		for(int i=0; i < U.length; i++){			// Imprimir la matriz inicial
			for(int j=0; j < U.length; j++){
				System.out.print(U[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();

		for(int i=0; i < U.length; i++){			// Eliminar los bordes
			for(int j=0; j < U.length; j++){
				if((i == 0) | (i == U.length-1) | (j == 0) | (U[0].length-1 == 0)){
					U[i][j] = Ures[i][j] = 0;
				};
			}
		}

		for(int i=0; i < Ures.length; i++){			// Imprimir la matriz resultado
			for(int j=0; j < Ures.length; j++){
				System.out.print(Ures[i][j] + "\t");
			}
			System.out.println();
		}


		for(int i=1; i < U.length-1; i++){			// 
			for(int j=1; j < U.length-1; j++){
				int result = 
						U[i-1][j-1] * gaussFilter[0][0]  +
						U[i  ][j-1] * gaussFilter[0][1]  +
						U[i+1][j-1] * gaussFilter[0][2]  +
						U[i-1][j  ] * gaussFilter[1][0]  +
						U[i  ][j  ] * gaussFilter[1][1]  +
						U[i+1][j  ] * gaussFilter[1][2]  +
						U[i-1][j+1] * gaussFilter[2][0]  + 
						U[i  ][j+1] * gaussFilter[2][1]  +
						U[i+1][j+1] * gaussFilter[2][2];
				Ures[i][j] = result;
				/*System.out.print  (U[i-1][j-1] + "*" + gaussFilter[0][0] + " ");
				System.out.print  (U[i  ][j-1] + "*" + gaussFilter[0][1] + " ");
				System.out.println(U[i+1][j-1] + "*" + gaussFilter[0][2] + " ");
				System.out.print  (U[i-1][j  ] + "*" + gaussFilter[1][0] + " ");
				System.out.print  (U[i  ][j  ] + "*" + gaussFilter[1][1] + " ");
				System.out.println(U[i+1][j  ] + "*" + gaussFilter[1][2] + " ");
				System.out.print  (U[i-1][j+1] + "*" + gaussFilter[2][0] + " ");
				System.out.print  (U[i  ][j+1] + "*" + gaussFilter[2][1] + " ");
				System.out.println(U[i+1][j+1] + "*" + gaussFilter[2][2] + " ");
				
				System.out.print("\t\t" + result);
				System.out.println();
				
			}
			//System.out.println();
		}
		System.out.println();
		
		for(int i=0; i < Ures.length; i++){
			for(int j=0; j < Ures.length; j++){
				System.out.print(Ures[i][j] + "\t");
			}
			System.out.println();
		}
	}*/
}