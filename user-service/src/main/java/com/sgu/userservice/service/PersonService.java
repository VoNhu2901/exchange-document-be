package com.sgu.userservice.service;

import com.sgu.userservice.dto.request.PersonRequest;
import com.sgu.userservice.dto.response.HttpResponseObject;

import java.text.ParseException;

public interface PersonService {
    public HttpResponseObject getAllPerson();

    public HttpResponseObject getAllPersonWithPagination(int page, int size);

    public HttpResponseObject getAccoutByPersonId(Long personId);

    public HttpResponseObject updatePerson(Long id, PersonRequest personRequest);
}
