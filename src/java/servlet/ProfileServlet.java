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
import jakarta.servlet.http.HttpSession;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            HttpSession session = request.getSession(false);

            if (session == null ||
                    session.getAttribute("email") == null) {

                response.sendRedirect("login.html");
                return;
            }

            String email =
                    session.getAttribute("email").toString();

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM citizens WHERE email=?");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>My Profile</title>");

            out.println("<style>");

            out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Segoe UI',sans-serif;}");

            out.println("body{background:#f4f7fc;}");

            out.println(".container{max-width:1100px;margin:40px auto;background:white;border-radius:20px;overflow:hidden;box-shadow:0 10px 30px rgba(0,0,0,.08);}");

            out.println(".cover{height:220px;background:linear-gradient(135deg,#4f46e5,#7c3aed);}");

            out.println(".profile-section{padding:0 50px 50px;position:relative;}");

out.println(".profile-wrapper{position:absolute;top:-85px;left:50px;width:170px;height:170px;}");

out.println(".profile-pic{width:170px;height:170px;border-radius:50%;border:6px solid white;object-fit:cover;background:#eee;}");            
            out.println(".profile-wrapper{position:absolute;top:-85px;left:50px;}");

out.println(".upload-icon{position:absolute;right:0;bottom:10px;width:42px;height:42px;background:#2563eb;color:white;border-radius:50%;display:flex;align-items:center;justify-content:center;font-size:28px;font-weight:bold;cursor:pointer;border:3px solid white;}");

out.println(".upload-icon:hover{background:#1d4ed8;}");

            out.println(".title-area{padding-top:100px;display:flex;justify-content:space-between;align-items:center;}");
            out.println(".upload-icon{position:absolute;right:0;bottom:0;width:42px;height:42px;background:#2563eb;color:white;border-radius:50%;display:flex;align-items:center;justify-content:center;font-size:28px;font-weight:bold;cursor:pointer;border:3px solid white;}");
            out.println(".title-area h1{font-size:34px;color:#111827;}");

            out.println(".title-area p{color:#6b7280;margin-top:5px;}");

            out.println(".save-btn{background:#111827;color:white;border:none;padding:12px 30px;border-radius:8px;font-size:15px;cursor:pointer;}");

            out.println(".save-btn:hover{background:#2563eb;}");

            out.println(".form-grid{margin-top:40px;display:grid;grid-template-columns:1fr 1fr;gap:25px;}");

            out.println(".field label{display:block;font-weight:600;color:#374151;margin-bottom:8px;}");

            out.println(".field input{width:100%;padding:14px;border:1px solid #d1d5db;border-radius:10px;font-size:15px;}");

            out.println(".field input:focus{outline:none;border-color:#2563eb;}");

            out.println(".full-width{grid-column:1/3;}");

            out.println(".upload-box{margin-top:25px;padding:20px;background:#f9fafb;border:2px dashed #d1d5db;border-radius:12px;}");

            out.println(".upload-box label{font-weight:bold;display:block;margin-bottom:10px;}");

            out.println(".upload-box input{padding:10px;}");

            out.println(".message-success{background:#d1fae5;color:#065f46;padding:14px;border-radius:10px;margin-top:20px;text-align:center;font-weight:bold;}");

            out.println(".message-error{background:#fee2e2;color:#991b1b;padding:14px;border-radius:10px;margin-top:20px;text-align:center;font-weight:bold;}");

            out.println(".back-btn{display:inline-block;margin-top:25px;text-decoration:none;color:#2563eb;font-weight:bold;}");

            out.println("@media(max-width:768px){");
            out.println(".form-grid{grid-template-columns:1fr;}");
            out.println(".full-width{grid-column:auto;}");
            out.println(".title-area{display:block;}");
            out.println("}");

            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            if (rs.next()) {

                String image =
                        rs.getString("profile_picture");

                if (image == null || image.trim().equals("")) {
                    image = "https://cdn-icons-png.flaticon.com/512/149/149071.png";
                } else {
                    image = "Picture/" + image;
                }

                out.println("<div class='container'>");

                out.println("<div class='cover'></div>");

                out.println("<div class='profile-section'>");

               



                out.println("<div class='title-area'>");

                out.println("<div>");
                out.println("<h1>" + rs.getString("full_name") + "</h1>");
                out.println("<p>Manage your personal information</p>");
                out.println("</div>");

                out.println("</div>");

                String success =
                        request.getParameter("success");

                String error =
                        request.getParameter("error");

                if (success != null) {
                    out.println("<div class='message-success'>" +
                            success +
                            "</div>");
                }

                if (error != null) {
                    out.println("<div class='message-error'>" +
                            error +
                            "</div>");
                }

                out.println("<form action='UpdateProfileServlet' method='post' enctype='multipart/form-data'>");

out.println("<input type='hidden' name='citizen_id' value='" +
        rs.getInt("citizen_id") +
        "'>");

out.println("<div class='profile-wrapper'>");
out.println("<img src='" + image + "' class='profile-pic' id='profilePreview'>");
out.println("<label for='profileFile' class='upload-icon'>+</label>");
out.println("<input type='file' id='profileFile' name='profile_picture' style='display:none;' onchange='previewImage(event)'>");
out.println("</div>");
                

                out.println("<div class='form-grid'>");

                out.println("<div class='field'>");
                out.println("<label>Full Name</label>");
                out.println("<input type='text' name='full_name' value='" +
                        rs.getString("full_name") + "'>");
                out.println("</div>");

                out.println("<div class='field'>");
                out.println("<label>Email</label>");
                out.println("<input type='email' name='email' value='" +
                        rs.getString("email") + "'>");
                out.println("</div>");

                out.println("<div class='field'>");
                out.println("<label>Phone</label>");
                out.println("<input type='text' name='phone' value='" +
                        rs.getString("phone") + "'>");
                out.println("</div>");

                out.println("<div class='field'>");
                out.println("<label>CNIC</label>");
                out.println("<input type='text' name='cnic' value='" +
                        rs.getString("cnic") + "'>");
                out.println("</div>");

                out.println("<div class='field full-width'>");
                out.println("<label>Address</label>");
                out.println("<input type='text' name='address' value='" +
                        rs.getString("address") + "'>");
                out.println("</div>");

                out.println("<div class='field full-width'>");
                out.println("<label>Date Of Birth</label>");
                out.println("<input type='date' name='dob' value='" +
                        rs.getString("dob") + "'>");
                out.println("</div>");

                out.println("</div>");

                out.println("<br>");
                out.println("<button class='save-btn' type='submit'>Save Changes</button>");

                out.println("</form>");

                out.println("<a class='back-btn' href='CitizenDashboardServlet'>← Back To Dashboard</a>");

                out.println("</div>");
                out.println("</div>");
            }
            
            out.println("<script>");
out.println("function previewImage(event){");
out.println("var reader=new FileReader();");
out.println("reader.onload=function(){");
out.println("document.getElementById('profilePreview').src=reader.result;");
out.println("};");
out.println("reader.readAsDataURL(event.target.files[0]);");
out.println("}");
out.println("</script>");

            out.println("</body>");
            out.println("</html>");

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}