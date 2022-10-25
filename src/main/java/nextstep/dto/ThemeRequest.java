package nextstep.dto;

import nextstep.domain.Theme;

public class ThemeRequest {

    private String name;
    private String desc;
    private long price;

    public ThemeRequest() {
    }

    public ThemeRequest(String name, String desc, long price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme toThema(long id) {
        return new Theme(id, name, desc, price);
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
