package com.example.crutcher.qrcodescannernareznoy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PointsActivity extends AppCompatActivity {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference pointsDatabaseReference;


    //final TextView biloPts = findViewById(R.id.ptsBilo);
    //final EditText pts = findViewById(R.id.pts);
    //final Button ConfirmationButton = findViewById(R.id.button);
    //final TextView ptsWillBe = findViewById(R.id.ptsWillBe);
    //final TextView ptsDiff = findViewById(R.id.ptsDiff);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);


        pointsDatabaseReference = firebaseDatabase.
                getReference(getIntent().getExtras().getString("userKey")).child("points");

        getStartingData();

        initPtsViews();

        initButtons();

    }

    public void onStart() {
        super.onStart();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    private void getStartingData()
    {
        final TextView biloPts = findViewById(R.id.ptsBilo);


        pointsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                biloPts.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "data read error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initPtsViews()
    {
        final EditText pts = findViewById(R.id.pts);
        final TextView ptsDiff = findViewById(R.id.ptsDiff);
        final TextView ptsWillBe = findViewById(R.id.ptsWillBe);
        final TextView biloPts = findViewById(R.id.ptsBilo);

        pts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                int a = Integer.parseInt(biloPts.getText().toString());
                int b;

                switch(pts.getText().toString())
                {
                    case "-": b=0; break;
                    case "": b=0; break;
                    default: b=Integer.parseInt(pts.getText().toString()); break;
                }

                if(b>0)
                    ptsDiff.setText(new StringBuilder("+").append(b));
                else
                    ptsDiff.setText(String.valueOf(b));



                if(a+b>=0)
                {
                    ptsWillBe.setText(String.valueOf(a+b));
                }
                else
                {
                    ptsWillBe.setText("Отриц. Очки!");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



    private void initButtons ()
    {
        final Button ConfirmationButton = findViewById(R.id.button);
        final TextView ptsWillBe = findViewById(R.id.ptsWillBe);

        ConfirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    pointsDatabaseReference.setValue(ptsWillBe.getText().toString());
                    Intent intent = new Intent(getApplicationContext(),ScanActivity.class);
                    PointsActivity.this.startActivity(intent);
                    PointsActivity.this.finish();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "confirmation error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
