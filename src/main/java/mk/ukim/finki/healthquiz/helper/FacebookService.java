package mk.ukim.finki.healthquiz.helper;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Simona on 8/23/2017.
 */
@Component
public class FacebookService {


    public User getFacebookUser(String accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken);
        return facebook.userOperations().getUserProfile();


    }
}