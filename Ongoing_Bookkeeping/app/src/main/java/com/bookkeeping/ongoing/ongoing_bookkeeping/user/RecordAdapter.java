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
public class RecordAdapter extends ArrayAdapter<Record> {
        Context context;
        List<Record> records;

public RecordAdapter(Context context, List<Record> records) {
        super(context, R.layout.list_records, records);
        this.context = context;
        this.records = records;
        }

@Override
public View getView(final int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_records, parent, false);

        if(((Record) (records.get(position))).getBoolean("isBilled")){
                TextView itemTextView3 = (TextView) view.findViewById((R.id.recordsBill));
                itemTextView3.setText("Credit");
        } else {
                TextView itemTextView4 = (TextView) view.findViewById((R.id.recordsBill));
                itemTextView4.setText("Debit");
        }

        TextView itemTextView = (TextView) view.findViewById((R.id.recordsDate));
        itemTextView.setText(((Record) (records.get(position))).getDate());

        TextView itemTextView2 = (TextView) view.findViewById((R.id.recordmoney));
        itemTextView2.setText(((Record) (records.get(position))).getMoney().toString());

        TextView itemTextView1 = (TextView) view.findViewById((R.id.recordsTitle));
        itemTextView1.setText(((Record) (records.get(position))).getTitle());





        return view;
        }
}
