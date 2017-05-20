package Ozlympic;

import Model.Athlete;
import Model.Official;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Game {
	protected int type;
	private String GID;
	private Official offical=null;
	private List<Athlete> athletes;// participants

	private List<Integer> times;// participants times
	private List<Integer> points;// participants points
	
	public abstract void show();
	
	public Game(String GID){
		this.GID=GID;
		athletes=new ArrayList<Athlete>();
		times = new ArrayList<>();
		points = new ArrayList<>();
	}
	
	public String getGID() {
		return GID;
	}
	public void setGID(String GID) {
		GID = GID;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	public Official getOffical() {
		return offical;
	}
	public void setOffical(Official offical) {
		this.offical = offical;
	}
	
	public void setAthletes(List<Athlete> athletes) {
		this.athletes = athletes;
	}

	public List<Athlete> getAthletes() {
		// TODO Auto-generated method stub
		return athletes;
	}

	public void run(){
		for(Athlete a:athletes){
			a.compete(type);
		}
		offical.summarise(athletes);
		for (int i = 0; i < athletes.size(); i++) {
			times.add(athletes.get(i).getTime());
			points.add(athletes.get(i).getPoint());
		}
	}

	@Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(GID);
        sb.append(',');
        sb.append(' ');
        sb.append(offical.getId());
        sb.append(',');
        sb.append(' ');
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        sb.append(df.format(new Date()));
        sb.append('\n');
		for (int i = 0; i < athletes.size(); i++) {
			sb.append(athletes.get(i).getID());
			sb.append(",");
			sb.append(times.get(i));
			sb.append(",");
			sb.append(points.get(i));
			sb.append("\n");
		}
        sb.append('\n');
        return sb.toString();
    }
}
	
	
class Running extends Game{
		
		public Running(String ID) {
			super(ID);
			type=1;
		}

		@Override
		public void show() {
			System.out.println("running");
			
		}
	}
	
class Swimming extends Game{

		public Swimming(String ID) {
			super(ID);
			type=2;
		}

		@Override
		public void show() {
			System.out.println("swimming");
			
		}

	}
	
class Cycling extends Game{
		public Cycling(String ID) {
			super(ID);
			type=3;
		}

		@Override
		public void show() {
			System.out.println("cycling");
			
		}
	}

