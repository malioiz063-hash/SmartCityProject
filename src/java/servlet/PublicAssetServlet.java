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

@WebServlet("/PublicAssetServlet")
public class PublicAssetServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;

        try {

            String assetName =
                    request.getParameter("asset_name");

            String assetType =
                    request.getParameter("asset_type");

            String location =
                    request.getParameter("location");

            String conditionStatus =
                    request.getParameter("condition_status");

            String lastMaintenance =
                    request.getParameter("last_maintenance");

            con = DBConnection.getConnection();

            String sql =
                    "INSERT INTO public_assets "
                    + "(asset_name, asset_type, location, condition_status, last_maintenance) "
                    + "VALUES (?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, assetName);
            ps.setString(2, assetType);
            ps.setString(3, location);
            ps.setString(4, conditionStatus);

            if (lastMaintenance == null
                    || lastMaintenance.trim().isEmpty()) {

                ps.setNull(5, java.sql.Types.DATE);

            } else {

                ps.setDate(
                        5,
                        java.sql.Date.valueOf(lastMaintenance));
            }

            int rows = ps.executeUpdate();

            if (rows > 0) {

                response.sendRedirect(
                        "ViewAssetsServlet");

            } else {

                response.getWriter().println(
                        "<h3>Asset Save Failed</h3>");
            }

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