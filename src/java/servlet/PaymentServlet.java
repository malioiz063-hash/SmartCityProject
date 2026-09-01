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

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String email =
                    request.getParameter("citizen_email");

            String service =
                    request.getParameter("service_name");

            double amount =
                    Double.parseDouble(
                    request.getParameter("amount"));

            String method =
                    request.getParameter("payment_method");

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                    "INSERT INTO payments(citizen_email,service_name,amount,payment_method,status) VALUES(?,?,?,?,?)");

            ps.setString(1,email);
            ps.setString(2,service);
            ps.setDouble(3,amount);
            ps.setString(4,method);
            ps.setString(5,"Paid");

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            if(rows > 0){

                response.sendRedirect("PaymentHistoryServlet");

            }else{

                response.getWriter().println(
                        "<h2>Payment Failed</h2>");
            }

        }catch(Exception e){

            e.printStackTrace();

            response.getWriter().println(
                    "<h2>Error : "+e.getMessage()+"</h2>");
        }
    }
}