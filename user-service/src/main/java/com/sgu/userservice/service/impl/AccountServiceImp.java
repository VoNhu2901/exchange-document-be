package com.sgu.userservice.service.impl;

import com.sgu.userservice.constant.ConstantMessage;
import com.sgu.userservice.dto.request.*;
import com.sgu.userservice.dto.response.HttpResponseObject;
import com.sgu.userservice.exception.*;
import com.sgu.userservice.model.Account;
import com.sgu.userservice.model.ActiveAccountRequest;
import com.sgu.userservice.model.EmailDetails;
import com.sgu.userservice.model.Pagination;
import com.sgu.userservice.repository.AccountRepository;
import com.sgu.userservice.service.CloudinaryService;
import com.sgu.userservice.service.EmailService;
import com.sgu.userservice.service.AccountService;
import com.sgu.userservice.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public HttpResponseObject getAllPerson() {

        return null;
    }

    @Override
    public HttpResponseObject getAllAccount() {
        List<Account> accountList = accountRepository.findAll();
        HttpResponseObject httpResponseObject = new HttpResponseObject().builder()
                .code(HttpStatus.OK.value())
                .data(accountList)
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject getAllAccountWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Account> accountPage = accountRepository.findAll(pageable);
        List<Account> accountList = accountPage.getContent();
        Pagination pagination = Pagination.builder()
                .page(page)
                .size(size)
                .total_page(accountPage.getTotalPages())
                .total_size(accountPage.getTotalElements())
                .build();

        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .pagination(pagination)
                .data(accountList)
                .build();

        return httpResponseObject;
    }

    @Override
    public HttpResponseObject getAccoutByPersonId(Long personId) {
        Optional<Account> accountOptional = accountRepository.findByPersonId(personId);
        if(accountOptional.isEmpty()){
            throw new UserNotFoundException("Can't find account with id = " + personId);
        }

        Account account = accountOptional.get();
        
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .data(Arrays.asList(account))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject getAccoutByUsername(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            throw new UserNotFoundException("Can't find account with username = " + username);
        }

        Account account = accountOptional.get();

        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .data(Arrays.asList(account))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject sendOtpCode(SendActiveCodeRequest sendActiveCodeRequest) {
        String username = sendActiveCodeRequest.getUsername();
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            throw new UserNotFoundException("Can't find account with username = " + username);
        }

        Account account = accountOptional.get();
        if(account.getIsBlock()){
            throw new ForbiddenException("Account has block: " + account.getReasonForBlock());
        }

        Random random = new Random();
        int min = 100000,max = 999999;
        int otpCode = ThreadLocalRandom.current().nextInt(min, max + 1);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(username)
                .msgBody("<h1>Active code :"+otpCode+" </h1>")
                .subject("Test send email spring boot")
                .build();

        Boolean isSend = emailService.sendSimpleMail(emailDetails);
        if(!isSend){
            throw new BadGateWayException("Send mail fail");
        }

        //update otp code
        String updatedAt = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());

        account.setOtpCode(String.valueOf(otpCode));
        account.setOtpCreatedAt(updatedAt);

        accountRepository.save(account);


        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .data(Arrays.asList(emailDetails))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject activeAccount(ActiveAccountRequest activeAccountRequest) {
        String username = activeAccountRequest.getUsername();
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            throw new UserNotFoundException("Can't find account with username = " + username);
        }

        Account account = accountOptional.get();
        if(account.getIsBlock()){
            throw new ForbiddenException("Account has block: " + account.getReasonForBlock());
        }

        if(account.getIsActive()){
            throw new ForbiddenException("Account already active: ");
        }

        if(activeAccountRequest.getCode() != Integer.valueOf(account.getOtpCode())){
            throw new ForbiddenException("Otp code not correct");
        }

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        long otpCreatedTime = Long.valueOf(account.getOtpCreatedAt());
        long diff = currentTimestamp.getTime() - otpCreatedTime;

        //Overcome 15p
        if(diff/60000 > 15){
            throw new ForbiddenException("Otp has expired");
        }

        account.setIsActive(true);
        account.setUpdatedAt(DateUtils.getNow());
        accountRepository.save(account);
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject blockAccount(BlockAccountRequest blockAccountRequest) {
        String username = blockAccountRequest.getUsername();
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            throw new UserNotFoundException("Can't find account with username = " + username);
        }

        Account account = accountOptional.get();
        if(account.getIsBlock()){
            throw new ForbiddenException("Account has block before : " + account.getReasonForBlock());
        }

        account.setIsBlock(true);
        account.setReasonForBlock(blockAccountRequest.getReasonForBlocking());
        account.setUpdatedAt(DateUtils.getNow());

        accountRepository.save(account);




        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject unBlockAccount(UnBlockAccountRequest unBlockAccountRequest) {
        String username = unBlockAccountRequest.getUsername();
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            throw new UserNotFoundException("Can't find account with username = " + username);
        }

        Account account = accountOptional.get();
        if(!account.getIsBlock()){
            throw new ForbiddenException("Account hasn't block before : " + account.getReasonForBlock());
        }

        account.setIsBlock(false);
        account.setReasonForBlock("");
        account.setUpdatedAt(DateUtils.getNow());

        accountRepository.save(account);




        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject changePassword(ChangePasswordRequest changePasswordRequest) {
        String username = changePasswordRequest.getUsername();
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            throw new UserNotFoundException("Can't find account with username = " + username);
        }

        Account account = accountOptional.get();

        if(account.getIsBlock()){
            throw new ForbiddenException("Account has block before : " + account.getReasonForBlock());
        }

        if(!account.getOtpCode().equals(String.valueOf(changePasswordRequest.getOtpCode()))){
            throw new ForbiddenException("OTP code isn't correct");
        }
        String encodePassword = new BCryptPasswordEncoder().encode(changePasswordRequest.getNewPassword());

        account.setPassword(encodePassword);
        account.setUpdatedAt(DateUtils.getNow());

        accountRepository.save(account);

        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .build();
        return httpResponseObject;

    }

    @Override
    public HttpResponseObject uploadImage(String username, MultipartFile file) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            throw new UserNotFoundException("Can't find account with username = " + username);
        }

        Account account = accountOptional.get();

        if(account.getIsBlock()){
            throw new ForbiddenException("Account has block: " + account.getReasonForBlock());
        }

        if(!account.getIsActive()){
            throw new ForbiddenException("Account hasn't active: ");
        }

        String contentType = file.getContentType();
        if(!contentType.equals("image/jpeg") && !contentType.equals("image/png")){
            throw new BadRequestException("Image only support 'jpg','jpeg' and 'png'");
        }

         //Upload
        try{
            Map<?,?> map = cloudinaryService.upload(file);
            String url = (String) map.get("url");

            account.setAvatar(url);

            accountRepository.save(account);
        }catch (InternalServerException e) {
            throw new RuntimeException(e);
        }


        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject updateVnpay(String username, MultipartFile file) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            throw new UserNotFoundException("Can't find account with username = " + username);
        }

        Account account = accountOptional.get();

        if(account.getIsBlock()){
            throw new ForbiddenException("Account has block: " + account.getReasonForBlock());
        }

        if(!account.getIsActive()){
            throw new ForbiddenException("Account hasn't active: ");
        }

        String contentType = file.getContentType();
        if(!contentType.equals("image/jpeg") && !contentType.equals("image/png")){
            throw new BadRequestException("Image only support 'jpg','jpeg' and 'png'");
        }

        //Upload
        try{
            Map<?,?> map = cloudinaryService.upload(file);
            String url = (String) map.get("url");

            account.setVnpayURL(url);

            accountRepository.save(account);
        }catch (InternalServerException e) {
            throw new RuntimeException(e);
        }


        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .build();
        return httpResponseObject;
    }
}
