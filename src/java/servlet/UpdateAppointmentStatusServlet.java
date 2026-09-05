package servlet;

import db.DBConnection;
import util.EmailUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateAppointmentStatusServlet")
public class UpdateAppointmentStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement psInfo = null;
        ResultSet rs = null;

        try {

            int id = Integer.parseInt(
                    request.getParameter("id"));

            String status =
                    request.getParameter("status");

            con = DBConnection.getConnection();

            ps = con.prepareStatement(
                    "UPDATE appointments SET status=? WHERE id=?");

            ps.setString(1, status);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                psInfo = con.prepareStatement(
                        "SELECT citizen_email, department, appointment_date, appointment_time FROM appointments WHERE id=?");

                psInfo.setInt(1, id);

                rs = psInfo.executeQuery();

                if (rs.next()) {

                    String email =
                            rs.getString("citizen_email");

                    String department =
                            rs.getString("department");

                    String appointmentDate =
                            rs.getString("appointment_date");

                    String appointmentTime =
                            rs.getString("appointment_time");

                    Thread emailThread = new Thread(() -> {
    try {
        EmailUtil.sendAppointmentStatusEmail(
                email,
                department,
                appointmentDate,
                appointmentTime,
                status);
    } catch (Exception e) {
        e.printStackTrace();
    }
});

emailThread.setDaemon(true);
emailThread.start();}}

            response.sendRedirect(
                    "ManageAppointmentsServlet");

        } catch(Exception e){

            e.printStackTrace();

            response.getWriter().println(
                    "Error : " + e.getMessage());

        } finally {

            try {
                if(rs != null) rs.close();
            } catch(Exception e){}

            try {
                if(psInfo != null) psInfo.close();
            } catch(Exception e){}

            try {
                if(ps != null) ps.close();
            } catch(Exception e){}

            try {
                if(con != null) con.close();
            } catch(Exception e){}
        }
    }
}