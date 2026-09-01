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

@WebServlet("/ViewEmergencyServlet")
public class ViewEmergencyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();
            st = con.createStatement();

            int totalReports = 0;
            int pendingReports = 0;
            int resolvedReports = 0;

            ResultSet totalRs =
                    st.executeQuery(
                            "SELECT COUNT(*) total FROM emergency_reports");

            if (totalRs.next()) {
                totalReports = totalRs.getInt("total");
            }

            ResultSet pendingRs =
                    st.executeQuery(
                            "SELECT COUNT(*) total FROM emergency_reports WHERE status='Pending'");

            if (pendingRs.next()) {
                pendingReports = pendingRs.getInt("total");
            }

            ResultSet resolvedRs =
                    st.executeQuery(
                            "SELECT COUNT(*) total FROM emergency_reports WHERE status='Resolved'");

            if (resolvedRs.next()) {
                resolvedReports = resolvedRs.getInt("total");
            }

            rs = st.executeQuery(
                    "SELECT * FROM emergency_reports ORDER BY id DESC");

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Emergency Response Management</title>");

            out.println("<style>");

            out.println("body{font-family:Arial;background:#f4f4f4;padding:30px;}");

            out.println("h2{text-align:center;color:#333;}");

            out.println(".stats{text-align:center;margin-bottom:30px;}");

            out.println(".card{display:inline-block;width:250px;padding:20px;margin:10px;background:white;border-radius:10px;box-shadow:0 0 10px #ccc;}");

            out.println(".total{border-top:6px solid #007bff;}");
            out.println(".pending{border-top:6px solid #ffc107;}");
            out.println(".resolved{border-top:6px solid #28a745;}");

            out.println("table{width:100%;border-collapse:collapse;background:white;}");

            out.println("th,td{padding:12px;border:1px solid #ddd;text-align:center;}");

            out.println("th{background:#dc3545;color:white;}");

            out.println(".updateBtn{background:#28a745;color:white;padding:6px 12px;border-radius:5px;text-decoration:none;}");
out.println(".deleteBtn{background:#dc3545;color:white;padding:6px 12px;border-radius:5px;text-decoration:none;}");

            out.println(".btn{background:#28a745;color:white;padding:10px 15px;text-decoration:none;border-radius:5px;}");

            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<h2>Emergency Response Management</h2>");

            out.println("<div class='stats'>");

            out.println("<div class='card total'>");
            out.println("<h3>Total Reports</h3>");
            out.println("<h1>" + totalReports + "</h1>");
            out.println("</div>");

            out.println("<div class='card pending'>");
            out.println("<h3>Pending Reports</h3>");
            out.println("<h1>" + pendingReports + "</h1>");
            out.println("</div>");

            out.println("<div class='card resolved'>");
            out.println("<h3>Resolved Reports</h3>");
            out.println("<h1>" + resolvedReports + "</h1>");
            out.println("</div>");

            out.println("</div>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Incident Type</th>");
            out.println("<th>Location</th>");
            out.println("<th>Description</th>");
            out.println("<th>Severity</th>");
            out.println("<th>Status</th>");
            
            out.println("<th>Action</th>");
            out.println("</tr>");

            while (rs.next()) {

                int id = rs.getInt("id");

                out.println("<tr>");

                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("incident_type") + "</td>");
                out.println("<td>" + rs.getString("location") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("<td>" + rs.getString("severity") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");

                String status = rs.getString("status");

out.println("<td>");

if ("Pending".equalsIgnoreCase(status)) {

    out.println(
            "<a class='updateBtn' href='UpdateEmergencyStatusServlet?id="
                    + id
                    + "'>Resolve</a>"
    );

    out.println(" | ");

    out.println(
            "<a class='deleteBtn' href='DeleteEmergencyServlet?id="
                    + id
                    + "' onclick=\"return confirm('Delete this report?')\">Delete</a>"
    );

} else {

    out.println("<span style='color:green;font-weight:bold;'>Resolved</span>");

    out.println(" | ");

    out.println(
            "<a class='deleteBtn' href='DeleteEmergencyServlet?id="
                    + id
                    + "' onclick=\"return confirm('Delete this report?')\">Delete</a>"
    );
}

out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");

            out.println("<center>");
            out.println("<a class='btn' href='AdminDashboardServlet'>Back Dashboard</a>");
            out.println("</center>");

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<h3>Error : " + e.getMessage() + "</h3>");

        } finally {

            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }

            try {
                if (st != null) st.close();
            } catch (Exception e) {
            }

            try {
                if (con != null) con.close();
            } catch (Exception e) {
            }
        }
    }
}