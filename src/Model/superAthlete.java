package Model;


public class superAthlete extends Athlete implements Swim, Run, Cycle {
    public superAthlete(String ID, String name, String age, String state) {
        super(ID, name, age, state);
        // TODO Auto-generated constructor stub
    }

    public void compete(int type) {
        if (type == 1) {

            time = (int) (10 + Math.random() * 10);
            running();
        } else if (type == 2) {

            time = (int) (100 + Math.random() * 100);
            swimming();
        } else if (type == 3) {

            time = (int) (500 + Math.random() * 300);
            cycling();
        }
    }

    public void swimming() {
        System.out.println("Athlete" + ID + " " + name + " swimming  time=" + time);
    }

    public void cycling() {
        System.out.println("Athlete" + ID + " " + name + " cycling  time=" + time);
    }

    public void running() {
        System.out.println("Athlete" + ID + " " + name + " running  time=" + time);
    }

    @Override
    public int compareTo(Athlete o) {
        // TODO Auto-generated method stub
        if (time > o.time) {
            return 1;
        } else if (time < o.time) {
            return -1;
        }
        return 0;
    }
}