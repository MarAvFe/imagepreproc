package rest;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.Imgproc;

/**
 * The Class FiltroGeneralizado.
 */
public class FiltroGeneralizado {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main( String[] args ){

	}


	public static BufferedImage principal(BufferedImage imagen, int pSize, int pSigma) {
		Mat m = bufToMat(imagen);
		double [][][] img = getMatrix(m);
		double [][][] filtermatrix = filter(pSize,pSigma,img);
		Mat withfilter = getFixedImage(filtermatrix);
		BufferedImage buffer = mat2BufferedImage(withfilter) ;
		return buffer;
	}
	
	public static BufferedImage Bilateral(BufferedImage imagen, int pSize, int pSigma, int pSigma2) {
		Mat m = bufToMat(imagen);
		Mat dest = new Mat(m.rows(), m.cols(), m.type());
		Imgproc.bilateralFilter(m, dest, pSize, pSigma, pSigma2);
		BufferedImage buffer = mat2BufferedImage(dest) ;
		return buffer;
	}


    /**
     * Se encarga de convertir un bufferedImage en un objeto de tipo Mat que es el que va a ser procesado.
     * @param img --> imagen de tipo bufferedImage
     * @return se retorna una matriz
     */
    public static Mat bufToMat(BufferedImage img){
    	Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
    	byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
    	mat.put(0, 0, pixels);
    	return mat;
    }

    /**
     * Mat 2 buffered image.
     *
     * @param m the m
     * @return the buffered image
     */
    public static BufferedImage mat2BufferedImage(Mat m){
		// source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
		// Fastest code
		// The output can be assigned either to a BufferedImage or to an Image

		    int type = BufferedImage.TYPE_BYTE_GRAY;
		    if ( m.channels() > 1 ) {
		        type = BufferedImage.TYPE_3BYTE_BGR;
		    }
		    int bufferSize = m.channels()*m.cols()*m.rows();
		    byte [] b = new byte[bufferSize];
		    m.get(0,0,b); // get all the pixels
		    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		    System.arraycopy(b, 0, targetPixels, 0, b.length);
		    return image;
		}

	/**
	 * Se encarga de convertir un double[][][] a un objeto de tipo Mat, esto despues de haber aplicado el filtro Gaussiano
	 *
	 * @param matrix -- la matriz que va a ser procesada
	 * @return retorna un Mat que es la imagen destino
	 */
	public static Mat getFixedImage(double[][][] matrix){
		try {
			double [][] temp2 = new double[matrix[0].length][matrix[0][0].length] ;
			Mat destination = new Mat(matrix[0].length,matrix[0][0].length, CvType.CV_8UC3);

			for(int i = 0; i< destination.size().height-1;i++){
				for(int j = 0; j< destination.size().width-1;j++){
					double [] hola = {matrix[0][i][j],matrix[1][i][j],matrix[2][i][j]};
					destination.put(i, j, hola);
					temp2[i][j] = destination.get(i, j)[0];
				}
			}
			Imgcodecs.imwrite("generado.png", destination);
			return destination;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	  /**
  	 * Mat2BufferedImage.
  	 *
  	 * @param m --> es la matriz que se va a convertir en un bufferedImage
  	 * @return retorna image que es un bufferedImage
  	 */
  	public static BufferedImage Mat2BufferedImage(Mat m){
		// source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
		// Fastest code
		// The output can be assigned either to a BufferedImage or to an Image

		    int type = BufferedImage.TYPE_BYTE_GRAY;
		    if ( m.channels() > 1 ) {
		        type = BufferedImage.TYPE_3BYTE_BGR;
		    }
		    int bufferSize = m.channels()*m.cols()*m.rows();
		    byte [] b = new byte[bufferSize];
		    m.get(0,0,b); // get all the pixels
		    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		    System.arraycopy(b, 0, targetPixels, 0, b.length);
		    return image;

		}


		/**
		 * Recibe el objeto de tipo Mat que se obtiene al leer la imagen y crea una matriz de tipo[][][]
		 *
		 * @param m --> Matriz obtenida con el source de la imagen
		 * @return devuelve un double[][][]
		 */
		public static double[][][] getMatrix(Mat m){

			m.convertTo(m, CvType.CV_8UC3);
			int columnas= m.cols();
			int filas = m.rows();
			double[][][] imageMatrix;
			imageMatrix = new double[3][filas][columnas];

			for(int g = 0;g<3;g++) {
				for(int i=0;i<filas;i++) {
					for(int j=0;j<columnas;j++){
						imageMatrix[g][i][j] = m.get(i,j)[g];
					}
				}
			}
			return imageMatrix;
		}


	/**
	 * Gets the kernel.
	 *
	 * @param k --> tama�o de la ventana del Kernel
	 * @param sigma --> parametro usado para definir el filtro
	 * @return retorna una matriz de dos dimensiones(la ventana o kernel)
	 */
	public static double [][] getKernel(int k,float sigma) {
		double sum=0;
		double [][] matrix = new double[k][k];
		for(int j=-(k/2);j<k/2;++j) {
            for(int i=-(k/2);i<k/2;++i) {
				matrix[i+k/2][j+k/2]=Math.pow(Math.E,-((Math.pow(i,2)+Math.pow(j,2))/2*Math.pow(sigma,2)));
	            sum=sum+matrix[i+k/2][j+k/2];
            }
        }
		return matrix;
	}


	/**
	 * Filter.
	 *
	 * @param K parametro usado para definir las dimensiones del Kernel
	 * @param sigma parametro usado para calcular los nuevos valores de la matriz
	 * @param imageMatrix matriz de tres dimensiones que contiene la informacion de la matriz procesada
	 * @return retorna una matriz de tres dimensiones con el filtro aplicado
	 */
	public static double[][][] filter(int K, float sigma, double[][][] imageMatrix){
		double [][][] matresult = imageMatrix;
		for(int g = 0; g<3;g++) {

			double[][] U = imageMatrix[g];
			double[][] Ures = imageMatrix[g];
			double[][] gaussKernel = getKernel(K, sigma);

			//TRACE: System.out.println("Matriz sin borde");
			for(int i=0; i < U.length; i++){			// Eliminar los bordes
				for(int j=0; j < U[0].length; j++){
					if((i == 0) | (i == U.length-1) | (j == 0) | (U[0].length-1 == 0)){
						U[i][j] = Ures[i][j] = 0;
					};
				}
			}

			//TRACE: System.out.println("Aplicando filtro...");
			int var = (int)Math.floor((K-1)/2);
			for(int x=var; x < U.length-var; x++){
				for(int y=var; y < U[0].length-var; y++){
					int result = 0;
					int
					xkmin = x-var,
					xkmax = x+var,
					ylmin = y-var,
					ylmax = y+var;
					// filtro
					for(int i=xkmin; i < xkmax+1; i++){			// se desliza F sobre U
						for(int j=ylmin; j < ylmax+1; j++){
						   result += U[i][j] * gaussKernel[i%gaussKernel.length][j%gaussKernel.length];
						}
					}
					Ures[x][y] = result;
				}
			}
			matresult[g] = Ures;
		}
		return matresult;
	}
}
