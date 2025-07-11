package chapter6.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/delete" })
public class DeleteMessageServlet extends HttpServlet {

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Integer id = Integer.parseInt(request.getParameter("id"));

        new MessageService().delete(id);
        request.setAttribute("id", id);
        response.sendRedirect("./");
    }
}
