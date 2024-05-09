package com.anonymous.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AbstractDTO {

    Long id;
    String createdBy;
    Date createdDate;
    String modifiedBy;
    Date modifiedDate;

}
