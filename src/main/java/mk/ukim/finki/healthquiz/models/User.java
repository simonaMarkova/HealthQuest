package mk.ukim.finki.healthquiz.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by Simona on 12.04.2017.
 */
@Entity
@Table(name="health_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    @Transient
    private Integer points = 0;

    @ManyToOne(targetEntity = Level.class)
    private Level level;

    private String profileImage;

    Boolean facebookAccount;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(Boolean facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

}
