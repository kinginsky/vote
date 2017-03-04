package com.lg.vote.db;

import java.util.ArrayList;
import java.util.List;

import com.lg.vote.model.VoteMessageModel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author LG
 *
 *         ˵��������ͶƱ��Ϣ�������ݿ⣬�û����������������ͶƱ��Ϣ�� ���ݿ�����VoteMessages; ������voter
 * 
 */
public class VoteMessageDB {
	private SQLiteDatabase db;

	public VoteMessageDB(Context context) {
		super();
		// TODO Auto-generated constructor stub
		db = context.openOrCreateDatabase("VoteMessages", 0, null);
		db.execSQL("CREATE table IF NOT EXISTS voter"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, ID TEXT, PollName TEXT, PollTime TEXT,"
				+ "Question TEXT,Answer1 TEXT,Answer2 TEXT,Answer3 TEXT,Answer4 TEXT,"
				+ "Answer1Number TEXT,Answer2Number TEXT,Answer3Number TEXT,Answer4Number TEXT)");
	}

	/*********************** ������ȡ����Ϣ�б���������ݿ��б� ***************************/
	public void updateVoteMessageList(List<VoteMessageModel> list) {
		System.out.println("--------�ٴ������Ϣ����ĿΪ��"+list.size());
		if (list.size() > 0) {
			db.execSQL("delete from voter");
			for (VoteMessageModel u : list) {
				/*db.execSQL(
						"insert into voter (ID,PollName,Question,Answer1,Answer2,Answer3,Answer4,"
						+ "Answer1Number,Answer2Number,Answer3Number,Answer4Number) values(?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { u.getID(), u.getPollName(), u.getQuestion() ,
								u.getAnswer1(),u.getAnswer2(),u.getAnswer3(),u.getAnswer4(),
								u.getAnswer1Number(),u.getAnswer2Number(),u.getAnswer3Number(),u.getAnswer4Number()});
				*/
			}
		}

	}
	/*********************** ������ȡ����Ϣ�б���������ݿ��б� (��Ҫ��ͶƱ��Ϣ)***************************/
	public void deleteVoteMessage(VoteMessageModel mVoteMessage) {
		
		 db.execSQL("delete from voter Where ID=?",new String[] { mVoteMessage.getID()+""});
			System.out.println("--------�ɹ�ɾ���û���ID=��"+mVoteMessage.getID());

		}



	  /*********************** ��ȡͶƱ��Ϣ ***************************/
	public List<VoteMessageModel> getVoteMessageList() {
		List<VoteMessageModel> list = new ArrayList<VoteMessageModel>();
		Cursor c = db.rawQuery("select * from voter", null);
		while (c.moveToNext()) {
			VoteMessageModel u = new VoteMessageModel();
			/*u.setPollName(c.getString(c.getColumnIndex("PollName")));
			u.setID(c.getInt(c.getColumnIndex("ID")));
			u.setQuestion(c.getString(c.getColumnIndex("Question")));
			
			u.setAnswer1(c.getString(c.getColumnIndex("Answer1")));
			u.setAnswer2(c.getString(c.getColumnIndex("Answer2")));
			u.setAnswer3(c.getString(c.getColumnIndex("Answer3")));
			u.setAnswer4(c.getString(c.getColumnIndex("Answer4")));
			
			u.setAnswer1Number(c.getInt(c.getColumnIndex("Answer1Number")));
			u.setAnswer2Number(c.getInt(c.getColumnIndex("Answer2Number")));
			u.setAnswer3Number(c.getInt(c.getColumnIndex("Answer3Number")));
			u.setAnswer4Number(c.getInt(c.getColumnIndex("Answer4Number")));
*/
			list.add(u);
		}
		c.close();
		// db.close();
		return list;
	}

       /**************************����ID��ȡͶƱ����*****************************/
	public VoteMessageModel selectInfo(int ID) {
		VoteMessageModel u = new VoteMessageModel();
		Cursor c = db.rawQuery("select * from voter where ID=?",
				new String[] {ID + "" });
		if (c.moveToFirst()) {
			
			/*u.setID(c.getInt(c.getColumnIndex("ID")));
			u.setPollName(c.getString(c.getColumnIndex("PollName")));
			u.setQuestion(c.getString(c.getColumnIndex("Question")));
			
			u.setAnswer1(c.getString(c.getColumnIndex("Answer1")));
			u.setAnswer2(c.getString(c.getColumnIndex("Answer2")));
			u.setAnswer3(c.getString(c.getColumnIndex("Answer3")));
			u.setAnswer4(c.getString(c.getColumnIndex("Answer4")));
			
			u.setAnswer1Number(c.getInt(c.getColumnIndex("Answer1Number")));
			u.setAnswer2Number(c.getInt(c.getColumnIndex("Answer2Number")));
			u.setAnswer3Number(c.getInt(c.getColumnIndex("Answer3Number")));
			u.setAnswer4Number(c.getInt(c.getColumnIndex("Answer4Number")));*/

		}
		return u;
	}
}
