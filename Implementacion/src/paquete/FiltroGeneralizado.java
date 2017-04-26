package paquete;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FiltroGeneralizado {
	public static void main( String[] args ){
		// solo nos  sirve con 3x3
		//getMatrix();
		
		getFixedImage(filter(3, 3, 1, getMatrix()));
		
		
	}
	
	public static void getFixedImage(double[][] matrix){
		try {
			System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
			
			Mat source = Imgcodecs.imread("C:\\Users\\nroma\\Desktop\\fresa.jpg");
			
			
			Mat destination = new Mat(source.rows(),source.cols(),source.type());
			Imgproc.cvtColor(destination, destination, Imgproc.COLOR_BGR2GRAY,0);
			
			for(int i = 0; i< source.rows();i++){
				for(int j = 0; j< source.cols();j++){
					destination.put(i, j, matrix[i][j]);
				}
			}
			
			//Imgproc.GaussianBlur(source, destination,new Size(45,45), 0);
			Imgcodecs.imwrite("OJALASIRVA.jpg", destination);

		} catch (Exception e) {
		System.out.println("Error: " + e.getMessage());
		}
	}
	
	 
	// lee con opencv la imagen y convierte de mat a double[][]
	public static double[][] getMatrix(){
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = Imgcodecs.imread("C:\\Users\\nroma\\Desktop\\fresa.jpg");//se lee la imagen 
		//Imgproc.getGaussianKernel(2, 1);
		img.convertTo(img, CvType.CV_64FC3);
		int size = (int) (img.total() * img.channels());
		double[] temp = new double[size]; 
						
		int columnas= img.cols();
		int filas = img.rows();
		
		double[][] imageMatrix;
		imageMatrix = new double[filas][columnas];
		
		//System.out.println(columnas);
		//System.out.println(filas);
		
		for(int i=0;i<filas;i++) {
			for(int j=0;j<columnas;j++){ 
				imageMatrix[i][j] = img.get(i,j,temp);
				//System.out.println(img.get(i,j,temp));
				//System.out.println(imageMatrix[i][j]);
	      }
			//System.out.println();
	    }
		return imageMatrix;
	}
	
	// solo hace con matriz cuadrada
	// calcula el kernel con un tamaño y sigma dados
	public static double [][] getKernel(int k,float sigma)
	{
		double sum=0;
		double [][] matrix = new double[k][k];
		for(int j=-(k/2);j<k/2;++j)
        {
            for(int i=-(k/2);i<k/2;++i)
            {
				matrix[i+k/2][j+k/2]=Math.pow(Math.E,-((Math.pow(i,2)+Math.pow(j,2))/2*Math.pow(sigma,2)));
	            sum=sum+matrix[i+k/2][j+k/2];
            }
        }
		return matrix;
	}
	
	
	public static double[][] filter(int K, int L, float sigma, double[][] imageMatrix){
		
		// int L = 3, K = 3;
		//int x = 2, y = 2;
		
		double[][] U = imageMatrix;
		double[][] Ures = imageMatrix;
		
		/*
		double[][] U = {           // matriz inicial --> debe ser matriz de pixeles de la imagen original
				{0,0,0,0,0,0,0,0},
				{0,1,1,1,1,0,0,0},
				{0,1,2,2,1,1,0,0},
				{0,1,2,2,2,1,0,0},
				{0,0,1,2,2,1,0,0},
				{0,0,1,1,1,1,0,0},
				{0,0,1,1,1,1,0,0},
				{0,0,1,1,1,1,0,0}
		};
		double[][] Ures = {		// copia de matriz original
				{0,0,0,0,0,0,0,0},
				{0,1,1,1,1,0,0,0},
				{0,1,2,2,1,1,0,0},
				{0,1,2,2,2,1,0,0},
				{0,0,1,2,2,1,0,0},
				{0,0,1,1,1,1,0,0},
				{0,0,1,1,1,1,0,0},
				{0,0,1,1,1,1,0,0}
		};
		
		int [][] gaussFilter = { // armar matriz con parametros
				{4,0,0},
				{0,0,0},
				{0,0,-4}
		}; */
		
		double[][] gaussKernel = getKernel(K, sigma);

		/*
		System.out.println("Matriz inicial");
		for(int i=0; i < U.length; i++){			// Imprimir la matriz inicial
			for(int j=0; j < U.length; j++){
				System.out.print(U[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
		*/
		
		System.out.println("Matriz sin borde");
		for(int i=0; i < U.length; i++){			// Eliminar los bordes
			for(int j=0; j < U.length; j++){
				if((i == 0) | (i == U.length-1) | (j == 0) | (U[0].length-1 == 0)){
					U[i][j] = Ures[i][j] = 0;
				};
			}
		}

		System.out.println("Aplicando filtro...");
		for(int x=1; x < U.length-1; x++){			
			for(int y=1; y < U[0].length-1; y++){
				//System.out.print(U[x][y] + "\t");
				int result = 0;
				int 
				xkmin = x-(int)Math.floor((K-1)/2), 
				xkmax = x+(int)Math.floor((K-1)/2), 
				ylmin = y-(int)Math.floor((L-1)/2), 
				ylmax = y+(int)Math.floor((L-1)/2);
															// filtro
				for(int i=xkmin; i < xkmax+1; i++){			// se desliza F sobre U
					for(int j=ylmin; j < ylmax+1; j++){
						result += U[i][j] * gaussKernel[i%gaussKernel.length][j%gaussKernel.length];
						// AQUI ES LA BRONCA WE
						System.out.print(U[i][j] + " ");
					}
					System.out.println();
				}
				System.out.println(result);
				Ures[x][y] = result;	
			}
			System.out.println();
		}
		
		System.out.println("Matriz resultado");
		for(int i=0; i < Ures.length; i++){
			for(int j=0; j < Ures.length; j++){
				System.out.print(Ures[i][j] + "\t");
			}
			System.out.println();
		}
		return Ures;
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
