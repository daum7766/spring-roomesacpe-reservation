package nextstep.dto;

import nextstep.domain.Thema;

public class ThemaRequest {

    private String name;
    private String desc;
    private long price;

    public ThemaRequest() {
    }

    public ThemaRequest(String name, String desc, long price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Thema toThema(long id) {
        return new Thema(id, name, desc, price);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public long getPrice() {
        return price;
    }
}
