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
 * Created by Seulki_Lee on 5/6/2015.
 */
public class WishAdapter extends ArrayAdapter<Wish> {
    Context context;
    List<Wish> wishs;

    public WishAdapter(Context context, List<Wish> wishs) {
        super(context, R.layout.list_wish, wishs);
        this.context = context;
        this.wishs = wishs;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_wish, parent, false);


        TextView itemTextView = (TextView) view.findViewById((R.id.wishViewDate));
        itemTextView.setText(((Wish) (wishs.get(0))).getDate());

        TextView itemTextView1 = (TextView) view.findViewById((R.id.wishViewAmount));
        itemTextView1.setText(((Wish) (wishs.get(0))).getMoney().toString());

        TextView itemTextView2 = (TextView) view.findViewById((R.id.wishViewNeed));
        itemTextView2.setText(((Wish) (wishs.get(0))).getRemain().toString());

        TextView itemTextView3 = (TextView) view.findViewById((R.id.wishViewsaved));
        itemTextView3.setText(((Wish) (wishs.get(0))).getsaved().toString());

        TextView itemTextView4 = (TextView) view.findViewById((R.id.wishView));
        itemTextView4.setText(((Wish) (wishs.get(0))).getTitle());



        return view;
    }
}
