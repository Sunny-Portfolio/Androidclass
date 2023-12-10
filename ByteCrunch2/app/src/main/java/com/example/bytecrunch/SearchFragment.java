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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bytecrunch.apiResponse.ResponseAPI;
import com.example.bytecrunch.apiResponse.ResultsItem;
import com.example.bytecrunch.helper.Constants;
import com.example.bytecrunch.helper.Resource;
import com.example.bytecrunch.news.FakeDataSource;
import com.example.bytecrunch.ui.NewsViewModel;
import com.example.bytecrunch.viewholder.NewsPostCallback;
import com.example.bytecrunch.viewholder.PostsListAdapter;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    RecyclerView searchListView;
    PostsListAdapter postsListAdapter;
    NewsViewModel viewModel;
    ProgressBar progressBar;
    EditText editText_search;
    private Runnable searchRunnable;
    private Handler searchHandler = new Handler();


    // Use for pagination condition
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean isScrolling = false;
    private boolean isError = false;





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchListView = view.findViewById(R.id.ID_search_list);
        editText_search = view.findViewById(R.id.ID_search_box);
        searchListView.setLayoutManager(new GridLayoutManager(getContext(),2));
        searchListView.setHasFixedSize(true);
        postsListAdapter = new PostsListAdapter(new NewsPostCallback());
        searchListView.setAdapter(postsListAdapter);
        progressBar = view.findViewById(R.id.ID_searchList_progressBar);



        // Set this viewModel to the viewModel in MainActivity to access it
        if (getActivity() instanceof MainActivity) {
            viewModel = ((MainActivity) getActivity()).viewModel;
        }


        /**
         * Set on click listener for the article news click event
         * and set navigation to the NewsDetailFragment (to read the article)
         */
        postsListAdapter.setOnItemClickListener(new PostsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ResultsItem resultsItem) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("resultsItem", resultsItem);
                Navigation.findNavController(view).navigate(R.id.action_ID_btm_bar_search_to_newsDetailsFragment, bundle);
            }
        });


        /**
         * Set on click listener for the article news click event
         * and set navigation to the NewsDetailFragment (to read the article)
         */
//        postsListAdapter.setOnNewsPostClickListener(new PostsListAdapter.OnPostItemClickEvent() {
//            @Override
//            public void onPostItemClick(ResultsItem resultsItem) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("KEY_ResultItem", resultsItem);
//                Navigation.findNavController(getActivity(), R.id.action_ID_btm_bar_search_to_newsDetailsFragment)
//                        .navigate(R.id.action_ID_btm_bar_search_to_newsDetailsFragment, bundle);
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


        /**
         * Insert a delay before executing API search query
         */
        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Cancel the previous searchRunnable if it exists
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                // Set a new runnable
                searchRunnable = new Runnable() {
                    @Override
                    public void run() {
                        String searchText = s.toString();
                        if (!searchText.isEmpty()) {
                            viewModel.searchNews(searchText);
                        }
                    }
                };

                // Post a delay before executing the search
                searchHandler.postDelayed(searchRunnable, Constants.SEARCH_NEWS_TIME_DELAY);
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /**
         * Setup Observer for LiveData changes from ViewModel and update UI accordingly
        */
        viewModel.grabSearchNews().observe(getViewLifecycleOwner(), new Observer<Resource<ResponseAPI>>() {
            @Override
            public void onChanged(Resource<ResponseAPI> responseAPIResource) {
                if (responseAPIResource instanceof Resource.Success) {
                    hideProgressBar();
                    hideErrorMessage();
                    ResponseAPI responseAPI = ((Resource.Success<ResponseAPI>) responseAPIResource).getData();

                    if (responseAPI != null) {
                        // get the list from API response
                        // Fix 1 Set and pass new List
                        postsListAdapter.submitList(new ArrayList<>(responseAPI.getArticles().getResults()));


                        // Last page checking and RecyclerView padding adjustment
                        int pageSize = Constants.QUERY_PAGE_SIZE;
                        int totalPages = responseAPI.getArticles().getTotalResults() / pageSize + 2;
                        isLastPage = viewModel.getSearchNewsPage() == totalPages;
                        if (isLastPage) {
                            searchListView.setPadding(0, 0, 0, 0);
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
        searchListView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(@NonNull RecyclerView searchListView, int dx, int dy) {
                super.onScrolled(searchListView, dx, dy);

                // Get the positions and counts for condition testing
                LinearLayoutManager layoutManager = (LinearLayoutManager) searchListView.getLayoutManager();
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
                    viewModel.searchNews(editText_search.getText().toString());
                    isScrolling = false;
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView searchListView, int newState) {
                super.onScrollStateChanged(searchListView, newState);

                // Set the scrolling to true if scrolling. Use for pagination
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    isScrolling = true;
                }
            }
        });


        

//        /**
//         * Instantiate Swipe refresh Layout
//         * and setup action when news list is refreshed
//         */
//        swipeRefreshLayout = view.findViewById(R.id.ID_newsList_swipeLayout);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // temp test
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Update the list with updated news
//                        // TODO: 12/3/23 change this
////                        postsListAdapter.submitList(FakeDataSource.getFakeUpdatedStaticListNews());
//                        swipeRefreshLayout.setRefreshing(false);
//
//
//                        /**
//                         * After list is updated, scroll recycleView to top of the list
//                         */
//                        postsListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//                            @Override
//                            public void onItemRangeInserted(int positionStart, int itemCount) {
//                                super.onItemRangeInserted(positionStart, itemCount);
//                                searchListView.smoothScrollToPosition(positionStart);
//                            }
//                        });
//
//
//                    }
//                }, 1200);
//
//            }
//        });



        // Get fake news
        FakeDataSource fakeDataSource = new FakeDataSource();
//        postsListAdapter.submitList(fakeDataSource.getFakeListNews());
    }


}