package servlet;

import db.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ViewComplaintServlet")
public class ViewComplaintServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            HttpSession session =
                    request.getSession(false);

            if(session == null ||
               session.getAttribute("email") == null){

                response.sendRedirect("login.html");
                return;
            }

            String email =
                    (String) session.getAttribute("email");

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM complaints WHERE citizen_email=? ORDER BY id DESC";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1,email);

            ResultSet rs =
                    ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>My Complaints</title>");

            out.println("<style>");

            out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial,sans-serif;}");

            out.println("body{background:#f4f6f9;padding:25px;}");

            out.println("h2{text-align:center;margin-bottom:20px;}");

            out.println(".container{background:white;padding:20px;border-radius:10px;box-shadow:0 0 15px rgba(0,0,0,0.1);}");

            out.println("table{width:100%;border-collapse:collapse;}");

            out.println("th{background:#4364f7;color:white;padding:12px;}");

            out.println("td{padding:10px;border:1px solid #ddd;text-align:center;}");

            out.println("tr:nth-child(even){background:#f8f9fa;}");

            out.println(".btn{background:#4364f7;color:white;padding:8px 15px;border:none;border-radius:5px;cursor:pointer;}");

            out.println(".btn:hover{background:#2d4de0;}");

            out.println(".back{display:inline-block;margin-bottom:20px;padding:10px 20px;background:#333;color:white;text-decoration:none;border-radius:5px;}");

            out.println("textarea{width:180px;height:60px;padding:5px;}");

            out.println(".resolved{color:green;font-weight:bold;}");

            out.println(".pending{color:red;font-weight:bold;}");

            out.println(".progress{color:orange;font-weight:bold;}");

            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<a class='back' href='dashboard.html'>Back To Dashboard</a>");

            out.println("<div class='container'>");

            out.println("<h2>My Complaint Status</h2>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Title</th>");
            out.println("<th>Department</th>");
            out.println("<th>Location</th>");
            out.println("<th>Priority</th>");
            out.println("<th>Deadline</th>");
            out.println("<th>Status</th>");
            out.println("<th>Feedback</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            while(rs.next()){

                int id =
                        rs.getInt("id");

                String status =
                        rs.getString("status");

                String feedback =
                        rs.getString("feedback");

                String statusClass = "pending";

                if("Resolved".equalsIgnoreCase(status)){
                    statusClass = "resolved";
                }
                else if("In Progress".equalsIgnoreCase(status)){
                    statusClass = "progress";
                }

                out.println("<tr>");

                out.println("<td>"+id+"</td>");

                out.println("<td>"+rs.getString("title")+"</td>");

                out.println("<td>"+rs.getString("category")+"</td>");

                out.println("<td>"+rs.getString("location")+"</td>");

                out.println("<td>"+rs.getString("priority")+"</td>");

                out.println("<td>-</td>");

                out.println("<td class='"+statusClass+"'>"
                        +status+
                        "</td>");

                out.println("<td>");

                if("Resolved".equalsIgnoreCase(status)){

                    if(feedback == null ||
                            feedback.trim().isEmpty()){

                        out.println("<form action='CitizenFeedbackServlet' method='post'>");

                        out.println("<input type='hidden' name='id' value='"+id+"'>");

                        out.println("<textarea name='feedback' required></textarea><br><br>");

                        out.println("<input class='btn' type='submit' value='Submit Feedback'>");

                        out.println("</form>");

                    }else{

                        out.println(feedback);
                    }

                }else{

                    out.println("-");
                }

                out.println("</td>");

                out.println("<td>-</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

            rs.close();
            ps.close();
            con.close();

        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}