package servlet;

import db.DBConnection;
import util.EmailUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.concurrent.CompletableFuture;
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM citizens WHERE email=? AND password=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {

                String fullName = rs.getString("full_name");

                // Create Session
                HttpSession session = request.getSession();

                session.setAttribute("email", email);
                session.setAttribute("fullName", fullName);
System.out.println("NEW VERSION DEPLOYED 123");
                // Login Time
                String loginTime =
                        LocalDateTime.now().format(
                                DateTimeFormatter.ofPattern(
                                        "dd-MM-yyyy hh:mm a"));

                // Send Login Notification Email
                Thread emailThread = new Thread(() -> {
    try {
        EmailUtil.sendLoginEmail(email, fullName);
    } catch (Exception e) {
        e.printStackTrace();
    }
});

emailThread.setDaemon(true);
emailThread.start();

response.sendRedirect("CitizenDashboardServlet");} else {

                response.sendRedirect(
                        "login.html?error=Invalid Email or Password");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "login.html?error=Something Went Wrong");

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }

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