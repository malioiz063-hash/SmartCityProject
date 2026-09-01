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

@WebServlet("/AnalyticsDashboardServlet")
public class AnalyticsDashboardServlet extends HttpServlet {

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

            // =========================
            // Complaints
            // =========================

            int totalComplaints = 0;
            int resolvedComplaints = 0;
            int pendingComplaints = 0;

            ResultSet rs = st.executeQuery(
                    "SELECT COUNT(*) total FROM complaints");

            if (rs.next()) {
                totalComplaints = rs.getInt("total");
            }

            rs = st.executeQuery(
                    "SELECT COUNT(*) total FROM complaints WHERE status='Resolved'");

            if (rs.next()) {
                resolvedComplaints = rs.getInt("total");
            }

            rs = st.executeQuery(
                    "SELECT COUNT(*) total FROM complaints WHERE status='Pending'");

            if (rs.next()) {
                pendingComplaints = rs.getInt("total");
            }

            double resolutionRate = 0;

            if (totalComplaints > 0) {
                resolutionRate =
                        (resolvedComplaints * 100.0)
                                / totalComplaints;
            }

            // =========================
            // Emergency Reports
            // =========================

            int emergencyReports = 0;

            rs = st.executeQuery(
                    "SELECT COUNT(*) total FROM emergency_reports");

            if (rs.next()) {
                emergencyReports = rs.getInt("total");
            }

            // =========================
            // Service Requests
            // =========================

            int serviceRequests = 0;
            int pendingServices = 0;
            int processingServices = 0;
            int completedServices = 0;

            rs = st.executeQuery(
                    "SELECT COUNT(*) total FROM service_requests");

            if (rs.next()) {
                serviceRequests = rs.getInt("total");
            }

            rs = st.executeQuery(
                    "SELECT COUNT(*) total FROM service_requests WHERE LOWER(status)='pending'");

            if (rs.next()) {
                pendingServices = rs.getInt("total");
            }

            rs = st.executeQuery(
                    "SELECT COUNT(*) total FROM service_requests WHERE LOWER(status)='processing'");

            if (rs.next()) {
                processingServices = rs.getInt("total");
            }

            rs = st.executeQuery(
                    "SELECT COUNT(*) total FROM service_requests WHERE LOWER(status)='completed'");

            if (rs.next()) {
                completedServices = rs.getInt("total");
            }

            // =========================
            // Appointments
            // =========================

            int appointments = 0;

            rs = st.executeQuery(
                    "SELECT COUNT(*) total FROM appointments");

            if (rs.next()) {
                appointments = rs.getInt("total");
            }

            // =========================
            // Citizens
            // =========================

            int citizens = 0;

            try {

                rs = st.executeQuery(
                        "SELECT COUNT(*) total FROM citizens");

                if (rs.next()) {
                    citizens = rs.getInt("total");
                }

            } catch (Exception ex) {
                citizens = 0;
            }

            // =========================
            // Public Assets
            // =========================

            int assets = 0;

            try {

                rs = st.executeQuery(
                        "SELECT COUNT(*) total FROM public_assets");

                if (rs.next()) {
                    assets = rs.getInt("total");
                }

            } catch (Exception ex) {
                assets = 0;
            }

            // =========================
            // UI
            // =========================

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Analytics Dashboard</title>");

            out.println("<style>");

            out.println("body{font-family:Arial;background:#f4f6f9;padding:30px;}");
            out.println("h1{text-align:center;color:#333;margin-bottom:30px;}");

            out.println(".container{");
            out.println("display:flex;");
            out.println("flex-wrap:wrap;");
            out.println("justify-content:center;");
            out.println("}");

            out.println(".card{");
            out.println("width:250px;");
            out.println("background:white;");
            out.println("padding:20px;");
            out.println("margin:15px;");
            out.println("border-radius:12px;");
            out.println("text-align:center;");
            out.println("box-shadow:0 0 10px rgba(0,0,0,0.15);");
            out.println("}");

            out.println(".card h3{");
            out.println("margin-bottom:15px;");
            out.println("color:#222;");
            out.println("}");

            out.println(".value{");
            out.println("font-size:35px;");
            out.println("font-weight:bold;");
            out.println("color:#0d6efd;");
            out.println("}");

            out.println(".btn{");
            out.println("display:inline-block;");
            out.println("padding:12px 25px;");
            out.println("background:#0d6efd;");
            out.println("color:white;");
            out.println("text-decoration:none;");
            out.println("border-radius:6px;");
            out.println("}");

            out.println(".btn:hover{");
            out.println("background:#0b5ed7;");
            out.println("}");

            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>Analytics Dashboard</h1>");

            out.println("<div class='container'>");

            out.println(card("Total Citizens", citizens));
            out.println(card("Total Complaints", totalComplaints));
            out.println(card("Resolved Complaints", resolvedComplaints));
            out.println(card("Pending Complaints", pendingComplaints));
            out.println(card("Resolution Rate", String.format("%.2f", resolutionRate) + "%"));

            out.println(card("Emergency Reports", emergencyReports));

            out.println(card("Service Requests", serviceRequests));
            out.println(card("Pending Services", pendingServices));
            out.println(card("Processing Services", processingServices));
            out.println(card("Completed Services", completedServices));

            out.println(card("Appointments", appointments));
            out.println(card("Public Assets", assets));

            out.println("</div>");

            out.println("<br><center>");
            out.println("<a class='btn' href='AdminDashboardServlet'>Back Dashboard</a>");
            out.println("</center>");

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {

            out.println("<h2>Error : " + e.getMessage() + "</h2>");
            e.printStackTrace();

        } finally {

            try {
                if (st != null)
                    st.close();
            } catch (Exception e) {
            }

            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
            }
        }
    }

    private String card(String title, Object value) {

        return "<div class='card'>"
                + "<h3>" + title + "</h3>"
                + "<div class='value'>" + value + "</div>"
                + "</div>";
    }
}