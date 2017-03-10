package io.github.anthonyeef.cattle.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

/**
 *
 */

public class StatusParsingUtils {
    private static final int LINK_COLOR = Color.parseColor("#E91E63");
    private static final Pattern PATTERN_USER = Pattern.compile("(@.+?)\\s+", Pattern.MULTILINE);

    public static void setStatus(TextView textView, String status) {
        @SuppressWarnings("deprecation")
        String plainText = Build.VERSION.SDK_INT > 24 ?
                Html.fromHtml(status, FROM_HTML_MODE_LEGACY).toString() : Html.fromHtml(status).toString();

        SpannableString spannable = new SpannableString(plainText);
        Linkify.addLinks(spannable, Linkify.WEB_URLS);
        tintUserName(spannable);
        removeUnderLines(spannable);
        textView.setText(spannable, TextView.BufferType.SPANNABLE);
    }

    private static void removeUnderLines(final SpannableString spannable) {
        URLSpan[] spans = spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (final URLSpan span : spans) {
            int start = spannable.getSpanStart(span);
            int end = spannable.getSpanEnd(span);
            spannable.removeSpan(span);
            spannable.setSpan(new URLSpanNoUnderline(span.getURL()), start, end, 0);
        }
    }

    private static void tintUserName(SpannableString spannable) {
        final Matcher m = PATTERN_USER.matcher(spannable);
        while (m.find()) {
            int start = m.start(1);
            int end = m.end(1);
            if (start >= 0 && start < end) {
                spannable.setSpan(new ForegroundColorSpan(LINK_COLOR), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    @SuppressLint("ParcelCreator")
    private static class URLSpanNoUnderline extends URLSpan {

        URLSpanNoUnderline(String url) {
            super(url);
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            super.updateDrawState(tp);
            tp.setUnderlineText(false);
            tp.setColor(LINK_COLOR);
        }
    }
}
