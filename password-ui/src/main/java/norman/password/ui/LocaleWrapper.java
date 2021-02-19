package norman.password.ui;

import java.util.Locale;
import java.util.Objects;

public class LocaleWrapper {
    private Locale locale;

    public LocaleWrapper() {
        this.locale = Locale.getDefault();
    }

    public LocaleWrapper(Locale locale) {
        this.locale = locale;
    }

    public LocaleWrapper(String languageTag) {
        this.locale = Locale.forLanguageTag(languageTag);
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocaleWrapper that = (LocaleWrapper) o;
        return Objects.equals(locale, that.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale);
    }

    @Override
    public String toString() {
        return locale.getDisplayName();
    }
}
