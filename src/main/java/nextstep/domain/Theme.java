package nextstep.domain;

import java.util.Objects;

public class Theme {

    private long id;
    private String name;
    private String desc;
    private long price;

    public Theme() {
    }

    public Theme(long id, String name, String desc, long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Theme theme = (Theme) o;
        return id == theme.id && price == theme.price && Objects.equals(name, theme.name)
            && Objects.equals(desc, theme.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, price);
    }
}
