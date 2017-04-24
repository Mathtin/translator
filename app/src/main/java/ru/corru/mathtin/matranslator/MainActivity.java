package ru.corru.mathtin.matranslator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import ru.corru.mathtin.bookmark.Database;
import ru.corru.mathtin.bookmark.Entry;
import ru.corru.mathtin.webtranslator.YandexTranslatorAPI;

public class MainActivity extends AppCompatActivity {
    private static YandexTranslatorAPI api = new YandexTranslatorAPI();
    // Important views
    private LinearLayout mTranslateLayout;
    private TextView mTextMessage;
    private TextView mTextResult;
    private Button mInputLanguage;
    private Button mOutputLanguage;
    private LinearLayout mHistoryLayout;
    private ListView mHistoryList;
    private LinearLayout mFavoriteLayout;
    private ListView mFavoriteList;
    // Bookmark data
    private Database db;
    private Map<Long, Entry> history;
    private Map<Long, Entry> favorite;
    // Preferences
    private String inputLangCodePref;
    private String outputLangCodePref;
    private String defaultInputLangCode;
    private String defaultOutputLangCode;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_translator:
                    mTranslateLayout.setVisibility(View.VISIBLE);
                    mFavoriteLayout.setVisibility(View.GONE);
                    mHistoryLayout.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_favorite:
                    mTranslateLayout.setVisibility(View.GONE);
                    mFavoriteLayout.setVisibility(View.VISIBLE);
                    mHistoryLayout.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_history:
                    mTranslateLayout.setVisibility(View.GONE);
                    mFavoriteLayout.setVisibility(View.GONE);
                    mHistoryLayout.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }

    };

    static YandexTranslatorAPI getApi() {
        return api;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Init fields
        mTranslateLayout = (LinearLayout) findViewById(R.id.translate_layout);
        mFavoriteLayout = (LinearLayout) findViewById(R.id.favorite_layout);
        mHistoryLayout = (LinearLayout) findViewById(R.id.history_layout);
        mTextMessage = (TextView) findViewById(R.id.message);
        mTextResult = (TextView) findViewById(R.id.text_result);
        mInputLanguage = (Button) findViewById(R.id.input_lang);
        mOutputLanguage = (Button) findViewById(R.id.output_lang);
        mHistoryList = (ListView) findViewById(R.id.history_list);
        mFavoriteList = (ListView) findViewById(R.id.favorite_list);
        inputLangCodePref = getString(R.string.input_lang_code_pref);
        outputLangCodePref = getString(R.string.output_lang_code_pref);
        defaultInputLangCode = getString(R.string.default_input_lang_code);
        defaultOutputLangCode = getString(R.string.default_output_lang_code);

        // Set navigation listener
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Load language setting
        LoadPreferences();
        // Database
        db = new Database(this);
        history = db.ReadHistory();
        favorite = db.ReadFavorite();
        UpdateHistoryLayout();
        UpdateFavoriteLayout();
    }

    public void LoadPreferences() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        api.setInputLangCode(sharedPref.getString(inputLangCodePref, defaultInputLangCode));
        api.setOutputLangCode(sharedPref.getString(outputLangCodePref, defaultOutputLangCode));
        mInputLanguage.setText(api.getInputLang());
        mOutputLanguage.setText(api.getOutputLang());
    }

    public void SavePreferences() {
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.putString(inputLangCodePref, api.getInputLangCode());
        editor.putString(outputLangCodePref, api.getOutputLangCode());
        editor.commit();
    }

    public void UpdateHistoryLayout() {
        Iterator<Entry> entries = history.values().iterator();
        String[] values = new String[history.values().size()];
        int i = 0;
        while(entries.hasNext()) {
            Entry e = entries.next();
            values[i] = e.toString();
            i++;
        }
        //String[] values = Arrays.copyOf(entries.toArray(), entries.size(), String[].class);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mHistoryList.setAdapter(adapter);
        mHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) mHistoryList.getItemAtPosition(position);
                db.HistoryDelete(itemValue);
                Toast.makeText(getApplicationContext(), "Deleted :\n" +
                        itemValue , Toast.LENGTH_SHORT).show();
                history = db.ReadHistory();
                UpdateHistoryLayout();
            }
        });
    }

    public void UpdateFavoriteLayout() {
        Iterator<Entry> entries = favorite.values().iterator();
        String[] values = new String[favorite.values().size()];
        int i = 0;
        while(entries.hasNext()) {
            Entry e = entries.next();
            values[i] = e.toString();
            i++;
        }
        //String[] values = Arrays.copyOf(entries.toArray(), entries.size(), String[].class);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mFavoriteList.setAdapter(adapter);
        mFavoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) mFavoriteList.getItemAtPosition(position);
                db.FavoriteDelete(itemValue);
                Toast.makeText(getApplicationContext(), "Deleted :\n" +
                        itemValue , Toast.LENGTH_SHORT).show();
                favorite = db.ReadFavorite();
                UpdateFavoriteLayout();
            }
        });
    }

    public void AddToFavorite(View v) {
        String input = mTextMessage.getText().toString();
        String output = mTextResult.getText().toString();
        long id = db.FavoriteInsert(input, output);
        favorite.put(id, new Entry(id, input, output));
        UpdateFavoriteLayout();
    }

    public void AddToHistory(View v) {
        String input = mTextMessage.getText().toString();
        String output = mTextResult.getText().toString();
        long id = db.HistoryInsert(input, output);
        history.put(id, new Entry(id, input, output));
        UpdateHistoryLayout();
    }

    public void Translate(View v) {
        String input = mTextMessage.getText().toString();
        String output = api.translate(input);
        mTextResult.setText(output);
        AddToHistory(v);
    }

    public void SwapLanguage(View v) {
        String tmp = api.getInputLangCode();
        api.setInputLangCode(api.getOutputLangCode());
        api.setOutputLangCode(tmp);
        SavePreferences();
        mInputLanguage.setText(api.getInputLang());
        mOutputLanguage.setText(api.getOutputLang());
    }

    public void ChangeLanguage(View view) {
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtra("input", view.getId() == R.id.input_lang);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (data.getBooleanExtra("input", true)) {
            mInputLanguage.setText(api.getInputLang());
        } else {
            mOutputLanguage.setText(api.getOutputLang());
        }
        SavePreferences();
    }

}
