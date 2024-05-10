package com.example.tradehub.nav_screen_fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tradehub.R;

import java.util.ArrayList;
import java.util.List;


public class Upload_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner spinner;
    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button addOptionButton, submitButton;
    private CardView pollView;
    private int optionCount = 1;

    private String mParam1;
    private String mParam2;

    public Upload_Fragment() {
        // Required empty public constructor
    }
    public static Upload_Fragment newInstance(String param1, String param2) {
        Upload_Fragment fragment = new Upload_Fragment();
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
//write code to open camera


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view=inflater.inflate(R.layout.fragment_upload, container, false);
spinner=view.findViewById(R.id.spinner3);
pollView=view.findViewById(R.id.pollView);
        setupSpinner();
        questionTextView = view.findViewById(R.id.text_question);
        optionsRadioGroup = view.findViewById(R.id.radio_group_options);
        addOptionButton = view.findViewById(R.id.btn_add_option);
        submitButton = view.findViewById(R.id.btn_submit);

        // Set click listeners
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             addOption();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handle submit

            }
        });
        return view;
    }

    private void setupSpinner() {
        // Create data for the spinner
        List<String> spinnerData = new ArrayList<>();
        spinnerData.add("Sell product");
        spinnerData.add("Real E-state");
        spinnerData.add("Poll");
        spinnerData.add("Found/theft");
        spinnerData.add("Community news");
        // Create ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, spinnerData);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Set item click listener if needed
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equalsIgnoreCase("poll"))
                    pollView.setVisibility(View.VISIBLE);
                else
                    pollView.setVisibility(View.GONE);
                // Do something with the selected item
                //set visiblity of proper views
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
                //set all invisible
            }
        });
    }
    private void addOption() {
        // Create a new RadioButton
        RadioButton radioButton = new RadioButton(requireContext());
        radioButton.setText("Option " + (++optionCount));
        radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        optionsRadioGroup.addView(radioButton);
    }

    }
