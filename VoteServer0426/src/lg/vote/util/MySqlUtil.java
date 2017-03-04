package lg.vote.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lg.vote.model.VoteMessageModel;
import lg.vote.model.VoterModel;

public class MySqlUtil {
	/**
	 * 
	 * @param name
	 * @param password
	 * @return
	 * @description: search the database by name and password,if the information
	 *               is true, return true,else if the name or password is either
	 *               wrong ,the function return false.
	 */
	public static boolean ValideVoter(String name, String password) {
		Statement stmt = connectDB();
		try {

			ResultSet rs = stmt.executeQuery("select * from "
					+ Constants.NAME_USER_TABLE + " where username='" + name
					+ "' and password='" + password + "'");
			while (rs.next()) {
				System.out.println(rs.getString("username") + "\t"
						+ rs.getString("password"));
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// �������ݿ�
		return false;
	}

	/**
	 * 
	 * @param name
	 * @return this method is used to check the registered information.if the
	 *         name the user wants to register has been registered,it returns
	 *         false.otherwise return true.
	 */
	public static boolean isRegistered(String name) {
		Statement stmt = connectDB();
		try {

			ResultSet rs = stmt.executeQuery("select * from "
					+ Constants.NAME_USER_TABLE + " where username='" + name
					+ "'");
			while (rs.next()) {
				System.out.println(rs.getString("username") + "\t"
						+ rs.getString("password"));
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// �������ݿ�
		return false;
	}

	/**
	 * 
	 * @param name
	 * @param password
	 * @return This method is used to add a user into the table,if it
	 *         succeed,return true.
	 */
	public static boolean addVoter(String name, String password) {
		Statement stmt = connectDB();
		try {
			stmt.execute("insert into " + Constants.NAME_USER_TABLE
					+ "(username,password) values(" + "'" + name + "'" + ","
					+ "'" + password + "'" + ")");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param name
	 * @return ����������ѯ�Ը����ǳ�Ϊ�����ı��ط����������û��йص���Ϣ
	 */
	public static List<VoteMessageModel> getMessageList(String name) {
		List<VoteMessageModel> messagelist = new ArrayList<VoteMessageModel>();
		Statement stmt = connectDB();
		try {
			System.out.println("������ѯ�ı���:" + name);
			stmt.execute("create table if not exists " + name
					+ " (questionID text,isAnswered text,isInvited text)");
			ResultSet rs = stmt.executeQuery("select * from " + name);
			while (rs.next()) {
				System.out
						.println(name + "�������� ID��:" + rs.getInt("questionID"));
				VoteMessageModel message = getMessage(rs.getInt("questionID"));
				message.setIsAnswered(rs.getString("isAnswered"));
				messagelist.add(message);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// �������ݿ�
		return messagelist;
	}

	/**
	 * 
	 * @param questionID
	 * @return ��������IDȥ���ݿ�votemessage������id��Ӧ��ͶƱ��Ϣ����
	 */
	public static VoteMessageModel getMessage(int questionID) {
		VoteMessageModel message = new VoteMessageModel();
		Statement stmt = connectDB();
		try {

			ResultSet rs = stmt.executeQuery("select * from "
					+ Constants.NAME_VOTEMESSAGE_TABLE + " where ID='"
					+ questionID + "'");
			while (rs.next()) {
				System.out.println("Pollster is:" + rs.getString("pollster"));
				message.setID(rs.getInt("ID"));
				message.setPollster(rs.getString("pollster"));
				message.setQuestionTitle(rs.getString("questionTitle"));
				message.setQuestionDetail(rs.getString("questionDetail"));
				List<String> choiceslist = new ArrayList<String>();
				List<Integer> answerNumberlist = new ArrayList<Integer>();

				// ��������ID,���Ҷ�Ӧ������𰸱�����Ϊquestion+ID����ȷ��ѡ���ÿ����ѡ��Ĵ𰸡�
				Statement stmt1 = connectDB();
				String questionchoices = "question"
						+ String.valueOf(questionID);
				stmt1.execute("create table if not exists " + questionchoices
						+ "(choice text,number text)");
				ResultSet rs1 = stmt1.executeQuery("select * from "
						+ questionchoices);
				while (rs1.next()) {
					String choice = rs1.getString("choice");
					choiceslist.add(choice);
					int number = rs1.getInt("number");
					answerNumberlist.add(number);
				}
				message.setChoices(choiceslist);
				message.setAnswerNumber(answerNumberlist);
				message.setAllowedAnswerNumber(rs.getInt("allowedAnswerNumber"));
				message.setStartTime("2015-04-29 12:00");
				message.setEndTime("2015-04-30 12:00");
				message.setIdentifyCode(rs.getString("identifyCode"));

				// ��������ID����ѯ��Ӧ���������������Ϊinvitee+ID��,ȷ��������������û�
				List<VoterModel> inviteelist = new ArrayList<VoterModel>();
				String invitee_table_name = "invitee"
						+ String.valueOf(questionID);
				stmt1.execute("create table if not exists "
						+ invitee_table_name + "(name text)");
				ResultSet rs2 = stmt1.executeQuery("select * from "
						+ invitee_table_name);
				while (rs2.next()) {
					String name = rs2.getString("name");
					VoterModel voter = new VoterModel();
					voter.setName(name);
					inviteelist.add(voter);
				}
				message.setInvitee(inviteelist);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// �������ݿ�
		return message;
	}

	/**
	 * 
	 * @param message
	 * @return ���·����ͶƱ��Ϣ��ӵ����ݿ��У����ظ������ID
	 */
	public static int addMessage(VoteMessageModel message) {
		// ��ѯ���ݿ��е���Ϣ������������ͶƱ��Ϣ��ID��
		Statement stmt = connectDB();
		try {
			ResultSet rs = stmt.executeQuery("select count(*) as num from "
					+ Constants.NAME_VOTEMESSAGE_TABLE);
			while (rs.next()) {
				int num = rs.getInt("num");
				message.setID(++num);
				System.out.println("��ͶƱ����Ϊ:" + num);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��votemessagedb���������Ϣ����Ϣ
		try {
			stmt.execute("insert into "
					+ Constants.NAME_VOTEMESSAGE_TABLE
					+ "(ID,pollster,questionTitle,questionDetail,allowedAnswerNumber,"
					+ "identifyCode) values(" + "'" + message.getID() + "'"
					+ "," + "'" + message.getPollster() + "'" + "," + "'"
					+ message.getQuestionTitle() + "'" + "," + "'"
					+ message.getQuestionDetail() + "'" + "," + "'"
					+ message.getAllowedAnswerNumber() + "'" + "," + "'"
					+ message.getIdentifyCode() + "'" + ")");
			System.out.println("��Ϣ�������Ϣ�ɹ�������ӵ�����IDΪ��"+message.getID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ����ͶƱ�˵ĸ�����Ϣ��
		try {
			stmt.execute("create table if not exists " + message.getPollster()
					+ "(questionID text,isAnswered text,isInvited text)");
			stmt.execute("insert into " + message.getPollster()
					+ " (questionID,isAnswered,isInvited) " + "values(" + "'"
					+ message.getID() + "'" + "," + "'" + "no" + "'" + ","
					+ "'" + "no" + "'" + ")");
			System.out.println("�����˸�����Ϣ�������Ϣ�ɹ���");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ����������ID�Ĵ�ѡ���
		try {
			stmt.execute("create table if not exists question"
					+ message.getID() + "(choice text,number text)");
			for (int i = 0; i < message.getChoices().size(); i++) {
				stmt.execute("insert into question" + message.getID()
						+ " (choice,number) " + "values(" + "'"
						+ message.getChoices().get(i) + "'" + "," + "'"
						+ message.getAnswerNumber().get(i) + "'" + ")");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ����������ID�������������������Ӧ�����ߵ��û���
		if (message.getInvitee() != null && message.getInvitee().size() > 0) {
			try {
				stmt.execute("create table if not exists invitee"
						+ message.getID() + "(name text)");
				for (int i = 0; i < message.getInvitee().size(); i++) {
					stmt.execute("insert into invitee" + message.getID()
							+ " (name) " + "values(" + "'"
							+ message.getInvitee().get(i).getName() + "'"+ ")");
					// �������ݿ��б������ߵ���Ϣ
					stmt.execute("create table if not exists "
							+ message.getInvitee().get(i).getName()
							+ "(questionID text,isPollster text,isAnswered text)");
					stmt.execute("insert into "
							+ message.getInvitee().get(i).getName()
							+ " (questionID,isAnswered,isInvited) " + "values("
							+ "'" + message.getID() + "'" + "," + "'" + "no"
							+ "'" + "," + "'" + "yes" + "'" + ")");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message.getID();
	}

	public static VoteMessageModel updateMessage(VoteMessageModel message) {

		Statement stmt = connectDB();
		String questionchoices = "question" + String.valueOf(message.getID());
		// ����ͶƱ�˸��Ĵ𰸸��´𰸱����ܴ��ڶ���𰸣�
		try {
			for (int i = 0; i < message.getAnswerDetail().size(); i++) {
				int number = 0;
				ResultSet rs = null;
				rs = stmt.executeQuery("select * from " + questionchoices
						+ " where choice='" + message.getAnswerDetail().get(i) + "'");
				while (rs.next()) {
					number = rs.getInt("number");
					++number;
					Statement stmt1 = connectDB();
					stmt1.execute("update " + questionchoices + " set number='"
							+ number + "'" + " where choice='"
							+ message.getAnswerDetail().get(i) + "'");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message.setID(0);
			return message;
		}
		
		//�����û�����������ش����Ǳ�������ˣ��ʸ����û���������ڴ�ID�ţ�������Ǳ�������ˣ������ڸ����û����н���������ID
		try {
			stmt.execute("create table if not exists " + message.getAnswerName()
					+ " (questionID text,isAnswered text,isInvited text)");
			ResultSet rs = stmt.executeQuery("select * from " +message.getAnswerName()
					+" where questionID='"+message.getID()+"'");
			
			if(rs.next()) {
				//������ڸ���Ϣ��¼�������� �����Լ������ͶƱ��Ϣ������ֻ�����isAnswered����
			Statement stmt1 = connectDB();
			stmt1.execute("update " + message.getAnswerName() + " set isAnswered='yes'"
				 + " where questionID='"+ message.getID() + "'");
			}else{
				//��������ڣ���������Ϣ��
				stmt.execute("insert into "
						+ message.getAnswerName()
						+ " (questionID,isAnswered,isInvited) " + "values("
						+ "'" + message.getID() + "'" + "," + "'" + "yes"
						+ "'" + "," + "'" + "no" + "'" + ")");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//���ݿ��и��³ɹ����޸ķ�����Ϣ������
		message.setIsAnswered("yes");
		
       try{
		// ����ͶƱ��Ϣ�е�answerNumber����
    	   List<Integer> answerNumber=new ArrayList<Integer>();
    	   ResultSet rs2 = stmt.executeQuery("select * from "
					+ questionchoices);
    	   while(rs2.next()){
    		   answerNumber.add(rs2.getInt("number"));
    	   }
    	   message.setAnswerNumber(answerNumber);
       } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 
	 * @return
	 * @description: connect the database by using the third jar,and if it
	 *               succeed,it returns the the statement which is used to
	 *               execute the sql sentence.
	 */
	public static Statement connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// �������ݿ�
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"
					+ Constants.NAME_DATABASE, Constants.USER_DATABASE,
					Constants.PASSWORD_DATABASE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
	/*
	 * public static void main(String[] args){ Statement stmt2 = connectDB();
	 * ResultSet rs2 =
	 * stmt2.executeQuery("select * from "+"invitee"+String.valueOf(1));
	 * System.out.println("������ѯ�ı�Ϊ��invitee"+String.valueOf(1)); while
	 * (rs2.next()) { String name=rs1.getString("name");
	 * 
	 * 
	 * }
	 * 
	 * }
	 */
}
