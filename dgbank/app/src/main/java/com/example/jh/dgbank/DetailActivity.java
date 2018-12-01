package com.example.jh.dgbank;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends Activity {
    protected JSONObject mResult = null;
    protected RequestQueue mQueue = null;

    private TextView nametext;
    private TextView destext;
    private TextView whotext;
    private TextView howtext;
    private TextView lexpenstext;
    private TextView hexpenstext;
    private TextView lperiodtext;
    private TextView hperiodtext;
    private TextView name;
    private NetworkImageView img;

    protected ImageLoader mImageLoader=null;

    int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        nametext = (TextView)findViewById(R.id.name);
        destext = (TextView)findViewById(R.id.des);
        whotext = (TextView)findViewById(R.id.who);
        howtext = (TextView)findViewById(R.id.howway);
        lexpenstext = (TextView)findViewById(R.id.lmoney);
        hexpenstext = (TextView)findViewById(R.id.hmoney);
        lperiodtext = (TextView)findViewById(R.id.lterm);
        hperiodtext = (TextView)findViewById(R.id.hterm);
        img=(NetworkImageView)findViewById(R.id.image);
        name=(TextView)findViewById(R.id.nametext);

        mQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(this)));

            Intent i =getIntent();
            String s = i.getExtras().getString("kind");
            n = i.getExtras().getInt("num");

            if(s.equals("deposit")){
            name.setText("예금 신규 추천");
            requestinfo();
        }else{
            name.setText("적금 신규 추천");
            requestinfo1();
        }

    }

    protected void requestinfo(){
        String url = "http://14.49.39.100/deposit.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResult = response;

                        try{
                            JSONArray list = mResult.getJSONArray("list");
                            JSONObject node = list.getJSONObject(n);
                            String name = node.getString("name");
                            String des = node.getString("des");
                            String who = node.getString("who");
                            String how = node.getString("how");
                            String lperiod = node.getString("lperiod");
                            String hperiod = node.getString("hperiod");
                            String lexpens = node.getString("lexpens");
                            String hexpens = node.getString("hexpens");

                            nametext.setText(name);
                            destext.setText(des);
                            whotext.setText(who);
                            howtext.setText(how);
                            lperiodtext.setText(lperiod);
                            hperiodtext.setText(hperiod);
                            lexpenstext.setText(lexpens);
                            hexpenstext.setText(hexpens);
                            img.setImageUrl(node.getString("image"),mImageLoader);


                        }catch (JSONException | NullPointerException e){
                            Toast.makeText(DetailActivity.this, "여기", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailActivity.this, "디비 연결 실패", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        mQueue.add(request);
    }

    protected void requestinfo1(){
        String url = "http://14.49.39.100/savings.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResult = response;

                        try{
                            JSONArray list = mResult.getJSONArray("list");
                            JSONObject node = list.getJSONObject(n);
                            String name = node.getString("name");
                            String des = node.getString("des");
                            String who = node.getString("who");
                            String how = node.getString("how");
                            String lperiod = node.getString("lperiod");
                            String hperiod = node.getString("hperiod");
                            String lexpens = node.getString("lexpens");
                            String hexpens = node.getString("hexpens");

                            nametext.setText(name);
                            destext.setText(des);
                            whotext.setText(who);
                            howtext.setText(how);
                            lperiodtext.setText(lperiod);
                            hperiodtext.setText(hperiod);
                            lexpenstext.setText(lexpens);
                            hexpenstext.setText(hexpens);
                            img.setImageUrl(node.getString("image"),mImageLoader);


                        }catch (JSONException | NullPointerException e){
                            Toast.makeText(DetailActivity.this, "여기", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailActivity.this, "디비 연결 실패", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        mQueue.add(request);
    }
}
