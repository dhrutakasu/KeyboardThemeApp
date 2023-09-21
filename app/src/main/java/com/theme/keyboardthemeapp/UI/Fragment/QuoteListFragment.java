package com.theme.keyboardthemeapp.UI.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.QuoteModel;
import com.theme.keyboardthemeapp.ModelClass.StatusItem;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInstance;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInterface;
import com.theme.keyboardthemeapp.UI.Activities.ViewJokeQuoteActivity;
import com.theme.keyboardthemeapp.UI.Adapters.JokesAdapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteListFragment extends Fragment {
    private Context context;
    private String Status_Id,Status_Str;
    private View QuoteView;
    private RecyclerView RvFancyTxt;
    private View LayoutProgress;

    public static Fragment newInstance(String id, String status) {
        QuoteListFragment fragment = new QuoteListFragment();
        Bundle args = new Bundle();
        args.putString("FRAG_ID", id);
        args.putString("FRAG_STR", status);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Status_Id = getArguments().getString("FRAG_ID");
            Status_Str = getArguments().getString("FRAG_STR");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        QuoteView = inflater.inflate(R.layout.fragment_fancy, container, false);
        if (QuoteView != null) {
            initViews();
        }

        return QuoteView;
    }

    private void initViews() {
        context = getContext();
        RvFancyTxt = (RecyclerView) QuoteView.findViewById(R.id.RvFancyTxt);
        LayoutProgress = (View) QuoteView.findViewById(R.id.LayoutProgress);
        GetQuotesList();
    }

    private void GetQuotesList() {
        if (Constants.isNetworkAvailable(context)) {
            LayoutProgress.setVisibility(View.VISIBLE);
            RetrofitInterface downloadService = RetrofitInstance.createService(RetrofitInterface.class, Constants.BASE_URL);
            Call<QuoteModel> call = downloadService.getQuotesData(Constants.QUOTE_BASE_URL + Status_Id + ".json");
            call.enqueue(new Callback<QuoteModel>() {
                @Override
                public void onResponse(Call<QuoteModel> call, Response<QuoteModel> response) {
                    if (response.isSuccessful()) {
                        LayoutProgress.setVisibility(View.GONE);
                        Constants.statusItems = new ArrayList<>();
                        Constants.statusItems = (ArrayList<StatusItem>) response.body().getStatus();
                        RvFancyTxt.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                        JokesAdapter jokesAdapter=new JokesAdapter(context, Constants.statusItems, new JokesAdapter.setQuoteListener() {
                            @Override
                            public void QuoteListen(int pos) {
                                Intent intent = new Intent(context, ViewJokeQuoteActivity.class);
                                intent.putExtra(Constants.QUOTE_POS, pos);
                                intent.putExtra(Constants.TITLES, Status_Str);
                                startActivity(intent);
                            }
                        });
                        RvFancyTxt.setAdapter(jokesAdapter);
                    }
                }

                @Override
                public void onFailure(Call<QuoteModel> call, Throwable t) {
                    LayoutProgress.setVisibility(View.GONE);
                }
            });
        } else {
            Constants.NoInternetConnection(getActivity());
        }
    }
}
