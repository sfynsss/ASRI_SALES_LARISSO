package com.asa.asribares.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asa.asribares.R;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterUser extends ArrayAdapter<String> {

    private String[]text;
    private String[]text2;
    private Integer[]img;
    private Activity context;

    public AdapterUser(Activity context, String[]text, String[]text2, Integer[]img) {
        super(context, R.layout.adapter_user, text);

        this.context = context;
        this.text = text;
        this.text2 = text2;
        this.img = img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_user, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.imageView.setImageResource(img[position]);
        viewHolder.textView.setText(text[position]);
        viewHolder.textView2.setText(text2[position]);

        return v;
    }

    class ViewHolder{
        TextView textView;
        TextView textView2;
        ImageView imageView;
        RelativeLayout relativeLayout;
        ViewHolder(View view){
            textView = (TextView) view.findViewById(R.id.text_listview);
            textView2 = (TextView) view.findViewById(R.id.text_listview2);
            imageView = (ImageView) view.findViewById(R.id.image_listview);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.list_id);
        }
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }
}
