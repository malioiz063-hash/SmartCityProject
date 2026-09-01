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

@WebServlet("/PaymentHistoryServlet")
public class PaymentHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out =
                response.getWriter();

        try {

            Connection con =
                    DBConnection.getConnection();

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(
                    "SELECT * FROM payments ORDER BY id DESC");

            out.println("<html><head><title>Payment History</title>");

            out.println("<style>");
            out.println("body{font-family:Arial;background:linear-gradient(135deg,#4364f7,#8f44ad);padding:30px;}");
            out.println(".container{background:white;padding:25px;border-radius:15px;}");
            out.println("table{width:100%;border-collapse:collapse;}");
            out.println("th,td{padding:12px;border:1px solid #ddd;text-align:center;}");
            out.println("th{background:#28a745;color:white;}");
            out.println("a{display:inline-block;margin-top:20px;text-decoration:none;color:white;background:#4364f7;padding:10px 20px;border-radius:5px;}");
            out.println("</style>");

            out.println("</head><body>");

            out.println("<div class='container'>");

            out.println("<h2 align='center'>Payment History</h2>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Email</th>");
            out.println("<th>Service</th>");
            out.println("<th>Amount</th>");
            out.println("<th>Method</th>");
            out.println("<th>Status</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            while(rs.next()) {

                out.println("<tr>");

                out.println("<td>"+rs.getInt("id")+"</td>");
                out.println("<td>"+rs.getString("citizen_email")+"</td>");
                out.println("<td>"+rs.getString("service_name")+"</td>");
                out.println("<td>"+rs.getDouble("amount")+"</td>");
                out.println("<td>"+rs.getString("payment_method")+"</td>");
                out.println("<td>"+rs.getString("status")+"</td>");
                out.println("<td>"+rs.getString("created_at")+"</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br>");

            out.println("<a href='dashboard.html'>Back Dashboard</a>");

            out.println("</div>");

            out.println("</body></html>");

            rs.close();
            st.close();
            con.close();

        } catch(Exception e) {

            out.println("<h3>"+e.getMessage()+"</h3>");
        }
    }
}