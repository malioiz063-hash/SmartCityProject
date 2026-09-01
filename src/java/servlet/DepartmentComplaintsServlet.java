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

@WebServlet("/DepartmentComplaintsServlet")
public class DepartmentComplaintsServlet extends HttpServlet {

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

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM complaints WHERE category=? ORDER BY id DESC");

            ps.setString(1, department);

            ResultSet rs =
                    ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + department + " Department</title>");

            out.println("<style>");

            out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial,sans-serif;}");

            out.println("body{background:linear-gradient(135deg,#4364f7,#8f44ad);padding:30px;}");

            out.println(".container{max-width:1700px;margin:auto;background:white;padding:30px;border-radius:15px;box-shadow:0 0 20px rgba(0,0,0,0.2);}");

            out.println("h1{text-align:center;color:#333;margin-bottom:25px;}");

            out.println("table{width:100%;border-collapse:collapse;}");

            out.println("th{background:#4364f7;color:white;padding:12px;}");

            out.println("td{padding:10px;border:1px solid #ddd;text-align:center;}");

            out.println("tr:nth-child(even){background:#f8f9fa;}");

            out.println("tr:hover{background:#eef2ff;}");

            out.println("select{padding:6px;border-radius:5px;}");

            out.println("input[type=submit]{padding:8px 15px;background:#4364f7;color:white;border:none;border-radius:5px;cursor:pointer;}");

            out.println(".back{display:inline-block;margin-top:20px;padding:10px 20px;background:#333;color:white;text-decoration:none;border-radius:5px;}");

            out.println(".desc{text-align:left;max-width:250px;}");

            out.println(".priority-high{color:red;font-weight:bold;}");

            out.println(".priority-medium{color:orange;font-weight:bold;}");

            out.println(".priority-low{color:green;font-weight:bold;}");

            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");

            out.println("<h1>" + department + " Department Complaints</h1>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Email</th>");
            out.println("<th>Title</th>");
            out.println("<th>Description</th>");
            out.println("<th>Category</th>");
            out.println("<th>Location</th>");
            out.println("<th>Priority</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            boolean found = false;

            while (rs.next()) {

                found = true;

                int id = rs.getInt("id");

                String status =
                        rs.getString("status");

                String priority =
                        rs.getString("priority");

                String priorityClass;

                if ("High".equalsIgnoreCase(priority)) {
                    priorityClass = "priority-high";
                } else if ("Medium".equalsIgnoreCase(priority)) {
                    priorityClass = "priority-medium";
                } else {
                    priorityClass = "priority-low";
                }

                out.println("<tr>");

                out.println("<td>" + id + "</td>");

                out.println("<td>"
                        + rs.getString("citizen_email")
                        + "</td>");

                out.println("<td>"
                        + rs.getString("title")
                        + "</td>");

                out.println("<td class='desc'>"
                        + rs.getString("description")
                        + "</td>");

                out.println("<td>"
                        + rs.getString("category")
                        + "</td>");

                out.println("<td>"
                        + rs.getString("location")
                        + "</td>");

                out.println("<td class='" + priorityClass + "'>"
                        + priority
                        + "</td>");

                out.println("<td>"
                        + status
                        + "</td>");

                out.println("<td>");

                out.println("<form action='UpdateDepartmentComplaintServlet' method='post'>");

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

                out.println("<input type='submit' value='Update'>");

                out.println("</form>");

                out.println("</td>");

                out.println("</tr>");
            }

            if (!found) {

                out.println("<tr>");
                out.println("<td colspan='9'>No Complaints Found For "
                        + department
                        + " Department</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<a class='back' href='DepartmentPortalServlet'>Back</a>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            out.println("<h2>Error : "
                    + e.getMessage()
                    + "</h2>");
        }
    }
}