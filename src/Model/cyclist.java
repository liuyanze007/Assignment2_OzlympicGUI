package Model;


public class cyclist extends Athlete implements Cycle {
    public cyclist(String ID, String name, String age, String state) {
        super(ID, name, age, state);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getClassName(){
        return "Cyclist";
    }

    public void compete(int type) {

        time = (int) (500 + Math.random() * 300);
        cycling();
    }

    public void cycling() {
        System.out.println("Athlete" + ID + " " + name + " cycling  time=" + time);
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

