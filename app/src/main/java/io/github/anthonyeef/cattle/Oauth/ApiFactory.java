package io.github.anthonyeef.cattle.oauth;

/**
 * @author mcxiaoke
 * @version 1.0 2012-2-27 上午11:42:44
 */
public final class ApiFactory {

    public static Api getDefaultApi() {
        return new FanFouApi();
    }

    public static ApiParser getDefaultParser() {
        return new FanFouParser();
    }
}
