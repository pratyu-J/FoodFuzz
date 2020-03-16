package com.example.foodfuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VidCook extends AppCompatActivity {

    private TextView description, ingredients, process;
    private static String TAG = "VidCook";
    private VideoView vid;
    private ImageButton imgbtn;
    ProgressDialog pd;
    String url;
    FirebaseStorage firestorage;
    StorageReference store, ref;
   /* private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference ref = databaseReference.child("vidurl");
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid_cook);

        Intent i = getIntent();
        String desc = i.getStringExtra("DETAILS");
        url = i.getStringExtra("URL");

        description = findViewById(R.id.description);
        vid = findViewById(R.id.video);
        imgbtn = findViewById(R.id.imgbtn);
        imgbtn.setVisibility(View.VISIBLE);
        pd = new ProgressDialog(VidCook.this);
        description.setText(url);
        pd.setMessage("Buffering. Please wait...");
        store = firestorage.getInstance().getReference("VidCook");
        ref = store.child(url);
        //pd.show();

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        vid.setVideoURI(uri);
                        vid.requestFocus();
                        imgbtn.setVisibility(View.GONE);
                        vid.start();
                    }
                });

            }
        });





        /*vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);
                vid.setVideoURI(uri);
                vid.requestFocus();
                vid.start();

            }
        });*/

    }

}
