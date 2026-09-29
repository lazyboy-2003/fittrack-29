package com.fittrack29.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.Manifest;
import android.os.Build;
import android.view.Gravity;
import android.widget.*;
import android.graphics.Typeface;

public class MainActivity extends Activity implements SensorEventListener {
  private SensorManager sm; private Sensor counter; private TextView stepsView, distanceView, caloriesView, statusView; private int steps=0;
  @Override public void onCreate(Bundle b){ super.onCreate(b); getWindow().setStatusBarColor(0xff10231b); getWindow().setNavigationBarColor(0xff10231b);
    LinearLayout root=new LinearLayout(this); root.setOrientation(1); root.setPadding(28,36,28,24); root.setBackgroundColor(0xff10231b);
    TextView title=new TextView(this); title.setText("FitTrack 29"); title.setTextSize(30); title.setTypeface(null,Typeface.BOLD); title.setTextColor(0xffe8fff1); root.addView(title);
    TextView sub=new TextView(this); sub.setText("Your daily movement, automatically"); sub.setTextColor(0xffa8c5b5); sub.setPadding(0,4,0,28); root.addView(sub);
    stepsView=metric(root,"0","STEPS"); distanceView=metric(root,"0.00 km","DISTANCE (EST.)"); caloriesView=metric(root,"0 kcal","ACTIVE CALORIES (EST.)");
    statusView=new TextView(this); statusView.setTextColor(0xffb6d9c5); statusView.setPadding(0,20,0,20); root.addView(statusView);
    Button refresh=new Button(this); refresh.setText("Refresh sensor"); refresh.setOnClickListener(v->startSensor()); root.addView(refresh);
    TextView note=new TextView(this); note.setText("Keep the phone with you. Grant Physical activity permission when asked. Sensor availability varies by phone; step totals may reset when the device restarts."); note.setTextColor(0xffa8c5b5); note.setPadding(0,20,0,0); root.addView(note);
    setContentView(root); sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE); counter=sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    if(Build.VERSION.SDK_INT>=29 && checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION)!=PackageManager.PERMISSION_GRANTED) requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION},7); else startSensor();
  }
  private TextView metric(LinearLayout root,String value,String label){ LinearLayout card=new LinearLayout(this); card.setOrientation(1); card.setPadding(20,18,20,18); card.setBackgroundColor(0xff1b382b); LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=14; root.addView(card,lp); TextView v=new TextView(this); v.setText(value); v.setTextSize(28); v.setTypeface(null,Typeface.BOLD); v.setTextColor(0xffe8fff1); card.addView(v); TextView l=new TextView(this); l.setText(label); l.setTextColor(0xffa8c5b5); l.setPadding(0,4,0,0); card.addView(l); return v; }
  private void startSensor(){ if(counter==null){statusView.setText("This phone does not report a step-counter sensor. Automatic steps are unavailable on this device.");return;} boolean ok=sm.registerListener(this,counter,SensorManager.SENSOR_DELAY_NORMAL); statusView.setText(ok?"Sensor connected. Carry your phone to count steps.":"Could not start sensor. Check Physical activity permission."); }
  @Override public void onSensorChanged(SensorEvent e){ if(e.sensor.getType()==Sensor.TYPE_STEP_COUNTER){ steps=(int)e.values[0]; stepsView.setText(String.valueOf(steps)); double km=steps*0.0007; distanceView.setText(String.format(java.util.Locale.US,"%.2f km",km)); caloriesView.setText(String.format(java.util.Locale.US,"%.0f kcal",steps*0.04)); }}
  @Override public void onAccuracyChanged(Sensor s,int a){}
  @Override protected void onResume(){super.onResume(); if(sm!=null&&counter!=null&&(Build.VERSION.SDK_INT<29||checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION)==PackageManager.PERMISSION_GRANTED)) startSensor();}
  @Override protected void onPause(){super.onPause(); if(sm!=null)sm.unregisterListener(this);}
}
