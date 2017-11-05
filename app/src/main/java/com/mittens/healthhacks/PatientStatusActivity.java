package com.mittens.healthhacks;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.mittens.healthhacks.models.SensorReading;

import java.util.Map;

public class PatientStatusActivity extends AppCompatActivity {
    private static final String TAG = "PatientStatusActivity";
    private GraphView heartRateGraph;
    private GraphView spo2Graph;
    private GraphView bloodPressureGraph;
    private GraphView temperateGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_status);
        heartRateGraph = (GraphView) findViewById(R.id.heart_rate_graph);
        spo2Graph = (GraphView) findViewById(R.id.spo2_graph);
        bloodPressureGraph = (GraphView) findViewById(R.id.blood_pressure_graph);
        temperateGraph = (GraphView) findViewById(R.id.temperature_graph);

        String patient = getIntent().getExtras().getString("patient");
        assert patient != null;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Clinic").child(patient);
        Log.d(TAG, "onCreate: " + ref);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot);
                Map<String, SensorReading> sensorReadings = (Map<String, SensorReading>) dataSnapshot;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}