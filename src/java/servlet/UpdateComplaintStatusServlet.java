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

@WebServlet("/UpdateComplaintStatusServlet")
public class UpdateComplaintStatusServlet extends HttpServlet {

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

            con = DBConnection.getConnection();

            // Update Complaint Status

            ps = con.prepareStatement(
                    "UPDATE complaints SET status=? WHERE id=?");

            ps.setString(1, status);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                // Get User Email + Complaint Title

                psEmail = con.prepareStatement(
                        "SELECT citizen_email,title FROM complaints WHERE id=?");

                psEmail.setInt(1, id);

                rs = psEmail.executeQuery();

                if (rs.next()) {

                    String email =
                            rs.getString("citizen_email");

                    String title =
                            rs.getString("title");

                    try {

                        EmailUtil.sendComplaintStatusEmail(
                                email,
                                title,
                                status);

                        System.out.println(
                                "Complaint status email sent.");

                    } catch (Exception emailError) {

                        System.out.println(
                                "Email failed: "
                                + emailError.getMessage());

                        emailError.printStackTrace();
                    }
                }
            }

            response.sendRedirect(
                    "ManageComplaintsServlet");

        }
        catch(Exception e){

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