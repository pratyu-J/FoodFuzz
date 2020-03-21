package com.example.foodfuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView head;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager mlayoutManager;
    ArrayList<itemAdapter> alist =new ArrayList<>();

    private DatabaseReference dbref;
    private StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "OnCreate: started");

        head = findViewById(R.id.food);
        recyclerView = findViewById(R.id.recfood);

        dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodfuzz-c5b01.firebaseio.com/");
        storageRef = FirebaseStorage.getInstance().getReference();

        recyclerView.setHasFixedSize(true);
        mlayoutManager =new LinearLayoutManager(this);
        /*madapter =new picsAdapter(alist);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(madapter);*/

        final Query query = dbref.child("images");
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //itemAdapter item = new itemAdapter();
                            itemAdapter item = snapshot.getValue(itemAdapter.class);

/*                    item.setPics(snapshot.child("pics").getValue().toString());
                    item.setDetails(snapshot.child("details").getValue().toString());*/

                            alist.add(item);
                        }

                        picsAdapter p =new picsAdapter(alist, getApplicationContext(), MainActivity.this);
                        //madapter = new picsAdapter(alist) ;
                        recyclerView.setLayoutManager(mlayoutManager);
                        recyclerView.setAdapter(p);
                        p.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }


}
