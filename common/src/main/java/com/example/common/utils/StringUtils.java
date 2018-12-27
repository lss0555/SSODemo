package com.example.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Description
 * @Author lss0555
 * @Date 2018/12/27/027 16:25
 **/
public class StringUtils {
    public static String appendUrlParameter(String origUrl, String parameterName, String parameterVal) {
        if (origUrl == null) {
            return null;
        } else {
            String bound = origUrl.contains("?") ? "&" : "?";

            try {
                return origUrl + bound + parameterName + "=" + URLEncoder.encode(parameterVal, "utf-8");
            } catch (UnsupportedEncodingException var5) {
                return null;
            }
        }
    }
}
