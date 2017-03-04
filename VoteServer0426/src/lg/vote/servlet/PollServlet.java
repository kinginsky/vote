package lg.vote.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lg.vote.model.VoteMessageModel;
import lg.vote.util.MySqlUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PollServlet  extends HttpServlet{
	 public void service(HttpServletRequest request, HttpServletResponse response)
		     throws ServletException, IOException{  
	 String voterJson=request.getParameter("json");
	 ObjectMapper mapper = new ObjectMapper();
	 VoteMessageModel message=mapper.readValue(voterJson, VoteMessageModel.class);
	 System.out.println("PollServlet接受到的投票人:"+message.getPollster() );
	 //更新数据库，并取得问题ID,且返回
	int id=MySqlUtil.addMessage(message);
	 message.setID(id);
	 //返回给客户端
	 System.out.println("消息添加成功，PollServlet返回问题ID为:"+message.getID());
	 response.getWriter().println(mapper.writeValueAsString(message));
	 }
}
