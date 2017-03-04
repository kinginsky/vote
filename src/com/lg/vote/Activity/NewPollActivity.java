package com.lg.vote.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.vote.R;
import com.lg.vote.Constants.Constants;
import com.lg.vote.application.MyApplication;
import com.lg.vote.db.SharedPreferenceUtil;
import com.lg.vote.db.VoteMessageDB;
import com.lg.vote.model.VoteMessageModel;
import com.lg.vote.model.VoterModel;
import com.lg.vote.util.HttpUtil;

import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.print.PrintAttributes.Margins;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewPollActivity extends Activity {
	public static int CHOICE_ID_IMAGEVIEW=100;
	public static int CHOICE_ID_EDITTEXT=200;
	public static int INVITEE_ID_IMAGEVIEW=300;
	public static int INVITEE_ID_EDITTEXT=400;	
	EditText questionEditText;
	EditText choice1EditText,choice2EditText,verification_input,invitee1EditText;
	LinearLayout choices_linearLayout,invitee_linearLayout;
	Spinner allowedAnswerNumber;
	TextView endTime_TextView;
	NumberPicker hourPicker,minutePicker;
	int choicesNumber,inviteeNumber,endTime_hour,endTime_minute;
	CheckBox checkBox,invitee_checkBox;
	VoteMessageModel mVoteMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_new_poll);
		android.app.ActionBar actionBar = getActionBar();
		actionBar.hide();
		init();
		
	}
	
     public void init(){
    	mVoteMessage=new VoteMessageModel();
    	choicesNumber=2;
    	inviteeNumber=0;
    	choices_linearLayout=(LinearLayout)findViewById(R.id.linearLayout_choices);
    	invitee_linearLayout=(LinearLayout)findViewById(R.id.linearLayout_invitee);
 		allowedAnswerNumber=(Spinner)findViewById(R.id.allowedNumber_spinner);
 		changeAnswerNumberSpinner();//��ʼ��spinner��ʾ���õĻش�ѡ�����
 		questionEditText=(EditText) findViewById(R.id.question);
 		choice1EditText=(EditText) findViewById(R.id.choice1);
 		choice2EditText=(EditText) findViewById(R.id.choice2);
 		invitee1EditText=(EditText)findViewById(R.id.invitee1);
        endTime_TextView=(TextView)findViewById(R.id.endTime_TextView);
        //�Զ�����ʾ��ֹʱ���ѡ���
        final View endtime_select_linearLayout=getLayoutInflater().inflate(R.layout.dialog_time_select, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(NewPollActivity.this).setIcon(R.drawable.ic_launcher)
				.setTitle("ѡ��ʣ��ʱ��")
				.setView(endtime_select_linearLayout)
				.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog,int which){
						endTime_TextView.setText(endTime_hour+"Сʱ"+endTime_minute+"����");
						//dialog.dismiss();
					}
				});
				
		final AlertDialog dialog=builder.create();
		//�����ʾʱ���TextView����Ӧ
        endTime_TextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.show();
			}
		});
        //����dialog���Զ�������ĳ�ʼ���ͼ���
        hourPicker=(NumberPicker)endtime_select_linearLayout.findViewById(R.id.hourPicker);
        minutePicker=(NumberPicker)endtime_select_linearLayout.findViewById(R.id.minutePicker);
        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        hourPicker.setOnValueChangedListener(new OnValueChangeListener(){

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				// TODO Auto-generated method stub
				endTime_hour=newVal;
			}
        	
        });
        minutePicker.setOnValueChangedListener(new OnValueChangeListener(){

     			@Override
     			public void onValueChange(NumberPicker picker, int oldVal,
     					int newVal) {
     				// TODO Auto-generated method stub
     				endTime_minute=newVal;
     			}
             	
             });
        //�����Ƿ���Ҫ��֤��
        checkBox=(CheckBox)findViewById(R.id.verification_checkBox);
        verification_input=(EditText)findViewById(R.id.verification_input);
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked){
					verification_input.setVisibility(View.VISIBLE);
					
				}else{
					verification_input.setVisibility(View.GONE);
				}
			}
		});
        //�����Ƿ���Ҫ���������
        invitee_checkBox=(CheckBox)findViewById(R.id.invitee_checkBox);
        final View invitee_add=(View)findViewById(R.id.invitee_add);
        invitee_checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked){
					invitee_linearLayout.setVisibility(View.VISIBLE);
					invitee_add.setVisibility(View.VISIBLE);
					++inviteeNumber;
					
				}else{
					invitee_linearLayout.setVisibility(View.GONE);
					invitee_add.setVisibility(View.GONE);
					inviteeNumber=0;
				}
			}
		});
     }
	
   //��̬���ѡƱ����Ӧ
	public void choices_add(View v){
		if(choicesNumber>2){
		ImageView choice_ImageView=(ImageView)findViewById(choicesNumber+NewPollActivity.CHOICE_ID_IMAGEVIEW);
		choice_ImageView.setVisibility(View.INVISIBLE);	
		}
		
		++choicesNumber;//���choices�ĸ���
		changeAnswerNumberSpinner();
		RelativeLayout new_choice_relativeLayout=getChoiceRelativeLayout();
		LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		choices_linearLayout.addView(new_choice_relativeLayout, LP_FW);
	}
	//��̬����������б�
	public void invitee_add(View v){
		if(inviteeNumber>1){
			ImageView choice_ImageView=(ImageView)findViewById(choicesNumber+NewPollActivity.INVITEE_ID_IMAGEVIEW);
			choice_ImageView.setVisibility(View.INVISIBLE);	
		}
		RelativeLayout new_invitee_relativeLayout=getInviteeRelativeLayout();
		LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		invitee_linearLayout.addView(new_invitee_relativeLayout, LP_FW);
	}
	//��̬���ѡƱ�����ļ���id
	public RelativeLayout getChoiceRelativeLayout(){
		RelativeLayout new_choice_relativeLayout=new RelativeLayout(this);
		new_choice_relativeLayout.setId(choicesNumber);
		//���TextView
		TextView tv = new TextView(this);
		RelativeLayout.LayoutParams RL_WW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);//����ע�����λ�ã��õ��Ǹ������Ĳ��ֲ���
		RL_WW.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		tv.setLayoutParams(RL_WW);
		tv.setPadding(10, 0, 0, 0);
		tv.setText(choicesNumber+".");
		tv.setTextColor(Color.rgb(0xa0, 0x00, 0x00));
		tv.setTextSize(20);
		new_choice_relativeLayout.addView(tv);
		//���EditText
		EditText editText=new EditText(this);
		RelativeLayout.LayoutParams RL_FW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		RL_FW.setMargins(30, 0, 30, 0);
		editText.setLayoutParams(RL_FW);
		editText.setId(NewPollActivity.CHOICE_ID_EDITTEXT+choicesNumber);
		new_choice_relativeLayout.addView(editText);
	
		//���imageView
		final ImageView imageView=new ImageView(this);
		imageView.setId(choicesNumber+NewPollActivity.CHOICE_ID_IMAGEVIEW);
			RL_WW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
			RL_WW.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			imageView.setPadding(0, 0, 3, 0);
			imageView.setLayoutParams(RL_WW);
			imageView.setImageResource(R.drawable.ic_del);
			new_choice_relativeLayout.addView(imageView);	
			imageView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("�����imageView��ID��Ϊ��"+imageView.getId());
					RelativeLayout relativeLayout=(RelativeLayout)findViewById(choicesNumber);
					//relativeLayout.setVisibility(View.GONE);
					choices_linearLayout.removeView(relativeLayout);
					--choicesNumber;
					changeAnswerNumberSpinner();
					System.out.println("�����imageView��choicesNumberΪ��"+choicesNumber);
					if(choicesNumber>2){
						ImageView choice_ImageView=(ImageView)findViewById(choicesNumber+NewPollActivity.CHOICE_ID_IMAGEVIEW);
						choice_ImageView.setVisibility(View.VISIBLE);	
					}
				}
				
				
			});
			return new_choice_relativeLayout;
	}
	//��̬����������б�
	public RelativeLayout getInviteeRelativeLayout(){
		
		RelativeLayout new_invitee_relativeLayout=new RelativeLayout(this);
		new_invitee_relativeLayout.setId(inviteeNumber);
		//���TextView
		TextView tv = new TextView(this);
		RelativeLayout.LayoutParams RL_WW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);//����ע�����λ�ã��õ��Ǹ������Ĳ��ֲ���
		RL_WW.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		tv.setLayoutParams(RL_WW);
		tv.setPadding(10, 0, 0, 0);
		tv.setText(choicesNumber+".");
		tv.setTextColor(Color.rgb(0xa0, 0x00, 0x00));
		tv.setTextSize(20);
		new_invitee_relativeLayout.addView(tv);
		//���EditText
		EditText editText=new EditText(this);
		RelativeLayout.LayoutParams RL_FW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		RL_FW.setMargins(30, 0, 30, 0);
		editText.setLayoutParams(RL_FW);
		editText.setId(NewPollActivity.INVITEE_ID_EDITTEXT+inviteeNumber);
		new_invitee_relativeLayout.addView(editText);
	
		//���imageView
		final ImageView imageView=new ImageView(this);
		imageView.setId(inviteeNumber+NewPollActivity.INVITEE_ID_IMAGEVIEW);
			RL_WW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
			RL_WW.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			imageView.setPadding(0, 0, 3, 0);
			imageView.setLayoutParams(RL_WW);
			imageView.setImageResource(R.drawable.ic_del);
			new_invitee_relativeLayout.addView(imageView);	
			imageView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					RelativeLayout relativeLayout=(RelativeLayout)findViewById(inviteeNumber);
					//relativeLayout.setVisibility(View.GONE);
					invitee_linearLayout.removeView(relativeLayout);
					--inviteeNumber;
					System.out.println("�����imageView��inviteeNumberΪ��"+inviteeNumber);
					if(inviteeNumber>1){
						ImageView invitee_ImageView=(ImageView)findViewById(inviteeNumber+NewPollActivity.INVITEE_ID_IMAGEVIEW);
						invitee_ImageView.setVisibility(View.VISIBLE);	
					}
				}
				
				
			});
			return new_invitee_relativeLayout;
	}
	//����choicesNumber�ĸı䣬��̬����spinner������
	public void changeAnswerNumberSpinner(){
		final String number[]=new String[choicesNumber];
 		for(int i=0;i<choicesNumber;i++){
 			number[i]=i+1+"";
 		}
 		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, number);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allowedAnswerNumber.setAdapter(adapter);
        allowedAnswerNumber.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
            	//���ø�����Ļش�ѡ�����
                mVoteMessage.setAllowedAnswerNumber(arg2+1);
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0){
                //
            }
        });
	}
	//����ͶƱ��ť����Ӧ
	public void btn_poll(View v){
		
		//����ͶƱ��������Ϣ
		SharedPreferenceUtil ps=new SharedPreferenceUtil(this,Constants.SAVE_VOTER);
	    mVoteMessage.setPollster(ps.getName());
		mVoteMessage.setQuestionDetail(questionEditText.getText().toString());
		//���ش�ѡ�����Ը�ֵ
		List<String> choices=new ArrayList<String>();
		if(choice1EditText.getText().toString()==""){
			Toast.makeText(this, "ѡƱ1����Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}else{
			choices.add(choice1EditText.getText().toString());
		}
		if(choice2EditText.getText().toString()==""){
			Toast.makeText(this, "ѡƱ2����Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}else{
			choices.add(choice2EditText.getText().toString());
		}
		
		if(choicesNumber>2){
			for(int i=2;i<choicesNumber;i++){
				EditText choiceEditText=(EditText)findViewById(i+1+NewPollActivity.CHOICE_ID_EDITTEXT);
				if(choiceEditText.getText().toString()==""){
					Toast.makeText(this, "ѡƱ"+i+1+"����Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}else{
					choices.add(choiceEditText.getText().toString());
				}
			}
		}
		mVoteMessage.setChoices(choices);
		//Ϊÿ��ѡ��Ĳ���������ֵΪ0
		List<Integer> choicenumberList=new ArrayList<Integer>();
		for(int i=0;i<choicesNumber;i++){
			choicenumberList.add(0);
		}
	    mVoteMessage.setAnswerNumber(choicenumberList);
		mVoteMessage.setStartTime("");
		mVoteMessage.setEndTime("");
		//������֤��
		if(checkBox.isChecked()){
			if(verification_input.getText().toString()=="")
			{
				Toast.makeText(this,"��������֤��", Toast.LENGTH_SHORT).show();
				return;
			}else{
				mVoteMessage.setIdentifyCode(verification_input.getText().toString());
			}
		}
		//��ֵͶƱ����Ϣ
		List<VoterModel> invitee=new ArrayList<VoterModel>();
		//Ĭ�ϵĵ�һ��ͶƱ��ѡ��
		if(inviteeNumber>0)
		{
		if(invitee1EditText.getText().toString()==""){
			Toast.makeText(this, "������1����Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}else{
			VoterModel voter=new VoterModel();
			voter.setName(invitee1EditText.getText().toString());
			invitee.add(voter);
		}
		}
		if(inviteeNumber>1){
			for(int i=1;i<inviteeNumber;i++){
				EditText inviteeEditText=(EditText)findViewById(i+1+NewPollActivity.INVITEE_ID_EDITTEXT);
				if(inviteeEditText.getText().toString()==""){
					Toast.makeText(this, "�������б�"+i+1+"����Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}else{
					VoterModel voter=new VoterModel();
					voter.setName(inviteeEditText.getText().toString());
					invitee.add(voter);
				}
			}
		}
		mVoteMessage.setInvitee(invitee);
		System.out.println("������������"+mVoteMessage.getInvitee().size());
		for(int i=0;i<invitee.size();i++){
			System.out.println("������������"+mVoteMessage.getInvitee().get(i).getName());
		}
		
		System.out.println("���������ͶƱ��ϢΪ��"+mVoteMessage.toString());
		//���͸������
		String result=sendToServer(mVoteMessage);
		//�����ܵ����ַ���ת��Ϊ����
		ObjectMapper mapper = new ObjectMapper();
		try {
			mVoteMessage=mapper.readValue(result, VoteMessageModel.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(mVoteMessage.getID()!=0){
			Toast.makeText(this, " Poll Succeed!",Toast.LENGTH_LONG).show();
			MyApplication.messagelist.add(mVoteMessage);
			System.out.println("��������ȫ���������ϢID��Ϊ��"+mVoteMessage.getID());
		this.finish();
		
		}
		else{
			Toast.makeText(this, " Poll Failed!",Toast.LENGTH_LONG).show();
		}
		
	}
	/**
	 * 
	 * @param message
	 * @return
	 *    ���û��༭��ͶƱ��Ϣ���͸��������ˣ������ǰ����ø�ID�ŵ���Ϣ��
	 */
	public String sendToServer(VoteMessageModel message){
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String voterjson,result = null;
		try {
			voterjson = mapper.writeValueAsString(message);
			map.put("json", voterjson);
		 String url = Constants.BASE_URL + "/PollServlet";
		 try {
			result = HttpUtil.postRequest(url, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void btn_back_poll(View v){
		this.finish();
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_poll, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
