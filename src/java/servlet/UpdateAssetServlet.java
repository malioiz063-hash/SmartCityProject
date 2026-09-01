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

@WebServlet("/UpdateAssetServlet")
public class UpdateAssetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id"));

            String assetName =
                    request.getParameter("asset_name");

            String location =
                    request.getParameter("location");

            String condition =
                    request.getParameter("condition_status");

            String maintenance =
                    request.getParameter("last_maintenance");

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "UPDATE public_assets SET asset_name=?, location=?, condition_status=?, last_maintenance=? WHERE id=?");

            ps.setString(1, assetName);
            ps.setString(2, location);
            ps.setString(3, condition);
            ps.setString(4, maintenance);
            ps.setInt(5, id);

            ps.executeUpdate();

            ps.close();
            con.close();

            response.sendRedirect(
                    "ViewAssetsServlet");

        } catch(Exception e){

            e.printStackTrace();
        }
    }
}