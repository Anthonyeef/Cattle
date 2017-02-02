package io.github.anthonyeef.cattle.oauth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.anthonyeef.cattle.exception.ApiException;

/**
 * @author mcxiaoke
 * @version 4.2 2012.03.13
 */
final class FanFouParser implements ApiParser {

    public static final String TAG = FanFouParser.class.getSimpleName();
    public static final boolean DEBUG = true/*AppContext.DEBUG*/;
    private static final String FANFOU_DATE_FORMAT_STRING = "EEE MMM dd HH:mm:ss Z yyyy";
    public static final SimpleDateFormat FANFOU_DATE_FORMAT = new SimpleDateFormat(
            FANFOU_DATE_FORMAT_STRING, Locale.US);
    private static final Pattern PATTERN_SOURCE = Pattern
            .compile("<a href.+blank\">(.+)</a>");
    private static final ParsePosition mPosition = new ParsePosition(0);
    private String account;

    public FanFouParser() {

    }

    public static BitSet parseFriendship(String response) throws ApiException {
        try {
            JSONObject o = new JSONObject(response);
            JSONObject relationship = o.getJSONObject("relationship");
            JSONObject source = relationship.getJSONObject("source");
            BitSet state = new BitSet(3);
            boolean following = source.getBoolean("following");
            boolean followedBy = source.getBoolean("followed_by");
            boolean blocking = source.getBoolean("blocking");
            state.set(0, following);
            state.set(1, followedBy);
            state.set(2, blocking);
            return state;
        } catch (JSONException e) {
            throw new ApiException(ApiException.DATA_ERROR, e.getMessage(), e);
        }
    }

    public static String error(String response) {
//        if (DEBUG) {
//            Log.e(TAG, "error() response:" + response);
//        }
        String result = response;
        try {
            JSONObject o = new JSONObject(response);
            if (o.has("error")) {
                result = o.getString("error");
            }
        } catch (Exception e) {
            result = parseXMLError(response);
        }
        return result;
    }

    private static String parseXMLError(String error) {
        String result = error;
        XmlPullParser pull;
        String tag = null;
        try {
            pull = XmlPullParserFactory.newInstance().newPullParser();
            pull.setInput(new StringReader(error));
            boolean found = false;
            while (!found) {
                int eventType = pull.getEventType();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = pull.getName();
                        if (tag.equalsIgnoreCase("error")) {
                            result = pull.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tag.equalsIgnoreCase("error")) {
                            found = true;
                        }
                        break;
                    default:
                        break;
                }
                pull.next();
            }
        } catch (Exception e) {
        }

        return result;
    }

    public static String parseSource(String input) {
        String source = input;
        Matcher m = PATTERN_SOURCE.matcher(input);
        if (m.find()) {
            source = m.group(1);
        }
        // Log.e("SourceParse", "source="+source);
        return source;
    }

    /**
     * @param s 代表饭否日期和时间的字符串
     * @return 字符串解析为对应的Date对象
     */
    public static Date date(String s) {
        return fanfouStringToDate(s);
    }

    public static Date fanfouStringToDate(String s) {
        // Fanfou Date String example --> "Mon Dec 13 03:10:21 +0000 2010"
        // final ParsePosition position = new ParsePosition(0);//
        // 这个如果放在方法外面的话，必须每次重置Index为0
        mPosition.setIndex(0);
        return FANFOU_DATE_FORMAT.parse(s, mPosition);
    }

    public static long stringToLong(String text) {
        try {
            return Long.parseLong(text);
        } catch (Exception e) {
            return -1;
        }
    }

    public static int stringToInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public List<String> strings(String response) throws ApiException {
        try {
            JSONArray a = new JSONArray(response);
            ArrayList<String> ids = new ArrayList<String>();
            for (int i = 0; i < a.length(); i++) {
                ids.add(a.getString(i));
            }
            return ids;
        } catch (JSONException e) {
            throw new ApiException(ApiException.DATA_ERROR, e.getMessage(), e);
        }
    }



}
