package com.ace.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by bamboo on 17-12-15.
 */
public class JpushSdk {
    private static Logger log = LoggerFactory.getLogger(JpushSdk.class);

    private final static String AppKey = "1392ea90708e869a1593b8bc";
    private final static String Secret = "5066c94d7b37d58612b5cfe7";

    public static void sendMsgByAlias(String context, String title, Map<String, String> extra, String... alias) {
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert_alias(context, title, extra, alias);

        sendPushPayload(payload);
    }

    public static void sendMsgByTag(String context, String title, Map<String, String> extra, String... tag) {
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert_tag(context, title, extra, tag);

        sendPushPayload(payload);
    }

    public static void sendPushPayload(PushPayload payload) {
        ClientConfig instance = ClientConfig.getInstance();
        instance.setPushHostName("https://bjapi.push.jiguang.cn");
        JPushClient jpushClient = new JPushClient(Secret, AppKey, null, instance);

        try {
            PushResult result = jpushClient.sendPush(payload);
            log.info("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            log.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        }
    }


    public static PushPayload buildPushObject_all_all_alert_alias(String context, String title, Map<String, String> extra, String... alias) {

        Message.Builder builder = Message.newBuilder()
                .setMsgContent("dsadsadsadsa")
                .setTitle(title);

        extra.forEach((k, v) -> {
            builder.addExtra(k, v);
        });

        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.alias(alias))
                        .build())
                .setMessage(builder.build())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(context)
                                .setTitle(title)
                                .addExtras(extra)
                                .setBuilderId(666)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_all_all_alert_tag(String context, String title, Map<String, String> extra, String... tag) {
        Message.Builder builder = Message.newBuilder()
                .setMsgContent(context)
                .setTitle(title);

        extra.forEach((k, v) -> {
            builder.addExtra(k, v);
        });

        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag(tag))
                        .build())
                .setMessage(builder.build())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(context)
                                .setTitle(title)
                                .addExtras(extra)
                                .setBuilderId(666)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)
                        .build())
                .build();
    }

    public static void main(String[] args) {
//        sendMsgByAlias("666", "777", ImmutableMap.of("id", "1"), "ff808181601707450160170901610000");
        sendMsgByTag("666", "777", ImmutableMap.of("id", "1"), "1");
    }
}
