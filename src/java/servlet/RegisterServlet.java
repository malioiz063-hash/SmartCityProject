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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;

        try {

            String fullName = request.getParameter("full_name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String password = request.getParameter("password");

            con = DBConnection.getConnection();

            String sql = "INSERT INTO citizens(full_name,email,phone,address,password) VALUES(?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setString(5, password);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try {

                    EmailUtil.sendWelcomeEmail(
        email,
        fullName);

                } catch (Exception emailError) {

                    System.out.println(
                            "Email sending failed: "
                            + emailError.getMessage());

                    emailError.printStackTrace();
                }

                response.sendRedirect(
                        "login.html?msg=registered");

            } else {

                response.getWriter().println(
                        "<h3>Registration Failed</h3>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h3>Error: "
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