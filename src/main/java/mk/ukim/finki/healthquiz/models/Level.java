package mk.ukim.finki.healthquiz.models;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Simona on 19.04.2017.
 */
@Entity
@Table(name = "health_level")
public class Level extends BaseEntity{

    private int level;

    private int xp;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
