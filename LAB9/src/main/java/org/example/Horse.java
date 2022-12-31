package org.example;
@Table(name = "Horse")
public class Horse {

    @Column
    private String name;

    @Column
    private Integer age;

    @Column
    private Integer growth;

    @Column
    private Integer weight;



    public Horse(String name, Integer age, Integer growth, Integer weight) {
        this.name = name;
        this.age = age;
        this.growth = growth;
        this.weight = weight;
    }


    public String  getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
