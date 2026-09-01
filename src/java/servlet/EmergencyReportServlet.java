package servlet;

import db.DBConnection;
import util.EmailUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmergencyReportServlet")
public class EmergencyReportServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;

        try {

            String citizenEmail =
                    request.getParameter("citizen_email");

            String incidentType =
                    request.getParameter("incident_type");

            String location =
                    request.getParameter("location");

            String description =
                    request.getParameter("description");

            String severity =
                    request.getParameter("severity");

            con = DBConnection.getConnection();

            String sql =
                    "INSERT INTO emergency_reports "
                    + "(citizen_email, incident_type, location, description, severity, status) "
                    + "VALUES(?,?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, citizenEmail);
            ps.setString(2, incidentType);
            ps.setString(3, location);
            ps.setString(4, description);
            ps.setString(5, severity);
            ps.setString(6, "Pending");

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try {

                    // Email to Admin
                    EmailUtil.sendEmergencyAlertToAdmin(
                            incidentType,
                            location,
                            description,
                            severity
                    );

                    // Email to Citizen
                    EmailUtil.sendEmergencyReportSubmittedEmail(
                            citizenEmail,
                            incidentType,
                            location
                    );

                    System.out.println(
                            "Admin and citizen emails sent successfully.");

                } catch (Exception emailError) {

                    System.out.println(
                            "Email failed: "
                                    + emailError.getMessage());

                    emailError.printStackTrace();
                }

                response.sendRedirect(
                        "dashboard.html?success=Emergency Report Submitted Successfully");

            } else {

                response.getWriter().println(
                        "<h3>Emergency Report Failed</h3>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h3>Error : "
                            + e.getMessage()
                            + "</h3>");

        } finally {

            try {
                if (ps != null)
                    ps.close();
            } catch (Exception e) {
            }

            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
            }
        }
    }
}