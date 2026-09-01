package servlet;

import db.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditAssetServlet")
public class EditAssetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id"));

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM public_assets WHERE id=?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                out.println("<html><body>");

                out.println("<h2>Edit Asset</h2>");

                out.println("<form action='UpdateAssetServlet' method='post'>");

                out.println("<input type='hidden' name='id' value='"+id+"'>");

                out.println("Asset Name:<br>");
                out.println("<input type='text' name='asset_name' value='"+rs.getString("asset_name")+"'><br><br>");

                out.println("Location:<br>");
                out.println("<input type='text' name='location' value='"+rs.getString("location")+"'><br><br>");

                out.println("Condition:<br>");
                out.println("<select name='condition_status'>");
                out.println("<option>Good</option>");
                out.println("<option>Needs Repair</option>");
                out.println("<option>Critical</option>");
                out.println("</select><br><br>");

                out.println("Last Maintenance:<br>");
                out.println("<input type='date' name='last_maintenance'><br><br>");

                out.println("<input type='submit' value='Update Asset'>");

                out.println("</form>");

                out.println("</body></html>");
            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e){

            e.printStackTrace();
        }
    }
}