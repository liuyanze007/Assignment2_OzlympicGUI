package Model;

import java.util.Collections;
import java.util.List;

public class Official {
    private String id;
    private String name;
    private String age;
    private String state;

    public Official(String id) {
        this.id = id;
    }

    public Official(String id, String name, String age, String state) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.state = state;
    }

    // game over,rank
    public void summarise(List<Athlete> Athlete) {
        Collections.sort(Athlete);//rank
        Athlete.get(0).addPoints(5);
        Athlete.get(1).addPoints(2);
        Athlete.get(2).addPoints(1);
    }

    public String getId() {
        return id;
    }
}
