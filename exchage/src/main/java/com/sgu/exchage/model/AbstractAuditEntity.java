package com.sgu.exchage.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
public class AbstractAuditEntity {
    private ZonedDateTime createAt = ZonedDateTime.now();

    private ZonedDateTime updateAt = ZonedDateTime.now();
}
