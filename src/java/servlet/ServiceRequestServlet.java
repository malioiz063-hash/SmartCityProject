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

@WebServlet("/ServiceRequestServlet")
public class ServiceRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;

        try {

            response.setContentType("text/html");

            String email =
                    request.getParameter("citizen_email");

            String service =
                    request.getParameter("service_type");

            String description =
                    request.getParameter("description");

            String location =
                    request.getParameter("location");

            String department = "";

if(service.equals("Water Authority")) {

    department = "Water Authority";

}
else if(service.equals("Municipal Services")) {

    department = "Municipal Services";

}
else if(service.equals("Health Department")) {

    department = "Health Department";

}
else if(service.equals("Electricity Authority")) {

    department = "Electricity Authority";

}
else if(service.equals("Transport Department")) {

    department = "Transport Department";

}
else if(service.equals("Education Department")) {

    department = "Education Department";

}
else if(service.equals("Emergency Services")) {

    department = "Emergency Services";

}
            con = DBConnection.getConnection();

            if (con == null) {

                response.getWriter().println(
                        "<h2>Database Connection Failed</h2>");
                return;
            }

            String sql =
                    "INSERT INTO service_requests(citizen_email,service_type,description,location,department,status) VALUES(?,?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, service);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, department);
            ps.setString(6, "Pending");

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try {

                    EmailUtil.sendServiceRequestEmail(
                            email,
                            service,
                            location);

                    System.out.println(
                            "Service request email sent.");

                } catch (Exception emailError) {

                    System.out.println(
                            "Email failed: "
                            + emailError.getMessage());

                    emailError.printStackTrace();
                }

                response.sendRedirect(
                        "CitizenDashboardServlet?success=Service Request Submitted");

            } else {

                response.getWriter().println(
                        "<h2>Service Request Submission Failed</h2>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h2>Error : "
                    + e.getMessage()
                    + "</h2>");

        } finally {

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }

            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
    }
}