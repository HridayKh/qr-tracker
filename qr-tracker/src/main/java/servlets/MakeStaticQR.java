package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MakeQR")
public class MakeStaticQR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Get parameters from request
		String data = request.getParameter("data"); // QR content
		int errorLevel = parseIntOrDefault(request.getParameter("error"), 2);
		int bgColor = parseHexOrDefault(request.getParameter("bg"), 0xFFFFFF); // Background color
		int fgColor = parseHexOrDefault(request.getParameter("fg"), 0x000000); // Foreground color
		int margin = parseIntOrDefault(request.getParameter("margin"), 10);
		int size = parseIntOrDefault(request.getParameter("size"), 40);

		// Fallback if data is null
		if (data == null || data.isEmpty()) {
			data = "value not given";
		}

		String qrImage = main.QR.makeStatic(data, errorLevel, bgColor, fgColor, margin, size);

		out.println(qrImage);
	}

	private int parseIntOrDefault(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException | NullPointerException e) {
			return defaultValue;
		}
	}

	private int parseHexOrDefault(String value, int defaultValue) {
		try {
			return Integer.parseInt(value.replaceFirst("^#?", ""), 16);
		} catch (NumberFormatException | NullPointerException e) {
			return defaultValue;
		}
	}
}
