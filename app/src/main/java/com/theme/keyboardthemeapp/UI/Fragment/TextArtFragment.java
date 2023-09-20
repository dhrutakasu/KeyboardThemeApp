package com.theme.keyboardthemeapp.UI.Fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.ArtAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextArtFragment extends Fragment {
    private String Art_Str;
    private View ArtView;
    private RecyclerView RvArtTxt;
    private ArtAdapter artAdapter;
    private View LayoutProgress;

    public static Fragment newInstance(String status) {
        TextArtFragment fragment = new TextArtFragment();
        Bundle args = new Bundle();
        args.putString("FRAG_STR", status);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Art_Str = getArguments().getString("FRAG_STR");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArtView = inflater.inflate(R.layout.fragment_fancy, container, false);
        if (ArtView != null) {
            initViews();
        }

        return ArtView;
    }

    private void initViews() {
        RvArtTxt = (RecyclerView) ArtView.findViewById(R.id.RvFancyTxt);
        LayoutProgress = (View) ArtView.findViewById(R.id.LayoutProgress);
        RvArtTxt.setLayoutManager(new LinearLayoutManager(getContext()));
        String[] stringsList = getList(Art_Str);
        RvArtTxt.setLayoutManager(new GridLayoutManager(getContext(),2));
        artAdapter = new ArtAdapter(getContext(), stringsList);
        RvArtTxt.setAdapter(artAdapter);
    }

    private String[] getList(String art_str) {
        String[] strings = new String[0];
        switch (art_str) {
            case "Nature":
                strings = Constants.ArtNature;
                break;
            case "Mood":
                strings = Constants.ArtMood;
                break;
            case "Love":
                strings = Constants.ArtLove;
                break;
            case "Fun":
                strings = Constants.ArtFun;
                break;
            case "Food":
                strings = Constants.ArtFood;
                break;
            case "Celebration":
                strings = Constants.ArtCelebration;
                break;
            case "Eraster":
                strings = Constants.ArtEaster;
                break;
            case "Christmas":
                strings = Constants.ArtChristmas;
                break;
            case "emojiart1":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtEmojiart1.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "emojiart2":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtEmojiart2.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "Flowers":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtFlowers.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "Gesture":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtGesture.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "Greetings":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtGreetings.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "Heart":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtHeart.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        Log.d("Details-->", jSONObject.getString("artid"));
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "Kiss":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtKiss.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        Log.d("Details-->", jSONObject.getString("artid"));
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "Life":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtLife.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        Log.d("Details-->", jSONObject.getString("artid"));
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "Newyear":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtNewyear.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        Log.d("Details-->", jSONObject.getString("artid"));
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "Pet":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtPet.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        Log.d("Details-->", jSONObject.getString("artid"));
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "Valentine":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtValentine.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        Log.d("Details-->", jSONObject.getString("artid"));
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "Weather":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(), "ArtWeather.json")).getJSONArray("artInfo");
                    strings = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        Log.d("Details-->", jSONObject.getString("artid"));
                        strings[i] = jSONObject.getString("artid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        return strings;
    }
}
