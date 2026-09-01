package servlet;

import db.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DepartmentDashboardServlet")
public class DepartmentDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        String department =
                request.getParameter("department");

        try {

            Connection con =
                    DBConnection.getConnection();

            // Total Complaints

            PreparedStatement ps1 =
                    con.prepareStatement(
                            "SELECT COUNT(*) FROM complaints WHERE category=?");

            ps1.setString(1, department);

            ResultSet rs1 = ps1.executeQuery();

            rs1.next();

            int total = rs1.getInt(1);

            // Pending

            PreparedStatement ps2 =
                    con.prepareStatement(
                            "SELECT COUNT(*) FROM complaints WHERE category=? AND status='Pending'");

            ps2.setString(1, department);

            ResultSet rs2 = ps2.executeQuery();

            rs2.next();

            int pending = rs2.getInt(1);

            // In Progress

            PreparedStatement ps3 =
                    con.prepareStatement(
                            "SELECT COUNT(*) FROM complaints WHERE category=? AND status='In Progress'");

            ps3.setString(1, department);

            ResultSet rs3 = ps3.executeQuery();

            rs3.next();

            int progress = rs3.getInt(1);

            // Resolved

            PreparedStatement ps4 =
                    con.prepareStatement(
                            "SELECT COUNT(*) FROM complaints WHERE category=? AND status='Resolved'");

            ps4.setString(1, department);

            ResultSet rs4 = ps4.executeQuery();

            rs4.next();

            int resolved = rs4.getInt(1);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>"+department+" Dashboard</title>");

            out.println("<style>");

            out.println("body{font-family:Arial;background:linear-gradient(135deg,#4364f7,#8f44ad);padding:30px;}");

            out.println("h1{text-align:center;color:white;margin-bottom:30px;}");

            out.println(".cards{display:flex;justify-content:center;gap:20px;flex-wrap:wrap;}");

            out.println(".card{background:white;width:220px;padding:25px;border-radius:15px;text-align:center;box-shadow:0 4px 15px rgba(0,0,0,0.2);}");

            out.println(".card h2{color:#4364f7;margin-bottom:10px;}");

            out.println(".card p{font-size:30px;font-weight:bold;}");

            out.println(".btn{display:block;width:250px;margin:40px auto;padding:15px;background:#28a745;color:white;text-align:center;text-decoration:none;border-radius:10px;font-size:18px;}");

            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<h1>"+department+" Department Dashboard</h1>");

            out.println("<div class='cards'>");

            out.println("<div class='card'>");
            out.println("<h2>Total</h2>");
            out.println("<p>"+total+"</p>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h2>Pending</h2>");
            out.println("<p>"+pending+"</p>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h2>In Progress</h2>");
            out.println("<p>"+progress+"</p>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h2>Resolved</h2>");
            out.println("<p>"+resolved+"</p>");
            out.println("</div>");

            out.println("</div>");

            out.println("<a class='btn' href='DepartmentComplaintsServlet?department="+department+"'>Manage Complaints</a>");

            out.println("<a class='btn' href='DepartmentPortalServlet'>Back To Departments</a>");

            out.println("</body>");
            out.println("</html>");

            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();

            ps1.close();
            ps2.close();
            ps3.close();
            ps4.close();

            con.close();

        } catch (Exception e) {

            out.println("<h2>Error : "+e.getMessage()+"</h2>");
        }
    }
}