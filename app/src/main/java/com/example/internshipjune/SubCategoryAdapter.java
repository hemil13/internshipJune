package com.example.internshipjune;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyHolder> {

    Context context;
    int[] subcategoryIdArray;
    String[] nameArray;
    int[] imageArray;
    int[] categoryIdArray;

    ArrayList<SubCategoryList> arrayList;



//    public SubCategoryAdapter(Context context, int[] categoryIdArray, int[] subcategoryIdArray, String[] nameArray, int[] imageArray){
//        this.context = context;
//        this.categoryIdArray = categoryIdArray;
//        this.subcategoryIdArray = subcategoryIdArray;
//        this.nameArray = nameArray;
//        this.imageArray = imageArray;
//    }

    public SubCategoryAdapter(Context context, ArrayList<SubCategoryList> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.category_text);
            image = itemView.findViewById(R.id.category_image);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyHolder holder, int position) {
//        holder.name.setText(nameArray[position]);
//        holder.image.setImageResource(imageArray[position]);


        holder.name.setText(arrayList.get(position).getName());
        holder.image.setImageResource(arrayList.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
