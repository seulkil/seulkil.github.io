package com.bookkeeping.ongoing.ongoing_bookkeeping.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bookkeeping.ongoing.ongoing_bookkeeping.R;

import java.util.List;

/**
 * Created by Seulki_Lee on 5/5/2015.
 */
public class ChildrenAdapter extends ArrayAdapter<Child>{
    Context context;
    List<Child> children;

    //Context is the SubMenuActivity
    //objects is the list of items
    public ChildrenAdapter(Context context, List<Child> children) {
        super(context, R.layout.list_children, children);
        this.context = context;
        this.children = children;
    }

    public Child getChild(int position){
        return children.get(position);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_children, parent, false);

        TextView itemTextView = (TextView) view.findViewById((R.id.txtLabel));

        itemTextView.setText(((Child) (children.get(position))).getName());

//        TextView priceTextView = (TextView) view.findViewById(R.id.itemPrice);
//        itemPriceStr = Double.toString(((MenuData) (menuItems.get(position))).getPrice());
//        priceTextView.setText(itemPriceStr);
        return view;
    }
}
