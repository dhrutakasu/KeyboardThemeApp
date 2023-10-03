package com.theme.keyboardthemeapp.UI.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.Helper.DictionaryDatabaseHelper;
import com.theme.keyboardthemeapp.ModelClass.DictionaryModel;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.FavouriteAdapter;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DictionaryTextFragment extends Fragment implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener, FavouriteAdapter.getFavListeners {
    private String Dictionary_Str;
    private View DictionaryView;
    private View LayoutProgress;
    private AutoCompleteTextView AutoTxtWord;
    private ImageView ImgClear, ImgAddFavourite, ImgShare, ImgSpeak, ImgRate, ImgSearch, ImgBackDic;
    private CardView CardEdtDictionaryText;
    private CardView CardRvDictionaryText;
    private ArrayList<String> EnglishToHindiWordList;
    private DictionaryDatabaseHelper helper;
    private int wid;
    private boolean fav;
    private TextView TxtWordEnglish, TxtWordHindi;
    private TextToSpeech tts;
    private RecyclerView RvDictionaryTxt;
    private ArrayList<DictionaryModel> dictionaryModels;

    public static Fragment newInstance(String status) {
        DictionaryTextFragment fragment = new DictionaryTextFragment();
        Bundle args = new Bundle();
        args.putString("FRAG_STR", status);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Dictionary_Str = getArguments().getString("FRAG_STR");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DictionaryView = inflater.inflate(R.layout.fragment_dictionary, container, false);
        if (getContext() != null) {
            initViews();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initListeners();
                }
            }, 2000);
        }

        return DictionaryView;
    }

    private void initViews() {
        CardEdtDictionaryText = DictionaryView.findViewById(R.id.CardEdtDictionaryText);
        CardRvDictionaryText = DictionaryView.findViewById(R.id.CardRvDictionaryText);
        AutoTxtWord = DictionaryView.findViewById(R.id.AutoTxtWord);
        ImgAddFavourite = DictionaryView.findViewById(R.id.ImgAddFavourite);
        ImgClear = DictionaryView.findViewById(R.id.ImgClear);
        ImgShare = DictionaryView.findViewById(R.id.ImgShare);
        ImgSpeak = DictionaryView.findViewById(R.id.ImgSpeak);
        ImgRate = DictionaryView.findViewById(R.id.ImgRate);
        ImgSearch = DictionaryView.findViewById(R.id.ImgSearch);
        ImgBackDic = DictionaryView.findViewById(R.id.ImgBackDic);
        TxtWordHindi = DictionaryView.findViewById(R.id.TxtWordHindi);
        TxtWordEnglish = DictionaryView.findViewById(R.id.TxtWordEnglish);
        RvDictionaryTxt = DictionaryView.findViewById(R.id.RvDictionaryTxt);
        LayoutProgress = DictionaryView.findViewById(R.id.LayoutProgress);
        helper = new DictionaryDatabaseHelper(getContext());
        helper.getDatabase();
        if (Dictionary_Str.equalsIgnoreCase("Home")) {
            ImgSearch.setVisibility(View.VISIBLE);
            ImgClear.setVisibility(View.INVISIBLE);
            ImgBackDic.setVisibility(View.INVISIBLE);
            CardEdtDictionaryText.setVisibility(View.VISIBLE);
            CardRvDictionaryText.setVisibility(View.VISIBLE);
            RvDictionaryTxt.setVisibility(View.GONE);
            tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == 0) {
                        tts.setLanguage(Locale.US);
                    }
                }
            });
        } else {
            CardEdtDictionaryText.setVisibility(View.GONE);
            CardRvDictionaryText.setVisibility(View.GONE);
            RvDictionaryTxt.setVisibility(View.VISIBLE);
            if (Dictionary_Str.equalsIgnoreCase("Favourite")) {
                RvDictionaryTxt.setLayoutManager(new LinearLayoutManager(getContext()));
                dictionaryModels = helper.getDictionaryFavorite();
                FavouriteAdapter adapter = new FavouriteAdapter(getContext(), dictionaryModels, Dictionary_Str, this);
                RvDictionaryTxt.setAdapter(adapter);
            } else {
                RvDictionaryTxt.setLayoutManager(new LinearLayoutManager(getContext()));
                dictionaryModels = helper.getDictionaryRecent();
                FavouriteAdapter adapter = new FavouriteAdapter(getContext(), dictionaryModels, Dictionary_Str, this);
                RvDictionaryTxt.setAdapter(adapter);
            }
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(DictionaryReceiver, new IntentFilter("custom-dictionary-listener"));
    }

    private BroadcastReceiver DictionaryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Constants.PagerDictionary.setCurrentItem(0);
            onResume();
            LayoutProgress.setVisibility(View.GONE);
        }
    };

    private void initListeners() {
        AutoTxtWord.addTextChangedListener(this);
        AutoTxtWord.setOnItemClickListener(this);
        ImgBackDic.setOnClickListener(this);
        ImgClear.setOnClickListener(this);
        ImgSearch.setOnClickListener(this);
        ImgShare.setOnClickListener(this);
        ImgRate.setOnClickListener(this);
        ImgAddFavourite.setOnClickListener(this);
        ImgSpeak.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBackDic:
            case R.id.ImgClear:
                GotoClear();
                break;
            case R.id.ImgSearch:
                GotoSearch();
                break;
            case R.id.ImgShare:
                GotoShare();
                break;
            case R.id.ImgRate:
                GotoRate();
                break;
            case R.id.ImgSpeak:
                GotoSpeak();
                break;
            case R.id.ImgAddFavourite:
                GotoAddFavorite();
                break;
        }
    }

    private void GotoClear() {
        TxtWordEnglish.setText("");
        TxtWordHindi.setText("");
        Constants.HindiWord = null;
        AutoTxtWord.setText("");
        ImgSearch.setVisibility(View.VISIBLE);
        ImgBackDic.setVisibility(View.INVISIBLE);
        ImgAddFavourite.setImageResource(R.drawable.favourite_unpresed);
        Constants.RecentWord = null;
    }

    private void GotoSearch() {
        if (AutoTxtWord.getText().length() != 0) {
            FilterWords(AutoTxtWord.getText().toString());
            return;
        }
        Toast.makeText(getActivity(), "Enter word to search!", Toast.LENGTH_SHORT).show();
    }

    private void GotoShare() {
        if (TxtWordEnglish.getText().length() != 0) {
            ImgShare.setImageResource(R.drawable.share_presed);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share Your Text To");
            intent.putExtra(Intent.EXTRA_TEXT, TxtWordEnglish.getText().toString());
            startActivity(intent);
            return;
        }
        ImgShare.setImageResource(R.drawable.share_unpresed);
        Toast.makeText(getContext(), "Enter any text", Toast.LENGTH_SHORT).show();
    }

    private void GotoRate() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName()));
        getContext().startActivity(intent);
    }

    private void GotoSpeak() {
        String word = AutoTxtWord.getText().toString();
        if (word.length() == 0) {
            ImgSpeak.setImageResource(R.drawable.voice_unpresed);
            return;
        }
        ImgSpeak.setImageResource(R.drawable.voice_presed);
        tts.speak(word, TextToSpeech.QUEUE_FLUSH, null);

    }

    private void GotoAddFavorite() {
        if (wid == -1) {
            return;
        }
        if (!fav) {
            ImgAddFavourite.setImageResource(R.drawable.favourite_presed);
            helper.AddFavorite(wid);
            fav = true;
            return;
        }
        ImgAddFavourite.setImageResource(R.drawable.favourite_unpresed);
        helper.RemoveFavorite(wid);
        fav = false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() == 0) {
            ImgClear.setVisibility(View.INVISIBLE);
            ImgSearch.setVisibility(View.VISIBLE);
            ImgBackDic.setVisibility(View.INVISIBLE);
            AutoTxtWord.setAdapter(new ArrayAdapter(DictionaryView.getContext(), R.layout.layout_item_autotext, new ArrayList()));
        } else if (charSequence.length() == 1) {
            ImgSearch.setVisibility(View.INVISIBLE);
            ImgBackDic.setVisibility(View.VISIBLE);
            ImgClear.setVisibility(View.VISIBLE);
            String s = charSequence.toString();
            EnglishToHindiWordList = helper.getEnglishWordMatching(s);
            AutoTxtWord.setAdapter(new ArrayAdapter<>(DictionaryView.getContext(), R.layout.layout_item_autotext, EnglishToHindiWordList));
        } else if (charSequence.length() >= 2) {
            EnglishToHindiWordList = WordsFilter(EnglishToHindiWordList, charSequence.toString());
            AutoTxtWord.setAdapter(new ArrayAdapter<>(DictionaryView.getContext(), R.layout.layout_item_autotext, EnglishToHindiWordList));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public ArrayList<String> WordsFilter(ArrayList<String> stringList, String Words) {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<String>();
        if (stringList == null) {
            return null;
        }
        for (int i = 0; i < stringList.size(); i++) {
            if (stringList.get(i).startsWith(Words.toLowerCase())) {
                list1.add(stringList.get(i));
            } else {
                arrayList.add(stringList.get(i));
            }
        }
        strings.addAll(list1);
        strings.addAll(arrayList);
        return strings;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (Dictionary_Str.equalsIgnoreCase("Home")) {
            String string = AutoTxtWord.getText().toString();
            if (string.length() != 0) {
                ImgClear.setVisibility(View.VISIBLE);
                EnglishToHindiWordList = helper.getEnglishWordMatching(string.substring(0, 1));
            } else {
                EnglishToHindiWordList = new ArrayList();
            }
            AutoTxtWord.setAdapter(new ArrayAdapter(getActivity(), R.layout.layout_item_autotext, EnglishToHindiWordList));
        }
    }


    public void refreshView() {
        if (Constants.EngBool) {
            FilterWords(Constants.EngWord);
            AutoTxtWord.setText("" + Constants.EngWord, true);
            AutoTxtWord.showDropDown();
            Constants.EngWord = null;
            Constants.EngBool = false;
            ImgClear.setVisibility(View.VISIBLE);
            ImgSearch.setVisibility(View.INVISIBLE);
            ImgBackDic.setVisibility(View.VISIBLE);
        } else if (Constants.RecentWord != null) {
            AutoTxtWord.setText(Constants.RecentWord);
            FilterWords(Constants.RecentWord);
            Constants.EngWord = null;
            Constants.EngBool = false;
            ImgClear.setVisibility(View.VISIBLE);
            ImgSearch.setVisibility(View.INVISIBLE);
            ImgBackDic.setVisibility(View.VISIBLE);
        }
    }

    public void FilterWords(String Word) {
        if (Word.length() != 0) {
            DictionaryModel helperWordByEnglish = helper.getWordByEnglish(Word);
            ArrayList<DictionaryModel> list = new ArrayList<DictionaryModel>();
            if (helperWordByEnglish != null) {
                Constants.RecentWord = Word;
                TxtWordEnglish.setText(helperWordByEnglish.getEnglishWord());
                String[] splitWord = helperWordByEnglish.getHindiWord().split(",");
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < splitWord.length; i++) {
                    builder.append(splitWord[i].trim() + "\n");
                }
                TxtWordHindi.setText(builder);
                wid = helperWordByEnglish.getWId();
                boolean isFavorite = helperWordByEnglish.isFavorite();
                fav = isFavorite;
                if (isFavorite) {
                    ImgAddFavourite.setImageResource(R.drawable.favourite_presed);
                } else {
                    ImgAddFavourite.setImageResource(R.drawable.favourite_unpresed);
                }
                list.add(helperWordByEnglish);
                helper.checkifIdExist(Integer.toString(wid));
            } else if (Constants.HindiWord != null) {
                int wordLastId = helper.getWordLastId();
                Toast.makeText(getContext(), wordLastId + "", Toast.LENGTH_SHORT).show();
                DictionaryModel dictionaryModel = new DictionaryModel(Word, false, Constants.HindiWord, wordLastId + 1);
                helper.InsertDictionaryWord(dictionaryModel);
                Constants.RecentWord = Word;
                TxtWordEnglish.setText(dictionaryModel.getEnglishWord());
                String[] split2 = dictionaryModel.getHindiWord().split(",");
                StringBuilder sb2 = new StringBuilder();
                for (int i2 = 0; i2 < split2.length; i2++) {
                    sb2.append(split2[i2].trim() + "\n");
                }
                TxtWordHindi.setText(sb2);
                wid = dictionaryModel.getWId();
                boolean isFavorite2 = dictionaryModel.isFavorite();
                fav = isFavorite2;
                if (isFavorite2) {
                    ImgAddFavourite.setImageResource(R.drawable.favourite_presed);
                } else {
                    ImgAddFavourite.setImageResource(R.drawable.favourite_unpresed);
                }
            } else {
                wid = -1;
                TxtWordHindi.setText("No Word found!");
            }
            EnglishToHindiWordList = helper.getEnglishWordMatching(Word.substring(0, 1));
            AutoTxtWord.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.layout_item_autotext, EnglishToHindiWordList));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Dictionary_Str.equalsIgnoreCase("Home")) {
            AutoTxtWord.setText("");
            refreshView();
        } else if (Dictionary_Str.equalsIgnoreCase("Favourite")) {
            if (RvDictionaryTxt != null) {
                ArrayList<DictionaryModel> favoriteWordList = helper.getDictionaryFavorite();
                RvDictionaryTxt.setAdapter(new FavouriteAdapter(getActivity(), favoriteWordList, Dictionary_Str, this));
            }
        } else if (Dictionary_Str.equalsIgnoreCase("Recent")) {
            if (RvDictionaryTxt != null) {
                ArrayList<DictionaryModel> recentWordList = helper.getDictionaryRecent();
                FavouriteAdapter adapter = new FavouriteAdapter(getContext(), recentWordList, Dictionary_Str, this);
                RvDictionaryTxt.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (Dictionary_Str.equalsIgnoreCase("Home")) {
            if (tts != null) {
                tts.stop();
                tts.shutdown();
                Constants.RecentWord = null;
            }
        }
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FilterWords(AutoTxtWord.getText().toString());
        Constants.hideKeyboard(getActivity());
    }

    @Override
    public void DeleteFav(int FavId, int position, String dictionary_Str) {
        if (dictionary_Str.equalsIgnoreCase("Favorite")) {
            helper.deleteFavourite(FavId);
        } else {
            helper.deleteDictionaryRecent(FavId);
        }
        dictionaryModels.remove(position);
        RvDictionaryTxt.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void ClickFav(DictionaryModel dictionaryModel, int pos) {
        Constants.EngWord = dictionaryModel.getEnglishWord();
        Constants.EngBool = true;
        LayoutProgress.setVisibility(View.VISIBLE);
        Intent intent = new Intent("custom-dictionary-listener");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
}
