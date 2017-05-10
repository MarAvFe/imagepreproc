package rest;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferDouble;
import java.awt.image.DataBufferByte;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.Imgproc;
import java.awt.image.WritableRaster;


public class FiltroGeneralizado {

	public static void main( String[] args ){
		// solo nos  sirve con 3x3

		//getMatrix();
		//double [][][] matrix = getMatrix();
		//double [][][] filtermatrix = filter(3,3,1,matrix);
		//getFixedImage(filtermatrix);
	}


	public static BufferedImage principal(BufferedImage imagen, int pSize, int pSigma) {
		//System.out.println(java.library.path);
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat m = bufToMat(imagen);
		double [][][] img = getMatrix(m);
		double [][][] filtermatrix = filter(pSize,pSigma,img);
		Mat withfilter = getFixedImage(filtermatrix);
		//System.out.println(withfilter.dump());
		BufferedImage buffer = mat2BufferedImage(withfilter) ;
		//BufferedImage buffer = matToBufferedImage50(withfilter);
		//BufferedImage buffer = toBufferedImage2(toBufferedImage1(withfilter));
		return buffer;
	}


    public static Mat bufToMat(BufferedImage img){
    	Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
    	byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
    	mat.put(0, 0, pixels);
    	return mat;
    }

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


	public static Image toBufferedImage1(Mat m){
	    int type = BufferedImage.TYPE_BYTE_GRAY;

	    if ( m.channels() > 1 ) {
	        Mat m2 = new Mat();
	        Imgproc.cvtColor(m,m2,Imgproc.COLOR_BGR2HSV);
	        type = BufferedImage.TYPE_INT_ARGB;
	        m = m2;
	    }
	    byte [] b = new byte[m.channels()*m.cols()*m.rows()];
	    m.get(0,0,b); // get all the pixels
	    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
	    image.getRaster().setDataElements(0, 0, m.cols(),m.rows(), b);
	    return image;

	}

	// convierte double[][][] to Mat
	public static Mat getFixedImage(double[][][] matrix){
		try {
			//System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

			System.out.println("SizeRcvGetFixedImage: "
				+ String.valueOf(matrix.length) + "x"
				+ String.valueOf(matrix[0].length) + "x"
				+ String.valueOf(matrix[0][0].length));
			double [][] temp2 = new double[matrix[0].length][matrix[0][0].length] ;
			Mat destination = new Mat(matrix[0].length,matrix[0][0].length, CvType.CV_8UC3);


			double [][] temp = new double [100][100];

			int size = (int) (destination.total() * destination.channels());
			//System.out.println("size(fxc): " + String.valueOf(destination.size().width-1) + "x" + String.valueOf(destination.size().height-1) + ", ");
			for(int i = 0; i< destination.size().height-1;i++){
				for(int j = 0; j< destination.size().width-1;j++){
					//System.out.println("indices(ixj): " + String.valueOf(i) + "x" + String.valueOf(j) + ", ");
					double [] hola = {matrix[0][i][j],matrix[1][i][j],matrix[2][i][j]};
					destination.put(i, j, hola);
					temp2[i][j] = destination.get(i, j)[0];
					//temp[i][j] = temp2[i][];
		        	//	System.out.println(destination.get(i, j).length);
				}
			}
			Imgcodecs.imwrite("generado.png", destination);
			return destination;

		} catch (Exception e) {
			System.out.println("Error en getFixedImage");
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage matToBufferedImage50(Mat mat) {

        if (mat.height() > 0 && mat.width() > 0) {
            BufferedImage image = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);
            WritableRaster raster = image.getRaster();
            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
            byte[] data = dataBuffer.getData();
            mat.get(0, 0, data);
            return image;
        }
        return null;
    }


	public static BufferedImage toBufferedImage2(Image img)
	{
	    if (img instanceof BufferedImage) {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}


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

	  public static void showImage(BufferedImage img){
	        JFrame frame = new JFrame();
	        frame.getContentPane().setLayout(new FlowLayout());
	        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
	        frame.pack();
	        frame.setVisible(true);
	    }

	// lee con opencv la imagen y convierte de mat a double[][]
		/*public static double[][][] getMatrix(){
			//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			Mat img = Imgcodecs.imread("C:\\Users\\kimco\\workspace\\EcualizacionHistograma\\photo.jpg",Imgcodecs.CV_LOAD_IMAGE_COLOR);//se lee la imagen
			//Imgproc.getGaussianKernel(2, 1);

			img.convertTo(img, CvType.CV_8UC3);
			int size = (int) (img.total() * img.channels());
			double[] temp = new double[size];

			int columnas= img.cols();
			int filas = img.rows();


			double[][][] imageMatrix;
			imageMatrix = new double[3][filas][columnas];

			for(int g = 0;g<3;g++) {
				for(int i=0;i<filas;i++) {
					for(int j=0;j<columnas;j++){
						imageMatrix[g][i][j] = img.get(i,j)[g];
						//	System.out.println(img.get(i, j).length);
					}
				}
			}
			return imageMatrix;
		}*/


		//recibe una matriz y la conveirte a double[][]
		public static double[][][] getMatrix(Mat m){
			//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			//Mat img = Imgcodecs.imread("C:\\Users\\kimco\\workspace\\EcualizacionHistograma\\photo.jpg",Imgcodecs.CV_LOAD_IMAGE_COLOR);//se lee la imagen
			//Imgproc.getGaussianKernel(2, 1);

			m.convertTo(m, CvType.CV_8UC3);
			int size = (int) (m.total() * m.channels());
			double[] temp = new double[size];

			int columnas= m.cols();
			int filas = m.rows();


			double[][][] imageMatrix;
			imageMatrix = new double[3][filas][columnas];

			for(int g = 0;g<3;g++) {
				for(int i=0;i<filas;i++) {
					for(int j=0;j<columnas;j++){
						imageMatrix[g][i][j] = m.get(i,j)[g];
						//	System.out.println(img.get(i, j).length);

					}
				}

			}
			System.out.println("SizeRcvGetMatrix: "
				+ String.valueOf(imageMatrix.length) + "x"
				+ String.valueOf(imageMatrix[0].length) + "x"
				+ String.valueOf(imageMatrix[0][0].length) + "x");
			return imageMatrix;
		}

	// solo hace con matriz cuadrada
	// calcula el kernel con un tama�o y sigma dados
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


	public static double[][][] filter(int K, float sigma, double[][][] imageMatrix){
		double [][][] matresult = imageMatrix;
		for(int g = 0; g<3;g++) {

			double[][] U = imageMatrix[g];
			double[][] Ures = imageMatrix[g];


			double[][] gaussKernel = getKernel(K, sigma);

			//double[][] gaussKernel ={{0,0,3},{2,0,1},{2,0,0}};


			System.out.println("Matriz sin borde");
			for(int i=0; i < U.length; i++){			// Eliminar los bordes
				for(int j=0; j < U[0].length; j++){
					if((i == 0) | (i == U.length-1) | (j == 0) | (U[0].length-1 == 0)){
						U[i][j] = Ures[i][j] = 0;
					};
				}
			}

			System.out.println("Aplicando filtro...");
			int var = (int)Math.floor((K-1)/2);
			for(int x=var; x < U.length-var; x++){
				for(int y=var; y < U[0].length-var; y++){
					// System.out.print(U[x][y] + "\t");
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
		System.out.println("SizeRcvFilter: "
			+ String.valueOf(matresult.length) + "x"
			+ String.valueOf(matresult[0].length) + "x"
			+ String.valueOf(matresult[0][0].length) + "x");
		return matresult;
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
