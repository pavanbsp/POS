package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class InventoryPojo extends AbstractPojo {

    @Id
    private int id;
    @NotNull
    @Column(nullable = false)
    private int quantity;

}
