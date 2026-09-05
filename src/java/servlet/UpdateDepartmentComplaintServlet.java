package servlet;

import db.DBConnection;
import util.EmailUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateDepartmentComplaintServlet")
public class UpdateDepartmentComplaintServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement psEmail = null;
        ResultSet rs = null;

        try {

            int id = Integer.parseInt(
                    request.getParameter("id"));

            String status =
                    request.getParameter("status");

            String feedback =
                    request.getParameter("feedback");

            con = DBConnection.getConnection();

            if ("Resolved".equalsIgnoreCase(status)) {

                ps = con.prepareStatement(
                        "UPDATE complaints SET status=?, feedback=? WHERE id=?");

                ps.setString(1, status);
                ps.setString(2, feedback);
                ps.setInt(3, id);

            } else {

                ps = con.prepareStatement(
                        "UPDATE complaints SET status=? WHERE id=?");

                ps.setString(1, status);
                ps.setInt(2, id);
            }

            int rows = ps.executeUpdate();

            if (rows > 0) {

                psEmail = con.prepareStatement(
                        "SELECT citizen_email,title FROM complaints WHERE id=?");

                psEmail.setInt(1, id);

                rs = psEmail.executeQuery();

                if (rs.next()) {

                    String email =
                            rs.getString("citizen_email");

                    String title =
                            rs.getString("title");

                    Thread emailThread = new Thread(() -> {
    try {
        EmailUtil.sendComplaintStatusEmail(
                email,
                title,
                status);
    } catch (Exception e) {
        e.printStackTrace();
    }
});

emailThread.setDaemon(true);
emailThread.start();}}

            response.sendRedirect(
                    request.getContextPath()
                    + "/DepartmentPortalServlet");

        }
        catch(Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error : " + e.getMessage());
        }
        finally {

            try {
                if(rs != null) rs.close();
            } catch(Exception e){}

            try {
                if(psEmail != null) psEmail.close();
            } catch(Exception e){}

            try {
                if(ps != null) ps.close();
            } catch(Exception e){}

            try {
                if(con != null) con.close();
            } catch(Exception e){}
        }
    }
}