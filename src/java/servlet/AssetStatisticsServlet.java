package servlet;

import db.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AssetStatisticsServlet")
public class AssetStatisticsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            int totalAssets = 0;
            int roads = 0;
            int buildings = 0;
            int parks = 0;
            int repair = 0;
            int critical = 0;

            ResultSet rs;

            rs = st.executeQuery(
                    "SELECT COUNT(*) FROM public_assets");
            if(rs.next()) totalAssets = rs.getInt(1);

            rs = st.executeQuery(
                    "SELECT COUNT(*) FROM public_assets WHERE asset_type='Road'");
            if(rs.next()) roads = rs.getInt(1);

            rs = st.executeQuery(
                    "SELECT COUNT(*) FROM public_assets WHERE asset_type='Building'");
            if(rs.next()) buildings = rs.getInt(1);

            rs = st.executeQuery(
                    "SELECT COUNT(*) FROM public_assets WHERE asset_type='Park'");
            if(rs.next()) parks = rs.getInt(1);

            rs = st.executeQuery(
                    "SELECT COUNT(*) FROM public_assets WHERE condition_status='Needs Repair'");
            if(rs.next()) repair = rs.getInt(1);

            rs = st.executeQuery(
                    "SELECT COUNT(*) FROM public_assets WHERE condition_status='Critical'");
            if(rs.next()) critical = rs.getInt(1);

            out.println("<html><head><title>Asset Statistics</title>");

            out.println("<style>");
            out.println("body{font-family:Arial;background:#f4f4f4;padding:30px;}");
            out.println(".card{display:inline-block;width:220px;margin:10px;padding:20px;background:white;border-radius:10px;text-align:center;box-shadow:0 0 10px #ccc;}");
            out.println("h1{font-size:32px;color:#007bff;}");
            out.println("h3{color:#333;}");
            out.println(".btn{background:#007bff;color:white;padding:10px 15px;text-decoration:none;border-radius:5px;}");
            out.println("</style>");

            out.println("</head><body>");

            out.println("<h2>Public Asset Statistics</h2>");

            out.println("<div class='card'><h3>Total Assets</h3><h1>"+totalAssets+"</h1></div>");
            out.println("<div class='card'><h3>Roads</h3><h1>"+roads+"</h1></div>");
            out.println("<div class='card'><h3>Buildings</h3><h1>"+buildings+"</h1></div>");
            out.println("<div class='card'><h3>Parks</h3><h1>"+parks+"</h1></div>");
            out.println("<div class='card'><h3>Needs Repair</h3><h1>"+repair+"</h1></div>");
            out.println("<div class='card'><h3>Critical Assets</h3><h1>"+critical+"</h1></div>");

            out.println("<br><br>");

            out.println("<a class='btn' href='ViewAssetsServlet'>View Assets</a>");

            out.println("</body></html>");

            st.close();
            con.close();

        } catch(Exception e){

            e.printStackTrace();

            out.println("Error : " + e.getMessage());
        }
    }
}