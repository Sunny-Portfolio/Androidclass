package com.example.bytecrunch;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    SettingsPref settingsPref;
    private RadioGroup themeSelection;
    private RadioButton pick_lightTheme, pick_darkTheme, pick_contrastTheme;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * Setup the radio button
         */
        themeSelection = view.findViewById(R.id.ID_theme_selection);
        pick_lightTheme = view.findViewById(R.id.ID_theme_light);
        pick_darkTheme = view.findViewById(R.id.ID_theme_dark);
        pick_contrastTheme = view.findViewById(R.id.ID_theme_contrast);

        settingsPref = new SettingsPref(getContext());

        /**
         * Check saved preference for theme selection and check the corresponding radio button
         */
        switch (settingsPref.getThemeSelection()) {
            case SettingsPref.THEME_LIGHT:
                pick_lightTheme.setChecked(true);
                break;
            case SettingsPref.THEME_DARK:
                pick_darkTheme.setChecked(true);
                break;
            case SettingsPref.THEME_CONTRAST:
                pick_contrastTheme.setChecked(true);
                break;
            default:
                pick_lightTheme.setChecked(true);

        }


        /**
         * Select the corresponding theme when a new theme is selected
         */
        themeSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.ID_theme_light)
                    settingsPref.setThemeSelection(SettingsPref.THEME_LIGHT);
                else if (checkedId == R.id.ID_theme_dark)
                    settingsPref.setThemeSelection(SettingsPref.THEME_DARK);
                else if (checkedId == R.id.ID_theme_contrast)
                    settingsPref.setThemeSelection(SettingsPref.THEME_CONTRAST);
                else
                    settingsPref.setThemeSelection(SettingsPref.THEME_LIGHT);

                getActivity().recreate();

            }
        });


    }
}