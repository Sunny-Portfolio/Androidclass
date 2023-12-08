package com.example.bytecrunch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bytecrunch.apiResponse.ResultsItem;
import com.example.bytecrunch.databinding.FragmentNewsDetailsBinding;
import com.example.bytecrunch.news.FakeDataSource;
import com.example.bytecrunch.news.NewsPost;
import com.example.bytecrunch.ui.NewsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailsFragment extends Fragment {

    NewsViewModel viewModel;

    private NewsDetailsFragmentArgs args;
    private ResultsItem resultsItem;
    private TextView tv_continue;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsDetailsFragment newInstance(String param1, String param2) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
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
        FragmentNewsDetailsBinding fragmentNewsDetailsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.fragment_news_details,
                container, false);

        // Get Safe Args data from another fragment. Set when navigating.
        if (getArguments() != null) {
            resultsItem = NewsDetailsFragmentArgs.fromBundle(getArguments()).getResultsItem();
        }
        fragmentNewsDetailsBinding.setNewsItemData(resultsItem);



        // Temp Test to see if the binding works
        FakeDataSource fakeDataSource = new FakeDataSource();
        NewsPost post = fakeDataSource.generateRandomNewsItem();

        post.setFav(true);
//        fragmentNewsDetailsBinding.setNewsItemData(post);

        return fragmentNewsDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set this viewModel to the viewModel in MainActivity to access it
        if (getActivity() instanceof MainActivity) {
            viewModel = ((MainActivity) getActivity()).viewModel;
        }

        if (getArguments() != null) {
            resultsItem = NewsDetailsFragmentArgs.fromBundle(getArguments()).getResultsItem();
        }


        // set the TextView for continue reading in webview
        tv_continue = view.findViewById(R.id.ID_newsDetail_continue);

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_continue.setTextColor(R.color.grey60);
                tv_continue.setBackgroundColor(R.color.grey30);

                Bundle bundle = new Bundle();
                bundle.putSerializable("resultsItem", resultsItem);
                Navigation.findNavController(view).navigate(R.id.action_newsDetailsFragment_to_webViewFragment, bundle);

            }
        });
//        fragmentNewsDetailsBinding.setNewsItemData(resultsItem);
    }


}