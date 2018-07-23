package com.example.crutcher.qrcodescannernareznoy;

import android.Manifest;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ScanActivity extends AppCompatActivity {

    String userEmail = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final TextView textView = findViewById(R.id.text);
        final TextView button = findViewById(R.id.button);

        final QRCodeReaderView qrCodeReaderView = findViewById(R.id.qrReader);


        qrCodeReaderView.setQRDecodingEnabled(true);
        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setTorchEnabled(false);
        qrCodeReaderView.setBackCamera();

        qrCodeReaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrCodeReaderView.forceAutoFocus();
            }
        });

        qrCodeReaderView.setOnQRCodeReadListener(new QRCodeReaderView.OnQRCodeReadListener() {
            @Override
            public void onQRCodeRead(final String text, PointF[] points) {

                try {


                    DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(text);

                    databaseReference.child("gmail").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            userEmail = dataSnapshot.getValue().toString();
                            textView.setText(userEmail);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    button.setText("Изменить очки");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), PointsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("userKey", text);
                            bundle.putString("userEmail", userEmail);
                            intent.putExtras(bundle);
                            ScanActivity.this.startActivity(intent);
                            ScanActivity.this.finish();

                        }
                    });

                }
                catch (Exception e)
                {
                    textView.setText("Нет такого юзера");
                }

            }
        });
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
}
