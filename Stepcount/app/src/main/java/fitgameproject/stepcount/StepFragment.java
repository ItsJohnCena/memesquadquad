package fitgameproject.stepcount;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class StepFragment extends Fragment implements SensorEventListener {

    private SensorManager sensormanager;//the sensor manager
    private TextView tCount;//the total count
    private TextView dCount;//the daily count
    boolean activityRunning;//is the activity running?
    int dSteps = 0;//daily steps



    int tSteps = 0;//total steps
    Calendar c = Calendar.getInstance();
    int h;
    int m;
    int subvalu = 0;
    boolean dayChange = false; //if we've changed the day
    private TextView kiloms; //km travelled
    private TextView calories;
    private Double km = 0.0;
    private int cals = 0;
    private TextView kmD;//the km
    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance() {
        return new StepFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);

        tCount = (TextView) view.findViewById(R.id.tCount);//ui stuff
        dCount = (TextView) view.findViewById(R.id.dCount);//ui stuff
        sensormanager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);//ui stuff
        kiloms = (TextView) view.findViewById(R.id.kiloms);
        calories = (TextView) view.findViewById(R.id.calories);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        activityRunning=true;
        Sensor countSensor = sensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);//initializing the steps from reboot
        if(countSensor != null){//error trapping
            sensormanager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);//listen for the sensor
        }else{
            Toast.makeText(getActivity(), "count sensor not avalable!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        activityRunning=true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {//if the sensor registers a step
         /* Time stuff
            if the time is twelve am (new day), we need the stepper to show 0
            So everyday we take away the current stepper to appear as 0
            and calculate the score as zero
            The number to take away value is subvalu
        */
        c = Calendar.getInstance();
        h  = c.get(Calendar.HOUR_OF_DAY);
        m = c.get(Calendar.MINUTE);
        if(h == 0  && m==0 && dayChange == false){//if its a new day the daily step must be 0
            // The daychange variable is to make sure we only reset when the time is 0:00 once, and not the whole minute
            subvalu = tSteps;
            dayChange = true;
        }
        if(activityRunning){//and the app is running
            tSteps = (int) event.values[0];
            if(subvalu!=0)
                dSteps = tSteps - subvalu;
            dSteps++;
            }

            dCount.setText(dSteps + "");
            tCount.setText(tSteps + "");
            km = dSteps * 0.0006608826;
            cals = dSteps / 20;
            kiloms.setText(String.format("%.2f", km));
            calories.setText(cals + "");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {//this is blank, but if its not here the program wont run
    }
}
