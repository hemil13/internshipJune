package com.example.internshipjune;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {

    Context context;
    int[] categoryIdArray;
    String[] nameArray;
    int[] imageArray;


    ArrayList<CategoryList> arrayList;


    SharedPreferences sp;


//    public CategoryAdapter(Context context, int[] categoryIdArray, String[] nameArray, int[] imageArray) {
//        this.context = context;
//        this.categoryIdArray = categoryIdArray;
//        this.nameArray = nameArray;
//        this.imageArray = imageArray;
//
//        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
//    }



    public CategoryAdapter(Context context, ArrayList<CategoryList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public CategoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_image);
            text = itemView.findViewById(R.id.category_text);
        }
    }



    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyHolder holder, int position) {
//        holder.image.setImageResource(imageArray[position]);
//        holder.text.setText(nameArray[position]);


        holder.image.setImageResource(arrayList.get(position).getImage());
        holder.text.setText(arrayList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                sp.edit().putString(ConstantSp.categoryid, String.valueOf(categoryIdArray[position])).commit();
                sp.edit().putString(ConstantSp.categoryid, String.valueOf(arrayList.get(position).getCategoryId())).commit();

                Intent intent = new Intent(context, SubCategoryActivity.class);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
