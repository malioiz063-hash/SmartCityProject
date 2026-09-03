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

@WebServlet("/MyComplaintsServlet")
public class MyComplaintsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            HttpSession session = request.getSession(false);

            if(session == null ||
               session.getAttribute("email") == null){

                response.sendRedirect("login.html");
                return;
            }

            String email =
                    (String) session.getAttribute("email");

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM complaints WHERE citizen_email=? ORDER BY id DESC");

            ps.setString(1, email);

            ResultSet rs =
                    ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>My Complaints</title>");

            out.println("<style>");

out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Segoe UI',sans-serif;}");

out.println("body{background:#f4f7fc;min-height:100vh;padding:40px;}");

out.println(".container{max-width:1400px;margin:auto;background:white;border-radius:20px;padding:35px;box-shadow:0 10px 30px rgba(0,0,0,.08);}");

out.println("h2{text-align:center;color:#111827;font-size:34px;margin-bottom:30px;font-weight:700;}");

out.println(".top-bar{display:flex;justify-content:space-between;align-items:center;margin-bottom:25px;}");

out.println(".back-btn{background:#2563eb;color:white;text-decoration:none;padding:12px 22px;border-radius:10px;font-weight:600;transition:.3s;}");

out.println(".back-btn:hover{background:#1d4ed8;}");

out.println(".table-wrapper{overflow-x:auto;border-radius:15px;}");

out.println("table{width:100%;border-collapse:collapse;background:white;}");

out.println("th{background:#2563eb;color:white;padding:16px;text-align:center;font-size:15px;}");

out.println("td{padding:14px;border-bottom:1px solid #e5e7eb;text-align:center;color:#374151;}");

out.println("tr:hover{background:#f8fafc;}");

out.println(".pending{background:#fef3c7;color:#92400e;padding:8px 14px;border-radius:20px;font-weight:bold;}");

out.println(".progress{background:#dbeafe;color:#1d4ed8;padding:8px 14px;border-radius:20px;font-weight:bold;}");

out.println(".resolved{background:#dcfce7;color:#166534;padding:8px 14px;border-radius:20px;font-weight:bold;}");

out.println("textarea{width:220px;height:80px;padding:10px;border:1px solid #d1d5db;border-radius:10px;resize:none;}");

out.println(".btn{background:#22c55e;color:white;border:none;padding:10px 18px;border-radius:8px;font-weight:600;cursor:pointer;}");

out.println(".btn:hover{background:#16a34a;}");

out.println("@media(max-width:768px){");
out.println("h2{font-size:26px;}");
out.println("th,td{font-size:13px;padding:10px;}");
out.println("textarea{width:100%;}");
out.println("}");

out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");

            out.println("<h2>My Complaints</h2>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Title</th>");
            out.println("<th>Category</th>");
            out.println("<th>Location</th>");
            out.println("<th>Status</th>");
            
            out.println("<th>Feedback</th>");
            out.println("</tr>");

            while(rs.next()) {

                int id = rs.getInt("id");

                String status =
                        rs.getString("status");

                String feedback =
                        rs.getString("feedback");

                out.println("<tr>");

                out.println("<td>"+id+"</td>");
                out.println("<td>"+rs.getString("title")+"</td>");
                out.println("<td>"+rs.getString("category")+"</td>");
                out.println("<td>"+rs.getString("location")+"</td>");

                if("Pending".equalsIgnoreCase(status)) {

                    out.println("<td><span class='pending'>Pending</span></td>");

                } else if("In Progress".equalsIgnoreCase(status)) {

                    out.println("<td><span class='progress'>In Progress</span></td>");

                } else {

                    out.println("<td><span class='resolved'>Resolved</span></td>");
                }

                

                out.println("<td>");

                if("Resolved".equalsIgnoreCase(status)) {

                    if(feedback == null || feedback.trim().isEmpty()) {

                        out.println("<form action='CitizenFeedbackServlet' method='post'>");

                        out.println("<input type='hidden' name='id' value='"+id+"'>");

                        out.println("<textarea name='feedback' required></textarea><br><br>");

                        out.println("<input class='btn' type='submit' value='Submit Feedback'>");

                        out.println("</form>");

                    } else {

                        out.println(feedback);
                    }

                } else {

                    out.println("-");
                }

                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<div class='top-bar'>");
out.println("<a href='CitizenDashboardServlet' class='back-btn'>← Dashboard</a>");
out.println("</div>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

            rs.close();
            ps.close();
            con.close();

        }
        catch(Exception e){

            out.println("<h3>Error : "+e.getMessage()+"</h3>");
        }
    }
}