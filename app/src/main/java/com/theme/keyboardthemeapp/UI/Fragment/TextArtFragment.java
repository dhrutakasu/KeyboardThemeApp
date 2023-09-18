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
                strings = Constants.Nature_art;
                break;
            case "Mood":
                strings = Constants.Mood_art;
                break;
            case "Love":
                strings = Constants.Love_art;
                break;
            case "Fun":
                strings = Constants.Fun_art;
                break;
            case "Food":
                strings = Constants.Food_art;
                break;
            case "Celebration":
                strings = Constants.Celebration_art;
                break;
            case "Eraster":
                strings = Constants.Easter_art;
                break;
            case "Christmas":
                strings = Constants.Christmas;
                break;
            case "emojiart1":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"emojiart1.json")).getJSONArray("artInfo");
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
            case "emojiart2":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"emojiart2.json")).getJSONArray("artInfo");
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
            case "Flowers":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"Flowers.json")).getJSONArray("artInfo");
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
            case "Gesture":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"Gesture.json")).getJSONArray("artInfo");
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
            case "Greetings":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"Greetings.json")).getJSONArray("artInfo");
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
            case "Heart":
                try {
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"Heart.json")).getJSONArray("artInfo");
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
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"Kiss.json")).getJSONArray("artInfo");
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
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"Life.json")).getJSONArray("artInfo");
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
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"Newyear.json")).getJSONArray("artInfo");
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
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"Pet.json")).getJSONArray("artInfo");
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
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"Valentine.json")).getJSONArray("artInfo");
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
                    JSONArray jSONArray = new JSONObject(Constants.loadJSONFromAsset(getContext(),"Weather.json")).getJSONArray("artInfo");
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

    private void getAssests() {
        // Assuming you're inside an Activity or a Context-aware class
        AssetManager assetManager = getActivity().getAssets();

        try {
            // Replace "your_file.txt" with the actual filename of your text file
            InputStream inputStream = assetManager.open("emojiart1.json");

            // Read the content of the file into a string
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                content.append(line);
                content.append('\n');
                System.out.println("--- -- - -- - linnneee  :: " + content.toString());
            }

            // Now, 'content' contains the content of your text file
            String fileContent = content.toString();

            System.out.println("--- -- - -- - fileContent  :: " + fileContent);
            // Split the fileContent using "----" as the delimiter
            String[] splitArray = fileContent.replace("----abcdef-----", "----abcdef").split("----");

            // Convert the array to an ArrayList if needed
//            ASCIIArray = new ArrayList<>(Arrays.asList(splitArray));
//            ASCIIArray.remove(0);
//            System.out.println("--- -- - -- - splitArrayList  :: " + Arrays.toString(ASCIIArray.toArray()));
//            System.out.println("--- -- - -- - splitArrayList  :: " + Arrays.toString(ASCIIArray.toArray()));
            inputStream.close();
        } catch (IOException e) {
            // Handle exceptions, e.g., file not found or IO errors
            System.out.println("----- exxexex ::: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
