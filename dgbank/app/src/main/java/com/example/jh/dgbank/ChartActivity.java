package com.example.jh.dgbank;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ChartActivity extends Activity {
    protected JSONObject mResult = null;
    protected RequestQueue mQueue = null;
    private LineChart lineChart;
    List<Entry> entries = new ArrayList<>();
    List<Entry> entries2 = new ArrayList<>();

    String s;
    int k, n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_main);

        mQueue = Volley.newRequestQueue(this);

        Intent i = getIntent();
        s = i.getExtras().getString("num");
        k = i.getExtras().getInt("int");

        if(k==2||k==3||k==4||k==10){
            n=244;
        }else{
            n=317;
        }
        lineChart = (LineChart)findViewById(R.id.chart);
        requestinfo();
    }

    protected void requestinfo(){
        String url = "http://14.49.39.100/" + s + ".php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResult = response;

                        try{
                            JSONArray list = mResult.getJSONArray("list");
                            for(int i = 100;i<=n;i++){
                                JSONObject node = list.getJSONObject(i);
                                entries.add(new Entry(i, (float)node.getDouble("price")));
                            }
                            for(int i = n;i<list.length();i++){
                                JSONObject node = list.getJSONObject(i);
                                entries2.add(new Entry(i, (float)node.getDouble("price")));
                            }

                            LineDataSet lineDataSet = new LineDataSet(entries, "현재 이율");
                            lineDataSet.setLineWidth(2);
                            lineDataSet.setCircleRadius(2);
                            lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
                            lineDataSet.setCircleColorHole(Color.BLUE);
                            lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
                            lineDataSet.setDrawCircleHole(true);
                            lineDataSet.setDrawCircles(true);
                            lineDataSet.setDrawHorizontalHighlightIndicator(false);
                            lineDataSet.setDrawHighlightIndicators(false);
                            lineDataSet.setDrawValues(false);

                            LineDataSet lineDataSet2 = new LineDataSet(entries2, "예상 이율");
                            lineDataSet2.setLineWidth(2);
                            lineDataSet2.setCircleRadius(2);
                            lineDataSet2.setCircleColor(Color.parseColor("#FFA7A7"));
                            lineDataSet2.setCircleColorHole(Color.RED);
                            lineDataSet2.setColor(Color.parseColor("#FFA7A7"));
                            lineDataSet2.setDrawCircleHole(true);
                            lineDataSet2.setDrawCircles(true);
                            lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                            lineDataSet2.setDrawHighlightIndicators(false);
                            lineDataSet2.setDrawValues(false);

                            LineData lineData = new LineData(lineDataSet);
                            lineData.addDataSet(lineDataSet2);
                            lineChart.setData(lineData);
                            XAxis xAxis = lineChart.getXAxis();
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setTextColor(Color.BLACK);
                            xAxis.enableGridDashedLine(8, 24, 0);
                            YAxis yLAxis = lineChart.getAxisLeft();
                            yLAxis.setTextColor(Color.BLACK);
                            YAxis yRAxis = lineChart.getAxisRight();
                            yRAxis.setDrawLabels(false);
                            yRAxis.setDrawAxisLine(false);
                            yRAxis.setDrawGridLines(false);

                            Description description = new Description();
                            description.setText("");
                            lineChart.setDoubleTapToZoomEnabled(false);
                            lineChart.setDrawGridBackground(false);
                            lineChart.setDescription(description);
                            lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
                            lineChart.invalidate();


                        }catch (JSONException | NullPointerException e){
                            Toast.makeText(ChartActivity.this, "여기" + k, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChartActivity.this, "디비 연결 실패", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        mQueue.add(request);
    }

}
