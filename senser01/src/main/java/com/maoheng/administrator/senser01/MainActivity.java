package com.maoheng.administrator.senser01;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Vibrator vibrator;
    private SoundPool soundPool;
    private int soundId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
        soundId = soundPool.load(this,R.raw.shake,1);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
//        Log.e("++++++","+++++个数+++++"+sensorList.size());
//        for (int i = 0; i < sensorList.size(); i++) {
//            Log.e("++++++","+++++名称+++++"+sensorList.get(i).getName());
//            Log.e("++++++","+++++供应商+++++"+sensorList.get(i).getVendor());
//            Log.e("++++++","+++++功率+++++"+sensorList.get(i).getPower());
//            Log.e("++++++","+++++精确度+++++"+sensorList.get(i).getResolution());
//        }
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
//                Log.e("+++x++","++++"+x);
//                Log.e("+++y++","++++"+y);
//                Log.e("+++z++","++++"+z);
                if(Math.abs(x)>19||Math.abs(y)>19||Math.abs(z)>19){
                    Log.e("+++++","++++摇动");
//                    vibrator.vibrate(500);
                    soundPool.play(soundId,0,1,0,-1,1);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onDestroy();
    }
}
