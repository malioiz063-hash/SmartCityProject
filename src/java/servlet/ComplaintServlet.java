package servlet;

import db.DBConnection;
import util.EmailUtil;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/ComplaintServlet")
@MultipartConfig
public class ComplaintServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "complaint_images";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        PreparedStatement ps = null;

        try {

            HttpSession session = request.getSession(false);

            if (session == null
                    || session.getAttribute("email") == null) {

                response.sendRedirect("login.html");
                return;
            }

            String email =
                    (String) session.getAttribute("email");

            String title =
                    request.getParameter("title");

            String category =
                    request.getParameter("category");

            String location =
                    request.getParameter("location");

            String description =
                    request.getParameter("description");

            String priority = "Medium";
String department = category;



            Part filePart =
                    request.getPart("image");

            String fileName = null;

            if (filePart != null
                    && filePart.getSize() > 0) {

                fileName =
                        System.currentTimeMillis()
                        + "_"
                        + filePart.getSubmittedFileName();

                String uploadPath =
                        getServletContext().getRealPath("")
                        + File.separator
                        + UPLOAD_DIR;

                File uploadDir =
                        new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                filePart.write(
                        uploadPath
                        + File.separator
                        + fileName);
            }

            con = DBConnection.getConnection();

            String sql =
"INSERT INTO complaints " +
"(citizen_email,title,description,location,status,category,priority,image_path,department) " +
"VALUES(?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, "Pending");
            ps.setString(6, category);
            ps.setString(7, priority);
            ps.setString(8, fileName);
ps.setString(9, department);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try {

                    EmailUtil.sendComplaintSubmittedEmail(
        email,
        title);

                    System.out.println(
                            "Complaint submission email sent.");

                } catch (Exception emailError) {

                    System.out.println(
                            "Complaint email failed: "
                            + emailError.getMessage());

                    emailError.printStackTrace();
                }

                response.sendRedirect(
                        "CitizenDashboardServlet?success=Complaint Submitted Successfully");

            } else {

                response.sendRedirect(
                        "CitizenDashboardServlet?error=Complaint Submission Failed");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "addComplaint.html?error=Something Went Wrong");

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