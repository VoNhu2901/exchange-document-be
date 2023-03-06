package com.sgu.userservice.service;

import com.sgu.userservice.dto.request.*;
import com.sgu.userservice.dto.response.HttpResponseObject;
import com.sgu.userservice.model.ActiveAccountRequest;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {
    public HttpResponseObject getAllPerson();

    public HttpResponseObject getAllAccount();

    public HttpResponseObject getAllAccountWithPagination(int page, int size);

    public HttpResponseObject getAccoutByPersonId(Long personId);

    public HttpResponseObject getAccoutByUsername(String username);

    public HttpResponseObject sendOtpCode(SendActiveCodeRequest sendActiveCodeRequest);

    public HttpResponseObject activeAccount(ActiveAccountRequest activeAccountRequest);

    public HttpResponseObject blockAccount(BlockAccountRequest blockAccountRequest);

    public HttpResponseObject unBlockAccount(UnBlockAccountRequest unBlockAccountRequest);

    public HttpResponseObject changePassword(ChangePasswordRequest changePasswordRequest);

    public HttpResponseObject uploadImage(String updateAvatarRequest, MultipartFile file);

    public HttpResponseObject updateVnpay(String username, MultipartFile file);
}
