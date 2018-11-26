package com.optimal.dbmodel;
// Generated Oct 10, 2017 8:01:20 AM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Teacher generated by hbm2java
 */
@Entity
@Table(name="teacher"
    ,catalog="optimal"
)
public class Teacher  implements java.io.Serializable {


     private Integer id;
     private Date birthdate;
     private String city;
     private String countery;
     private String gender;
     private String habits;
     private String imageName;
     private String name;
     private String street;

    public Teacher() {
    }

    public Teacher(Date birthdate, String city, String countery, String gender, String habits, String imageName, String name, String street) {
       this.birthdate = birthdate;
       this.city = city;
       this.countery = countery;
       this.gender = gender;
       this.habits = habits;
       this.imageName = imageName;
       this.name = name;
       this.street = street;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="birthdate", length=10)
    public Date getBirthdate() {
        return this.birthdate;
    }
    
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    
    @Column(name="city", length=45)
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    
    @Column(name="countery", length=45)
    public String getCountery() {
        return this.countery;
    }
    
    public void setCountery(String countery) {
        this.countery = countery;
    }

    
    @Column(name="gender", length=10)
    public String getGender() {
        return this.gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    
    @Column(name="habits", length=10000)
    public String getHabits() {
        return this.habits;
    }
    
    public void setHabits(String habits) {
        this.habits = habits;
    }

    
    @Column(name="image_path", length=200)
    public String getImageName() {
        return this.imageName;
    }
    
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    
    @Column(name="name", length=45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="street", length=45)
    public String getStreet() {
        return this.street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }




}


