package com.zxw.controller;

import com.zxw.dto.ChartDTO;
import com.zxw.model.User;
import com.zxw.service.UserService;
import com.zxw.utils.WebSocketUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 数据接口
 * @Author Zhouxw
 * @Date 2020/9/18 0018 15:15
 **/
@ServerEndpoint("/chart/{groupNum}")
@RestController
public class UserController {
    @Resource
    UserService userService;

    @OnOpen
    public void openSession(@PathParam("groupNum") String groupNum, Session session) {
        List<Session> list = WebSocketUtils.ONLINE_USER_SESSIONS.get(groupNum);
        if (null == list) {
            list = new ArrayList<>();
        }

        if (!list.contains(session)) {
            list.add(session);
        }
        WebSocketUtils.ONLINE_USER_SESSIONS.put(groupNum, list);
    }

    @OnMessage
    public void onMessage(@PathParam("groupNum") String groupNum, String message) {
        System.out.println(groupNum + "客户端ws.send发送的消息：" + message);
    }

    @OnClose
    public void onClose(@PathParam("groupNum") String groupNum, Session session) {
        //当前的Session 移除
        List<Session> list = WebSocketUtils.ONLINE_USER_SESSIONS.get(groupNum);
        list.remove(session);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Throwable msg " + throwable.getMessage());
    }

    @GetMapping("list")
    public List<User> userList() {
        return userService.getList();
    }

    /**
     * @Author Zhouxw
     * @Date 2020/09/21 13:23
     * @Description 页面初始化加载，数据展示
     * @Param [groupNum]
     * @Return com.zxw.dto.ChartDTO
     */
    @GetMapping("chartOfGroup/{groupNum}")
    public ChartDTO chartOfGroup(@PathVariable String groupNum) {
        return parse(groupNum);
    }

    @PostMapping("add")
    public User add(User user) {
        //保存数据
        User result = userService.addUser(user);
        //拼装数据DTO通知前端
        ChartDTO chartDTO = parse(user.getGroupNum());
        WebSocketUtils.sendMessage(user.getGroupNum(), JSON.toJSONString(chartDTO));
        return result;
    }

    private ChartDTO parse(String groupNum) {
        ChartDTO chartDTO = new ChartDTO();
        List<Map<String, Object>> mapList = userService.getChartNum(groupNum);
        if (null != mapList && mapList.size() > 0) {
            mapList.forEach((Map<String, Object> map) -> {
                switch ((int) map.get("sex")) {
                    case 0:
                        chartDTO.setGirl((long) map.get("chartNum"));
                        break;
                    case 1:
                        chartDTO.setBoy((long) map.get("chartNum"));
                        break;
                    default:
                        chartDTO.setUnknown((long) map.get("chartNum"));
                        break;
                }
            });
        }
        return chartDTO;
    }
}
