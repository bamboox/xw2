package com.ace.util;

import com.google.common.base.Joiner;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by bamboo on 18-1-2.
 */
public class SaltHelper {
    private static Logger log = LoggerFactory.getLogger(SaltHelper.class);

    private static Joiner.MapJoiner joiner = Joiner.on("").withKeyValueSeparator("").useForNull("");

    public static String buildSign(Map<String, String> params, String code) {
        try {
            String line = joiner.join(new TreeMap<>(params));
            Mac hmac = Mac.getInstance("HmacSHA1");
            SecretKeySpec sec = new SecretKeySpec(code.getBytes(), "HmacSHA1");
            hmac.init(sec);
            byte[] digest = hmac.doFinal(line.getBytes());
            return new String(new Hex().encode(digest), "UTF-8");
        } catch (Exception e) {
            log.error("build sign caught: " + e.getMessage(), e);
            return "";
        }
    }
}
