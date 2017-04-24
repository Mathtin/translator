package ru.corru.mathtin.webtranslator;

/**
 * Created by mathtin on 23.04.2017.
 */

public class Language {
    private String code;
    private String name;

    public Language(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        if (!code.equals(language.code)) return false;
        return name.equals(language.name);

    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Language{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
