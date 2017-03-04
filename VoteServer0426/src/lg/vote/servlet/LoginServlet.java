package lg.vote.servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lg.vote.model.VoteMessageModel;
import lg.vote.model.VoterModel;
import lg.vote.util.MySqlUtil;

import com.fasterxml.jackson.databind.ObjectMapper;


public class LoginServlet extends HttpServlet{
	/* public void doGet(HttpServletRequest request, HttpServletResponse response)
			     throws ServletException, IOException{  
         response.setContentType("text/html");  
         response.setCharacterEncoding("UTF-8");  
	 }
	 public void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException{  
         response.setContentType("text/html");  
         response.setCharacterEncoding("UTF-8");  
         
	 }*/
	 public void service(HttpServletRequest request, HttpServletResponse response)
		     throws ServletException, IOException{  
	 String voterJson=request.getParameter("json");
	 ObjectMapper mapper = new ObjectMapper();
	 VoterModel voter=mapper.readValue(voterJson, VoterModel.class);
	 System.out.println("detail: name:"+voter.getName()+" password:"+voter.getPassword());
	final List<VoteMessageModel> messagelist=new ArrayList<VoteMessageModel>();
	 //数据库查询若无此数据，则设置name属性为空
	 if(!MySqlUtil.ValideVoter(voter.getName(), voter.getPassword())){
		 VoteMessageModel message=new VoteMessageModel();
		 message.setID(0);
		 messagelist.add(message);
	 }
	 else{
		 messagelist.addAll(MySqlUtil.getMessageList(voter.getName()));
	 }
	 //放回给客户端
	 response.getWriter().println(mapper.writeValueAsString(messagelist));
	 }
}
