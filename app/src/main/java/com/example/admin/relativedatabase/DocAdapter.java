package com.example.admin.relativedatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 10-Apr-17.
 */

public class DocAdapter extends ArrayAdapter<Doctor> {

    private Context context;
    private ArrayList<Doctor> doctors;

    public DocAdapter(@NonNull Context context, ArrayList<Doctor>doctors) {
        super(context, R.layout.custom_doc_layuot, doctors);
        this.context = context;
        this.doctors = doctors;
    }

    class ViewHolder{
        TextView nameTV, emailTV, phoneTV;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_doc_layuot,parent,false);
            holder.nameTV = (TextView) convertView.findViewById(R.id.docName);
            holder.emailTV = (TextView) convertView.findViewById(R.id.docEmail);
            holder.phoneTV = (TextView) convertView.findViewById(R.id.docPhone);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameTV.setText(doctors.get(position).getDocName());
        holder.emailTV.setText(doctors.get(position).getDocEmail());
        holder.phoneTV.setText(doctors.get(position).getDocPhone());
        return convertView;
    }
}
