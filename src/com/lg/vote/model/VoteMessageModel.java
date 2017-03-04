package com.lg.vote.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author LG
 *
 *@说明：定义一条投票信息的基本信息量，用于用户参与和发起投票的业务。
 *
 */
public class VoteMessageModel implements Serializable{
	private int ID=0;//投票问题唯一标识符	
	private String Pollster=null;//投票发起人
	private String QuestionTitle=null;//投票问题题目
	private String QuestionDetail=null;//投票问题详细内容
	private List<String> Choices=null;//投票问题答案可选项	
	private List<Integer> AnswerNumber=null;//各个可选项对应的回答个数
	private int AllowedAnswerNumber=0;//设置回答者应该回答的选项个数
	private String StartTime=null;//问题开始时间
	private String EndTime=null;//问题截止时间
	private String IdentifyCode=null;//问题验证码，可部分防止作弊
	private List<VoterModel> invitee=null; //根据id号邀请相应好友
    private String AnswerName=null;//回答者的ID号
    private String isAnswered=null;//该问题是否已经被回答
    private List<String> AnswerDetail=null;//根据相应问题的设置，对于ID用户给出自己的答案,A.B....
	public VoteMessageModel(int iD, String pollster, String quesitonTitle,
			String quesitonDetail, List<String> choices,
			List<Integer> answerNumber, int allowedAnswerNumber,
			String startTime, String endTime, String identifyCode,
			List<VoterModel> invitee, String answerName,
			List<String> answerDetail) {
		super();
		ID = iD;
		Pollster = pollster;
		QuestionTitle = quesitonTitle;
		QuestionDetail = quesitonDetail;
		Choices = choices;
		AnswerNumber = answerNumber;
		AllowedAnswerNumber = allowedAnswerNumber;
		StartTime = startTime;
		EndTime = endTime;
		IdentifyCode = identifyCode;
		this.invitee = invitee;
		AnswerName = answerName;
		AnswerDetail = answerDetail;
	}
	public VoteMessageModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getPollster() {
		return Pollster;
	}
	public void setPollster(String pollster) {
		Pollster = pollster;
	}
	
	public String getQuestionTitle() {
		return QuestionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		QuestionTitle = questionTitle;
	}
	public String getQuestionDetail() {
		return QuestionDetail;
	}
	public void setQuestionDetail(String questionDetail) {
		QuestionDetail = questionDetail;
	}

	public String getIsAnswered() {
		return isAnswered;
	}
	public void setIsAnswered(String isAnswered) {
		this.isAnswered = isAnswered;
	}
	public List<String> getChoices() {
		return Choices;
	}
	public void setChoices(List<String> choices) {
		Choices = choices;
	}
	public List<Integer> getAnswerNumber() {
		return AnswerNumber;
	}
	public void setAnswerNumber(List<Integer> answerNumber) {
		AnswerNumber = answerNumber;
	}
	public int getAllowedAnswerNumber() {
		return AllowedAnswerNumber;
	}
	public void setAllowedAnswerNumber(int allowedAnswerNumber) {
		AllowedAnswerNumber = allowedAnswerNumber;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getIdentifyCode() {
		return IdentifyCode;
	}
	public void setIdentifyCode(String identifyCode) {
		IdentifyCode = identifyCode;
	}
	public List<VoterModel> getInvitee() {
		return invitee;
	}
	public void setInvitee(List<VoterModel> invitee) {
		this.invitee = invitee;
	}
	public String getAnswerName() {
		return AnswerName;
	}
	public void setAnswerName(String answerName) {
		AnswerName = answerName;
	}
	public List<String> getAnswerDetail() {
		return AnswerDetail;
	}
	public void setAnswerDetail(List<String> answerDetail) {
		AnswerDetail = answerDetail;
	}
	@Override
	public String toString() {
		return "VoteMessageModel [ID=" + ID + ", Pollster=" + Pollster
				+ ", QuestionTitle=" + QuestionTitle + ", QuestionDetail="
				+ QuestionDetail + ", Choices=" + Choices + ", AnswerNumber="
				+ AnswerNumber + ", AllowedAnswerNumber=" + AllowedAnswerNumber
				+ ", StartTime=" + StartTime + ", EndTime=" + EndTime
				+ ", IdentifyCode=" + IdentifyCode + ", invitee=" + invitee
				+ ", AnswerName=" + AnswerName + ", isAnswered=" + isAnswered
				+ ", AnswerDetail=" + AnswerDetail + "]";
	}
    
	
}
