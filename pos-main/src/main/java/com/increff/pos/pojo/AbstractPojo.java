package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
@Getter
@Setter
public class AbstractPojo {
    @CreationTimestamp
    private Timestamp Created_At;
    @UpdateTimestamp
    private Timestamp Updated_At;
}
