package com.example.simpletourguide;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_B#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_B extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView cityDetails_text;
    static final String STATE_cityDetails = "selectedDetail";
    private int selectedPos;
    private String selectedCityDetails;
    private Fragment_B.OnFragmentInteractionListener mListener;


    public Fragment_B() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_B.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_B newInstance(String param1, String param2) {
        Fragment_B fragment = new Fragment_B();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Recover the instance state.
//        if (savedInstanceState != null) {
////            cityDetails_text.setText(savedInstanceState.getString(STATE_cityDetails));
////            selectedPos = savedInstanceState.getInt(STATE_cityDetails);
//            selectedCityDetails = savedInstanceState.getString(STATE_cityDetails);
////            update_text(selectedPos);
//
//            Log.d("SaveState", "onCreate: Recovered saved instance state: detail is : "
//                + selectedCityDetails);
//
//            cityDetails_text = (TextView) getActivity().findViewById(R.id.textView_citiesDetails);
//
//            cityDetails_text.setText(selectedCityDetails);
//
//        }

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityDetails_text = (TextView) getActivity().findViewById(R.id.textView_citiesDetails);
        Log.d("DB1", "onViewCreated: 0");

        // Recover the instance state.
        if (savedInstanceState != null) {
//            cityDetails_text.setText(savedInstanceState.getString(STATE_cityDetails));
//            selectedPos = savedInstanceState.getInt(STATE_cityDetails);
            selectedCityDetails = savedInstanceState.getString(STATE_cityDetails);
//            update_text(selectedPos);

            Log.d("SaveState", "onViewCreated: Recovered saved instance state: detail is : "
                + selectedCityDetails);

//            cityDetails_text = (TextView) getActivity().findViewById(R.id.textView_citiesDetails);
            Log.d("SaveState", "onViewCreated: 1");
            cityDetails_text.setText(selectedCityDetails);
            Log.d("SaveState", "onViewCreated: 2");


        }

        cityDetails_text.setMovementMethod(new ScrollingMovementMethod());
    }

    public void update_text(int position) {
        selectedPos = position;
        Log.d("db1", "update_text: 1");
        Resources res = getResources();
        Log.d("db1", "update_text: 2");

        String [] cities_details = res.getStringArray(R.array.string_array_city_descriptions);
        Log.d("db1", "update_text: 3 " + cities_details[position]);

        cityDetails_text.setText(cities_details[position]);
        Log.d("TAG", "In Update_text --->>> Item " + position + " is selected!");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Fragment_B.OnFragmentInteractionListener) {
            mListener = (Fragment_B.OnFragmentInteractionListener)  context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int position);
    }

//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
////        cityDetails_text.setText(savedInstanceState.getString(STATE_cityDetails));
//
//        Log.d("SaveState", "onRestoreInstanceState: 1");
//        selectedCityDetails = savedInstanceState.getString(STATE_cityDetails);
//
//        Log.d("SaveState", "onRestoreInstanceState: 2");
//        cityDetails_text = (TextView) getActivity().findViewById(R.id.textView_citiesDetails);
//        Log.d("SaveState", "onRestoreInstanceState: 3");
//
//        cityDetails_text.setText(selectedCityDetails);
//        Log.d("SaveState", "onRestoreInstanceState: 4");
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_cityDetails, String.valueOf(cityDetails_text.getText()));
//        outState.putInt(STATE_cityDetails, selectedPos);
        Log.d("SaveState", "onSaveInstanceState: Text is: " + String.valueOf(cityDetails_text.getText()));

        // Call superclass to save any view hierarchy.
        super.onSaveInstanceState(outState);
    }

}