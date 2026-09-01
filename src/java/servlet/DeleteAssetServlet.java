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

@WebServlet("/DeleteAssetServlet")
public class DeleteAssetServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id"));

            con = DBConnection.getConnection();

            ps = con.prepareStatement(
                    "DELETE FROM public_assets WHERE id=?");

            ps.setInt(1, id);

            ps.executeUpdate();

            response.sendRedirect(
                    "ViewAssetsServlet");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error : " + e.getMessage());

        } finally {

            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
            }

            try {
                if (con != null) con.close();
            } catch (Exception e) {
            }
        }
    }
}