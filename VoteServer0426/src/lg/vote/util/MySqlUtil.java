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
		}// 遍历数据库
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
		}// 遍历数据库
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
	 * @return 根据姓名查询以个人昵称为表名的表返回服务器端与用户有关的消息
	 */
	public static List<VoteMessageModel> getMessageList(String name) {
		List<VoteMessageModel> messagelist = new ArrayList<VoteMessageModel>();
		Statement stmt = connectDB();
		try {
			System.out.println("即将查询的表名:" + name);
			stmt.execute("create table if not exists " + name
					+ " (questionID text,isAnswered text,isInvited text)");
			ResultSet rs = stmt.executeQuery("select * from " + name);
			while (rs.next()) {
				System.out
						.println(name + "表中问题 ID有:" + rs.getInt("questionID"));
				VoteMessageModel message = getMessage(rs.getInt("questionID"));
				message.setIsAnswered(rs.getString("isAnswered"));
				messagelist.add(message);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 遍历数据库
		return messagelist;
	}

	/**
	 * 
	 * @param questionID
	 * @return 根据问题ID去数据库votemessage搜索该id对应的投票信息详情
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

				// 根据问题ID,查找对应的问题答案表（表名为question+ID），确定选择项及每个可选项的答案。
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

				// 根据问题ID，查询对应的问题邀请表（表名为invitee+ID）,确定该问题邀请的用户
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
		}// 遍历数据库
		return message;
	}

	/**
	 * 
	 * @param message
	 * @return 将新发起的投票信息添加到数据库中，返回该问题的ID
	 */
	public static int addMessage(VoteMessageModel message) {
		// 查询数据库中的消息个数，设置新投票信息的ID号
		Statement stmt = connectDB();
		try {
			ResultSet rs = stmt.executeQuery("select count(*) as num from "
					+ Constants.NAME_VOTEMESSAGE_TABLE);
			while (rs.next()) {
				int num = rs.getInt("num");
				message.setID(++num);
				System.out.println("总投票个数为:" + num);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 往votemessagedb中添加新消息的信息
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
			System.out.println("消息表添加信息成功！且添加的问题ID为："+message.getID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 更新投票人的个人信息表
		try {
			stmt.execute("create table if not exists " + message.getPollster()
					+ "(questionID text,isAnswered text,isInvited text)");
			stmt.execute("insert into " + message.getPollster()
					+ " (questionID,isAnswered,isInvited) " + "values(" + "'"
					+ message.getID() + "'" + "," + "'" + "no" + "'" + ","
					+ "'" + "no" + "'" + ")");
			System.out.println("发起人个人信息表添加信息成功！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 创建该问题ID的答案选项表
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
		// 创建该问题ID的邀请者项表，并更新相应邀请者的用户表
		if (message.getInvitee() != null && message.getInvitee().size() > 0) {
			try {
				stmt.execute("create table if not exists invitee"
						+ message.getID() + "(name text)");
				for (int i = 0; i < message.getInvitee().size(); i++) {
					stmt.execute("insert into invitee" + message.getID()
							+ " (name) " + "values(" + "'"
							+ message.getInvitee().get(i).getName() + "'"+ ")");
					// 更新数据库中被邀请者的信息
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
		// 根据投票人给的答案更新答案表（可能存在多个答案）
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
		
		//更新用户表，若该问题回答者是被邀请的人，故个人用户表中则存在此ID号；如果不是被邀请的人，则需在个人用户表中建立此问题ID
		try {
			stmt.execute("create table if not exists " + message.getAnswerName()
					+ " (questionID text,isAnswered text,isInvited text)");
			ResultSet rs = stmt.executeQuery("select * from " +message.getAnswerName()
					+" where questionID='"+message.getID()+"'");
			
			if(rs.next()) {
				//如果存在该消息记录（被邀请 或者自己发起的投票信息），则只需更改isAnswered属性
			Statement stmt1 = connectDB();
			stmt1.execute("update " + message.getAnswerName() + " set isAnswered='yes'"
				 + " where questionID='"+ message.getID() + "'");
			}else{
				//如果不存在，则插入该消息表
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
		//数据库中更新成功后修改返回消息的属性
		message.setIsAnswered("yes");
		
       try{
		// 更新投票消息中的answerNumber属性
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
		}// 链接数据库
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
	 * System.out.println("即将查询的表为：invitee"+String.valueOf(1)); while
	 * (rs2.next()) { String name=rs1.getString("name");
	 * 
	 * 
	 * }
	 * 
	 * }
	 */
}
