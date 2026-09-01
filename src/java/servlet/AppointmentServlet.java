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

@WebServlet("/AppointmentServlet")
public class AppointmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;

        try {

            String email =
                    request.getParameter("citizen_email");

            String department =
                    request.getParameter("department");

            String date =
                    request.getParameter("appointment_date");

            String time =
                    request.getParameter("appointment_time");

            con = DBConnection.getConnection();

            ps = con.prepareStatement(
                    "INSERT INTO appointments(citizen_email,department,appointment_date,appointment_time,status) VALUES(?,?,?,?,?)");

            ps.setString(1, email);
            ps.setString(2, department);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.setString(5, "Pending");

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try {

                    EmailUtil.sendAppointmentEmail(
                            email,
                            department,
                            date,
                            time);

                    System.out.println(
                            "Appointment booking email sent.");

                } catch (Exception emailError) {

                    System.out.println(
                            "Appointment email failed: "
                            + emailError.getMessage());

                    emailError.printStackTrace();
                }

                response.sendRedirect(
                        "MyAppointmentsServlet");

            } else {

                response.getWriter().println(
                        "Appointment Booking Failed");
            }

        } catch(Exception e){

            e.printStackTrace();

            response.getWriter().println(
                    "Error : " + e.getMessage());

        } finally {

            try {
                if(ps != null) ps.close();
            } catch(Exception e){}

            try {
                if(con != null) con.close();
            } catch(Exception e){}
        }
    }
}