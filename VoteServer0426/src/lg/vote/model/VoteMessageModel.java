package lg.vote.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author LG
 *
 *@˵��������һ��ͶƱ��Ϣ�Ļ�����Ϣ���������û�����ͷ���ͶƱ��ҵ��
 *
 */
public class VoteMessageModel implements Serializable{
	private int ID=0;//ͶƱ����Ψһ��ʶ��	
	private String Pollster=null;//ͶƱ������
	private String QuestionTitle=null;//ͶƱ������Ŀ
	private String QuestionDetail=null;//ͶƱ������ϸ����
	private List<String> Choices=null;//ͶƱ����𰸿�ѡ��	
	private List<Integer> AnswerNumber=null;//������ѡ���Ӧ�Ļش����
	private int AllowedAnswerNumber=0;//���ûش���Ӧ�ûش��ѡ�����
	private String StartTime=null;//���⿪ʼʱ��
	private String EndTime=null;//�����ֹʱ��
	private String IdentifyCode=null;//������֤�룬�ɲ��ַ�ֹ����
	private List<VoterModel> invitee=null; //����id��������Ӧ����
    private String AnswerName=null;//�ش��ߵ�ID��
    private String isAnswered=null;//�������Ƿ��Ѿ����ش�
    private List<String> AnswerDetail=null;//������Ӧ��������ã�����ID�û������Լ��Ĵ�,A.B....
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
