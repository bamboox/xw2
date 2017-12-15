package com.ace.service;

import com.ace.util.JpushSdk;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by bamboo on 17-12-14.
 */
@Service
public class MsgService {

    @Async
    public void sendMsgByAlias(String context, String title, Map<String, String> extra, String... alias) {
        JpushSdk.sendMsgByAlias(context, title, extra, alias);
    }

    @Async
    public void sendMsgByTag(String context, String title, Map<String, String> extra, String... tag) {
        JpushSdk.sendMsgByTag(context, title, extra, tag);
    }

}
