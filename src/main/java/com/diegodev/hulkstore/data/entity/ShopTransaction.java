package com.diegodev.hulkstore.data.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class ShopTransaction extends AbstractEntity {

}
