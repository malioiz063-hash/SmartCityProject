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

@WebServlet("/GISDashboardServlet")
public class GISDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        Connection con = null;
        Statement st = null;

        try {

            con = DBConnection.getConnection();
            st = con.createStatement();

            int complaints = 0;
            int assets = 0;
            int emergencies = 0;
            int requests = 0;

            ResultSet rs;

            rs = st.executeQuery("SELECT COUNT(*) total FROM complaints");
            if (rs.next()) complaints = rs.getInt("total");

            rs = st.executeQuery("SELECT COUNT(*) total FROM public_assets");
            if (rs.next()) assets = rs.getInt("total");

            rs = st.executeQuery("SELECT COUNT(*) total FROM emergency_reports");
            if (rs.next()) emergencies = rs.getInt("total");

            rs = st.executeQuery("SELECT COUNT(*) total FROM service_requests");
            if (rs.next()) requests = rs.getInt("total");

            out.println("<html>");
            out.println("<head>");
            out.println("<title>GIS Dashboard</title>");

            out.println("<style>");

            out.println("body{");
            out.println("margin:0;");
            out.println("padding:20px;");
            out.println("font-family:Arial,sans-serif;");
            out.println("background:#eef2f7;");
            out.println("}");

            out.println("h1{");
            out.println("text-align:center;");
            out.println("color:#2c3e50;");
            out.println("margin-bottom:30px;");
            out.println("}");

            out.println(".stats{");
            out.println("display:flex;");
            out.println("flex-wrap:wrap;");
            out.println("justify-content:center;");
            out.println("gap:20px;");
            out.println("margin-bottom:30px;");
            out.println("}");

            out.println(".stat-card{");
            out.println("width:220px;");
            out.println("background:white;");
            out.println("padding:20px;");
            out.println("border-radius:12px;");
            out.println("box-shadow:0 4px 10px rgba(0,0,0,0.1);");
            out.println("text-align:center;");
            out.println("}");

            out.println(".stat-card h2{");
            out.println("margin:0;");
            out.println("font-size:35px;");
            out.println("color:#007bff;");
            out.println("}");

            out.println(".stat-card p{");
            out.println("margin-top:10px;");
            out.println("font-size:16px;");
            out.println("font-weight:bold;");
            out.println("}");

            out.println(".container{");
            out.println("display:flex;");
            out.println("flex-wrap:wrap;");
            out.println("justify-content:center;");
            out.println("gap:25px;");
            out.println("}");

            out.println(".card{");
            out.println("width:650px;");
            out.println("background:white;");
            out.println("padding:20px;");
            out.println("border-radius:12px;");
            out.println("box-shadow:0 4px 10px rgba(0,0,0,0.1);");
            out.println("}");

            out.println(".card h2{");
            out.println("margin-top:0;");
            out.println("color:#007bff;");
            out.println("}");

            out.println("table{");
            out.println("width:100%;");
            out.println("border-collapse:collapse;");
            out.println("}");

            out.println("th{");
            out.println("background:#007bff;");
            out.println("color:white;");
            out.println("padding:10px;");
            out.println("}");

            out.println("td{");
            out.println("padding:10px;");
            out.println("border-bottom:1px solid #ddd;");
            out.println("}");

            out.println("tr:hover{");
            out.println("background:#f5f5f5;");
            out.println("}");

            out.println(".pending{color:orange;font-weight:bold;}");
            out.println(".completed{color:green;font-weight:bold;}");
            out.println(".resolved{color:green;font-weight:bold;}");
            out.println(".critical{color:red;font-weight:bold;}");
            out.println(".high{color:#ff6600;font-weight:bold;}");
            out.println(".good{color:green;font-weight:bold;}");

            out.println(".btn{");
            out.println("background:#007bff;");
            out.println("color:white;");
            out.println("padding:12px 25px;");
            out.println("text-decoration:none;");
            out.println("border-radius:6px;");
            out.println("}");

            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>SMART CITY GIS DASHBOARD</h1>");

            out.println("<div class='stats'>");

            out.println("<div class='stat-card'>");
            out.println("<h2>" + complaints + "</h2>");
            out.println("<p>Total Complaints</p>");
            out.println("</div>");

            out.println("<div class='stat-card'>");
            out.println("<h2>" + assets + "</h2>");
            out.println("<p>Public Assets</p>");
            out.println("</div>");

            out.println("<div class='stat-card'>");
            out.println("<h2>" + emergencies + "</h2>");
            out.println("<p>Emergency Reports</p>");
            out.println("</div>");

            out.println("<div class='stat-card'>");
            out.println("<h2>" + requests + "</h2>");
            out.println("<p>Service Requests</p>");
            out.println("</div>");

            out.println("</div>");

            out.println("<div class='container'>");
            
            out.println("<div class='card'>");
out.println("<h2>Complaint Heatmap</h2>");

out.println("<table>");
out.println("<tr>");
out.println("<th>Department</th>");
out.println("<th>Total Complaints</th>");
out.println("</tr>");

rs = st.executeQuery(
"SELECT department, COUNT(*) total FROM complaints GROUP BY department");

while(rs.next()){

    out.println("<tr>");
    out.println("<td>"+rs.getString("department")+"</td>");
    out.println("<td>"+rs.getInt("total")+"</td>");
    out.println("</tr>");
}

out.println("</table>");
out.println("</div>");

out.println("<div class='card'>");
out.println("<h2>Department Coverage</h2>");

out.println("<table>");
out.println("<tr>");
out.println("<th>Department</th>");
out.println("<th>Total Requests</th>");
out.println("</tr>");

rs = st.executeQuery(
"SELECT department, COUNT(*) total FROM complaints GROUP BY department");

while(rs.next()){

    out.println("<tr>");
    out.println("<td>"+rs.getString("department")+"</td>");
    out.println("<td>"+rs.getInt("total")+"</td>");
    out.println("</tr>");
}

out.println("</table>");
out.println("</div>");

            // PUBLIC ASSETS

            out.println("<div class='card'>");
            out.println("<h2>Public Assets</h2>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Asset Type</th>");
            out.println("<th>Location</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            rs = st.executeQuery(
                    "SELECT asset_type, location, condition_status FROM public_assets");

            while(rs.next()){

                String status = rs.getString("condition_status");

                out.println("<tr>");
                out.println("<td>" + rs.getString("asset_type") + "</td>");
                out.println("<td>" + rs.getString("location") + "</td>");
                out.println("<td class='good'>" + status + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</div>");

            // EMERGENCY REPORTS

            out.println("<div class='card'>");
            out.println("<h2>Emergency Reports</h2>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Incident</th>");
            out.println("<th>Location</th>");
            out.println("<th>Severity</th>");
            out.println("</tr>");

            rs = st.executeQuery(
                    "SELECT incident_type, location, severity FROM emergency_reports");

            while(rs.next()){

                String severity = rs.getString("severity");

                String css = "high";

                if(severity != null &&
                        severity.equalsIgnoreCase("Critical")){
                    css = "critical";
                }

                out.println("<tr>");
                out.println("<td>" + rs.getString("incident_type") + "</td>");
                out.println("<td>" + rs.getString("location") + "</td>");
                out.println("<td class='" + css + "'>" + severity + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</div>");

            // SERVICE REQUESTS

            out.println("<div class='card'>");
            out.println("<h2>Service Requests</h2>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Service Type</th>");
            out.println("<th>Location</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            rs = st.executeQuery(
                    "SELECT service_type, location, status FROM service_requests");

            while(rs.next()){

                String status = rs.getString("status");

                String css = "pending";

                if(status != null &&
                        (status.equalsIgnoreCase("Completed")
                        || status.equalsIgnoreCase("Resolved"))){
                    css = "completed";
                }

                out.println("<tr>");
                out.println("<td>" + rs.getString("service_type") + "</td>");
                out.println("<td>" + rs.getString("location") + "</td>");
                out.println("<td class='" + css + "'>" + status + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</div>");

            out.println("</div>");

            out.println("<center>");
            out.println("<br><br>");
            out.println("<a class='btn' href='AdminDashboardServlet'>Back Dashboard</a>");
            out.println("</center>");

            out.println("</body>");
            out.println("</html>");

        }
        catch(Exception e){

            out.println("<h2>Error : " + e.getMessage() + "</h2>");
            e.printStackTrace();
        }
        finally{

            try{
                if(st!=null) st.close();
            }catch(Exception e){}

            try{
                if(con!=null) con.close();
            }catch(Exception e){}
        }
    }
}