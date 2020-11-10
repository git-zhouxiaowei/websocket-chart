package com.zxw.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Zhouxw
 * @Date 2020/09/21 13:20
 * @Description 发送消息工具类
 */
public final class WebSocketUtils {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketUtils.class);

    /**
     * @Description 存储 websocket session
     */
    public static final Map<String, List<Session>> ONLINE_USER_SESSIONS = new ConcurrentHashMap<>();

    /**
     * @Author Zhouxw
     * @Date 2020/09/21 13:19
     * @Description 向客户端发送信息
     * @Param [session, message]
     * @Return void
     */
    public static void sendMessage(Session session, String message) {
        if (session == null) {
            return;
        }
        final RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            return;
        }
        try {
            basic.sendText(message);
        } catch (IOException e) {
            logger.error("sendMessage IOException ", e);
        }
    }

    public static void sendMessage(String key, String message) {
        List<Session> list = ONLINE_USER_SESSIONS.get(key);
        list.stream().forEach(se -> {
            if(se.isOpen()){
                sendMessage(se, message);
            }
        });
    }

}
