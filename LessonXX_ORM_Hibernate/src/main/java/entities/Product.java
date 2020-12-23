package entities;

import enums.TypeProduct;
import enums.UnitsMeasurement;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

    private long id;
    private String name;
    private TypeProduct type;
    private UnitsMeasurement units;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "TYPE")
    public TypeProduct getType() {
        return type;
    }

    public void setType(TypeProduct type) {
        this.type = type;
    }

    @Column(name = "UNITS")
    public UnitsMeasurement getUnits() {
        return units;
    }

    public void setUnits(UnitsMeasurement units) {
        this.units = units;
    }
}
