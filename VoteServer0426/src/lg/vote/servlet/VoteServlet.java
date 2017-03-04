package lg.vote.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lg.vote.model.VoteMessageModel;
import lg.vote.util.MySqlUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VoteServlet extends HttpServlet{
	 public void service(HttpServletRequest request, HttpServletResponse response)
		     throws ServletException, IOException{  
	 String voterJson=request.getParameter("json");
	 ObjectMapper mapper = new ObjectMapper();
	 VoteMessageModel message=mapper.readValue(voterJson, VoteMessageModel.class);
	 System.out.println("VoteServlet接受到的投票人及ID号为:"+message.getAnswerName()+" "+message.getID() );
	if("yes".equals(message.getIsAnswered())){
		//直接查询最新结果并放回即可
		message=MySqlUtil.getMessage(message.getID());
		message.setIsAnswered("yes");
	}else{
	 //更新数据库，并取得问题ID,且返回
	message=MySqlUtil.updateMessage(message);
	System.out.println("VoteServlet---MySqlUtil.updateMessage返回该消息的回答属性:"+message.getIsAnswered() );
	}
	
	 //放回给客户端
	 response.getWriter().println(mapper.writeValueAsString(message));
	 }
}
