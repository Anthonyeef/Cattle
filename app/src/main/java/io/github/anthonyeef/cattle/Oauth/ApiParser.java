package io.github.anthonyeef.cattle.Oauth;

import java.util.List;

import io.github.anthonyeef.cattle.Exception.ApiException;

/**
 * @author mcxiaoke
 * @version 1.0 2012-2-23 上午11:54:58
 */
public interface ApiParser {

    public void setAccount(String account);

    public List<String> strings(String response) throws ApiException;

}
