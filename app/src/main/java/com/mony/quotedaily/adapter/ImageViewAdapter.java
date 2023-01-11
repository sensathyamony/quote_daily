package com.mony.quotedaily.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mony.quotedaily.DesignScreen;
import com.mony.quotedaily.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.ImageViewHolder> {

   ArrayList<String> imageList;
   ArrayList<String> tempImage;
   Context context;

   public ImageViewAdapter(ArrayList<String> imageList, ArrayList<String> tempImage, Context context) {
      this.imageList = imageList;
      this.tempImage = tempImage;
      this.context = context;
   }

   @NonNull
   @Override
   public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.list_fragment, parent, false);

      return new ImageViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ImageViewHolder holder, @SuppressLint("RecyclerView") int position) {

      Picasso.get().load(tempImage.get(holder.getAdapterPosition())).into(holder.imageView);

      holder.imageView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            DesignScreen.setImageUrl(imageList.get(position));
         }
      });
   }

   public static String imageClick(String imageSource){
      return imageSource;
   }

   @Override
   public int getItemCount() {
      return imageList.size();
   }

   public class ImageViewHolder extends RecyclerView.ViewHolder{

      ImageView imageView;

      public ImageViewHolder(@NonNull View itemView) {
         super(itemView);
         imageView = itemView.findViewById(R.id.image_item);
      }
   }

}
