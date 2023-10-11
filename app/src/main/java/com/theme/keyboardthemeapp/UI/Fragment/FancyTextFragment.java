package com.theme.keyboardthemeapp.UI.Fragment;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.ASCIIArtAdapter;
import com.theme.keyboardthemeapp.UI.Adapters.DecorativeAdapter;
import com.theme.keyboardthemeapp.UI.Adapters.FancyAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FancyTextFragment extends Fragment implements View.OnClickListener {
    private String Fancy_Str;
    private View FancyView;
    private RecyclerView RvFancyTxt;
    private CardView CardEdtFancyText;
    private EditText EdtFancyTxt;
    private ImageView ImgCancelFancyTxt;
    private String EdtFancyStr = "Font Style";
    private FancyAdapter fancyAdapter;
    private DecorativeAdapter decorativeAdapter;
    private ASCIIArtAdapter asciiArtAdapter;
    private ArrayList<String> ASCIIArray = new ArrayList<>();
    private View LayoutProgress;

    public static Fragment newInstance(String status) {
        FancyTextFragment fragment = new FancyTextFragment();
        Bundle args = new Bundle();
        args.putString("FRAG_STR", status);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Fancy_Str = getArguments().getString("FRAG_STR");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FancyView = inflater.inflate(R.layout.fragment_fancy, container, false);
        if (FancyView != null) {
            initViews();
        }

        return FancyView;
    }

    private void initViews() {
        RvFancyTxt = (RecyclerView) FancyView.findViewById(R.id.RvFancyTxt);
        CardEdtFancyText = (CardView) FancyView.findViewById(R.id.CardEdtFancyText);
        EdtFancyTxt = (EditText) FancyView.findViewById(R.id.EdtFancyTxt);
        ImgCancelFancyTxt = (ImageView) FancyView.findViewById(R.id.ImgCancelFancyTxt);
        LayoutProgress = (View) FancyView.findViewById(R.id.LayoutProgress);
        CardEdtFancyText.setVisibility(View.VISIBLE);
        EdtFancyTxt.clearFocus();
        ImgCancelFancyTxt.setOnClickListener(this);
        RvFancyTxt.setLayoutManager(new LinearLayoutManager(getContext()));

        EdtFancyTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                if (s.equalsIgnoreCase("")) {
                    EdtFancyStr = "Font Style";
                } else {
                    EdtFancyStr = charSequence.toString();
                }
                if (Fancy_Str.equalsIgnoreCase("Fancy")) {
                    fancyAdapter = new FancyAdapter(getActivity(),getContext(), Constants.FancyNameStyle, EdtFancyStr);
                    RvFancyTxt.setAdapter(fancyAdapter);
                } else if (Fancy_Str.equalsIgnoreCase("Decorative")) {
                    decorativeAdapter = new DecorativeAdapter(getActivity(),getContext(), Constants.FancyDecorative, EdtFancyStr);
                    RvFancyTxt.setAdapter(decorativeAdapter);
                } else if (Fancy_Str.equalsIgnoreCase("ASCII art")) {
                    asciiArtAdapter = new ASCIIArtAdapter(getActivity(),getContext(), ASCIIArray, EdtFancyStr);
                    RvFancyTxt.setAdapter(asciiArtAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (Fancy_Str.equalsIgnoreCase("Fancy")) {
            fancyAdapter = new FancyAdapter(getActivity(),getContext(), Constants.FancyNameStyle, EdtFancyStr);
            RvFancyTxt.setAdapter(fancyAdapter);
        } else if (Fancy_Str.equalsIgnoreCase("Decorative")) {
            decorativeAdapter = new DecorativeAdapter(getActivity(),getContext(), Constants.FancyDecorative, EdtFancyStr);
            RvFancyTxt.setAdapter(decorativeAdapter);
        } else if (Fancy_Str.equalsIgnoreCase("ASCII art")) {
            LayoutProgress.setVisibility(View.VISIBLE);
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    getAssests();
                    return null;
                }

                @Override
                protected void onPostExecute(Void unused) {
                    super.onPostExecute(unused);
                    LayoutProgress.setVisibility(View.GONE);
                    for (int i = 0; i < ASCIIArray.size(); i++) {
                        if (ASCIIArray.get(i).equalsIgnoreCase("abcdef")){
                            ASCIIArray.remove(i);
                        }
                    }
                    asciiArtAdapter = new ASCIIArtAdapter(getActivity(),getContext(),  ASCIIArray, EdtFancyStr);
                    RvFancyTxt.setAdapter(asciiArtAdapter);
                }
            }.execute();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgCancelFancyTxt:
                GotoCancelFancyText();
                break;
        }
    }

    private void GotoCancelFancyText() {
        EdtFancyTxt.setText("");
        EdtFancyStr = "Font Style";
        if (Fancy_Str.equalsIgnoreCase("Fancy")) {
            fancyAdapter = new FancyAdapter(getActivity(),getContext(), Constants.FancyNameStyle, EdtFancyStr);
            RvFancyTxt.setAdapter(fancyAdapter);
        } else if (Fancy_Str.equalsIgnoreCase("Decorative")) {
            decorativeAdapter = new DecorativeAdapter(getActivity(),getContext(), Constants.FancyDecorative, EdtFancyStr);
            RvFancyTxt.setAdapter(decorativeAdapter);
        } else if (Fancy_Str.equalsIgnoreCase("ASCII art")) {
            asciiArtAdapter = new ASCIIArtAdapter(getActivity(),getContext(), ASCIIArray, EdtFancyStr);
            RvFancyTxt.setAdapter(asciiArtAdapter);
        }
    }

    private void getAssests() {
        AssetManager assetManager = getActivity().getAssets();

        try {
            InputStream inputStream = assetManager.open("FancyASCII.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append('\n');
            }

            String fileContent = content.toString();
            String[] splitArray = fileContent.replace("----abcdef-----","----abcdef").split("----");
            ASCIIArray = new ArrayList<>(Arrays.asList(splitArray));
            ASCIIArray.remove(0);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
