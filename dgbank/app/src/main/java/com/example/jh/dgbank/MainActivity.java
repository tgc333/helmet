package com.example.jh.dgbank;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.TabActivity;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;

import android.widget.TabHost.TabSpec;
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
import com.github.mikephil.charting.charts.Chart;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends TabActivity {

    protected JSONObject mResult = null;
    protected JSONObject mResult1 = null;
    protected JSONObject mResult2 = null;
    protected JSONObject mResult3 = null;
    protected RequestQueue mQueue = null;
    private Button database;
    private Button fundchart;
    private Button depositdetail1;
    private Button depositdetail2;
    private Button depositdetail3;
    private Button savingsdetail1;
    private Button savingsdetail2;
    private Button savingsdetail3;
    private TextView fund[] = new TextView[14];
    private TextView funddes[] = new TextView[14];
    private Button fundbtn[] = new Button[14];

    private TextView nametext;
    private TextView agetext;
    private TextView tendency;
    private TextView deposit1;
    private TextView deposit2;
    private TextView deposit3;
    private TextView depositdes1;
    private TextView depositdes2;
    private TextView depositdes3;
    private TextView depositrate1;
    private TextView depositrate2;
    private TextView depositrate3;
    private TextView predictdepositrate1;
    private TextView predictdepositrate2;
    private TextView predictdepositrate3;
    private TextView savings1;
    private TextView savings2;
    private TextView savings3;
    private TextView savingsdes1;
    private TextView savingsdes2;
    private TextView savingsdes3;
    private TextView savingsrate1;
    private TextView savingsrate2;
    private TextView savingsrate3;
    private TextView predictsavingsrate1;
    private TextView predictsavingsrate2;
    private TextView predictsavingsrate3;
    private int n, k;
    private int depositn1;
    private int depositn2;
    private int depositn3;
    private int savingsn1;
    private int savingsn2;
    private int savingsn3;

    private NetworkImageView depositimage1;
    private NetworkImageView depositimage2;
    private NetworkImageView depositimage3;
    private NetworkImageView savingsimage1;
    private NetworkImageView savingsimage2;
    private NetworkImageView savingsimage3;

    protected ImageLoader mImageLoader=null;

    int tenint;
    String url3;
    int fundnum;

    Random r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkUtil.setNetworkPolicy();
        TabHost tabHost = getTabHost();
        database = (Button)findViewById(R.id.database);
        fundchart = (Button)findViewById(R.id.fundbtn1);
        depositdetail1 = (Button)findViewById(R.id.depositdetail1);
        depositdetail2 = (Button)findViewById(R.id.depositdetail2);
        depositdetail3 = (Button)findViewById(R.id.depositdetail3);
        savingsdetail1 = (Button)findViewById(R.id.savingsdetail1);
        savingsdetail2 = (Button)findViewById(R.id.savingsdetail2);
        savingsdetail3 = (Button)findViewById(R.id.savingsdetail3);
        fund[0] = (TextView)findViewById(R.id.fund1);
        fund[1] = (TextView)findViewById(R.id.fund2);
        fund[2] = (TextView)findViewById(R.id.fund3);
        fund[3] = (TextView)findViewById(R.id.fund4);
        fund[4] = (TextView)findViewById(R.id.fund5);
        fund[5] = (TextView)findViewById(R.id.fund6);
        fund[6] = (TextView)findViewById(R.id.fund7);
        fund[7] = (TextView)findViewById(R.id.fund8);
        fund[8] = (TextView)findViewById(R.id.fund9);
        fund[9] = (TextView)findViewById(R.id.fund10);
        fund[10] = (TextView)findViewById(R.id.fund11);
        fund[11] = (TextView)findViewById(R.id.fund12);
        fund[12] = (TextView)findViewById(R.id.fund13);
        funddes[0] = (TextView)findViewById(R.id.funddes1);
        funddes[1] = (TextView)findViewById(R.id.funddes2);
        funddes[2] = (TextView)findViewById(R.id.funddes3);
        funddes[3] = (TextView)findViewById(R.id.funddes4);
        funddes[4] = (TextView)findViewById(R.id.funddes5);
        funddes[5] = (TextView)findViewById(R.id.funddes6);
        funddes[6] = (TextView)findViewById(R.id.funddes7);
        funddes[7] = (TextView)findViewById(R.id.funddes8);
        funddes[8] = (TextView)findViewById(R.id.funddes9);
        funddes[9] = (TextView)findViewById(R.id.funddes10);
        funddes[10] = (TextView)findViewById(R.id.funddes11);
        funddes[11] = (TextView)findViewById(R.id.funddes12);
        funddes[12] = (TextView)findViewById(R.id.funddes13);
        fundbtn[1] =(Button)findViewById(R.id.fundbtn1);
        fundbtn[2] =(Button)findViewById(R.id.fundbtn2);
        fundbtn[3] =(Button)findViewById(R.id.fundbtn3);
        fundbtn[4] =(Button)findViewById(R.id.fundbtn4);
        fundbtn[5] =(Button)findViewById(R.id.fundbtn5);
        fundbtn[6] =(Button)findViewById(R.id.fundbtn6);
        fundbtn[7] =(Button)findViewById(R.id.fundbtn7);
        fundbtn[8] =(Button)findViewById(R.id.fundbtn8);
        fundbtn[9] =(Button)findViewById(R.id.fundbtn9);
        fundbtn[10] =(Button)findViewById(R.id.fundbtn10);
        fundbtn[11] =(Button)findViewById(R.id.fundbtn11);
        fundbtn[12] =(Button)findViewById(R.id.fundbtn12);
        fundbtn[13] =(Button)findViewById(R.id.fundbtn13);



        nametext = (TextView)findViewById(R.id.name);
        agetext = (TextView)findViewById(R.id.age);
        tendency = (TextView)findViewById(R.id.tendency);
        deposit1 = (TextView)findViewById(R.id.deposit1);
        deposit2 = (TextView)findViewById(R.id.deposit2);
        deposit3 = (TextView)findViewById(R.id.deposit3);
        depositdes1 = (TextView)findViewById(R.id.depositdes1);
        depositdes2 = (TextView)findViewById(R.id.depositdes2);
        depositdes3 = (TextView)findViewById(R.id.depositdes3);
        depositrate1 = (TextView)findViewById(R.id.depositrate1);
        depositrate2 = (TextView)findViewById(R.id.depositrate2);
        depositrate3 = (TextView)findViewById(R.id.depositrate3);
        depositimage1 = (NetworkImageView)findViewById(R.id.depositimage1);
        depositimage2 = (NetworkImageView)findViewById(R.id.depositimage2);
        depositimage3 = (NetworkImageView)findViewById(R.id.depositimage3);
        predictdepositrate1 = (TextView)findViewById(R.id.predepositrate1);
        predictdepositrate2 = (TextView)findViewById(R.id.predepositrate2);
        predictdepositrate3 = (TextView)findViewById(R.id.predepositrate3);
        savings1 = (TextView)findViewById(R.id.savings1);
        savings2 = (TextView)findViewById(R.id.savings2);
        savings3 = (TextView)findViewById(R.id.savings3);
        savingsdes1 = (TextView)findViewById(R.id.savingsdesd1);
        savingsdes2 = (TextView)findViewById(R.id.savingsdesd2);
        savingsdes3 = (TextView)findViewById(R.id.savingsdesd3);
        savingsrate1 = (TextView)findViewById(R.id.savingsrate1);
        savingsrate2 = (TextView)findViewById(R.id.savingsrate2);
        savingsrate3 = (TextView)findViewById(R.id.savingsrate3);
        savingsimage1 = (NetworkImageView)findViewById(R.id.savingsimage1);
        savingsimage2 = (NetworkImageView)findViewById(R.id.savingsimage2);
        savingsimage3 = (NetworkImageView)findViewById(R.id.savingsimage3);
        predictsavingsrate1 = (TextView)findViewById(R.id.presavigsrate1);
        predictsavingsrate2 = (TextView)findViewById(R.id.presavigsrate2);
        predictsavingsrate3 = (TextView)findViewById(R.id.presavigsrate3);

        url3 = "http://14.49.39.100/fundtable.php";

        NetworkUtil.setNetworkPolicy();

        mQueue= Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(this)));

        r = new Random();

        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = r.nextInt(5);

                if(tenint==1) {
                    tendency.setText("저위험군");
                    url3 = "http://14.49.39.100/fundtable1.php";
                }
                else if(tenint==2) {
                    tendency.setText("중위험군");
                    url3 = "http://14.49.39.100/fundtable2.php";
                }
                else if(tenint==3) {
                    tendency.setText("고위험군");
                    url3 = "http://14.49.39.100/fundtable3.php";
                }

                requestinfo();
            }
        });
        fundchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(i);
            }
        });
        depositdetail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("kind", "deposit");
                i.putExtra("num", depositn1);
                startActivity(i);
            }
        });
        depositdetail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("kind", "deposit");
                i.putExtra("num", depositn2);
                startActivity(i);
            }
        });
        depositdetail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("kind", "deposit");
                i.putExtra("num", depositn3);
                startActivity(i);
            }
        });
        savingsdetail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("kind", "savings");
                i.putExtra("num", savingsn1);
                startActivity(i);
            }
        });
        savingsdetail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("kind", "savings");
                i.putExtra("num", savingsn2);
                startActivity(i);
            }
        });
        savingsdetail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("kind", "savings");
                i.putExtra("num", savingsn3);
                startActivity(i);
            }
        });

        for(k = 1; k<=13;k++){
            fundbtn[k].setOnClickListener(new View.OnClickListener() {
                int t = k;
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, ChartActivity.class);
                    i.putExtra("num", "fund" + t);
                    i.putExtra("int", t);
                    startActivity(i);
                }
            });
        }




        TabSpec tabSpecTab1 = tabHost.newTabSpec("TAB1").setIndicator("내 정보");
        tabSpecTab1.setContent(R.id.tab1);
        tabHost.addTab(tabSpecTab1);
        TabSpec tabSpecTab2 = tabHost.newTabSpec("TAB2").setIndicator("예금 추천");
        tabSpecTab2.setContent(R.id.tab2);
        tabHost.addTab(tabSpecTab2);
        TabSpec tabSpecTab3 = tabHost.newTabSpec("TAB3").setIndicator("적금 추천");
        tabSpecTab3.setContent(R.id.tab3);
        tabHost.addTab(tabSpecTab3);
        TabSpec tabSpecTab4 = tabHost.newTabSpec("TAB4").setIndicator("펀드 추천");
        tabSpecTab4.setContent(R.id.tab4);
        tabHost.addTab(tabSpecTab4);
        tabHost.setCurrentTab(0);
    }

    protected void requestinfo(){
        String url = "http://14.49.39.100/user2.php";
        String url1 = "http://14.49.39.100/deposit.php";
        String url2 = "http://14.49.39.100/savings.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResult = response;

                        try{
                            JSONArray list = mResult.getJSONArray("list");
                            JSONObject node = list.getJSONObject(n);
                            String name = node.getString("name");
                            String age = node.getString("age");
                            nametext.setText(name.toString());
                            agetext.setText(age.toString());
                            tenint = node.getInt("tendency");
                            if(tenint==1) {
                                tendency.setText("저위험군");
                                url3 = "http://14.49.39.100/fundtable1.php";
                            }
                            else if(tenint==2) {
                                tendency.setText("중위험군");
                                url3 = "http://14.49.39.100/fundtable2.php";
                            }
                            else if(tenint==3) {
                                tendency.setText("고위험군");
                                url3 = "http://14.49.39.100/fundtable3.php";
                            }
                            requestinfo1();

                            depositn1 = node.getInt("deposit1")-1;
                            depositn2 = node.getInt("deposit2")-1;
                            depositn3 = node.getInt("deposit3")-1;
                            savingsn1 = node.getInt("savings1")-1;
                            savingsn2 = node.getInt("savings2")-1;
                            savingsn3 = node.getInt("savings3")-1;
                            predictdepositrate1.setText(node.getString("d_interest1") + "%");
                            predictdepositrate2.setText(node.getString("d_interest2") + "%");
                            predictdepositrate3.setText(node.getString("d_interest3") + "%");
                            predictsavingsrate1.setText(node.getString("s_interest1") + "%");
                            predictsavingsrate2.setText(node.getString("s_interest2") + "%");
                            predictsavingsrate3.setText(node.getString("s_interest3") + "%");


                        }catch (JSONException | NullPointerException e){
                            Toast.makeText(MainActivity.this, "여기", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "디비 연결 실패", Toast.LENGTH_SHORT).show();

                    }
                }
        );

        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResult1 = response;

                        try{
                            JSONArray list = mResult1.getJSONArray("list");
                            JSONObject node = list.getJSONObject(depositn1);
                            String depositname1 = node.getString("name");
                            String depositdesc1 = node.getString("des");
                            String deposit_int1 = node.getString("rate");
                            depositimage1.setImageUrl(node.getString("image"), mImageLoader);


                            node = list.getJSONObject(depositn2);
                            String depositname2 = node.getString("name");
                            String depositdesc2 = node.getString("des");
                            String deposit_int2 = node.getString("rate");
                            depositimage2.setImageUrl(node.getString("image"), mImageLoader);

                            node = list.getJSONObject(depositn3);
                            String depositname3 = node.getString("name");
                            String depositdesc3 = node.getString("des");
                            String deposit_int3 = node.getString("rate");

                            depositimage3.setImageUrl(node.getString("image"), mImageLoader);

                            deposit1.setText(depositname1);
                            deposit2.setText(depositname2);
                            deposit3.setText(depositname3);
                            depositdes1.setText(depositdesc1);
                            depositdes2.setText(depositdesc2);
                            depositdes3.setText(depositdesc3);
                            depositrate1.setText(deposit_int1 + "%");
                            depositrate2.setText(deposit_int2 + "%");
                            depositrate3.setText(deposit_int3 + "%");



                        }catch (JSONException | NullPointerException e){
                            Toast.makeText(MainActivity.this, "여기", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "디비 연결 실패", Toast.LENGTH_SHORT).show();

                    }
                }
        );

        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResult2 = response;

                        try{
                            JSONArray list = mResult2.getJSONArray("list");
                            JSONObject node = list.getJSONObject(savingsn1);
                            String savingsname1 = node.getString("name");
                            String savingsdesc1 = node.getString("des");
                            String savings_int1 = node.getString("rate");
                            savingsimage1.setImageUrl(node.getString("image"),mImageLoader);

                            node = list.getJSONObject(savingsn2);
                            String savingsname2 = node.getString("name");
                            String savingsdesc2 = node.getString("des");
                            String savings_int2 = node.getString("rate");
                            savingsimage2.setImageUrl(node.getString("image"),mImageLoader);

                            node = list.getJSONObject(savingsn3);
                            String savingsname3 = node.getString("name");
                            String savingsdesc3 = node.getString("des");
                            String savings_int3 = node.getString("rate");
                            savingsimage3.setImageUrl(node.getString("image"),mImageLoader);


                            savings1.setText(savingsname1);
                            savings2.setText(savingsname2);
                            savings3.setText(savingsname3);
                            savingsdes1.setText(savingsdesc1);
                            savingsdes2.setText(savingsdesc2);
                            savingsdes3.setText(savingsdesc3);
                            savingsrate1.setText(savings_int1 + "%");
                            savingsrate2.setText(savings_int2 + "%");
                            savingsrate3.setText(savings_int3 + "%");

                        }catch (JSONException | NullPointerException e){
                            Toast.makeText(MainActivity.this, "여기", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "디비 연결 실패", Toast.LENGTH_SHORT).show();

                    }
                }
        );


        mQueue.add(request);
        mQueue.add(request1);
        mQueue.add(request2);

    }
    protected void requestinfo1(){
        JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET, url3, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResult3 = response;

                        try{
                            JSONArray list = mResult3.getJSONArray("list");
                            for(k = 0;k<=12;k++){
                                JSONObject node = list.getJSONObject(k);
                                String name = node.getString("name");
                                String des = node.getString("des");
                                fundnum = node.getInt("idd");
                                fund[k].setText(name);
                                funddes[k].setText(des);

                                fundbtn[k+1].setOnClickListener(new View.OnClickListener() {
                                    int t = fundnum;
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(MainActivity.this, ChartActivity.class);
                                        //Toast.makeText(MainActivity.this, t, Toast.LENGTH_SHORT).show();
                                        i.putExtra("num", "fund" + t);
                                        i.putExtra("int", t);
                                        startActivity(i);
                                    }
                                });
                            }

                        }catch (JSONException | NullPointerException e){
                            Toast.makeText(MainActivity.this, "여기", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "디비 연결 실패", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        mQueue.add(request3);
    }

}
