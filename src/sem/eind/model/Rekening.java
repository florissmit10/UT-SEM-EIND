package sem.eind.model;

import java.util.ArrayList;

public class Rekening {
	private ArrayList<Billable> rekening=new ArrayList<Billable>();
	
	
	
	public double getTotaalbedrag(){
		double result=0.0;
			for(Billable billable:rekening){
				result=result+billable.getPrijs();
			}
		return result;
	}
	
	@Override
	public String toString(){
		String result="De rekening:"+'\n';
		for(Billable b:rekening){
			result=result+b.toString()+'\n';
		}
		result=result+String.format("%-30s", "Totaal:")+getTotaalbedrag()+'\n';
		return result;
	}
	
	public void addBillable(Billable billable){
		rekening.add(billable);
	}
}
