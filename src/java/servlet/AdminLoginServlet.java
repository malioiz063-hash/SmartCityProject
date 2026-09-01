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
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM admins WHERE username=? AND password=?");

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                HttpSession session =
                        request.getSession();

                session.setAttribute(
                        "admin",
                        rs.getString("username"));

                session.setAttribute(
                        "department",
                        rs.getString("department"));

                response.sendRedirect(
                        "AdminDashboardServlet");
                System.out.println("Department = " + rs.getString("department"));

            } else {

                response.sendRedirect(
                        "Home.jsp");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "adminLogin.html");
        }
    }
}