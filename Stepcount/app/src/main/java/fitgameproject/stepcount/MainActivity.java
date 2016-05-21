package fitgameproject.stepcount;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensormanager;//the sensor manager
    private TextView count;//the count
    boolean activityRunning;//is the activity running?
    @Override
    protected void onCreate(Bundle savedInstanceState) {//when the thing starts
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = (TextView) findViewById(R.id.count);
        sensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume(){//when the thing is running
        super.onResume();
        activityRunning=true;
        Sensor countSensor = sensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){//if there is a censor
            sensormanager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);//listen for the sensor
        }else{
            Toast.makeText(this, "count sensor not avalable!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {//i have no clue what this does
        super.onPause();
        activityRunning=true;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {//i dont know what this does either
        if(activityRunning){
            count.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {//this is blank, but if its not here the program wont run

    }
}
