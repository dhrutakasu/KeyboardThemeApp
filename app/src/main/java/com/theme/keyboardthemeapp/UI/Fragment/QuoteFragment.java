package com.theme.keyboardthemeapp.UI.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theme.keyboardthemeapp.R;

import androidx.fragment.app.Fragment;

public class QuoteFragment extends Fragment {
    private String Status_Str;
    private View QuoteView;
    private TextView TxtJokeQuote;

    public static Fragment newInstance(String status) {
        QuoteFragment fragment = new QuoteFragment();
        Bundle args = new Bundle();
        args.putString("FRAG_STR", status);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Status_Str = getArguments().getString("FRAG_STR");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        QuoteView = inflater.inflate(R.layout.fragment_quote, container, false);
        if (QuoteView != null) {
            initViews();
        }

        return QuoteView;
    }

    private void initViews() {
        TxtJokeQuote = (TextView) QuoteView.findViewById(R.id.TxtJokeQuote);
        TxtJokeQuote.setText(Status_Str);
    }

}
