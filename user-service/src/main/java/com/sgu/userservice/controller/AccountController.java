package com.sgu.userservice.controller;

import com.sgu.userservice.dto.request.*;
import com.sgu.userservice.dto.response.HttpResponseObject;
import com.sgu.userservice.model.ActiveAccountRequest;
import com.sgu.userservice.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/get-all-account")
    public ResponseEntity<HttpResponseObject> getAllAccount(){
        HttpResponseObject httpResponseObject = accountService.getAllAccount();

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @GetMapping("/get-all-account-with-pagination")
    public ResponseEntity<HttpResponseObject> getAllAccountWithPagination(
            @RequestParam(name = "page", required = true) int page,
            @RequestParam(name = "size", required = true) int size
    ){
        HttpResponseObject httpResponseObject = accountService.getAllAccountWithPagination(page,size);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @GetMapping("/get-account-by-person-id/{id}")
    public ResponseEntity<HttpResponseObject> getAccountByPersonId(
            @PathVariable(name = "id") Long personId
    ){
        HttpResponseObject httpResponseObject = accountService.getAccoutByPersonId(personId);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @GetMapping("/get-account-by-username/{username}")
    public ResponseEntity<HttpResponseObject> getAccountByUsername(
            @PathVariable(name = "username") String username
    ){
        HttpResponseObject httpResponseObject = accountService.getAccoutByUsername(username);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @PostMapping("/send-otp-code")
    public ResponseEntity<HttpResponseObject> sendOtpCode(
            @RequestBody @Valid SendActiveCodeRequest sendActiveCodeRequest
    ){
        HttpResponseObject httpResponseObject = accountService.sendOtpCode(sendActiveCodeRequest);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @PostMapping("/active-account")
    public ResponseEntity<HttpResponseObject> activeAccount(
            @RequestBody @Valid ActiveAccountRequest activeAccountRequest
    ){
        HttpResponseObject httpResponseObject = accountService.activeAccount(activeAccountRequest);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @PostMapping("/block-account")
    public ResponseEntity<HttpResponseObject> blockAccount(
            @RequestBody @Valid BlockAccountRequest blockAccountRequest
    ){
        HttpResponseObject httpResponseObject = accountService.blockAccount(blockAccountRequest);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @PostMapping("/unblock-account")
    public ResponseEntity<HttpResponseObject> unBlockAccount(
            @RequestBody @Valid UnBlockAccountRequest unBlockAccountRequest
    ){
        HttpResponseObject httpResponseObject = accountService.unBlockAccount(unBlockAccountRequest);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }


    @PostMapping("/change-password")
    public ResponseEntity<HttpResponseObject> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest
    ){
        HttpResponseObject httpResponseObject = accountService.changePassword(changePasswordRequest);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @PostMapping("/upload-image/{username}")
    public ResponseEntity<?> updateAvatar(
            @RequestParam("file") MultipartFile file,
            @PathVariable(name = "username") String username
    ){

        HttpResponseObject httpResponseObject = accountService.uploadImage(username, file);
        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @PostMapping("/update-vnpay/{username}")
    public ResponseEntity<HttpResponseObject> updateVnpay(
            @RequestParam("file") MultipartFile file,
            @PathVariable(name = "username") String username
    ){
        HttpResponseObject httpResponseObject = accountService.updateVnpay(username,file);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }
}
