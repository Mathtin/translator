package ru.corru.mathtin.matranslator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.Iterator;

import ru.corru.mathtin.webtranslator.Language;
import ru.corru.mathtin.webtranslator.YandexTranslatorAPI;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        LinearLayout selectionGroup = (LinearLayout) findViewById(R.id.SelectionGroup);

        Iterator<Language> i = MainActivity.getApi().getLanguages();
        while(i.hasNext()) {
            Language lang = i.next();
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(lang.getName());
            radioButton.setHint(lang.getCode());
            radioButton.setTextSize(20);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    Intent intent = new Intent();
                    boolean input = getIntent().getBooleanExtra("input", true);
                    if (input) {
                        MainActivity.getApi().setInputLangCode(b.getHint().toString());
                    } else {
                        MainActivity.getApi().setOutputLangCode(b.getHint().toString());
                    }
                    intent.putExtra("input", input);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            selectionGroup.addView(radioButton);
        }
    }
}
