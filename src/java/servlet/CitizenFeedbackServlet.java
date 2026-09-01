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
import jakarta.servlet.http.HttpSession;

@WebServlet("/CitizenFeedbackServlet")
public class CitizenFeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            HttpSession session =
                    request.getSession(false);

            if(session == null ||
               session.getAttribute("email") == null){

                response.sendRedirect("login.html");
                return;
            }

            int complaintId =
                    Integer.parseInt(
                            request.getParameter("id"));

            String feedback =
                    request.getParameter("feedback");

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "UPDATE complaints SET feedback=? WHERE id=?");

            ps.setString(1, feedback);
            ps.setInt(2, complaintId);

            int rows =
                    ps.executeUpdate();

            ps.close();
            con.close();

            if(rows > 0){

                response.sendRedirect(
                        "ViewComplaintServlet?success=Feedback Submitted");

            } else {

                response.sendRedirect(
                        "ViewComplaintServlet?error=Feedback Failed");
            }

        }
        catch(Exception e){

            e.printStackTrace();

            response.sendRedirect(
                    "ViewComplaintServlet?error=Something Went Wrong");
        }
    }
}