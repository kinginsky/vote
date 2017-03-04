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
	 * @description:用于显示消息的对话框
	 */
		public static void showDialog(final Context context,
				String title,String msg,boolean goHome){
			AlertDialog.Builder builder=new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setCancelable(false);
			if(goHome){
				builder.setPositiveButton("确定", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog,int which){
						Intent i=new Intent(context,LoginActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						context.startActivity(i);					
					}
				});
			}
			else{
				builder.setPositiveButton("确定", null);
			}
			builder.create().show();
		}
		/**
		 * 
		 * @param context
		 * @param view
		 * @说明：显示特定组件的对话框
		 */
		public static void showDialog(Context context,View view){
			new AlertDialog.Builder(context).setView(view).setCancelable(false).setPositiveButton("确定", null)
			.create().show();
		}
}
