package servlet;

import db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateEmergencyStatusServlet")
public class UpdateEmergencyStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id"));

            String currentStatus = "Pending";

            con = DBConnection.getConnection();

            ps = con.prepareStatement(
                    "SELECT status FROM emergency_reports WHERE id=?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {

                currentStatus =
                        rs.getString("status");
            }

            response.getWriter().println(

                    "<html>" +

                    "<head>" +

                    "<title>Update Emergency Status</title>" +

                    "<style>" +

                    "body{" +
                    "font-family:Arial;" +
                    "background:#f4f4f4;" +
                    "padding:30px;" +
                    "}" +

                    ".container{" +
                    "width:500px;" +
                    "margin:auto;" +
                    "background:white;" +
                    "padding:25px;" +
                    "border-radius:10px;" +
                    "box-shadow:0 0 10px #ccc;" +
                    "}" +

                    "h2{" +
                    "text-align:center;" +
                    "color:#dc3545;" +
                    "}" +

                    "select{" +
                    "width:100%;" +
                    "padding:10px;" +
                    "margin-top:10px;" +
                    "margin-bottom:20px;" +
                    "}" +

                    "button{" +
                    "background:#28a745;" +
                    "color:white;" +
                    "border:none;" +
                    "padding:12px;" +
                    "width:100%;" +
                    "cursor:pointer;" +
                    "border-radius:5px;" +
                    "}" +

                    "a{" +
                    "text-decoration:none;" +
                    "}" +

                    "</style>" +

                    "</head>" +

                    "<body>" +

                    "<div class='container'>" +

                    "<h2>Update Emergency Status</h2>" +

                    "<p><b>Report ID:</b> " + id + "</p>" +

                    "<p><b>Current Status:</b> "
                    + currentStatus +
                    "</p>" +

                    "<form action='UpdateEmergencyStatusServlet' method='post'>" +

                    "<input type='hidden' name='id' value='" + id + "'>" +

                    "<label>Select New Status</label>" +

                    "<select name='status'>" +

                    "<option value='Pending'>Pending</option>" +

                    "<option value='In Progress'>In Progress</option>" +

                    "<option value='Resolved'>Resolved</option>" +

                    "</select>" +

                    "<button type='submit'>Update Status</button>" +

                    "</form>" +

                    "<br>" +

                    "<center>" +

                    "<a href='ViewEmergencyServlet'>Back to Emergency Reports</a>" +

                    "</center>" +

                    "</div>" +

                    "</body>" +

                    "</html>"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h3>Error : "
                            + e.getMessage()
                            + "</h3>");

        } finally {

            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
            }

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

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id"));

            String status =
                    request.getParameter("status");

            con = DBConnection.getConnection();

            ps = con.prepareStatement(
                    "UPDATE emergency_reports SET status=? WHERE id=?");

            ps.setString(1, status);
            ps.setInt(2, id);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                response.sendRedirect(
                        "ViewEmergencyServlet");

            } else {

                response.getWriter().println(
                        "<h3>Status Update Failed</h3>");
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