package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String messageId = request.getParameter("id");
		HttpSession session = request.getSession();

		List<String> errorMessages = new ArrayList<String>();
		Message message = null;

		if(!StringUtils.isBlank(messageId) && messageId.matches("^[0-9]*$")) {
			Integer messageid = Integer.parseInt(messageId);
			message = new MessageService().select(messageid);
		}

		//トップ画面に戻ってエラー表示
		if(message == null) {
			errorMessages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<String> errorMessages = new ArrayList<String>();

		Message message = new Message();
		message.setId(Integer.parseInt(request.getParameter("id")));
		message.setText(request.getParameter("text"));

		if (isValid(message, errorMessages)) {
			new MessageService().update(message);
		}
		if(errorMessages.size() != 0) {
			request.setAttribute("errorMessages", errorMessages);
			request.setAttribute("message" , message);
			request.getRequestDispatcher("edit.jsp").forward(request, response);
			return;
		}
		response.sendRedirect("./");
	}


	private boolean isValid(Message message, List<String> errorMessages) {

		if (StringUtils.isBlank(message.getText())) {
			errorMessages.add("メッセージを入力してください");
		} else if (140 < message.getText().length()) {
			errorMessages.add("140文字以下で入力してください");
		}

		if (errorMessages.size() != 0) {
			return false;
		}
		return true;
	}
}

