package nextstep.domain;

import java.util.Objects;

public class Thema {

    private long id;
    private String name;
    private String desc;
    private long price;

    public Thema() {
    }

    public Thema(long id, String name, String desc, long price) {
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
        Thema thema = (Thema) o;
        return id == thema.id && price == thema.price && Objects.equals(name, thema.name)
            && Objects.equals(desc, thema.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, price);
    }
}
