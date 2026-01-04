package servlet;

import util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/DeleteAssignmentServlet")
public class DeleteAssignmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int assignmentId = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = DBConnection.getConnection();

            // 1️⃣ Check if submissions exist
            String checkSql = "SELECT COUNT(*) FROM submission WHERE assignment_id = ?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, assignmentId);
            ResultSet rs = checkPs.executeQuery();
            rs.next();

            int count = rs.getInt(1);

            if (count > 0) {
                // ❌ Do NOT delete
                response.sendRedirect(
                    "ViewInstructorAssignmentsServlet?error=submissions_exist");
                return;
            }

            // 2️⃣ Safe to delete
            String deleteSql = "DELETE FROM assignment WHERE assignment_id = ?";
            PreparedStatement deletePs = con.prepareStatement(deleteSql);
            deletePs.setInt(1, assignmentId);
            deletePs.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("ViewInstructorAssignmentsServlet");
    }
}
