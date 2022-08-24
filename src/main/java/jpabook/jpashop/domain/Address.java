package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable  //jpa의 내장 타입이기에
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //jpa에서 기본 생성자를 만들면 리플렉션이나 프록시 같은 기능을 사용할 수 있음.. public이면 많은 사람이 접근 가능하니까 jpa에서는 protected까지 허용해줌
    //이러면 jpa가 protected 되어 있는 걸 알고 아래의 코드를 따름
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
