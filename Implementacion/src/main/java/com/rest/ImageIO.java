package rest;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import sun.applet.Main;
import sun.misc.BASE64Encoder;

// mvn install:install-file -Dfile=C:\opencv\build\java\opencv-320.jar -DgroupId=parma.org -DartifactId=opencv -Dversion=3.2.0 -Dpackaging=jar

@Path("ImageIO")
public class ImageIO {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		String output = "Jersey say : " + msg;
		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/echoBase64")
	public Response echoBase64(@FormParam("imgStr") String imgCode, @FormParam("imgTitle") String imgTitle) {
		String output = "<h3>Image title:</h3> " + imgTitle + "<br><h3>Image Code:</h3> " + imgCode;
		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/processImage")
	public Response processImage(
			@FormParam("imgStr") String imgCode,
			@FormParam("algorithmType") int algType,
			@FormParam("sigmaGauss") int sigma,
			@FormParam("sigmaBilateral") int sigmaB,
			@FormParam("kernelSize") int kSize,
			@FormParam("PicoSenal") double picoSenal,
			@FormParam("imgTitle") String imgTitle) {
		String[] imgs = imgCode.split("0o0o0o0");
		String output = "<html><body>";
		for (int i = 0; i < imgs.length; i++){
			String imgType = imgs[i].split(",")[0].split("/")[1].split(";")[0];
			String base64Image = imgs[i].split(",")[1];
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
			String result = "data:image/" + imgType + ";base64,";
			BufferedImage img = null;
			try{
				nu.pattern.OpenCV.loadShared();
			} catch (Exception f) {
				System.out.println("ERR320 Classloader: " + this.getClass().getClassLoader());
				try{
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				} catch (Exception g) {
					System.out.println("ERRNAT Classloader: " + this.getClass().getClassLoader());
				}
			}
			try {
				//System.loadLibrary("opencv_java320");
				img = javax.imageio.ImageIO.read(new ByteArrayInputStream(imageBytes));

				// Trabajar con la imagen
				BufferedImage bridge = rest.FiltroGeneralizado.principal(img, kSize, sigma);
				//double pico;
				switch(algType){
					case 0:
						bridge = rest.FiltroGeneralizado.principal(img, kSize, sigma);
						break;
					case 1:
						bridge = rest.FiltroGeneralizado.Bilateral(img,kSize,sigma,sigmaB);
						break;
					
					default:
						break;
				}
				
				picoSenal = rest.FiltroGeneralizado.PicoSeñal(img, bridge);
				result += encodeToString(bridge, imgType);
			} catch (IOException e) {
				e.printStackTrace();
			}
			output +=
					"<h3>Image title:</h3> " + imgTitle
					+ "<br><h3>Pico senal antes/despues:</h3> " + picoSenal 
					+ "<br><h3>Image Type:</h3> " + imgType
					+ "<br><h3>Processed img:</h3> <a download='img" + result.substring(result.length()-10) + "." + imgType + "' href='" + result + "' title='img" + result.substring(result.length()-10) + "'><img src=\"" + result + "\" /></a><hr>";
					//"<br><h3>Recieved img:</h3> <img src=\"" + imgCode + "\" />" +
				//"<br><h3>Correct DecodeEncode:</h3> " + String.valueOf(imgCode.equals(result)) +
				//"<br><h3>DecodeEncode Result:</h3> " + result +
				//"<br><h3>Image Only:</h3> " + base64Image +
				// + "<br><h3>Received Image Code:</h3> " + imgs.length
				// + "<br><h3>Received Image Code:</h3> " + imgCode ;
			//showImage(img);
		}
		output += "</html></body>";
		return Response.status(200).entity(output).build();
	}

	/**
     * Encode image to Base64 string
     * @param image The image to Base64 encode
     * @param type jpeg, bmp, ...
     * @return Base64 encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            javax.imageio.ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public void showImage(BufferedImage img){
    	JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.pack();
        frame.setVisible(true);
    }
   }
