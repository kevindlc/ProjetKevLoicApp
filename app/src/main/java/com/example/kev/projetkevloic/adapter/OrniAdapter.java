package com.example.kev.projetkevloic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kev.projetkevloic.ViewHolder.OrniViewHolder;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.object.Ornithologue;

import java.util.List;

public class OrniAdapter  extends ArrayAdapter<Ornithologue> {

    public OrniAdapter(Context context, List<Ornithologue> ornis) {
        super(context, 0, ornis);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_ornitho,parent, false);

        }

        OrniViewHolder viewHolder = (OrniViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new OrniViewHolder();
            viewHolder.username = (TextView) convertView.findViewById(R.id.usernameOrnitho);
            viewHolder.canton = (TextView) convertView.findViewById(R.id.cantonOrnitho);
            convertView.setTag(viewHolder);

        }

        //getItem(position) va récupérer l'item [position] de la List<Ornitho> Ornis
        Ornithologue orni = getItem(position);
        viewHolder.username.setText(orni.getUsername());
        viewHolder.canton.setText(orni.getCanton());


        return convertView;
    }


}
