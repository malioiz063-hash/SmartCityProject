package servlet;

import db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEmergencyServlet")
public class DeleteEmergencyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;

        try {

            String idParam =
                    request.getParameter("id");

            if (idParam == null || idParam.trim().isEmpty()) {

                response.getWriter().println(
                        "<h3>Invalid Emergency Report ID</h3>");
                return;
            }

            int id =
                    Integer.parseInt(idParam);

            con =
                    DBConnection.getConnection();

            ps =
                    con.prepareStatement(
                            "DELETE FROM emergency_reports WHERE id=?");

            ps.setInt(1, id);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                response.sendRedirect(
                        "ViewEmergencyServlet?success=Emergency Report Deleted Successfully");

            } else {

                response.getWriter().println(
                        "<h3>Emergency Report Not Found</h3>");
            }

        } catch (NumberFormatException e) {

            response.getWriter().println(
                    "<h3>Invalid Report ID Format</h3>");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h3>Error : "
                            + e.getMessage()
                            + "</h3>");

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