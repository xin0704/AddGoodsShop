package com.demo.addgoodsshop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/18.
 */

public class CustomAdapter extends android.widget.BaseAdapter {
    private Activity activity;
    private List<Goods> mData;
    private ViewHolder holder;
    private Map<Integer , ImageView> map = new HashMap<>();

    public CustomAdapter(Activity activity, List<Goods> mData) {
        this.activity = activity;
        this.mData = mData;
    }


    private class ViewHolder{
        TextView name;
        ImageView img;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(activity).inflate(R.layout.item,null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tv);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        map.put(position,holder.img);

        holder.name.setText(mData.get(position).getName());


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity context = (MainActivity) activity;
                int loc[] = new int[2];
               map.get(position).getLocationInWindow(loc);
                context.playAnimation(loc);

            }
        });


        return convertView;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
