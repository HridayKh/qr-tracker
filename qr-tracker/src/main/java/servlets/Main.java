package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hello World!</title>");
		out.println("</head>");
		out.println("<body>");
		out.println(
				"<img src=\"data:image/png;base64," + main.QR.makeStatic("test", 3, 0xFFFFFF, 0x000000, 10, 40) + "\">");
		out.println("</body>");
		out.println("</html>");

		String url = "jdbc:mysql://db.hriday.tech:3306/QR_DB";
		String user = System.getenv("REMOTE_TOMCAT_QR_DB_USER");
		out.println(user);
		String password = System.getenv("REMOTE_TOMCAT_QR_DB_PASSWORD");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			conn.close();
			System.out.println("Connected to the database!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
