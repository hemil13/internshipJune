package com.example.internshipjune;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {
    Context context;
    ArrayList<ProductList> arrayList;

    SharedPreferences sp;

    SQLiteDatabase db;


    Boolean isWishlist = false;


//    public ProductAdapter(Context context, ArrayList<ProductList> arrayList) {
//        this.context = context;
//        this.arrayList = arrayList;
//
//        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
//    }

    public ProductAdapter(Context context, ArrayList<ProductList> arrayList, SQLiteDatabase db) {
        this.context = context;
        this.arrayList = arrayList;
        this.db = db;

        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ProductAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemproduct, parent, false);
        return new ProductAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image, wishlist;
        TextView text, price;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.product_image);
            text = itemView.findViewById(R.id.product_text);
            price = itemView.findViewById(R.id.product_price);
            wishlist = itemView.findViewById(R.id.product_wishlist);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyHolder holder, int position) {
        holder.image.setImageResource(arrayList.get(position).getImage());
        holder.text.setText(arrayList.get(position).getName());
        holder.price.setText(ConstantSp.rupees+arrayList.get(position).getPrice());

        if(arrayList.get(position).isWishlist ){
//            isWishlist = true;
            holder.wishlist.setImageResource(R.drawable.wishlist_fill);
        }
        else {
//            isWishlist = false;
            holder.wishlist.setImageResource(R.drawable.wishlist_empty);
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sp.edit().putString(ConstantSp.productid, String.valueOf(arrayList.get(position).getProductId())).commit();
                sp.edit().putString(ConstantSp.productname, arrayList.get(position).getName()).commit();
                sp.edit().putString(ConstantSp.productimage, String.valueOf(arrayList.get(position).getImage())).commit();
                sp.edit().putString(ConstantSp.productprice, arrayList.get(position).getPrice()).commit();
                sp.edit().putString(ConstantSp.productdesc, arrayList.get(position).getDescription()).commit();


                Intent intent = new Intent(context, ProductDetailActivity.class);
                context.startActivity(intent);
            }
        });

        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isWishlist){
                    isWishlist = false;
                    String deleteWishlist = "DELETE FROM wishlist WHERE userid = '"+sp.getString(ConstantSp.userid,"")+"' AND productid = '"+ arrayList.get(position).getProductId()  +"'";
                    db.execSQL(deleteWishlist);
                    holder.wishlist.setImageResource(R.drawable.wishlist_empty);
                    Toast.makeText(context, "Removed From Wishlist", Toast.LENGTH_SHORT).show();
                }
                else{
                    isWishlist = true;
                    String insertWishlist = "INSERT INTO wishlist(productid, userid) VALUES('"+arrayList.get(position).getProductId()+"', '"+sp.getString(ConstantSp.userid,"")+"')";
                    db.execSQL(insertWishlist);
                    holder.wishlist.setImageResource(R.drawable.wishlist_fill);
                    Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
