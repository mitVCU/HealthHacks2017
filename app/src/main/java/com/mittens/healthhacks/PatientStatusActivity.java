package com.mittens.healthhacks;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.mittens.healthhacks.models.SensorReading;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PatientStatusActivity extends AppCompatActivity {
    private static final String TAG = "PatientStatusActivity";
    private GraphView heartRateGraph;
    private GraphView spo2Graph;
    private GraphView bloodPressureGraph;
    private GraphView temperateGraph;

    private ArrayList<DataPoint> heartRateData = new ArrayList<>();
    private ArrayList<DataPoint> spo2Data = new ArrayList<>();
    private ArrayList<DataPoint> bloodPressureData = new ArrayList<>();
    private ArrayList<DataPoint> temperatureData = new ArrayList<>();


    private TextView patientNameTv;
    private TextView ageTv;
    private double patientAge;
    private String patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_status);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        heartRateGraph = (GraphView) findViewById(R.id.heart_rate_graph);
        spo2Graph = (GraphView) findViewById(R.id.spo2_graph);
        bloodPressureGraph = (GraphView) findViewById(R.id.blood_pressure_graph);
        temperateGraph = (GraphView) findViewById(R.id.temperature_graph);

        patientNameTv = (TextView) findViewById(R.id.patient_name);
        ageTv = (TextView) findViewById(R.id.patient_age);

        final String patient = getIntent().getExtras().getString("patient");
        assert patient != null;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Clinic").child(patient);
        Log.d(TAG, "onCreate: " + ref);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot);
                patientName = patient;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.i(TAG, "onDataChange: " + snapshot.getValue());
                    SensorReading reading = snapshot.getValue(SensorReading.class);
                    patientAge = reading.getAge();
                    try {
                        heartRateData.add(new DataPoint(dateFormat.parse(reading.getTime()), reading.getHR()));
                        spo2Data.add(new DataPoint(dateFormat.parse(reading.getTime()), reading.getSpO2()));
                        bloodPressureData.add(new DataPoint(dateFormat.parse(reading.getTime()), reading.getSystolicBP()));
                        temperatureData.add(new DataPoint(dateFormat.parse(reading.getTime()), reading.getTemperature()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                temperateGraph.addSeries(new LineGraphSeries<>(temperatureData.toArray(new DataPoint[] {})));
                temperateGraph.setTitle("Temperature");
                temperateGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
                temperateGraph.getGridLabelRenderer().setNumHorizontalLabels(3);
                temperateGraph.getViewport().setMinY(50);
                temperateGraph.getViewport().setMaxY(125);
                temperateGraph.getViewport().setScalable(true);
                temperateGraph.getViewport().setScrollable(true);

                spo2Graph.addSeries(new LineGraphSeries<>(spo2Data.toArray(new DataPoint[] {})));
                spo2Graph.setTitle("SpO2");
                spo2Graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
                spo2Graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                spo2Graph.getViewport().setMinY(50);
                spo2Graph.getViewport().setMaxY(100);
                spo2Graph.getViewport().setScalable(true);
                spo2Graph.getViewport().setScrollable(true);

                bloodPressureGraph.addSeries(new LineGraphSeries<>(bloodPressureData.toArray(new DataPoint[] {})));
                bloodPressureGraph.setTitle("Systolic Blood Pressure");
                bloodPressureGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
                bloodPressureGraph.getGridLabelRenderer().setNumHorizontalLabels(3);
                bloodPressureGraph.getViewport().setMinY(20);
                bloodPressureGraph.getViewport().setMaxY(150);
                bloodPressureGraph.getViewport().setScalable(true);
                bloodPressureGraph.getViewport().setScrollable(true);

                heartRateGraph.addSeries(new LineGraphSeries<>(heartRateData.toArray(new DataPoint[] {})));
                heartRateGraph.setTitle("Heart Rate");
                heartRateGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
                heartRateGraph.getGridLabelRenderer().setNumHorizontalLabels(3);
                heartRateGraph.getViewport().setMinY(0);
                heartRateGraph.getViewport().setMaxY(300);
                heartRateGraph.getViewport().setScalable(true);
                heartRateGraph.getViewport().setScrollable(true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: The hell happened here?");
            }
        });
    }
}