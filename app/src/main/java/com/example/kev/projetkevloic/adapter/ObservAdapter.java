package com.example.kev.projetkevloic.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kev.projetkevloic.Database.OiseauDB;
import com.example.kev.projetkevloic.Database.OrnithoDB;
import com.example.kev.projetkevloic.ViewHolder.ObservViewHolder;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.object.Observation;

import java.util.List;

/**
 * Created by Kev on 26.04.2017.
 */

public class ObservAdapter extends ArrayAdapter<Observation> {

    public ObservAdapter(Context context, List<Observation> observs) {
        super(context, 0, observs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_observ,parent, false);

        }

        ObservViewHolder viewHolder = (ObservViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ObservViewHolder();
            viewHolder.orni = (TextView) convertView.findViewById(R.id.nomOrniObserv);
            viewHolder.oiseau = (TextView) convertView.findViewById(R.id.nomOiseauObserv);
            viewHolder.text = (TextView) convertView.findViewById(R.id.nomTextObserv);
            convertView.setTag(viewHolder);

        }

        //getItem(position) va récupérer l'item [position] de la List<Oiseau> oiseaux
        Observation observ = getItem(position);


        viewHolder.orni.setText(observ.getOrniN());
        viewHolder.oiseau.setText(observ.getOiseauN());
        viewHolder.text.setText(observ.getText());

        return convertView;
    }

    private class TweetViewHolder{
        public TextView nom;
        public TextView text;
        public ImageView avatar;

    }
}
