package com.lg.vote.Activity;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lg.vote.R;
import com.lg.vote.model.VoteMessageModel;

public class VoteResultActivity extends Activity {
	private VoteMessageModel mVoteMessage;
	private LinearLayout resultLinear;
	private TextView description;
    private int size;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote_result);
		android.app.ActionBar actionBar = getActionBar();
		actionBar.hide();
		mVoteMessage = (VoteMessageModel) getIntent().getSerializableExtra(
				"VoteMessage");
		System.out.println("The result is :" + mVoteMessage.toString());

		initView();
	}

	public void initView() {
		resultLinear = (LinearLayout) findViewById(R.id.vote_result);
		//description=(TextView)findViewById(R.id.result_description);
		//��Listת��Ϊ����
		List<Integer> choicesnumber=mVoteMessage.getAnswerNumber();
		size=choicesnumber.size();
		//int[] numbers=(int[])choicesnumber.toArray(new int[choicesnumber.size()]);
		double numbers[]=new double[size];
		double totalnumber=0;
		int j=0;
		String show_description="˵����\n";
		for(int i=0;i<size;i++){
			numbers[i]=choicesnumber.get(i);
			totalnumber=totalnumber+numbers[i];
			j=i+1;
			//System.out.println("��������"+mVoteMessage.getAnswerDetail().get(i).toString());
			show_description=show_description+j+"����ѡ�"+mVoteMessage.getChoices().get(i)+"��\n";
		}
		//String[] titles = new String[] { "ͶƱ���" };
		String[] titles=new String[1];
		titles[0]=show_description;
		// �����״ͼ���е�ֵ
		ArrayList<double[]> value = new ArrayList<double[]>();
		value.add(numbers);
		// ״����ɫ
		int[] colors = { Color.BLUE };
		//����x,y��ķ�Χ
		double range[]=new double[4];
		//x�ᣬˮƽ��
		range[0]=0;
		range[1]=size+0.5;
		//y�ᣬ��ֱ��
		range[2]=0;
		range[3]=totalnumber;
		//����x���ǩ����Ϊsize+1,�������þ���ĸ���Ϊsize(��ʼ�㲻��)��x���ǩֵxLable[size]
		int xLable[]=new int[size];
		for(int i=0;i<size;i++){
			xLable[i]=i+1;
		}
		// Ϊli1�����״ͼ
		resultLinear.addView(
				xychar(titles, value, colors, size+1, 1, range, xLable, "��л���Ĳ��룡", true),
				new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
	}
/**
 * 
 * @param titles ����ͼ������ı�ע�����ڶ����״ͼһ����ʾ�������������
 * @param value ��״ͼ��ÿһ����״��ֵ
 * @param colors �����״ͼ����ɫ
 * @param x  ˮƽx��ı�ǩ����
 * @param y  ��ֱy��ı�ǩ����
 * @param range ˮƽx������ֱy�����ʼֵ
 * @param xLable ����x���ϵı��
 * @param xtitle ͼ��С����
 * @param f  �Ƿ���ʾ����ÿ����״ͼ������
 * @return  �������������ʾ��view
 */
	public GraphicalView xychar(String[] titles, ArrayList<double[]> value,
			int[] colors, int x, int y, double[] range, int[] xLable,
			String xtitle, boolean f) {
		// �����Ⱦ
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		// ������е����ݼ�
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// �������ݼ��Լ���Ⱦ
		for (int i = 0; i < titles.length; i++) {

			XYSeries series = new XYSeries(titles[i]);
			double[] yLable = value.get(i);
			for (int j = 0; j < yLable.length; j++) {
				series.add(xLable[j], yLable[j]);
			}
			dataset.addSeries(series);
			XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
			// ������ɫ
			xyRenderer.setColor(colors[i]);
			// ���õ����ʽ //
			xyRenderer.setPointStyle(PointStyle.SQUARE);
			// ��Ҫ���Ƶĵ���ӵ����������

			renderer.addSeriesRenderer(xyRenderer);
		}
		// ����x���ǩ��
		renderer.setXLabels(x);
		// ����Y���ǩ��
		renderer.setYLabels(y);
		// ����x������ֵ
		renderer.setXAxisMax(x - 0.5);
		// ���������ɫ
		renderer.setAxesColor(Color.BLACK);
		// ����x���y��ı�ǩ���뷽ʽ
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		// ������ʵ����
		renderer.setShowGrid(true);

		renderer.setShowAxes(true);
		// ��������ͼ֮��ľ���
		renderer.setBarSpacing(0.2);
		renderer.setInScroll(false);
		renderer.setPanEnabled(false, false);
		renderer.setClickEnabled(false);
		// ����x���y���ǩ����ɫ
		renderer.setXLabelsColor(Color.RED);
		renderer.setYLabelsColor(0, Color.RED);

		int length = renderer.getSeriesRendererCount();
		// ����ͼ��ı���
		renderer.setChartTitle(xtitle);
		renderer.setLabelsColor(Color.RED);

		// ����ͼ���������С
		renderer.setLegendTextSize(18);
		// ����x���y��������Сֵ
		renderer.setRange(range);
		renderer.setMarginsColor(0x00888888);
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer ssr = renderer.getSeriesRendererAt(i);
			ssr.setChartValuesTextAlign(Align.RIGHT);
			ssr.setChartValuesTextSize(12);
			ssr.setDisplayChartValues(f);
		}
		GraphicalView mChartView = ChartFactory.getBarChartView(
				getApplicationContext(), dataset, renderer, Type.DEFAULT);

		return mChartView;

	}

	public void btn_back_result(View v) {
		this.finish();
		Intent intent=new Intent(this,MainActivity.class);
		this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vote_result, menu);
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
