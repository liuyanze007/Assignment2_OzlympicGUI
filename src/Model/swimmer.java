package Model;


public class swimmer extends Athlete implements Swim {
    public swimmer(String ID, String name, String age, String state) {
        super(ID, name, age, state);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getClassName(){
        return "Swimmer";
    }

    public void compete(int type) {

        time = (int) (100 + Math.random() * 100);
        swimming();
    }

    public void swimming() {
        System.out.println("Athlete" + ID + " " + name + " swimming  time=" + time);
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
