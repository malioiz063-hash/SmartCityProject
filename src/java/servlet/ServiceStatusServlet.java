package servlet;

import db.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ServiceStatusServlet")
public class ServiceStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                    "SELECT id, service_type, status, created_at FROM service_requests ORDER BY id DESC");

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Service Status</title>");

            out.println("<style>");
            out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial;}");
            out.println("body{background:linear-gradient(135deg,#28a745,#20c997);padding:30px;}");
            out.println(".container{background:white;padding:25px;border-radius:15px;max-width:1000px;margin:auto;box-shadow:0 5px 20px rgba(0,0,0,.2);}");
            out.println("h2{text-align:center;margin-bottom:20px;color:#333;}");
            out.println("table{width:100%;border-collapse:collapse;}");
            out.println("th,td{padding:12px;border:1px solid #ddd;text-align:center;}");
            out.println("th{background:#28a745;color:white;}");
            out.println("tr:nth-child(even){background:#f9f9f9;}");
            out.println(".back{display:inline-block;margin-top:20px;padding:10px 20px;background:#28a745;color:white;text-decoration:none;border-radius:5px;}");
            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");

            out.println("<h2>Service Request Status</h2>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Service Type</th>");
            out.println("<th>Status</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            while (rs.next()) {

                out.println("<tr>");

                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("service_type") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");
                out.println("<td>" + rs.getString("created_at") + "</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<a class='back' href='dashboard.html'>Back Dashboard</a>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {

            out.println("<h3>" + e.getMessage() + "</h3>");
        }
    }
}