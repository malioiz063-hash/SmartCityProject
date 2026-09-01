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

@WebServlet("/ManageServicesServlet")
public class ManageServicesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {

            HttpSession session = request.getSession(false);

            if(session == null){
                response.sendRedirect("adminLogin.html");
                return;
            }

            String department =
                    (String) session.getAttribute("department");

            con = DBConnection.getConnection();

            ps = con.prepareStatement(
    "SELECT * FROM service_requests WHERE service_type=? ORDER BY id DESC"
);

ps.setString(1, department);

            rs = ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Manage Services</title>");

            out.println("<style>");

            out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Segoe UI',sans-serif;}");

            out.println("body{background:#f4f7fc;padding:30px;}");

            out.println(".container{max-width:1400px;margin:auto;background:white;padding:30px;border-radius:15px;box-shadow:0 4px 15px rgba(0,0,0,.08);}");

            out.println("h2{margin-bottom:20px;color:#0f172a;}");

            out.println(".dept{margin-bottom:20px;font-size:18px;color:#2563eb;font-weight:bold;}");

            out.println("table{width:100%;border-collapse:collapse;}");

            out.println("th{background:#2563eb;color:white;padding:14px;}");

            out.println("td{padding:12px;border:1px solid #e5e7eb;text-align:center;}");

            out.println("tr:nth-child(even){background:#f8fafc;}");

            out.println("select{padding:6px;border-radius:6px;}");

            out.println("input[type=submit]{background:#2563eb;color:white;border:none;padding:7px 12px;border-radius:6px;cursor:pointer;}");

            out.println("input[type=submit]:hover{background:#1d4ed8;}");

            out.println(".back-btn{display:inline-block;margin-top:20px;padding:12px 20px;background:#2563eb;color:white;text-decoration:none;border-radius:8px;}");

            out.println(".back-btn:hover{background:#1d4ed8;}");

            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");

            out.println("<h2>Manage Services</h2>");

            out.println("<div class='dept'>Department: "
                    + department +
                    "</div>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Email</th>");
            out.println("<th>Department</th>");
            out.println("<th>Service Type</th>");
            out.println("<th>Description</th>");
            out.println("<th>Location</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            while(rs.next()) {

                int id = rs.getInt("id");

                String currentStatus =
                        rs.getString("status");

                out.println("<tr>");

                out.println("<td>"+id+"</td>");
                out.println("<td>"+rs.getString("citizen_email")+"</td>");
                out.println("<td>"+rs.getString("department")+"</td>");
                out.println("<td>"+rs.getString("service_type")+"</td>");
                out.println("<td>"+rs.getString("description")+"</td>");
                out.println("<td>"+rs.getString("location")+"</td>");
                out.println("<td>"+currentStatus+"</td>");

                out.println("<td>");

                out.println("<form action='UpdateServiceStatusServlet' method='post'>");

                out.println("<input type='hidden' name='id' value='"+id+"'>");

                out.println("<select name='status'>");

                out.println("<option "
                        + ("Pending".equals(currentStatus) ? "selected" : "")
                        + ">Pending</option>");

                out.println("<option "
                        + ("Processing".equals(currentStatus) ? "selected" : "")
                        + ">Processing</option>");

                out.println("<option "
                        + ("Completed".equals(currentStatus) ? "selected" : "")
                        + ">Completed</option>");

                out.println("</select>");

                out.println(" ");

                out.println("<input type='submit' value='Update'>");

                out.println("</form>");

                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<a class='back-btn' href='AdminDashboardServlet'>Back Dashboard</a>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

        }
        catch(Exception e){

            e.printStackTrace();

            out.println("<h3>Error : "
                    + e.getMessage()
                    + "</h3>");
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