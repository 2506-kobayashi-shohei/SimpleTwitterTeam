package chapter6.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.beans.User;
import chapter6.beans.UserComment;
import chapter6.beans.UserMessage;
import chapter6.service.CommentService;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {


        boolean isShowMessageForm = false;
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
            isShowMessageForm = true;
        }

		String userId = request.getParameter("user_id");
		String start = request.getParameter("start");
        String end = request.getParameter("end");
        List<UserMessage> messages = new MessageService().select(userId, start, end);

        //返信コメントを表示する
        List<UserComment> comments = new CommentService().select();

        request.setAttribute("start", start);
        request.setAttribute("end", end);
        request.setAttribute("messages", messages);
        request.setAttribute("comments", comments);
        request.setAttribute("isShowMessageForm", isShowMessageForm);
        request.getRequestDispatcher("/top.jsp").forward(request, response);
    }
}