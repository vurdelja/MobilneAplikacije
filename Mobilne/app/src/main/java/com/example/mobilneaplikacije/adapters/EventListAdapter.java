package com.example.mobilneaplikacije.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobilneaplikacije.R;
import com.example.mobilneaplikacije.model.Event;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<Event> {
    private ArrayList<Event> aEvents;
    private OnMoreButtonClickListener moreButtonClickListener;


    public EventListAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.event_card, events);
        this.aEvents = events;
    }

    public interface OnMoreButtonClickListener {
        void onMoreButtonClick(Event event);
    }

    public void setOnMoreButtonClickListener(OnMoreButtonClickListener listener) {
        this.moreButtonClickListener = listener;
    }

    @Override
    public int getCount() {
        return aEvents.size();
    }

    @Nullable
    @Override
    public Event getItem(int position) {
        return aEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_card,
                    parent, false);
        }

        TextView eventName = convertView.findViewById(R.id.eventName);
        TextView eventDate = convertView.findViewById(R.id.eventDate);
        TextView eventStartTime = convertView.findViewById(R.id.eventStartTime);
        TextView eventEndTime = convertView.findViewById(R.id.eventEndTime);
        TextView eventType = convertView.findViewById(R.id.eventType);

        if (event != null) {
            eventName.setText(event.getName());
            eventDate.setText(event.getDate().toString());
            eventType.setText(event.toString());
        }

        return convertView;
    }
}
