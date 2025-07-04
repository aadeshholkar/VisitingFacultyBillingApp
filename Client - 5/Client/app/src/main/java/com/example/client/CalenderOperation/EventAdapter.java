package com.example.client.CalenderOperation;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client.R;

import java.util.List;

public  class EventAdapter extends  RecyclerView.Adapter<EventAdapter.EventViewHolder>{
   private  List<Event> events;
   private AdapterView.OnItemClickListener listener;
   private  TextView eventCategoryView;

    public EventAdapter( List<Event> events) {
        this.events = events;
    }

    public EventAdapter() {

    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_event,parent,false);
        try {
            return new EventViewHolder((CardView) view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
      Event event = events.get(position);
      holder.subject2.setText("Subject :"+event.getSubject());
      holder.class2.setText("Class :"+event.getClass2());
      holder.date2.setText("Date :"+event.getDate2());
      holder.time2.setText("Time :"+event.getTime2());
      holder.type2.setText("Type :"+event.getCalender_t());
      holder.student2.setText(" Student No :"+event.getCalender_S());
      holder.content2.setText("Content :"+event.getCont());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void clearEvents() {
        events.clear();
        events.addAll(events);
        notifyDataSetChanged();
    }
    public  void addEvent(Event event){
        events.add(event);
        notifyItemInserted(events.size() -1);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        public  TextView subject2,class2,date2,time2,type2,student2,content2;
        public CardView cardView;
        public EventViewHolder(@NonNull CardView itemView) throws Exception{
            super(itemView);
            cardView = itemView;
            subject2 = cardView.findViewById(R.id.subject2);
            class2 = cardView.findViewById(R.id.class2);
            date2 = cardView.findViewById(R.id.date2);
            time2 = cardView.findViewById(R.id.time2);
            type2 = cardView.findViewById(R.id.typePRTH);
            student2 = cardView.findViewById(R.id.No_Student);
            content2 = cardView.findViewById(R.id.content2);


        }
    }
}