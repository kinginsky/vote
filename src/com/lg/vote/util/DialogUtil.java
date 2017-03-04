package com.lg.vote.util;

import com.lg.vote.Activity.LoginActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

public class DialogUtil {
	/**
	 * 
	 * @param context
	 * @param msg
	 * @param goHome
	 * @description:������ʾ��Ϣ�ĶԻ���
	 */
		public static void showDialog(final Context context,
				String title,String msg,boolean goHome){
			AlertDialog.Builder builder=new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setCancelable(false);
			if(goHome){
				builder.setPositiveButton("ȷ��", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog,int which){
						Intent i=new Intent(context,LoginActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						context.startActivity(i);					
					}
				});
			}
			else{
				builder.setPositiveButton("ȷ��", null);
			}
			builder.create().show();
		}
		/**
		 * 
		 * @param context
		 * @param view
		 * @˵������ʾ�ض�����ĶԻ���
		 */
		public static void showDialog(Context context,View view){
			new AlertDialog.Builder(context).setView(view).setCancelable(false).setPositiveButton("ȷ��", null)
			.create().show();
		}
}
