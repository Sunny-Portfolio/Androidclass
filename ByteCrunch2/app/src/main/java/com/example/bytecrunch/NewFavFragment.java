package com.example.bytecrunch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bytecrunch.apiResponse.ResultsItem;
import com.example.bytecrunch.news.FakeDataSource;
import com.example.bytecrunch.ui.NewsViewModel;
import com.example.bytecrunch.viewholder.NewsPostCallback;
import com.example.bytecrunch.viewholder.PostsListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewFavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewFavFragment extends Fragment {


    private RecyclerView fav_list;
    private PostsListAdapter postsListAdapter;
    NewsViewModel viewModel;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewFavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewFavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewFavFragment newInstance(String param1, String param2) {
        NewFavFragment fragment = new NewFavFragment();
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
        return inflater.inflate(R.layout.fragment_new_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * Setup the view and adapter
         */
        fav_list = view.findViewById(R.id.ID_fav_news_list);
        fav_list.setLayoutManager(new GridLayoutManager(getContext(),2));
        fav_list.setHasFixedSize(true);

        postsListAdapter = new PostsListAdapter(new NewsPostCallback());
        fav_list.setAdapter(postsListAdapter);


        // Set this viewModel to the viewModel in MainActivity to access it
        if (getActivity() instanceof MainActivity) {
            viewModel = ((MainActivity) getActivity()).viewModel;
        }


        /**
         * Set on click listener for the article news click event
         * and set navigation to the NewsDetailFragment (to read the article)
         */
//        postsListAdapter.setOnNewsPostClickListener(new PostsListAdapter.OnPostItemClickEvent() {
//            @Override
//            public void onPostItemClick(ResultsItem resultsItem) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("KEY_ResultItem", resultsItem);
//                Navigation.findNavController(getActivity(), R.id.action_ID_btm_bar_fav_to_newsDetailsFragment)
//                        .navigate(R.id.action_ID_btm_bar_fav_to_newsDetailsFragment, bundle);
//            }
//
//            @Override
//            public void onPostTextClick() {
//
//            }
//
//            @Override
//            public void onPostImageClick() {
//
//            }
//
//            @Override
//            public void onPostLongClick() {
//
//            }
//        });



        // TODO: 12/2/23 need to change to real data
        // Get fake news
        FakeDataSource fakeDataSource = new FakeDataSource();
//        adapter.submitList(fakeDataSource.getFakeListNews());

    }
}