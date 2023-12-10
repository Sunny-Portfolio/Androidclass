package com.example.bytecrunch;

import static com.example.bytecrunch.helper.Constants.COUNTRY_USA;
import static com.example.bytecrunch.helper.Constants.QUERY_PAGE_SIZE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bytecrunch.apiResponse.ResponseAPI;
import com.example.bytecrunch.helper.Constants;
import com.example.bytecrunch.helper.Resource;
import com.example.bytecrunch.apiResponse.ResultsItem;
import com.example.bytecrunch.ui.NewsViewModel;
import com.example.bytecrunch.viewholder.NewsPostCallback;
import com.example.bytecrunch.viewholder.PostsListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


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

    private RecyclerView recyclerView;
    private PostsListAdapter postsListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsViewModel viewModel;
    private ProgressBar progressBar;

    // Use for pagination condition
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean isScrolling = false;
    private boolean isError = false;


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


        /**
         * Set on click listener for the article news click event
         * and set navigation to the NewsDetailFragment (to read the article)
         */
        postsListAdapter.setOnItemClickListener(new PostsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ResultsItem resultsItem) {
                Log.d("NEWSLIST", "onPostItemClick: NewsListFragment : ResultItem is : " + resultsItem.getUri());
                Bundle bundle = new Bundle();
                bundle.putSerializable("resultsItem", resultsItem);
                Navigation.findNavController(view).navigate(R.id.action_ID_btm_bar_home_to_newsDetailsFragment, bundle);
            }
        });





        // Set this viewModel to the viewModel in MainActivity to access it
        if (getActivity() instanceof MainActivity) {
            viewModel = ((MainActivity) getActivity()).viewModel;
        }


        /**
         * Setup Observer for LiveData changes from ViewModel and update UI accordingly
         */
        viewModel.grabTopNews().observe(getViewLifecycleOwner(), new Observer<Resource<ResponseAPI>>() {
            @Override
            public void onChanged(Resource<ResponseAPI> responseAPIResource) {
                if (responseAPIResource instanceof Resource.Success) {
                    hideProgressBar();
                    hideErrorMessage();

                    // Retrieve the actual data from the response
                    ResponseAPI responseAPI = ((Resource.Success<ResponseAPI>) responseAPIResource).getData();

                    if (responseAPI != null) {
                        // Submit the list of articles to the adapter to be displayed
                        // There is a bug where the list is not loading more items when scrolled to
                        // the bottom. Here are some work around.

                        // Fix 1 Set and pass new List
                        List<ResultsItem> resultsItemList = new ArrayList<>(responseAPI.getArticles().getResults()) ;
                        postsListAdapter.submitList((resultsItemList));

                        // Fix 2 Turn into Json and convert back
//                        List<ResultsItem> resultsItemList2 = responseAPI.getArticles().getResults();
//                        Gson gson = new Gson();
//                        String json = gson.toJson(resultsItemList2);
//                        Type listType = new TypeToken<List<ResultsItem>>() {}.getType();
//                        postsListAdapter.submitList(gson.fromJson(json, listType)); // This seem to fix for all

                        // Fix 3 Use Java 8 Stream tool toList(). Must have API34
//                        postsListAdapter.submitList(responseAPI.getArticles().getResults().stream().toList()); // Bug: this only works with API 34


                        // These doesn't work
//                        postsListAdapter.submitList(responseAPI.getArticles().getResults()); // Bug: this doesn't load more item


                        // Last page checking and RecyclerView padding adjustment
                        int pageSize = QUERY_PAGE_SIZE;
                        int totalPages = responseAPI.getArticles().getTotalResults() / pageSize + 2;
                        isLastPage = viewModel.getTopNewsPage() == totalPages;
                        if (isLastPage) {
                            recyclerView.setPadding(0, 0, 0, 0);
                        }
                    }
                } else if (responseAPIResource instanceof Resource.Error) {
                    hideProgressBar();
                    String message = ((Resource.Error) responseAPIResource).getMessage();
                    if (message != null) {
                        Toast.makeText(getContext(), "An error occurred: " + message, Toast.LENGTH_LONG).show();
                        showErrorMessage(message);
                    }
                } else if (responseAPIResource instanceof Resource.Loading) {
                    // Show the progress bar while loading data
                    showProgressBar();
                }

            }


            /**
             * Set the Loading boolean accordingly when scrolling reaches bottom and is laoding
             */
            private void hideProgressBar() {
                progressBar.setVisibility(View.INVISIBLE);
                isLoading = false;
            }
            private void showProgressBar() {
                progressBar.setVisibility(View.VISIBLE);
                isLoading = true;
            }

            private void hideErrorMessage() {
//                itemErrorMessage.visibility = View.INVISIBLE;
                isError = false;
            }

            private void showErrorMessage(String message) {
//                itemErrorMessage.visibility = View.VISIBLE;
//                tvErrorMessage.text = message;
                isError = true;
            }

        });




        /**
         * Setup OnScrollListener for the recycle view to loads news when
         * it reaches the bottom of the list
         */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Get the positions and counts for condition testing
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                // Condition testing for pagination
                boolean isNoErrors = !isError;
                boolean isNotLoadingAndNotLastPage = !isLoading && !isLastPage;
                boolean isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount;
                boolean isNotAtBeginning = firstVisibleItemPosition >= 0;
                boolean isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE;
                boolean canPaginate = isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem &&
                        isNotAtBeginning && isTotalMoreThanVisible && isScrolling;

                // If all conditions checks out, do pagination
                if (canPaginate) {
                    viewModel.getTopNews(COUNTRY_USA);
                    isScrolling = false;
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // Set the scrolling to true if scrolling. Use for pagination
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    isScrolling = true;
                }
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
                        // TODO: 12/3/23 change this, currently no refresh data
//                        postsListAdapter.submitList(FakeDataSource.getFakeUpdatedStaticListNews());
//                        postsListAdapter.submitList(responseAPI.getArticles().getResults());


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