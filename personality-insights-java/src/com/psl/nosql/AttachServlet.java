package com.psl.nosql;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.psl.speechtext.SpeechText;

@WebServlet("/attach")
@MultipartConfig()   
public class AttachServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static int i ;

    

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileName="";
		String filePath = "";
		String contentType="";
		i++;
		OutputStream outputStream = null;
		outputStream = 
                new FileOutputStream(new File("file"+i+"soundfile.wav"));
		
		
		String line;

		for (Part part : request.getParts()) {
			fileName = extractFileName(part);
			// part.write(SAVE_DIR + "/" +fileName);
			InputStream is = part.getInputStream();		
			try {
				
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = is.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				System.out.println("Done!");

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (outputStream != null) {
					try {
						// outputStream.flush();
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
			

		}
		
		File audiofile = new File("file"+i+"soundfile.wav");		
		System.out.println("Uploaded file name :" + audiofile.getName());
		System.out.println("Uploaded file Size :" + audiofile.length());
	
		String textResponse = SpeechText.getText(audiofile);
		/*response.getWriter().write(textResponse);
		response.getWriter().flush();*/
		
		request.setAttribute("message", textResponse);
        getServletContext().getRequestDispatcher("/index.jsp").forward(
                request, response);
		
		
	}
	
	
	private String extractFileName(Part part) {
	    String contentDisp = part.getHeader("content-disposition");
	    String[] items = contentDisp.split(";");
	    for (String s : items) {
	        if (s.trim().startsWith("filename")) {
	            return s.substring(s.indexOf("=") + 2, s.length()-1);
	        }
	    }
	    return "";
	}
	
	
	/*static String readFile(String path, String encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}*/
	

}
