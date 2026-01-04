package servlet;

import dao.AssignmentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/CreateAssignmentServlet")
public class CreateAssignmentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");
        
        AssignmentDAO dao = new AssignmentDAO();
        dao.createAssignment(title, description, dueDate);

        response.sendRedirect("createAssignment.html?success=true");

    }
}