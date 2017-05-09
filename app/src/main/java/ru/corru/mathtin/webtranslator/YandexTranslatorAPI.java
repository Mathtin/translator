package ru.corru.mathtin.webtranslator;

import org.json.JSONObject;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 *  Author: Daniil [Mathtin] Shigapov
 *  Copyright (c) 2017 Mathtin <wdaniil@mail.ru>
 *  This file is released under the MIT license.
 */

public class YandexTranslatorAPI {
    public static final String url = "https://translate.yandex.net/api/v1.5/tr.json/";
    public static final String tkey = "trnsl.1.1.20170423T123952Z.ead051afc7e8b73a.93f4fd1341d1b849f0be8d3301ce7e3b1c8ddce0";
    public static final String dkey = "dict.1.1.20170423T183854Z.188231a4f580071e.6870e3a8ed13d8ffea8b4e743f57db9ce4e9254a";
    public static final String charset = "UTF-8";
    public static final String ui = "ru";

    private Map<String, Language> languages;
    private String inputLangCode;
    private String outputLangCode;

    public YandexTranslatorAPI() {
        try {
            String query = url + String.format("getLangs?key=%s&ui=%s",
                                               URLEncoder.encode(tkey, charset),
                                               URLEncoder.encode(ui, charset));
            JSONRequest jsonRequest = new JSONRequest();
            jsonRequest.execute(query);
            JSONObject jsonResponse = jsonRequest.get();
            if (jsonResponse == null) {
                // TODO handle response errors
                return;
            } else if (!jsonResponse.isNull("code")) {
                // TODO handle API errors
                return;
            }
            JSONObject jsonLang = jsonResponse.getJSONObject("langs");
            languages = new Hashtable<String, Language>(jsonLang.length());
            Iterator<String> i = jsonLang.keys();
            while (i.hasNext()) {
                String code = i.next();
                languages.put(code, new Language(code, jsonLang.getString(code)));
            }
        } catch (Exception e) {
            // TODO handle init errors
            return;
        }
    }

    public String translate(String text) {
        String res = "";
        try {
            String query = url + String.format("translate?key=%s&text=%s&lang=%s",
                    URLEncoder.encode(tkey, charset),
                    URLEncoder.encode(text, charset),
                    URLEncoder.encode(inputLangCode + '-' + outputLangCode, charset));
            JSONRequest jsonRequest = new JSONRequest();
            jsonRequest.execute(query);
            JSONObject jsonResponse = jsonRequest.get();
            int code = jsonResponse.getInt("code");
            if (code != 200) {
                // TODO handle api translate errors
                return res;
            }
            res = (String) jsonResponse.getJSONArray("text").get(0);
        } catch (Exception e) {
            // TODO handle translate errors
            return res;
        }
        return res;
    }

    public String getInputLangCode() {
        return inputLangCode;
    }

    public void setInputLangCode(String inputLangCode) {
        this.inputLangCode = inputLangCode;
    }

    public String getOutputLangCode() {
        return outputLangCode;
    }

    public void setOutputLangCode(String outputLangCode) {
        this.outputLangCode = outputLangCode;
    }

    public String getInputLang() {
        return languages.get(inputLangCode).getName();
    }

    public String getOutputLang() {
        return languages.get(outputLangCode).getName();
    }

    public Iterator<Language> getLanguages() {
        return languages.values().iterator();
    }
}
