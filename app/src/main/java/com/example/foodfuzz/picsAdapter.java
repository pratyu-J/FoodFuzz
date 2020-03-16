package com.example.foodfuzz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class picsAdapter extends RecyclerView.Adapter<picsAdapter.picViewHolder> {

    ArrayList<itemAdapter> flist;
    StorageReference storageReference;
    Activity activity;
    Context context;


    public picsAdapter(ArrayList<itemAdapter> mlist, Context context, Activity activity){
        flist = mlist;
        this.context = context;
        this.activity = activity;
    }

    public static class picViewHolder extends RecyclerView.ViewHolder{
        public TextView info;
        public ImageView img;


        public picViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.info);
            img = itemView.findViewById(R.id.images);
        }
    }


    @NonNull
    @Override
    public picViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        picViewHolder vh =new picViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final picViewHolder holder, final int position) {

    final itemAdapter item1 = flist.get(position);
    holder.info.setText(flist.get(position).getDetails());
    storageReference = FirebaseStorage.getInstance().getReference("foodPictures").child(item1.getPics());

    activity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    item1.setPics(uri.toString());
                    //Picasso.get().load(item1.getPics()).into(holder.img);
                    Glide.with(context).load(uri).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.img);

                }
            });
        }
    });


    holder.img.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            goToVideo(item1);
        }
    });

    holder.info.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToVideo(item1);
        }
    });

    }

   public void goToVideo(itemAdapter item){
        String details = item.details;
        String vidurl = item.Vidurl;

        Intent intent = new Intent(context, VidCook.class);
        intent.putExtra("DETAILS", details);
        intent.putExtra("URL", vidurl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);



   }

    @Override
    public int getItemCount() {
        return flist.size();
    }
}
