package com.mittens.healthhacks;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DatabaseReference databaseReference;
    private RecyclerView roomList;
    private RoomAdapter roomAdapter;
    private Button onCallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseInstanceId.getInstance().getToken();
        Log.d("Look: ",FirebaseInstanceId.getInstance().getToken());
        roomList = (RecyclerView) findViewById(R.id.rooms_rv);
        onCallButton = (Button) findViewById(R.id.on_call_button);
        roomList.setLayoutManager(new LinearLayoutManager(this));
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Clinic");
        Log.d(TAG, "onCreate: " + databaseReference);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.getValue());
                Map<String, Object> snapshot = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "onDataChange: the rooms are:\t" + snapshot.keySet());
                roomAdapter = new RoomAdapter(Arrays.asList(snapshot.keySet().toArray(new String[] {})));
                roomList.setAdapter(roomAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: Canceled the database call for some reason");
            }
        });
    }


    public void onClick(View view) {
        if (onCallButton.getText().toString().equalsIgnoreCase("On Call")) {
            onCallButton.setText("Not On Call");
        } else {
            onCallButton.setText("On Call");
        }
    }
}
