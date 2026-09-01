package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DepartmentPortalServlet")
public class DepartmentPortalServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Department Portal</title>");

        out.println("<style>");

        out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial,sans-serif;}");

        out.println("body{background:linear-gradient(135deg,#4364f7,#8f44ad);min-height:100vh;padding:30px;}");

        out.println("h1{text-align:center;color:white;margin-bottom:40px;}");

        out.println(".container{max-width:1200px;margin:auto;}");

        out.println(".cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(250px,1fr));gap:25px;}");

        out.println(".card{background:white;padding:35px;border-radius:15px;text-align:center;text-decoration:none;color:#333;font-size:22px;font-weight:bold;box-shadow:0 5px 15px rgba(0,0,0,0.2);transition:0.3s;}");

        out.println(".card:hover{background:#4364f7;color:white;transform:translateY(-5px);}");

        out.println(".back{display:block;width:250px;margin:40px auto 0 auto;padding:15px;background:#333;color:white;text-align:center;text-decoration:none;border-radius:10px;font-size:18px;}");

        out.println(".back:hover{background:#111;}");

        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");

        out.println("<h1>Government Department Portal</h1>");

        out.println("<div class='cards'>");

        out.println("<a class='card' href='DepartmentDashboardServlet?department=Health'>Health Department</a>");

        out.println("<a class='card' href='DepartmentDashboardServlet?department=Education'>Education Department</a>");

        out.println("<a class='card' href='DepartmentDashboardServlet?department=Transport'>Transport Department</a>");

        out.println("<a class='card' href='DepartmentDashboardServlet?department=Water'>Water Authority</a>");

        out.println("<a class='card' href='DepartmentDashboardServlet?department=Electricity'>Electricity Authority</a>");

        out.println("<a class='card' href='DepartmentDashboardServlet?department=Municipal'>Municipal Services</a>");

        out.println("<a class='card' href='DepartmentDashboardServlet?department=Emergency'>Emergency Services</a>");

        out.println("</div>");

        out.println("<a class='back' href='AdminDashboardServlet'>Back Dashboard</a>");

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}