package com.example.jh.mycap;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jh.mycap.BluetoothService;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private static final int workpersecond = 5;
    private BluetoothAdapter btAdapter;
    private BluetoothService btService = null;
    private BluetoothDevice btDevice = null;
    BluetoothSocket mmSocket = null;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;

    private  static final String TAG = "MAIN";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private Button btn_Connect, leftButton, rightButton;
    private TextView tv_roll, tv_pitch, tv_yaw, tv_averyaw;

    /*Used for Accelometer & Gyroscoper*/
    private SensorManager mSensorManager = null;
    private UserSensorListner userSensorListner;
    private Sensor mGyroscopeSensor = null;
    private Sensor mAccelerometer = null;

    /*Sensor variables*/
    private float[] mGyroValues = new float[3];
    private float[] mAccValues = new float[3];
    private double mAccPitch, mAccRoll, mAccYaw;

    /*for unsing complementary fliter*/
    private float a = 0.2f;
    private static final float NS2S = 1.0f/1000000000.0f;
    private double RAD2DGR = 180 / Math.PI;

    private double pitch = 0, roll = 0, yaw;
    private double timestamp;
    private double dt;
    private double temp;
    private boolean running;
    private boolean gyroRunning;
    private boolean accRunning;

    double aver[] = new double[5];
    double aver2[] = new double[5];
    double aver3[] = new double[5];
    double yawarr[] = new double[5];
    double pitcharr[] = new double[5];
    double rollarr[] = new double[5];
    double average=0;
    double average2=0;
    double average3=0;
    int i=0;

    private double time_start = 1000000000, time_tmp = 1000000000;
    private boolean flag_left, flag_right;

    private TimerTask second;
    private final Handler handler = new Handler();
    double timer_sec;
    int count;
    public void testStart() {
        timer_sec = 0;
        count = 0;
        second = new TimerTask() {
            @Override
            public void run() {
                Update();
                timer_sec+=0.2;
            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 0, 200);
    }
    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {
                String str = String.format("%.2f", timer_sec) + "," + average + "," + pitch + "\n";
                tv_averyaw.setText("Average Yaw : " + String.format("%.1f", average));
                Log.e("LOG", str);
                byte[] buffer2 = new byte[60];
                buffer2 = str.getBytes();
                btService.write(buffer2);

                yawarr[i%workpersecond] = yaw*RAD2DGR;
                if(i==2000000001) {
                    i=1;
                }
                else if(i==0){
                    aver[i] = yawarr[i];
                }else{
                    aver[i%workpersecond] = yawarr[i%workpersecond] - yawarr[(i-1)%workpersecond];
                }
                i++;
                for(int c = 0;c<5;c++){
                    average += aver[c];
                }
                average/=5;

                if((timer_sec - time_tmp) >= 3) {
                    byte[] buffer1 = new byte[2];
                    buffer1[0] = 'U';
                    btService.write(buffer1);
                    time_tmp = 1000000000;
                    flag_left = false;
                    flag_right = false;
                }

                if((timer_sec - time_start) >= 3) {
                    byte[] buffer1 = new byte[2];
                    buffer1[0] = 'U';
                    btService.write(buffer1);
                    time_start = 1000000000;
                    flag_left = false;
                    flag_right = false;
                }

                if(average > 10 || pitch < -15) {
                    if(flag_right) {
                        if(average > 20 || pitch < -20) {
                            byte[] buffer1 = new byte[2];
                            buffer1[0] = 'U';
                            btService.write(buffer1);

                            buffer1[0] = 'J';
                            btService.write(buffer1);
                            time_start = timer_sec;
                            flag_left= true;
                        }
                    }
                    else {
                        byte[] buffer1 = new byte[2];
                        buffer1[0] = 'J';
                        btService.write(buffer1);
                        time_start = timer_sec;
                        flag_left = true;
                    }
                }else if(average < -10 || pitch > 15){
                    if(flag_left) {
                        if(average < -20 || pitch > 20) {
                            byte[] buffer1 = new byte[2];
                            buffer1[0] = 'U';
                            btService.write(buffer1);

                            buffer1[0] = 'Q';
                            btService.write(buffer1);
                            time_start = timer_sec;
                            flag_right= true;
                        }
                    }
                    else {
                        byte[] buffer1 = new byte[2];
                        buffer1[0] = 'Q';
                        btService.write(buffer1);
                        time_start = timer_sec;
                        flag_right = true;
                    }
                }
            }
        };

        handler.post(updater);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Connect = (Button)findViewById(R.id.connect);
        tv_roll = (TextView)findViewById(R.id.tv_roll);
        tv_pitch = (TextView)findViewById(R.id.tv_pitch);
        tv_yaw = (TextView)findViewById(R.id.tv_yaw);
        tv_averyaw = (TextView)findViewById(R.id.tv_averyaw);
        leftButton = (Button)findViewById(R.id.left_btn);
        rightButton = (Button)findViewById(R.id.right_btn);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] buffer1 = new byte[2];
                buffer1[0] = 'U';
                btService.write(buffer1);

                buffer1[0] = 'Q';
                btService.write(buffer1);
                time_tmp = timer_sec;
                flag_left = true;
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] buffer1 = new byte[2];
                buffer1[0] = 'U';
                btService.write(buffer1);

                buffer1[0] = 'J';
                btService.write(buffer1);
                time_tmp = timer_sec;
                flag_right = true;
            }
        });

        btn_Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btService.getDeviceState())
                    btService.enableBluetooth();
                else
                    finish();
            }
        });

        if(btService == null){
            btService = new BluetoothService(this, mHandler);
        }

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        userSensorListner = new UserSensorListner();
        mGyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mAccelerometer= mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(userSensorListner, mGyroscopeSensor, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(userSensorListner, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        testStart();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult" + resultCode);

        switch (requestCode){
            case REQUEST_CONNECT_DEVICE:
                if(resultCode == Activity.RESULT_OK) {
                    btService.getDeviceInfo(data);
                }
                break;

            case REQUEST_ENABLE_BT:
                if(resultCode == Activity.RESULT_OK){
                    btService.scanDevice();
                }else {
                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
        }
    }

    private void complementaty(double new_ts){
        if(timestamp == 0){
            timestamp = new_ts;
            return;
        }

        double gyroZ = mGyroValues[2];

        dt = (new_ts - timestamp) * NS2S; // ns->s 변환
        timestamp = new_ts;

        if (dt - timestamp * NS2S != 0) {
            yaw = yaw + gyroZ * dt;
        }

        mAccPitch = -Math.atan2(mAccValues[0], mAccValues[2]) * 180.0 / Math.PI; // Y 축 기준
        mAccRoll= Math.atan2(mAccValues[1], mAccValues[2]) * 180.0 / Math.PI; // X 축 기준
        mAccYaw = mAccValues[2];

        temp = (1/a) * (mAccPitch - pitch) + mGyroValues[1];
        pitch = pitch + (temp*dt);

        temp = (1/a) * (mAccRoll - roll) + mGyroValues[0];
        roll = roll + (temp*dt);

        tv_roll.setText("roll : " + String.format("%.1f", roll));
        tv_pitch.setText("pitch : " + String.format("%.1f", pitch));
        tv_yaw.setText("yaw : " + String.format("%.1f", yaw * RAD2DGR));

    }

    public class UserSensorListner implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()){

                /** GYROSCOPE */
                case Sensor.TYPE_GYROSCOPE:
                    mGyroValues = event.values;
                    if(!gyroRunning)
                        gyroRunning = true;
                    break;

                /** ACCELEROMETER */
                case Sensor.TYPE_ACCELEROMETER:
                    mAccValues = event.values;
                    if(!accRunning)
                        accRunning = true;
                    break;

            }
            if(gyroRunning && accRunning){
                complementaty(event.timestamp);
            }




        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    }

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}