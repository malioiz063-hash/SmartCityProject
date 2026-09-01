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
import jakarta.servlet.http.HttpSession;

@WebServlet("/MyServicesServlet")
public class MyServicesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("email") == null) {

                response.sendRedirect("login.html");
                return;
            }

            String email =
                    (String) session.getAttribute("email");

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM service_requests WHERE citizen_email=? ORDER BY id DESC");

            ps.setString(1, email);

            ResultSet rs =
                    ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>My Services</title>");

            out.println("<style>");

out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Segoe UI',sans-serif;}");

out.println("body{background:#f4f7fc;min-height:100vh;padding:40px;}");

out.println(".container{max-width:1400px;margin:auto;background:white;border-radius:20px;padding:35px;box-shadow:0 10px 30px rgba(0,0,0,.08);}");

out.println(".top-bar{margin-bottom:20px;}");

out.println(".back-btn{display:inline-block;padding:12px 22px;background:#2563eb;color:white;text-decoration:none;border-radius:10px;font-weight:600;transition:.3s;}");

out.println(".back-btn:hover{background:#1d4ed8;}");

out.println("h2{text-align:center;color:#111827;font-size:34px;margin-bottom:30px;}");

out.println(".table-wrapper{overflow-x:auto;}");

out.println("table{width:100%;border-collapse:collapse;}");

out.println("th{background:#2563eb;color:white;padding:16px;font-size:15px;}");

out.println("td{padding:14px;border-bottom:1px solid #e5e7eb;text-align:center;color:#374151;}");

out.println("tr:hover{background:#f8fafc;}");

out.println(".pending{background:#fef3c7;color:#92400e;padding:8px 14px;border-radius:20px;font-weight:600;}");

out.println(".progress{background:#dbeafe;color:#1d4ed8;padding:8px 14px;border-radius:20px;font-weight:600;}");

out.println(".completed{background:#dcfce7;color:#166534;padding:8px 14px;border-radius:20px;font-weight:600;}");

out.println(".empty{padding:25px;font-size:18px;color:#6b7280;text-align:center;}");

out.println("@media(max-width:768px){");

out.println("h2{font-size:28px;}");

out.println("th,td{padding:10px;font-size:13px;}");

out.println("}");

out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");

out.println("<div class='top-bar'>");
out.println("<a href='CitizenDashboardServlet' class='back-btn'>← Back To Dashboard</a>");
out.println("</div>");

out.println("<h2>My Service Requests</h2>");

out.println("<div class='table-wrapper'>");

out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Email</th>");
            out.println("<th>Service</th>");
            out.println("<th>Description</th>");
            out.println("<th>Location</th>");
            out.println("<th>Status</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            boolean found = false;

            while (rs.next()) {

                found = true;

                out.println("<tr>");

                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("citizen_email") + "</td>");
                out.println("<td>" + rs.getString("service_type") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("<td>" + rs.getString("location") + "</td>");
                String status = rs.getString("status");

if(status.equalsIgnoreCase("Pending")){

    out.println("<td><span class='pending'>Pending</span></td>");

}
else if(status.equalsIgnoreCase("In Progress")){

    out.println("<td><span class='progress'>In Progress</span></td>");

}
else{

    out.println("<td><span class='completed'>Completed</span></td>");

}
                out.println("<td>" + rs.getString("created_at") + "</td>");

                out.println("</tr>");
            }

            if (!found) {

                out.println("<tr>");
out.println("<td colspan='7' class='empty'>No Service Requests Found</td>");
out.println("</tr>");
            }

            out.println("</table>");
out.println("</div>");
out.println("</div>");

            out.println("</body>");
            out.println("</html>");

            rs.close();
            ps.close();
            con.close();

        }
        catch (Exception e) {

            out.println("<h3>Error : " + e.getMessage() + "</h3>");
        }
    }
}