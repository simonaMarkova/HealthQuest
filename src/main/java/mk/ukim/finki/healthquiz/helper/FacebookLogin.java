package mk.ukim.finki.healthquiz.helper;

import mk.ukim.finki.healthquiz.models.User;

/**
 * Created by Simona on 8/23/2017.
 */
public class FacebookLogin {
    private String accessToken;
    private User user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
