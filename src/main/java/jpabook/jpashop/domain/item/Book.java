package jpabook.jpashop.domain.item;

import javax.persistence.Entity;

@Entity
//상속이기에 extends Item
public class Book  extends Item{

    private String author;
    private String isbn;
}
