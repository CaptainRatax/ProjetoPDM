package com.example.projetopdmgrupo3.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projetopdmgrupo3.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Caso> casosArrayList = new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<Caso> casosArrayList){
        this.context = context;
        this.casosArrayList = casosArrayList;
    }

    @Override
    public int getCount() {
        return casosArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return casosArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        }

        Caso tempCaso = (Caso) getItem(position);

        TextView txt_titulo = (TextView) convertView.findViewById(R.id.item_titulo);
        TextView txt_subtitulo = (TextView) convertView.findViewById(R.id.item_subtitulo);

        if (tempCaso.getTitulo().length()>30){
            String temp = tempCaso.getTitulo().substring(0,30) + "...";
        }else{
            txt_titulo.setText(tempCaso.getTitulo());
        }
        if(tempCaso.getDescricao().length()>108){
            String temp1 = tempCaso.getDescricao().substring(0,108) + "...";
        }else{
            txt_subtitulo.setText(tempCaso.getDescricao());
        }

        return convertView;
    }
}
