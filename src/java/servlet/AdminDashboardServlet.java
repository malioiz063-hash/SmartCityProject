package servlet;

import db.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        Connection con = null;
        Statement st = null;
            HttpSession session =
request.getSession(false);

if(session == null){

    response.sendRedirect(
    "adminLogin.html");

    return;
}

String department =
(String)session.getAttribute(
"department");
String complaintCategory = department;
String appointmentDepartment = department;

if(department.equals("Education")){
    department = "Education Department";
}
else if(department.equals("Transport")){
    department = "Transport Department";
}
else if(department.equals("Health")){
    department = "Health Department";
}
else if(department.equals("Emergency")){
    department = "Emergency Services";
}
else if(department.equals("Municipal")){
    department = "Municipal Services";
}
else if(department.equals("Water")){
    department = "Water Authority";
}
else if(department.equals("Electricity")){
    department = "Electricity Authority";
}

appointmentDepartment = department;
        try {

            con = DBConnection.getConnection();
            st = con.createStatement();

            // Complaints Stats

            PreparedStatement ps1 =
con.prepareStatement(
"SELECT COUNT(*) FROM complaints WHERE department=?");

ps1.setString(1, department);

ResultSet rs1 =
ps1.executeQuery();

rs1.next();

int totalComplaints =
rs1.getInt(1);


PreparedStatement ps2 =
con.prepareStatement(
"SELECT COUNT(*) FROM complaints WHERE department=? AND status='Pending'");

ps2.setString(1, department);

ResultSet rs2 =
ps2.executeQuery();

rs2.next();

int pendingComplaints =
rs2.getInt(1);


PreparedStatement ps3 =
con.prepareStatement(
"SELECT COUNT(*) FROM complaints\n" +
"WHERE department=? AND status='Resolved'");

ps3.setString(1, department);

ResultSet rs3 =
ps3.executeQuery();

rs3.next();

int resolvedComplaints =
rs3.getInt(1);

            // Appointments Stats

            PreparedStatement ps4 = con.prepareStatement(
"SELECT COUNT(*) FROM appointments WHERE department=?");

ps4.setString(1, appointmentDepartment);

ResultSet rs4 = ps4.executeQuery();
rs4.next();
int totalAppointments = rs4.getInt(1);


PreparedStatement ps5 = con.prepareStatement(
"SELECT COUNT(*) FROM appointments WHERE department=? AND status='Pending'");

ps5.setString(1, appointmentDepartment);

ResultSet rs5 = ps5.executeQuery();
rs5.next();
int pendingAppointments = rs5.getInt(1);


PreparedStatement ps6 = con.prepareStatement(
"SELECT COUNT(*) FROM complaints\n" +
"WHERE department=? AND status='Pending'");

ps6.setString(1, appointmentDepartment);

ResultSet rs6 = ps6.executeQuery();
rs6.next();
int approvedAppointments = rs6.getInt(1);

            // Emergency Stats

            ResultSet rs7 =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM emergency_reports");
            rs7.next();
            int totalEmergencies = rs7.getInt(1);

            ResultSet rs8 =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM emergency_reports WHERE status='Pending'");
            rs8.next();
            int pendingEmergencies = rs8.getInt(1);

            ResultSet rs9 =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM emergency_reports WHERE status='Resolved'");
            rs9.next();
            int resolvedEmergencies = rs9.getInt(1);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Admin Dashboard</title>");

            out.println("<style>");

out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Segoe UI',sans-serif;}");

out.println("body{background:#f1f5f9;}");

out.println(".sidebar{position:fixed;left:0;top:0;width:250px;height:100%;background:#0f172a;padding:20px;}");

out.println(".logo{color:white;font-size:24px;font-weight:bold;text-align:center;margin-bottom:30px;}");

out.println(".sidebar a{display:block;color:white;text-decoration:none;padding:14px;margin:10px 0;border-radius:8px;}");

out.println(".sidebar a:hover{background:#2563eb;}");

out.println(".main{margin-left:270px;padding:30px;}");

out.println(".header{background:white;padding:20px;border-radius:15px;box-shadow:0 2px 10px rgba(0,0,0,.1);margin-bottom:25px;}");

out.println(".header h1{color:#0f172a;}");

out.println(".cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(250px,1fr));gap:20px;}");

out.println(".card{background:white;padding:25px;border-radius:15px;box-shadow:0 4px 15px rgba(0,0,0,.08);}");

out.println(".card h3{color:#64748b;font-size:15px;margin-bottom:10px;}");

out.println(".number{font-size:34px;font-weight:bold;color:#2563eb;}");

out.println(".section-title{font-size:24px;font-weight:bold;color:#0f172a;margin:30px 0 20px;}");

out.println(".actions{display:grid;grid-template-columns:repeat(auto-fit,minmax(250px,1fr));gap:15px;}");

out.println(".btn{display:block;text-decoration:none;background:#2563eb;color:white;padding:16px;border-radius:10px;text-align:center;font-weight:bold;}");

out.println(".btn:hover{background:#1d4ed8;}");

out.println("</style>");

            out.println("</head>");
out.println("<body>");

out.println("<div class='sidebar'>");

out.println("<div class='logo'>Smart City</div>");

out.println("<a href='AdminDashboardServlet'>Dashboard</a>");
out.println("<a href='ManageComplaintsServlet'>Complaints</a>");
out.println("<a href='ManageServicesServlet'>Services</a>");
out.println("<a href='ManageAppointmentsServlet'>Appointments</a>");
out.println("<a href='ViewEmergencyServlet'>Emergency Reports</a>");
out.println("<a href='AnalyticsDashboardServlet'>Analytics</a>");
out.println("<a href='GISDashboardServlet'>GIS Dashboard</a>");
out.println("<a href='DepartmentPortalServlet'>Departments</a>");
out.println("<a href='AdminLogoutServlet'>Logout</a>");
out.println("</div>");

out.println("<div class='main'>");

out.println("<div class='header'>");
out.println(
"<h1>"+department+
" Dashboard</h1>");
out.println("</div>");

out.println("<div class='cards'>");

out.println("<div class='card'>");
out.println("<h3>Total Complaints</h3>");
out.println("<div class='number'>" + totalComplaints + "</div>");
out.println("</div>");

out.println("<div class='card'>");
out.println("<h3>Pending Complaints</h3>");
out.println("<div class='number'>" + pendingComplaints + "</div>");
out.println("</div>");

out.println("<div class='card'>");
out.println("<h3>Resolved Complaints</h3>");
out.println("<div class='number'>" + resolvedComplaints + "</div>");
out.println("</div>");

out.println("<div class='card'>");
out.println("<h3>Total Appointments</h3>");
out.println("<div class='number'>" + totalAppointments + "</div>");
out.println("</div>");

out.println("<div class='card'>");
out.println("<h3>Pending Appointments</h3>");
out.println("<div class='number'>" + pendingAppointments + "</div>");
out.println("</div>");

out.println("<div class='card'>");
out.println("<h3>Approved Appointments</h3>");
out.println("<div class='number'>" + approvedAppointments + "</div>");
out.println("</div>");

out.println("<div class='card'>");
out.println("<h3>Total Emergencies</h3>");
out.println("<div class='number'>" + totalEmergencies + "</div>");
out.println("</div>");

out.println("<div class='card'>");
out.println("<h3>Pending Emergencies</h3>");
out.println("<div class='number'>" + pendingEmergencies + "</div>");
out.println("</div>");

out.println("<div class='card'>");
out.println("<h3>Resolved Emergencies</h3>");
out.println("<div class='number'>" + resolvedEmergencies + "</div>");
out.println("</div>");

out.println("</div>");

out.println("<div class='section-title'>Management Modules</div>");

out.println("<div class='actions'>");

out.println("<a class='btn' href='ManageComplaintsServlet'>Manage Complaints</a>");

out.println("<a class='btn' href='ManageServicesServlet'>Manage Services</a>");

out.println("<a class='btn' href='ManageAppointmentsServlet'>Manage Appointments</a>");

out.println("<a class='btn' href='publicAssets.html'>Public Asset Management</a>");

out.println("<a class='btn' href='ViewEmergencyServlet'>Emergency Reports</a>");

out.println("<a class='btn' href='AnalyticsDashboardServlet'>Analytics Dashboard</a>");

out.println("<a class='btn' href='GISDashboardServlet'>GIS Dashboard</a>");

out.println("<a class='btn' href='DepartmentPortalServlet'>Department Portal</a>");

out.println("</div>");

out.println("</div>");

out.println("</body>");
out.println("</html>");

            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();
            rs5.close();
            rs6.close();
            rs7.close();
            rs8.close();
            rs9.close();

            st.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<h2>Error : "
                    + e.getMessage()
                    + "</h2>");
        }
    }
}