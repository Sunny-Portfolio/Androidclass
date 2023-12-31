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

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView cityDetails_text;
    static final String STATE_cityDetails = "selectedDetail";

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

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        Log.d("ddd", "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        Log.d("ddd", "onCreateView: ");
        return inflater.inflate(R.layout.fragment_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityDetails_text = (TextView) getActivity().findViewById(R.id.textView_citiesDetails);

        // Recover the instance state.
        if (savedInstanceState != null) {
            selectedCityDetails = savedInstanceState.getString(STATE_cityDetails);
            cityDetails_text.setText(selectedCityDetails);
        }
        // Allow scrolling on bottom textview
        cityDetails_text.setMovementMethod(new ScrollingMovementMethod());
//        Log.d("ddd", "onViewCreated: ");
    }

    public void update_text(int position) {
        Resources res = getResources();
        String [] cities_details = res.getStringArray(R.array.string_array_city_descriptions);
        cityDetails_text.setText(cities_details[position]);
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
//        Log.d("ddd", "onAttach: ");
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int position);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_cityDetails, String.valueOf(cityDetails_text.getText()));

        // Call superclass to save any view hierarchy.
        super.onSaveInstanceState(outState);
//        Log.d("ddd", "onSaveInstanceState: ");
    }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        Log.d("ddd", "onViewStateRestored: ");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d("ddd", "onStart: ");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("ddd", "onResume: ");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.d("ddd", "onPause: ");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.d("ddd", "onStop: ");
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.d("ddd", "onDestroyView: ");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d("ddd", "onDestroy: ");
//    }


}