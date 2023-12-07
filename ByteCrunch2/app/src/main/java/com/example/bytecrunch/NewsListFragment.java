package com.example.bytecrunch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bytecrunch.apiResponse.ResponseAPI;
import com.example.bytecrunch.helper.Constants;
import com.example.bytecrunch.helper.Resource;
import com.example.bytecrunch.apiResponse.ResultsItem;
import com.example.bytecrunch.ui.NewsViewModel;
import com.example.bytecrunch.viewholder.NewsPostCallback;
import com.example.bytecrunch.viewholder.PostsListAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    PostsListAdapter postsListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsViewModel viewModel;
    ProgressBar progressBar;


    public NewsListFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsListFragment newInstance(String param1, String param2) {
        NewsListFragment fragment = new NewsListFragment();
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
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.ID_newsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        postsListAdapter = new PostsListAdapter(new NewsPostCallback());
//        postsListAdapter = new AsyncListDiffer<ResultsItem>(recyclerView, NewsPostCallback());

        recyclerView.setAdapter(postsListAdapter);

        progressBar = view.findViewById(R.id.ID_newsList_progressBar);

        // Set this viewModel to the viewModel in MainActivity to access it
        if (getActivity() instanceof MainActivity) {
            viewModel = ((MainActivity) getActivity()).viewModel;
        }

        viewModel.grabTopNews().observe(getViewLifecycleOwner(), new Observer<Resource<ResponseAPI>>() {
            @Override
            public void onChanged(Resource<ResponseAPI> responseAPIResource) {
                if (responseAPIResource instanceof Resource.Success) {
                    hideProgressBar();
//                    hideErrorMessage();
                    ResponseAPI responseAPI = ((Resource.Success<ResponseAPI>) responseAPIResource).getData();

                    if (responseAPI != null) {
                        // TODO: 12/5/23 newsAdapter needs to change, ?to postsListAdapter
//                        newsAdapter.differ.submitList(responseAPI.getArticles().toList());
                        postsListAdapter.submitList(responseAPI.getArticles().getResults()); // get the list from API response

                        int pageSize = Constants.QUERY_PAGE_SIZE;
                        int totalPages = responseAPI.getArticles().getTotalResults() / pageSize + 2;
//                        isLastPage = viewModel.getTopNewsPage() == totalPages;
//                        if (isLastPage) {
//                            rvBreakingNews.setPadding(0, 0, 0, 0);
//                        }
                    }
                } else if (responseAPIResource instanceof Resource.Error) {
                    hideProgressBar();
                    String message = ((Resource.Error) responseAPIResource).getMessage();
                    if (message != null) {
                        Toast.makeText(getContext(), "An error occurred: " + message, Toast.LENGTH_LONG).show();
//                        showErrorMessage(message);
                    }
                } else if (responseAPIResource instanceof Resource.Loading) {
                    showProgressBar();
                }


            }
            private void hideProgressBar() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            private void showProgressBar() {
                progressBar.setVisibility(View.VISIBLE);
            }

        });






        /**
         * Instantiate Swipe refresh Layout
         * and setup action when news list is refreshed
         */
        swipeRefreshLayout = view.findViewById(R.id.ID_newsList_swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // temp test
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Update the list with updated news
                        // TODO: 12/3/23 change this
//                        postsListAdapter.submitList(FakeDataSource.getFakeUpdatedStaticListNews());
                        swipeRefreshLayout.setRefreshing(false);


                        /**
                         * After list is updated, scroll recycleView to top of the list
                         */
                        postsListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                            @Override
                            public void onItemRangeInserted(int positionStart, int itemCount) {
                                super.onItemRangeInserted(positionStart, itemCount);
                                recyclerView.smoothScrollToPosition(positionStart);
                            }
                        });


                    }
                }, 1200);

            }
        });

        /**
         * fake data
         */
//        FakeDataSource fakeDataSource = new FakeDataSource();
//        postsListAdapter.submitList(fakeDataSource.getFakeListNews());
//
        ResultsItem resultsItem = new ResultsItem();
//        postsListAdapter.submitList(resultsItem.get);
        Log.d("News Fragment", "onViewCreated: sent fake data to adapter");
    }
}