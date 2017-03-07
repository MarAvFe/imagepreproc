/*
 * 
 */
package paquete;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;
/**
 * La clase Ecualizaci�n.
 */
public class Ecualizaci�n {
	// Para solucionar los errores de que no exista el folder de src
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]){
		System.out.println("Calling method to print...");
		HistogramEqualizationColorGray("C:\\Users\\kimco\\workspace\\EcualizacionHistograma\\celulas.jpg");
	}
	
	public static void HistogramEqualizationColorGray(String imagesource) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = Imgcodecs.imread(imagesource);//se lee la imagen 
		System.out.println("Hola aca llegue");
		Imgproc.cvtColor(img, img,  Imgproc.COLOR_BGR2GRAY); //se pasa la imagen a escala de grises 
		// convertir en escala de grises y ecualizar histograma
		CLAHE clahe = Imgproc.createCLAHE(); //creamos el objeto de tipo clahe /
		clahe.apply(img, img); //aplicamos el acualizador con el metodo apply
		Imgcodecs.imwrite("celulas2new.png", img); // escribimos(creamos una nueva imagen con el clahe aplicado)
	}
	/**
	 * Prueba funcionamiento exitoso ecualizaci�n.
	 *
	 * @return void
	 */
	@Test
	public static void HistogramEqualizationTest(String imagesource) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = Imgcodecs.imread(imagesource);//se lee la imagen 
		System.out.println("Hola aca llegue");
		Imgproc.cvtColor(img, img,  Imgproc.COLOR_BGR2GRAY); //se pasa la imagen a escala de grises 
		// convertir en escala de grises y ecualizar histograma
		CLAHE clahe = Imgproc.createCLAHE(); //creamos el objeto de tipo clahe /
		clahe.apply(img, img); //aplicamos el acualizador con el metodo apply
		Imgcodecs.imwrite("celulas2new.png", img); // escribimos(creamos una nueva imagen con el clahe aplicado)
	}
}
