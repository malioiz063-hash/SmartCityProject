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

@WebServlet("/ViewAssetsServlet")
public class ViewAssetsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();

            st = con.createStatement();

            int totalAssets = 0;
            int goodAssets = 0;
            int repairAssets = 0;
            int criticalAssets = 0;

            ResultSet r1 =
                    st.executeQuery(
                    "SELECT COUNT(*) total FROM public_assets");

            if(r1.next()){
                totalAssets = r1.getInt("total");
            }

            ResultSet r2 =
                    st.executeQuery(
                    "SELECT COUNT(*) total FROM public_assets WHERE condition_status='Good'");

            if(r2.next()){
                goodAssets = r2.getInt("total");
            }

            ResultSet r3 =
                    st.executeQuery(
                    "SELECT COUNT(*) total FROM public_assets WHERE condition_status='Needs Repair'");

            if(r3.next()){
                repairAssets = r3.getInt("total");
            }

            ResultSet r4 =
                    st.executeQuery(
                    "SELECT COUNT(*) total FROM public_assets WHERE condition_status='Critical'");

            if(r4.next()){
                criticalAssets = r4.getInt("total");
            }

            rs = st.executeQuery(
                    "SELECT * FROM public_assets ORDER BY id DESC");

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Public Assets</title>");

            out.println("<style>");
            out.println("body{font-family:Arial;background:#f4f4f4;padding:30px;}");
            out.println("h2{text-align:center;color:#333;}");

            out.println(".stats{display:flex;justify-content:center;gap:20px;margin-bottom:30px;flex-wrap:wrap;}");

            out.println(".card{background:white;padding:20px;width:220px;border-radius:10px;text-align:center;box-shadow:0 2px 8px rgba(0,0,0,0.1);}");

            out.println(".card h3{margin:0;color:#333;}");
            out.println(".card p{font-size:30px;font-weight:bold;color:#007bff;}");

            out.println("table{width:100%;border-collapse:collapse;background:white;}");
            out.println("th,td{padding:12px;border:1px solid #ddd;text-align:center;}");
            out.println("th{background:#007bff;color:white;}");

            out.println(".btn{background:#28a745;color:white;padding:10px 15px;text-decoration:none;border-radius:5px;margin-right:10px;}");

            out.println(".editBtn{color:blue;font-weight:bold;text-decoration:none;}");
            out.println(".deleteBtn{color:red;font-weight:bold;text-decoration:none;}");
            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<h2>Public Asset Management</h2>");

            out.println("<div class='stats'>");

            out.println("<div class='card'>");
            out.println("<h3>Total Assets</h3>");
            out.println("<p>" + totalAssets + "</p>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h3>Good Assets</h3>");
            out.println("<p>" + goodAssets + "</p>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h3>Needs Repair</h3>");
            out.println("<p>" + repairAssets + "</p>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h3>Critical Assets</h3>");
            out.println("<p>" + criticalAssets + "</p>");
            out.println("</div>");

            out.println("</div>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Asset Name</th>");
            out.println("<th>Asset Type</th>");
            out.println("<th>Location</th>");
            out.println("<th>Condition</th>");
            out.println("<th>Last Maintenance</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            while(rs.next()) {

                int id = rs.getInt("id");

                String condition =
                        rs.getString("condition_status");

                String maintenance =
                        rs.getString("last_maintenance");

                if(condition == null || condition.trim().isEmpty()){
                    condition = "Not Available";
                }

                if(maintenance == null || maintenance.trim().isEmpty()){
                    maintenance = "Not Available";
                }

                out.println("<tr>");

                out.println("<td>"+id+"</td>");
                out.println("<td>"+rs.getString("asset_name")+"</td>");
                out.println("<td>"+rs.getString("asset_type")+"</td>");
                out.println("<td>"+rs.getString("location")+"</td>");
                out.println("<td>"+condition+"</td>");
                out.println("<td>"+maintenance+"</td>");

                out.println("<td>");

                out.println(
                        "<a class='editBtn' href='EditAssetServlet?id="
                        + id
                        + "'>Edit</a>"
                );

                out.println(" | ");

                out.println(
                        "<a class='deleteBtn' href='DeleteAssetServlet?id="
                        + id
                        + "' onclick=\"return confirm('Delete this asset?')\">Delete</a>"
                );

                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");

            out.println(
                    "<a class='btn' href='publicAssets.html'>Add New Asset</a>");

            out.println(
                    "<a class='btn' href='AdminDashboardServlet'>Back Dashboard</a>");

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<h3>Error : "
                    + e.getMessage()
                    + "</h3>");
        }
    }
}