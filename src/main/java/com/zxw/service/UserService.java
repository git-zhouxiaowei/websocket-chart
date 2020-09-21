package com.zxw.service;

import com.zxw.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getList();

    User addUser(User user);

    List<Map<String, Object>> getChartNum(String groupNum);
}
