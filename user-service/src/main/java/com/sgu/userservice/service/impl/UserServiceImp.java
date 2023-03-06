package com.sgu.userservice.service.impl;

import com.sgu.userservice.constant.ConstantMessage;
import com.sgu.userservice.constant.Role;
import com.sgu.userservice.dto.request.UserRequest;
import com.sgu.userservice.dto.response.HttpResponseObject;
import com.sgu.userservice.exception.BadRequestException;
import com.sgu.userservice.model.Account;
import com.sgu.userservice.model.Person;
import com.sgu.userservice.repository.AccountRepository;
import com.sgu.userservice.repository.PersonRepository;
import com.sgu.userservice.service.PersonService;
import com.sgu.userservice.service.UserService;
import com.sgu.userservice.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public HttpResponseObject register(UserRequest userRequest) {

        if(!DateUtils.isValidDate(userRequest.getBirthday())){
            throw new BadRequestException("Birthday is invalid");
        }

        List<Person> personList = personRepository.findAll();
        personList.sort((p1,p2)->{
            if(p1.getId() == p2.getId()) return 0;
            return p1.getId() > p2.getId() ? 1:-1;
        });

        Long newId = personList.get(personList.size()-1).getId() + 1;
        String now = DateUtils.getNow();

        Person newPerson = Person.builder()
                .id(newId)
                .name(userRequest.getName())
                .phone(userRequest.getPhone())
                .address(userRequest.getAddress())
                .birthday(userRequest.getBirthday())
                .gender(userRequest.getGender())
                .createdAt(now)
                .updatedAt(now)
                .build();

        Account newAccount = Account.builder()
                .personId(newId)
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .role(Role.USER)
                .avatar("")
                .isBlock(false)
                .isActive(false)
                .otpCode("")
                .refreshToken("")
                .vnpayURL("")
                .otpCreatedAt("")
                .createdAt(now)
                .updatedAt(now)
                .build();

        Person savePerson = personRepository.save(newPerson);
        Account saveAccount = accountRepository.save(newAccount);

        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .data(Arrays.asList(saveAccount,savePerson))
                .build();
        return httpResponseObject;
    }
}
