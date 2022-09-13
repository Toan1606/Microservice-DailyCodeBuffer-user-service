package com.eric.india.userservice.service;

import com.eric.india.userservice.VO.Department;
import com.eric.india.userservice.VO.ResponseTemplateVO;
import com.eric.india.userservice.model.User;
import com.eric.india.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside saveUser method of UserService");
        return userRepository.save(user);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment method of UserController");
        ResponseTemplateVO response = new ResponseTemplateVO();

        User user = userRepository.findByUserId(userId);

        log.info("User info : localhost:9001/api/v1/departments/" + user.getDepartmentId());

        Department department = restTemplate.getForObject("http://department-service/api/v1/departments/" + user.getDepartmentId(), Department.class);

        response.setUser(user);
        response.setDepartment(department);
        return response;
    }

    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }
}
