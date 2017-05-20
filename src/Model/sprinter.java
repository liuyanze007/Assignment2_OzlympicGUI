package Model;


public class sprinter extends Athlete implements Run {
    public sprinter(String ID, String name, String age, String state) {
        super(ID, name, age, state);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getClassName(){
        return "Sprinter";
    }

    public void compete(int type) {

        time = (int) (10 + Math.random() * 10);
        running();
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
