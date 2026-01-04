package servlet;

import util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/EditAssignmentServlet")
public class EditAssignmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int assignmentId = Integer.parseInt(request.getParameter("id"));

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<link rel='stylesheet' href='css/main.css'>");

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM assignment WHERE assignment_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, assignmentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<div class='container'><div class='card'>");
                out.println("<h2>Edit Assignment</h2>");
                out.println("<form method='post' action='EditAssignmentServlet'>");

                out.println("<input type='hidden' name='id' value='" + assignmentId + "'>");

                out.println("Title:<br>");
                out.println("<input type='text' name='title' value='" + rs.getString("title") + "' required><br><br>");

                out.println("Description:<br>");
                out.println("<textarea name='description' required>" +
                        rs.getString("description") + "</textarea><br><br>");

                out.println("Due Date:<br>");
                out.println("<input type='date' name='dueDate' value='" +
                        rs.getDate("due_date") + "' required><br><br>");

                out.println("<input type='submit' value='Update Assignment'>");
                out.println("</form></div></div>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error loading assignment.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");

        try {
            Connection con = DBConnection.getConnection();
            String sql =
                "UPDATE assignment SET title=?, description=?, due_date=? WHERE assignment_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, dueDate);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("ViewInstructorAssignmentsServlet");
    }
}
