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
	 System.out.println("PollServlet���ܵ���ͶƱ��:"+message.getPollster() );
	 //�������ݿ⣬��ȡ������ID,�ҷ���
	int id=MySqlUtil.addMessage(message);
	 message.setID(id);
	 //���ظ��ͻ���
	 System.out.println("��Ϣ��ӳɹ���PollServlet��������IDΪ:"+message.getID());
	 response.getWriter().println(mapper.writeValueAsString(message));
	 }
}
