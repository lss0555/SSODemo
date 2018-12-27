package com.example.server.dao;

import com.example.server.model.Result;
import com.example.server.model.User;
import org.springframework.stereotype.Repository;

/**
 * @Description 用户操作
 * @Author lss0555
 **/
@Repository
public interface UserDaoMapper {
    User login(User user);
}
