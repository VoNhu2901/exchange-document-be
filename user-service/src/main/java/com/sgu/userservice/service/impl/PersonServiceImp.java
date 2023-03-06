package com.sgu.userservice.service.impl;

import com.sgu.userservice.constant.ConstantMessage;
import com.sgu.userservice.dto.request.PersonRequest;
import com.sgu.userservice.dto.response.HttpResponseObject;
import com.sgu.userservice.exception.BadRequestException;
import com.sgu.userservice.exception.NotFoundException;
import com.sgu.userservice.model.Account;
import com.sgu.userservice.model.Pagination;
import com.sgu.userservice.model.Person;
import com.sgu.userservice.repository.PersonRepository;
import com.sgu.userservice.service.PersonService;
import com.sgu.userservice.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImp implements PersonService {
    @Autowired
    PersonRepository personRepository;
    @Override
    public HttpResponseObject getAllPerson() {
        List<Person> accountList = personRepository.findAll();
        HttpResponseObject httpResponseObject = new HttpResponseObject().builder()
                .code(HttpStatus.OK.value())
                .data(accountList)
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject getAllPersonWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Person> accountPage = personRepository.findAll(pageable);
        List<Person> accountList = accountPage.getContent();
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
    public HttpResponseObject getAccoutByPersonId(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if(personOptional.isEmpty()){
            throw new NotFoundException("Can't find person with id = " + id);
        }

        Person person = personOptional.get();

        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .data(Arrays.asList(person))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject updatePerson(Long id, PersonRequest personRequest){
        Optional<Person> personOptional = personRepository.findById(id);
        if(personOptional.isEmpty()){
            throw new NotFoundException("Can't find person with id = " + id);
        }

        Person person = personOptional.get();
        if(!DateUtils.isValidDate(personRequest.getBirthday())){
            throw new BadRequestException("Birthday is invalid");
        }


        person.setName(personRequest.getName());
        person.setAddress(personRequest.getAddress());
        person.setPhone(personRequest.getPhone());
        person.setBirthday(personRequest.getBirthday());
        person.setGender(personRequest.getGender());
        person.setUpdatedAt(DateUtils.getNow());

        personRepository.save(person);

        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(ConstantMessage.SUCCESS))
                .data(Arrays.asList(person))
                .build();
        return httpResponseObject;
    }
}
