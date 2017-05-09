package ru.corru.mathtin.bookmark;

/**
 *  Author: Daniil [Mathtin] Shigapov
 *  Copyright (c) 2017 Mathtin <wdaniil@mail.ru>
 *  This file is released under the MIT license.
 */

public class Entry {
    private long id;
    private String text;
    private String translate;

    public Entry(long id, String text, String translate) {
        this.id = id;
        this.text = text;
        this.translate = translate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry that = (Entry) o;

        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        return translate != null ? translate.equals(that.translate) : that.translate == null;

    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (translate != null ? translate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return text + "\n" + translate;
    }
}
