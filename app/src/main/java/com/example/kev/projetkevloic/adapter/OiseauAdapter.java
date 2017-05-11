package com.example.kev.projetkevloic.adapter;

/**
 * Created by Kev on 18.04.2017.
 */


 import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

 import com.example.kev.projetkevloic.ViewHolder.OiseauViewHolder;
 import com.example.kev.projetkevloic.R;
 import com.example.kev.projetkevloic.object.Oiseau;

 import java.util.List;

    public class OiseauAdapter extends ArrayAdapter<Oiseau> {

        public OiseauAdapter(Context context, List<Oiseau> oiseaux) {
            super(context, 0, oiseaux);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_oiseau,parent, false);

            }

            OiseauViewHolder viewHolder = (OiseauViewHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new OiseauViewHolder();
                viewHolder.nom = (TextView) convertView.findViewById(R.id.nom);
                viewHolder.text = (TextView) convertView.findViewById(R.id.text);
                viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
                convertView.setTag(viewHolder);

            }

            //getItem(position) va récupérer l'item [position] de la List<Oiseau> oiseaux
            Oiseau oiseau = getItem(position);
            viewHolder.nom.setText(oiseau.getNom());
            viewHolder.text.setText(oiseau.getTaille());


            return convertView;
        }

        private class TweetViewHolder{
            public TextView nom;
            public TextView text;
            public ImageView avatar;

        }
    }

