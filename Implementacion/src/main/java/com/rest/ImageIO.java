package main.java.com.rest;

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

import sun.misc.BASE64Encoder;

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
			@FormParam("imgTitle") String imgTitle) {
		String imgType = imgCode.split(",")[0].split("/")[1].split(";")[0];
		String base64Image = imgCode.split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		String result = "data:image/" + imgType + ";base64,";
		BufferedImage img = null;
		try {
			img = javax.imageio.ImageIO.read(new ByteArrayInputStream(imageBytes));

			// Trabajar con la imagen
			
			result += encodeToString(img, imgType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String output = "<html><body>" + 
				"<h3>Image title:</h3> " + imgTitle + 
				"<br><h3>Image Type:</h3> " + imgType +  
				"<br><h3>Processed img:</h3> <img src=\"" + result + "\" />" +
				/*"<br><h3>Recieved img:</h3> <img src=\"" + imgCode + "\" />" +
				"<br><h3>Correct DecodeEncode:</h3> " + String.valueOf(imgCode.equals(result)) + 
				"<br><h3>DecodeEncode Result:</h3> " + result + 
				"<br><h3>Image Only:</h3> " + base64Image + 
				"<br><h3>Received Image Code:</h3> " + imgCode + */
				"</html></body>";
		// showImage(img);
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
    
    /*public Mat bufToMat(BufferedImage img){
    	Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
    	byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
    	mat.put(0, 0, pixels);
    	return mat;
    }*/
}
