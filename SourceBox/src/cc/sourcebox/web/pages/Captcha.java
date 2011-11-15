package cc.sourcebox.web.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.sourcebox.beans.UtilsBeanRemote;

@WebServlet(urlPatterns = "/captcha.png")
public class Captcha extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8032979343067982167L;

	int _width = 150;
	int _height = 50;
	
	@EJB(mappedName="SourceBoxLogicEAR/UtilsBean/remote")
	private UtilsBeanRemote utils;
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String randomString = utils.getRandomString(6);
		/*Random r = new Random();
		
		String randomString="";
		
		do {
			randomString += "s";//urlHelper.idToAlias(r.nextInt(62));
		} while (randomString.length()<6);*/

		BufferedImage bufferedImage = new BufferedImage(_width, _height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = bufferedImage.createGraphics();

		Font font = new Font("Georgia", Font.BOLD, 18);
		g2d.setFont(font);

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);

		/*GradientPaint gp = new GradientPaint(0, 0, Color.white, 0, height / 2,
				Color.black, true);

		g2d.setPaint(gp);*/
		g2d.fillRect(0, 0, _width, _height);

		g2d.setColor(new Color(0, 0, 0));

		Random r = new Random();
		/*int index = Math.abs(r.nextInt()) % 5;*/

		//String captcha = randomString;
		request.getSession().setAttribute("captcha", randomString);

		int x = 0;
		int y = 0;
		
		char [] data = randomString.toCharArray();

		for (int i = 0; i < data.length; i++) {
			x += 10 + (Math.abs(r.nextInt()) % 15);
			y = 20 + Math.abs(r.nextInt()) % 20;
			g2d.drawChars(data, i, 1, x, y);
		}


		g2d.dispose();

		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		ImageIO.write(bufferedImage, "png", os);
		os.close();
	}

	
	
	
	
	
	

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
