package paquete;

public class FiltroGeneralizado {
	public static void main( String[] args ){
		filter();
	}
	
	public static void filter(){
		
		int L = 3, K = 3;
		//int x = 2, y = 2;
		
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

		System.out.println("Matriz inicial");
		for(int i=0; i < U.length; i++){			// Imprimir la matriz inicial
			for(int j=0; j < U.length; j++){
				System.out.print(U[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Matriz sin borde");
		for(int i=0; i < U.length; i++){			// Eliminar los bordes
			for(int j=0; j < U.length; j++){
				if((i == 0) | (i == U.length-1) | (j == 0) | (U[0].length-1 == 0)){
					U[i][j] = Ures[i][j] = 0;
				};
			}
		}

		System.out.println("Aplicando filtro...");
		for(int x=1; x < U.length-1; x++){			// Imprimir la matriz inicial
			for(int y=1; y < U[0].length-1; y++){
				//System.out.print(U[x][y] + "\t");
				int result = 0;
				int 
				xkmin = x-(int)Math.floor((K-1)/2), 
				xkmax = x+(int)Math.floor((K-1)/2), 
				ylmin = y-(int)Math.floor((L-1)/2), 
				ylmax = y+(int)Math.floor((L-1)/2);
				
				for(int i=xkmin; i < xkmax+1; i++){			// Filtro
					for(int j=ylmin; j < ylmax+1; j++){
						result += U[i][j] * gaussFilter[i%gaussFilter.length][j%gaussFilter.length];
						//System.out.print(U[i][j] + " ");
					}
					//System.out.println();
				}
				Ures[x][y] = result;
			}
			//System.out.println();
		}
		
		System.out.println("Matriz resultado");
		for(int i=0; i < Ures.length; i++){
			for(int j=0; j < Ures.length; j++){
				System.out.print(Ures[i][j] + "\t");
			}
			System.out.println();
		}
	}
}


/*
  
0	0	0	0	0	0	0	
0	-8	-8	-4	-4	0	0	
0	-8	-4	-4	0	4	0	
0	-4	-4	0	4	4	0	
0	-4	0	4	4	8	0	
0	0	0	4	8	8	0	
0	0	0	0	0	0	0 


  
0	0	0	0	0	0	0	
0	-8	-8	-8	-4	-4	0	
0	-8	0	8	4	-4	0	
0	0	8	8	48	16	0	
0	-4	28	28	28	-4	0	
0	-4	-4	16	-4	-4	0	
0	0	0	0	0	0	0
  
  
  import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class Gauss {
public static void main( String[] args ){

try {
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	
	Mat source = Imgcodecs.imread("digital_image_processing.jpg",
	Imgcodecs.CV_LOAD_IMAGE_COLOR);
	
	Mat destination = new Mat(source.rows(),source.cols(),source.type());
	Imgproc.GaussianBlur(source, destination,new Size(45,45), 0);
	Imgcodecs.imwrite("Gaussian45.jpg", destination);

} catch (Exception e) {
System.out.println("Error: " + e.getMessage());
}
}
}
  
  
  
  
 * */
