package com.zxw.service.impl;

import com.zxw.model.User;
import com.zxw.repository.UserRepository;
import com.zxw.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户信息实现
 * @Author Zhouxw
 * @Date 2020/9/18 0018 15:20
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;

    @Override
    public List<User> getList() {
        return userRepository.findAll();
    }

    @Transient
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<Map<String, Object>> getChartNum(String groupNum) {
        return userRepository.getChartNum(groupNum);
    }
}
