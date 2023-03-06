package com.sgu.userservice.service;

import com.sgu.userservice.dto.request.DeleteRequest;
import com.sgu.userservice.dto.request.UserRequest;
import com.sgu.userservice.dto.response.HttpResponseObject;

public interface UserService {
    public HttpResponseObject register(UserRequest userRequest);

    public HttpResponseObject delete(DeleteRequest deleteRequest);
}
