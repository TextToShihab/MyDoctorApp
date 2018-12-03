package com.example.admin.relativedatabase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 10-Apr-17.
 */

public class PreAdapter extends ArrayAdapter<Prescription> {

    private Context context;
    private ArrayList<Prescription> prescriptions;

    public PreAdapter(@NonNull Context context, ArrayList<Prescription> prescriptions) {
        super(context, R.layout.custom_pre_layout, prescriptions);
        this.context = context;
        this.prescriptions = prescriptions;
    }

    class ViewHolder{
        TextView preDocNameTV, detailsTV, dateTV;
        ImageView preImageIV;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        PreAdapter.ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_pre_layout,parent,false);
            holder.preDocNameTV = (TextView) convertView.findViewById(R.id.pre_doc_name);
            holder.detailsTV = (TextView) convertView.findViewById(R.id.pre_details);
            holder.dateTV = (TextView) convertView.findViewById(R.id.pre_date);
            holder.preImageIV = (ImageView) convertView.findViewById(R.id.pre_image);
            convertView.setTag(holder);
        }else {
            holder = (PreAdapter.ViewHolder) convertView.getTag();
        }
        holder.dateTV.setText(prescriptions.get(position).getPreDate());
        holder.preImageIV.setImageBitmap(prescriptions.get(position).getBitmapImage());
        holder.preDocNameTV.setText(prescriptions.get(position).getPreDocName());
        holder.detailsTV.setText(prescriptions.get(position).getPreDocDetails());
        return convertView;
    }
}
