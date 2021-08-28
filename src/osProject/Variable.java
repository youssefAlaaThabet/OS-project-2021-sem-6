package osProject;

public class Variable {
	private String name ;
	private  Object o ;
	public Variable(String name , Object o){
		this.name= name ; 
		this.o=o;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getO() {
		return o;
	}
	public void setO(Object o) {
		this.o = o;
	}
}
