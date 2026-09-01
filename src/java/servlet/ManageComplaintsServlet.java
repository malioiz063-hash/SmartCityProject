package servlet;

import db.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ManageComplaintsServlet")
public class ManageComplaintsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            HttpSession session = request.getSession(false);

            if(session == null){

                response.sendRedirect("adminLogin.html");
                return;
            }

            String department =
                    (String) session.getAttribute("department");
            

            // Department Mapping
            

            con = DBConnection.getConnection();
System.out.println(
"Query Department = " + department);
            ps = con.prepareStatement(
        "SELECT * FROM complaints WHERE department=? ORDER BY id DESC");

ps.setString(1, department);
            rs = ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Manage Complaints</title>");

            out.println("<style>");
            out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial,sans-serif;}");
            out.println("body{background:#f4f6f9;padding:25px;}");
            out.println("h1{text-align:center;margin-bottom:25px;color:#333;}");
            out.println(".container{background:white;padding:25px;border-radius:12px;box-shadow:0 0 15px rgba(0,0,0,0.15);}");
            out.println("table{width:100%;border-collapse:collapse;}");
            out.println("th{background:#4364f7;color:white;padding:12px;}");
            out.println("td{padding:10px;border:1px solid #ddd;text-align:center;}");
            out.println("tr:nth-child(even){background:#f8f9fa;}");
            out.println("tr:hover{background:#eef2ff;}");
            out.println(".high{color:red;font-weight:bold;}");
            out.println(".medium{color:orange;font-weight:bold;}");
            out.println(".low{color:green;font-weight:bold;}");
            out.println(".btn{padding:8px 15px;background:#4364f7;color:white;border:none;border-radius:5px;cursor:pointer;}");
            out.println(".btn:hover{background:#2f54d6;}");
            out.println("select{padding:6px;border-radius:5px;width:150px;}");
            out.println(".back{display:inline-block;margin-top:20px;padding:10px 20px;background:#333;color:white;text-decoration:none;border-radius:5px;}");
            out.println(".back:hover{background:#111;}");
            out.println(".desc{text-align:left;max-width:250px;}");
            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");

            out.println("<h1>" + department + " Complaints</h1>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Email</th>");
            out.println("<th>Title</th>");
            out.println("<th>Description</th>");
            out.println("<th>Department</th>");
            out.println("<th>Location</th>");
            out.println("<th>Priority</th>");
            out.println("<th>Status</th>");
            out.println("<th>Citizen Feedback</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            while(rs.next()) {

                int id = rs.getInt("id");

                String status = rs.getString("status");

                String priority = rs.getString("priority");

                if(priority == null || priority.trim().isEmpty()) {
                    priority = "Medium";
                }

                String feedback = rs.getString("feedback");

                String priorityClass = "low";

                if("High".equalsIgnoreCase(priority)) {
                    priorityClass = "high";
                }
                else if("Medium".equalsIgnoreCase(priority)) {
                    priorityClass = "medium";
                }

                out.println("<tr>");

                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("citizen_email") + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");

                out.println("<td class='desc'>"
                        + rs.getString("description")
                        + "</td>");

                out.println("<td>"
        + rs.getString("department")
        + "</td>");

                out.println("<td>"
                        + rs.getString("location")
                        + "</td>");

                out.println("<td class='" + priorityClass + "'>"
                        + priority
                        + "</td>");

                out.println("<td>" + status + "</td>");

                out.println("<td>"
                        + (feedback == null || feedback.trim().isEmpty()
                        ? "-"
                        : feedback)
                        + "</td>");

                out.println("<td>");

                out.println("<form action='UpdateComplaintStatusServlet' method='post'>");

                out.println("<input type='hidden' name='id' value='" + id + "'>");

                out.println("<select name='status'>");

                out.println("<option "
                        + ("Pending".equals(status) ? "selected" : "")
                        + ">Pending</option>");

                out.println("<option "
                        + ("In Progress".equals(status) ? "selected" : "")
                        + ">In Progress</option>");

                out.println("<option "
                        + ("Resolved".equals(status) ? "selected" : "")
                        + ">Resolved</option>");

                out.println("</select><br><br>");

                out.println("<input class='btn' type='submit' value='Update'>");

                out.println("</form>");

                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<a class='back' href='AdminDashboardServlet'>Back Dashboard</a>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

        }
        catch(Exception e){

            e.printStackTrace();

            out.println("<h2>Error : " + e.getMessage() + "</h2>");
        }
        finally{

            try{
                if(rs != null) rs.close();
            }catch(Exception e){}

            try{
                if(ps != null) ps.close();
            }catch(Exception e){}

            try{
                if(con != null) con.close();
            }catch(Exception e){}
        }
    }
}