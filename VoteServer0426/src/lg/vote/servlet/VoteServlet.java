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
	 System.out.println("VoteServlet���ܵ���ͶƱ�˼�ID��Ϊ:"+message.getAnswerName()+" "+message.getID() );
	if("yes".equals(message.getIsAnswered())){
		//ֱ�Ӳ�ѯ���½�����Żؼ���
		message=MySqlUtil.getMessage(message.getID());
		message.setIsAnswered("yes");
	}else{
	 //�������ݿ⣬��ȡ������ID,�ҷ���
	message=MySqlUtil.updateMessage(message);
	System.out.println("VoteServlet---MySqlUtil.updateMessage���ظ���Ϣ�Ļش�����:"+message.getIsAnswered() );
	}
	
	 //�Żظ��ͻ���
	 response.getWriter().println(mapper.writeValueAsString(message));
	 }
}
