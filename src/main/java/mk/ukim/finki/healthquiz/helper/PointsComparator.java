package mk.ukim.finki.healthquiz.helper;

import mk.ukim.finki.healthquiz.models.User;

import java.util.Comparator;

/**
 * Created by user on 06.8.2017.
 */

public class PointsComparator implements Comparator<User> {
    public int compare(User o1, User o2) {
        return o2.getPoints().compareTo(o1.getPoints());
    }
}