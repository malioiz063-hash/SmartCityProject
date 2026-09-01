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

@WebServlet("/CitizenDashboardServlet")
public class CitizenDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        HttpSession session =
                request.getSession(false);

        if(session == null ||
           session.getAttribute("email") == null){

            response.sendRedirect("login.html");
            return;
        }

        String email =
                session.getAttribute("email").toString();

        String fullName =
                session.getAttribute("fullName").toString();

        int complaints = 0;
        int services = 0;
        int appointments = 0;
        int emergencies = 0;

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            // Complaints

            try {

                PreparedStatement ps =
                        con.prepareStatement(
                                "SELECT COUNT(*) FROM complaints WHERE citizen_email=?");

                ps.setString(1, email);

                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    complaints = rs.getInt(1);
                }

                rs.close();
                ps.close();

            } catch(Exception e){}

            // Services

            try {

                PreparedStatement ps =
                        con.prepareStatement(
                                "SELECT COUNT(*) FROM service_requests WHERE citizen_email=?");

                ps.setString(1, email);

                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    services = rs.getInt(1);
                }

                rs.close();
                ps.close();

            } catch(Exception e){}

            // Appointments

            try {

                PreparedStatement ps =
                        con.prepareStatement(
                                "SELECT COUNT(*) FROM appointments WHERE citizen_email=?");

                ps.setString(1, email);

                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    appointments = rs.getInt(1);
                }

                rs.close();
                ps.close();

            } catch(Exception e){}

            // Emergency Reports

            try {

                PreparedStatement ps =
                        con.prepareStatement(
                                "SELECT COUNT(*) FROM emergency_reports WHERE citizen_email=?");

                ps.setString(1, email);

                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    emergencies = rs.getInt(1);
                }

                rs.close();
                ps.close();

            } catch(Exception e){}

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Citizen Dashboard</title>");

            out.println("<style>");
            out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Segoe UI',sans-serif;}");
            out.println("body{background:#f4f7fc;display:flex;height:100vh;}");
            out.println(".sidebar{width:250px;background:#111827;color:white;padding:25px 0;position:fixed;height:100%;}");
            out.println(".logo{text-align:center;font-size:28px;font-weight:bold;margin-bottom:40px;}");
            out.println(".sidebar a{display:block;padding:15px 25px;color:#d1d5db;text-decoration:none;font-size:16px;transition:.3s;}");
            out.println(".sidebar a:hover{background:#2563eb;color:white;}");
            out.println(".logout{margin-top:30px;color:#ff6b6b !important;}");
            out.println(".main{margin-left:250px;width:calc(100% - 250px);}");
            out.println(".topbar{height:80px;background:white;display:flex;justify-content:space-between;align-items:center;padding:0 40px;box-shadow:0 2px 10px rgba(0,0,0,.08);}");
            out.println(".profile{background:#2563eb;color:white;padding:10px 18px;border-radius:25px;font-weight:bold;}");
            out.println(".content{padding:35px;}");
            out.println(".stats{display:grid;grid-template-columns:repeat(4,1fr);gap:20px;margin-bottom:35px;}");
            out.println(".stat-card{background:white;padding:25px;border-radius:15px;box-shadow:0 4px 12px rgba(0,0,0,.08);}");
            out.println(".number{font-size:34px;font-weight:bold;color:#2563eb;}");
            out.println(".section-title{font-size:24px;font-weight:bold;margin-bottom:20px;}");
            out.println(".cards{display:grid;grid-template-columns:repeat(3,1fr);gap:20px;}");
            out.println(".card{background:white;padding:35px;border-radius:15px;text-align:center;text-decoration:none;color:#111827;font-size:18px;font-weight:600;box-shadow:0 4px 12px rgba(0,0,0,.08);transition:.3s;}");
            out.println(".card:hover{background:#2563eb;color:white;transform:translateY(-5px);}");
            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<div class='sidebar'>");
            out.println("<div class='logo'>Smart City</div>");
            out.println("<a href='CitizenDashboardServlet'>🏠 Dashboard</a>");
            out.println("<a href='addComplaint.html'>📝 Add Complaint</a>");
            out.println("<a href='MyComplaintsServlet'>📋 My Complaints</a>");
            out.println("<a href='serviceRequest.html'>🔧 Service Request</a>");
            out.println("<a href='MyServicesServlet'>⚙ My Services</a>");
            out.println("<a href='appointment.html'>📅 Appointment Booking</a>");
            out.println("<a href='MyAppointmentsServlet'>📆 My Appointments</a>");
            out.println("<a href='emergencyReport.html'>🚨 Emergency Report</a>");
            out.println("<a href='payment.html'>💳 Online Payment</a>");
            out.println("<a href='PaymentHistoryServlet'>💰 Payment History</a>");
            out.println("<a href='ProfileServlet'>👤 Profile</a>");
            out.println("<a href='LogoutServlet' class='logout'>🚪 Logout</a>");
            out.println("</div>");

            out.println("<div class='main'>");

            out.println("<div class='topbar'>");
            out.println("<h2>Citizen Dashboard</h2>");
            out.println("<div class='profile'>" + fullName + "</div>");
            out.println("</div>");

            out.println("<div class='content'>");

            out.println("<div class='stats'>");

            out.println("<div class='stat-card'><h3>Total Complaints</h3><div class='number'>" + complaints + "</div></div>");
            out.println("<div class='stat-card'><h3>Services</h3><div class='number'>" + services + "</div></div>");
            out.println("<div class='stat-card'><h3>Appointments</h3><div class='number'>" + appointments + "</div></div>");
            out.println("<div class='stat-card'><h3>Emergency Reports</h3><div class='number'>" + emergencies + "</div></div>");

            out.println("</div>");

            out.println("<div class='section-title'>Quick Actions</div>");

            out.println("<div class='cards'>");

            out.println("<a href='addComplaint.html' class='card'>📝 Add Complaint</a>");
            out.println("<a href='serviceRequest.html' class='card'>🔧 Service Request</a>");
            out.println("<a href='appointment.html' class='card'>📅 Book Appointment</a>");
            out.println("<a href='emergencyReport.html' class='card'>🚨 Emergency Report</a>");
            out.println("<a href='payment.html' class='card'>💳 Online Payment</a>");
            out.println("<a href='ProfileServlet' class='card'>👤 Profile</a>");

            out.println("</div>");

            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch(Exception e){

            e.printStackTrace();
            out.println("Error : " + e.getMessage());

        } finally {

            try{
                if(con != null)
                    con.close();
            }catch(Exception e){}
        }
    }
}