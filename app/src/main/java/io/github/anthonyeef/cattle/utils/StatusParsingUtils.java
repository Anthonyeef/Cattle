package io.github.anthonyeef.cattle.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.TextView;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

/**
 *
 */

public class StatusParsingUtils {
    private static final int LINK_COLOR = Color.parseColor("#E91E63");
    private static final String SCHEME_PROFILE = "cat://profile/";
    private static final Pattern PATTERN_USER = Pattern.compile("(@.+?)\\s+", Pattern.MULTILINE);
    private static final Pattern PATTERN_USERLINK = Pattern.compile("<a href=\"http://fanfou\\.com/(.*?)\" class=\"former\">(.*?)</a>");

    public static void setStatus(TextView textView, String status) {
        final HashMap<String, String> mentions = findMentions(status);

        @SuppressWarnings("deprecation")
        String plainText = Build.VERSION.SDK_INT > 24 ?
                Html.fromHtml(status, FROM_HTML_MODE_LEGACY).toString() : Html.fromHtml(status).toString();

        SpannableString spannable = new SpannableString(plainText);
        Linkify.addLinks(spannable, Linkify.WEB_URLS);
        linkifyUsers(spannable, mentions);
        tintUserName(spannable);
        removeUnderLines(spannable);
        textView.setText(spannable, TextView.BufferType.SPANNABLE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void setSource(TextView textView, String source) {
        source = "来自" + source;
        @SuppressWarnings("deprecation")
        String plainText = Build.VERSION.SDK_INT > 24 ?
                Html.fromHtml(source, FROM_HTML_MODE_LEGACY).toString() : Html.fromHtml(source).toString();

        SpannableString spannable = new SpannableString(plainText);
        Linkify.addLinks(spannable, Linkify.WEB_URLS);
        textView.setText(spannable, TextView.BufferType.SPANNABLE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
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

    private static HashMap<String, String> findMentions(final String htmlText) {
        final HashMap<String, String> map = new HashMap<>();
        final Matcher m = PATTERN_USERLINK.matcher(htmlText);
        while (m.find()) {
            final String userId = m.group(1);
            final String screenName = Html.fromHtml(m.group(2)).toString();
            map.put(screenName, userId);
        }
        return map;
    }

    private static void linkifyUsers(final Spannable spannable, final HashMap<String, String> mentions) {
        final Linkify.MatchFilter filter = (s, start, end) -> {
            String name = s.subSequence(start + 1, end).toString().trim();
            return mentions.containsKey(name);
        };
        final Linkify.TransformFilter transformer = (match, url) -> {
            String name = url.subSequence(1, url.length()).toString().trim();
            return mentions.get(name);
        };
        Linkify.addLinks(spannable, PATTERN_USER, SCHEME_PROFILE, filter,
            transformer);
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
