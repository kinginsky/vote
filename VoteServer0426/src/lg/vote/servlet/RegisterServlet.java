package lg.vote.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lg.vote.model.VoterModel;
import lg.vote.util.MySqlUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RegisterServlet extends HttpServlet{
	public void service(HttpServletRequest request, HttpServletResponse response)
		     throws ServletException, IOException{  
	 String voterJson=request.getParameter("json");
	 ObjectMapper mapper = new ObjectMapper();
	 VoterModel voter=mapper.readValue(voterJson, VoterModel.class);
	 System.out.println("detail: name:"+voter.getName()+" password:"+voter.getPassword());
	 //���ݿ��ѯ���޴����ݣ�������name����Ϊ��
	 if(!MySqlUtil.isRegistered(voter.getName())){
		if(! MySqlUtil.addVoter(voter.getName(), voter.getPassword())){
			voter.setName(null);
		}
	 }else{
		 voter.setName(null);
	 }
	 //�Żظ��ͻ���
	 response.getWriter().println(mapper.writeValueAsString(voter));
	 }
}
