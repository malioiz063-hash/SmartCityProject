package servlet;

import db.DBConnection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/UpdateProfileServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class UpdateProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int citizenId =
                    Integer.parseInt(
                            request.getParameter("citizen_id"));

            String name =
                    request.getParameter("full_name");

            String email =
                    request.getParameter("email");

            String phone =
                    request.getParameter("phone");

            String address =
                    request.getParameter("address");

            String cnic =
                    request.getParameter("cnic");

            String dob =
                    request.getParameter("dob");

            String imageName = null;

            Part imagePart = request.getPart("profile_picture");


            

            if (imagePart != null &&
                    imagePart.getSize() > 0) {

                imageName =
                        System.currentTimeMillis() + "_" +
                        imagePart.getSubmittedFileName();

                String uploadPath =
                        getServletContext()
                                .getRealPath("/Picture");

                File uploadDir =
                        new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                File file =
                        new File(uploadDir, imageName);

                Files.copy(
                        imagePart.getInputStream(),
                        file.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps;

            if (imageName != null) {

                ps = con.prepareStatement(
                        "UPDATE citizens SET full_name=?, email=?, phone=?, address=?, cnic=?, dob=?, profile_picture=? WHERE citizen_id=?");

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setString(4, address);
                ps.setString(5, cnic);
                ps.setString(6, dob);
                ps.setString(7, imageName);
                ps.setInt(8, citizenId);

            } else {

                ps = con.prepareStatement(
                        "UPDATE citizens SET full_name=?, email=?, phone=?, address=?, cnic=?, dob=? WHERE citizen_id=?");

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setString(4, address);
                ps.setString(5, cnic);
                ps.setString(6, dob);
                ps.setInt(7, citizenId);
            }

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            if (rows > 0) {

                response.sendRedirect(
                        "ProfileServlet?success=Profile Updated Successfully");

            } else {

                response.sendRedirect(
                        "ProfileServlet?error=Profile Update Failed");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "ProfileServlet?error=" +
                    e.getMessage());
        }
    }
}





