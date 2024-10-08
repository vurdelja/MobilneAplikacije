package com.example.mobilneaplikacije.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mobilneaplikacije.R;
import com.example.mobilneaplikacije.adapters.EventListAdapter;
import com.example.mobilneaplikacije.model.Event;

import java.util.ArrayList;

public class EmployeeEventsAddFragment extends Fragment implements EventListAdapter.OnMoreButtonClickListener {

    private static final String TAG = "EmployeeEventsAddFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public EmployeeEventsAddFragment() {
        // Required empty public constructor
    }

    public static EmployeeEventsAddFragment newInstance(String param1, String param2) {
        EmployeeEventsAddFragment fragment = new EmployeeEventsAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_events_add, container, false);

        if (view != null) {
            ListView listView = view.findViewById(R.id.eventList);
            if (listView != null) {
                ArrayList<Event> events = loadEvents();
                EventListAdapter adapter = new EventListAdapter(getContext(), events);
                adapter.setOnMoreButtonClickListener(this);
                listView.setAdapter(adapter);
            } else {
                Log.e(TAG, "ListView is null");
            }

            Button addEventButton = view.findViewById(R.id.addEventButton);
            if (addEventButton != null) {
                addEventButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventAddFragment eventAddFragment = EventAddFragment.newInstance(null, null);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.employeeConstraint, eventAddFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
            } else {
                Log.e(TAG, "addEventButton is null");
            }
        } else {
            Log.e(TAG, "View is null");
        }

        return view;
    }

    private ArrayList<Event> loadEvents() {
        ArrayList<Event> events = new ArrayList<>();
        //events.add(new Event(""1L"", "John's Cleaning and Gardening", "2024-04-01", "RESERVED"));
        //events.add(new Event(2L, "Jane's Cooking and Babysitting", "2024-04-02", "RESERVED"));
        //events.add(new Event(3L, "Robert's Plumbing and Electrical Work", "2024-04-03", "BUSY"));
        return events;
    }

    @Override
    public void onMoreButtonClick(Event event) {
        if (getContext() != null) {
            Toast.makeText(getContext(), "More button clicked for event: " + event.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Context is null");
        }
    }
}
