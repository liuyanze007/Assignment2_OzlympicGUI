package Model;


public abstract class Athlete implements Comparable<Athlete> {
    protected String ID;
    protected String name;
    private String age;
    private String state;
    private int points;
    private int point;
    protected int time;


    public Athlete(String ID, String name, String age, String state) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.state = state;
        this.points = 0;
        this.time = 0;
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return ID + ", " + time + ", " + points;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void addPoints(int p) {
        this.points += p;
        point = p;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public abstract void compete(int type); //set a method for every subclass

}





	
