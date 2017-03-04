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
		//将List转化为数组
		List<Integer> choicesnumber=mVoteMessage.getAnswerNumber();
		size=choicesnumber.size();
		//int[] numbers=(int[])choicesnumber.toArray(new int[choicesnumber.size()]);
		double numbers[]=new double[size];
		double totalnumber=0;
		int j=0;
		String show_description="说明：\n";
		for(int i=0;i<size;i++){
			numbers[i]=choicesnumber.get(i);
			totalnumber=totalnumber+numbers[i];
			j=i+1;
			//System.out.println("输出结果："+mVoteMessage.getAnswerDetail().get(i).toString());
			show_description=show_description+j+"代表选项‘"+mVoteMessage.getChoices().get(i)+"’\n";
		}
		//String[] titles = new String[] { "投票结果" };
		String[] titles=new String[1];
		titles[0]=show_description;
		// 存放柱状图序列的值
		ArrayList<double[]> value = new ArrayList<double[]>();
		value.add(numbers);
		// 状的颜色
		int[] colors = { Color.BLUE };
		//设置x,y轴的范围
		double range[]=new double[4];
		//x轴，水平轴
		range[0]=0;
		range[1]=size+0.5;
		//y轴，竖直轴
		range[2]=0;
		range[3]=totalnumber;
		//由于x轴标签个数为size+1,故需设置具体的个数为size(起始点不算)的x轴标签值xLable[size]
		int xLable[]=new int[size];
		for(int i=0;i<size;i++){
			xLable[i]=i+1;
		}
		// 为li1添加柱状图
		resultLinear.addView(
				xychar(titles, value, colors, size+1, 1, range, xLable, "感谢您的参与！", true),
				new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
	}
/**
 * 
 * @param titles 绘制图形下面的标注（用于多个柱状图一起显示情况下用于区别）
 * @param value 柱状图中每一个柱状的值
 * @param colors 填充柱状图的颜色
 * @param x  水平x轴的标签个数
 * @param y  竖直y轴的标签个数
 * @param range 水平x轴与竖直y轴的起始值
 * @param xLable 坐标x轴上的标记
 * @param xtitle 图的小标题
 * @param f  是否显示具体每条柱状图的数字
 * @return  返回用于添加显示的view
 */
	public GraphicalView xychar(String[] titles, ArrayList<double[]> value,
			int[] colors, int x, int y, double[] range, int[] xLable,
			String xtitle, boolean f) {
		// 多个渲染
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		// 多个序列的数据集
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// 构建数据集以及渲染
		for (int i = 0; i < titles.length; i++) {

			XYSeries series = new XYSeries(titles[i]);
			double[] yLable = value.get(i);
			for (int j = 0; j < yLable.length; j++) {
				series.add(xLable[j], yLable[j]);
			}
			dataset.addSeries(series);
			XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
			// 设置颜色
			xyRenderer.setColor(colors[i]);
			// 设置点的样式 //
			xyRenderer.setPointStyle(PointStyle.SQUARE);
			// 将要绘制的点添加到坐标绘制中

			renderer.addSeriesRenderer(xyRenderer);
		}
		// 设置x轴标签数
		renderer.setXLabels(x);
		// 设置Y轴标签数
		renderer.setYLabels(y);
		// 设置x轴的最大值
		renderer.setXAxisMax(x - 0.5);
		// 设置轴的颜色
		renderer.setAxesColor(Color.BLACK);
		// 设置x轴和y轴的标签对齐方式
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		// 设置现实网格
		renderer.setShowGrid(true);

		renderer.setShowAxes(true);
		// 设置条形图之间的距离
		renderer.setBarSpacing(0.2);
		renderer.setInScroll(false);
		renderer.setPanEnabled(false, false);
		renderer.setClickEnabled(false);
		// 设置x轴和y轴标签的颜色
		renderer.setXLabelsColor(Color.RED);
		renderer.setYLabelsColor(0, Color.RED);

		int length = renderer.getSeriesRendererCount();
		// 设置图标的标题
		renderer.setChartTitle(xtitle);
		renderer.setLabelsColor(Color.RED);

		// 设置图例的字体大小
		renderer.setLegendTextSize(18);
		// 设置x轴和y轴的最大最小值
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
