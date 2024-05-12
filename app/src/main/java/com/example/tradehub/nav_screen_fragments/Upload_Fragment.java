package com.example.tradehub.nav_screen_fragments;

import static com.example.tradehub.Utility.ImagePickerr.REQUEST_GALLERY;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tradehub.R;
import com.example.tradehub.Utility.ImagePickerr;

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
    private Button addOptionButton, submitButtonPoll , buttonRealEstate,buttondrllproduct,buttonfound,buttonCommunitynews;
    private CardView pollView,realEstateview,productsellview,postnewsView,theftfoundView;
    private ImageView imagesellproduct,imageCommunitynews,imageLostFound,imageRealEstate;
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
productsellview=view.findViewById(R.id.postProductCardView);
postnewsView=view.findViewById(R.id.postNewsCardView);
realEstateview=view.findViewById(R.id.postRealEstateCardView);
theftfoundView=view.findViewById(R.id.foundTheftCardView);
        setupSpinner();
        questionTextView = view.findViewById(R.id.text_question);
        optionsRadioGroup = view.findViewById(R.id.radio_group_options);
        addOptionButton = view.findViewById(R.id.btn_add_option);
        submitButtonPoll = view.findViewById(R.id.btn_submit);
        imageRealEstate=view.findViewById(R.id.imageViewPropertyPhoto);
        imageCommunitynews=view.findViewById(R.id.imageViewNewsPhoto);
        imagesellproduct=view.findViewById(R.id.imageViewProductPhoto);
        imageLostFound=view.findViewById(R.id.imageViewlost);
        Fragment fragment=this;
        imageLostFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragment!=null)
               // ImagePicker.openCameraOrGallery(getActivity(), fragment, imageLostFound);
                ImagePickerr.openCameraOrGallery(getActivity());
            }
        });
        imagesellproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragment!=null)
               // ImagePicker.openCameraOrGallery(getActivity(), fragment, imagesellproduct);
                    ImagePickerr.openCameraOrGallery(getActivity());
            }

        });
        imageRealEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragment!=null)
                //ImagePicker.openCameraOrGallery(getActivity(), fragment, imageRealEstate);
                    ImagePickerr.openCameraOrGallery(getActivity());

            }
        });
        imageCommunitynews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(fragment!=null)
//                ImagePicker.openCameraOrGallery(getActivity(), fragment,imageCommunitynews);
                ImagePickerr.openCameraOrGallery(getActivity());
            }
        });
        // Set click listeners
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             addOption();
            }
        });
        submitButtonPoll.setOnClickListener(new View.OnClickListener() {
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


                switch (selectedItem) {
                    case "Sell product":
                        productsellview.setVisibility(View.VISIBLE);
                        realEstateview.setVisibility(View.GONE);
                        pollView.setVisibility(View.GONE);
                        theftfoundView.setVisibility(View.GONE);
                        postnewsView.setVisibility(View.GONE);
                        break;
                    case "Real E-state":
                        productsellview.setVisibility(View.GONE);
                        realEstateview.setVisibility(View.VISIBLE);
                        pollView.setVisibility(View.GONE);
                        theftfoundView.setVisibility(View.GONE);
                        postnewsView.setVisibility(View.GONE);
                        break;
                    case "Poll":
                        productsellview.setVisibility(View.GONE);
                        realEstateview.setVisibility(View.GONE);
                        pollView.setVisibility(View.VISIBLE);
                        theftfoundView.setVisibility(View.GONE);
                        postnewsView.setVisibility(View.GONE);
                        break;
                    case "Found/theft":
                        productsellview.setVisibility(View.GONE);
                        realEstateview.setVisibility(View.GONE);
                        pollView.setVisibility(View.GONE);
                        theftfoundView.setVisibility(View.VISIBLE);
                        postnewsView.setVisibility(View.GONE);
                        break;
                    case "Community news":
                        productsellview.setVisibility(View.GONE);
                        realEstateview.setVisibility(View.GONE);
                        pollView.setVisibility(View.GONE);
                        theftfoundView.setVisibility(View.GONE);
                        postnewsView.setVisibility(View.VISIBLE);
                        break;
                    default:
                        productsellview.setVisibility(View.GONE);
                        realEstateview.setVisibility(View.GONE);
                        pollView.setVisibility(View.GONE);
                        theftfoundView.setVisibility(View.GONE);
                        postnewsView.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
                //set all invisible
                productsellview.setVisibility(View.GONE);

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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("image main ",Integer.toString(requestCode));
//
//        if (requestCode == ImagePicker.REQUEST_GALLERY_LOST_FOUND && resultCode == Activity.RESULT_OK) {
//            ImagePicker.handleImageSelectionResult(getActivity(), imageLostFound, data);
//            Log.d("image",Integer.toString(requestCode));
//        }
//
//        // Check if the result is from the image selection request for imagesellproduct
//        else if (requestCode == ImagePicker.REQUEST_GALLERY_SELL_PRODUCT && resultCode == Activity.RESULT_OK) {
//            ImagePicker.handleImageSelectionResult(getActivity(), imagesellproduct, data);
//            Log.d("image",Integer.toString(requestCode));
//        }
//
//        // Check if the result is from the image selection request for imageRealEstate
//        else if (requestCode == ImagePicker.REQUEST_GALLERY_REAL_ESTATE && resultCode == Activity.RESULT_OK) {
//            ImagePicker.handleImageSelectionResult(getActivity(), imageRealEstate, data);
//            Log.d("image",Integer.toString(requestCode));
//        }
//
//        // Check if the result is from the image selection request for imageCommunitynews
//        else if (requestCode == ImagePicker.REQUEST_GALLERY_COMMUNITY_NEWS && resultCode == Activity.RESULT_OK) {
//            ImagePicker.handleImageSelectionResult(getActivity(), imageCommunitynews, data);
//            Log.d("image",Integer.toString(requestCode));
//        }
   // }
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d("image","newwwwwwwwwww");
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
        // The user has selected an image either from the camera or the gallery
        if (data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            // Use the selectedImageUri to do something with the image
            // For example, you can display it in an ImageView
            imagesellproduct.setImageURI(selectedImageUri);
            imageCommunitynews.setImageURI(selectedImageUri);
            imageRealEstate.setImageURI(selectedImageUri);
            imageLostFound.setImageURI(selectedImageUri);
        }
    }
}

}
