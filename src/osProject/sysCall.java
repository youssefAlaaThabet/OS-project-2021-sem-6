package osProject;
import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;
import java.util.Vector;
public class sysCall {
	static Vector <Variable> v  =new Vector();
	static Variable [] Memory = new Variable[3100];
	Queue q = new LinkedList();
	int h=0;
	int jj=1;
	int [] ok= new int [3];
	public void parser(String s){
		String r="src/osProject/files/"+s+".txt";
		try {
			q.add(h);
			String [] t = Execute(r);
			int yy=0;
			while(t[yy]!=null){
				System.out.println(t[yy]);
			yy++;
			}
			ok[jj-1]=yy;
			Variable x= new Variable("id",jj);
			jj=jj+1;
			Variable y= new Variable("ProcessState","Not running");
			Variable z= new Variable("ProgramCounter",h+500);
			Variable zz= new Variable("MemoryBoundariesMin",h);
			Variable zzz= new Variable("MemoryBoundariesMax",h+999);
			System.out.println("Process ID:"+x.getO());
			System.out.println("ProcessState:"+y.getO());
			System.out.println("ProgramCounter:"+z.getO());
			System.out.println("MemoryBoundariesMin:"+zz.getO());
			System.out.println("MemoryBoundariesMax:"+zzz.getO());

			Memory[h]=x;
			Memory[h+1]=y;
			Memory[h+2]=z;
			Memory[h+3]=zz;
			Memory[h+4]=zzz;
			int k=0;
			while(t[k]!=null){
			Variable p = new Variable("Inst",t[k]);
			int n=h+500+k;
			Memory[n]=p;
			k++;
			}
			h=h+1000;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void figure(String s,int min ,int max) {
		String [] u;
			u= s.split(" ");

		if (u[0].equals("print")){
			print(s,min,max);
		}
		if (u[0].equals("add")){
			add(s,min,max);
		}
		if (u[0].equals("assign")){
			assign(s,min,max);
		}
		if (u[0].equals("writeFile")){
			writeFile(s,min,max);
		}
	}
	public static void  prin(int k){
		System.out.println("ID:"+Memory[k].getO()); //ID
		System.out.println("Process State:"+Memory[k+1].getO()); //Process state
		System.out.println("PC:"+Memory[k+2].getO()); //PC
		System.out.println("Min Bound:"+Memory[k+3].getO()); //MIN
		System.out.println("Max Bound"+Memory[k+4].getO()); //MAX
	}
		public void scheduler(){
		while(!q.isEmpty()){
			int k = (int) q.remove();
			int pc = (int) Memory[k+2].getO();
			int min =(int)Memory[k+3].getO();
			int pp= (int)Memory[k].getO();
			System.out.println("ID:"+Memory[k].getO());

			int quanta=0;

			for(int i=0;i<2;i++){
				if(Memory[pc]==null){
					Memory[k+1]=new Variable("ProcessState","Not Running");
					break;
				}
				String s= (String) Memory[pc].getO();
				if(s==null){
					Memory[k+1]=new Variable("ProcessState","Not Running");
					break;
				}
				else
				if(ok[pp-1]>pc-min-500) {
					Memory[k+1]=new Variable("ProcessState","Running");
					quanta++;
					pc++;
					figure(s, (int) Memory[k + 3].getO(), (int) Memory[k + 4].getO());
					Memory[k + 2] = new Variable("Program Counter", pc);
					if(ok[pp-1]==pc-min-500){
					Memory[k+1]=new Variable("ProcessState","Not Running");

					}
				}
				if(ok[pp-1]>pc-min-500 && i==1){
					Memory[k+1]=new Variable("ProcessState","Not Running");
					q.add(k);
				}

			}
			System.out.println("ID:"+Memory[k].getO());/////TTT

			if(ok[pp-1]==pc-min-500){
				System.out.println("Total Quanta:"+ok[pp-1]);


			}

		}
	}
		public static String[] Execute(String filepath) throws IOException
		{
			File file = new File(filepath); 

			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));
				 String [] strings = new String[7] ;
				 String string;				 
				int i=0;
				while((string = br.readLine()) != null) 
				{
					strings[i]=string;
					i++;
				}
				
				return strings;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} 


		}
		public static String readFile(String filepath) throws IOException
		{
			File file = new File(filepath); 

			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));
				  String s = "" ; 
				 String string;				 
				int i=0;
				while((string = br.readLine()) != null) 
				{
					s=s+string+"\n";
					i++;
				}
				
				return s;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "";
			} 

		}
		public static void print(String s, int min, int max){
			String [] vr= s.split(" ");
				boolean flag =false;
        	  for (int i =min+500; i<max;i++){
        		  if(Memory[i]!=null &&Memory[i].getName().equals(vr[1])){
					 // prin(min);
					  System.out.println("Index:"+i);
        			  System.out.println(Memory[i].getO().toString());
        			  flag =true ;
        		  }
        	  }
        	  if(vr[1].equals("readFile")){
        		  try {
        		  	//prin(min);
					System.out.println(readFile(vr[2].toString()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	  }
        	  if(!flag){
        	  		 // prin(min);
        		  System.out.println(vr[1]);
        	  }
        	  
		}
		public static void assign(String s, int min, int max) {
			String[] vr = s.split(" ");
			if (vr[2].equals("readFile")) {
				try {
					String k = "src/osProject/files/";
					boolean Flag = true;

					for (int i = min+500; i <= max; i++) {
						if (Memory[i]!=null && Memory[i].getName().equals(vr[3])) {
							//prin(min);
							System.out.println("Index:"+i);
							System.out.println(Memory[i].getO());
							k = k + (String) Memory[i].getO() + ".txt";
						}
					}
					String x = readFile(k);
					boolean flag = false;
					for (int i = min+500; i < max; i++) {
						if (Memory[i]!=null &&Memory[i].getName().equals(vr[1])) {
							//prin(min);
							System.out.println("Index:"+i);
							System.out.println(x);
							Memory[i] = new Variable(Memory[i].getName(), x);
							flag = true;
						}
					}
					if (!flag) {
						for (int i = min+500; i < max; i++) {
							if (Memory[i] == null) {
							//	prin(min);
								System.out.println("Index:"+i);
								System.out.println(x);
								Memory[i] = new Variable(vr[1], x);
								break;
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Scanner in = new Scanner(System.in);
				String name = in.nextLine();

				boolean flag = false;
				for (int i = min+500; i < max; i++) {
					if (Memory[i]!=null && Memory[i].getName().equals(vr[1])) {
					//	prin(min);
						System.out.println("Index:"+i);
						System.out.println(name);
						Memory[i] = new Variable(Memory[i].getName(), name);
						flag = true;
					}
				}
				if (!flag) {
					for (int i = min+500; i < max; i++) {

						if (Memory[i] == null) {
							//prin(min);
							System.out.println("Index:"+i);
							System.out.println(name);
							Memory[i] = new Variable(vr[1], name);
							break;
						}
					}
				}
			}
		}
		public static void writeFile(String s, int min, int max){
			String [] vr= s.split(" ");
              //boolean flag = false;
              String filename ="src/osProject/files/";
              String data = "";
			for (int i =min+500 ; i<=max;i++){
        		  if(Memory[i]!=null &&Memory[i].getName().equals(vr[1])){
					//  prin(min);
					  System.out.println("Index:"+i);
					  System.out.println(Memory[i].getO().toString());
        			 // flag = true;
        			  filename=filename+Memory[i].getO().toString()+".txt";
        		  }
        		  if(Memory[i]!=null &&Memory[i].getName().equals(vr[2])){
					 // prin(min);
					  System.out.println("Index:"+i);
					  System.out.println(Memory[i].getO().toString());
        			  data=Memory[i].getO().toString()+"\n";
        		  }
			}		
				try {
					FileWriter writer = new FileWriter(filename, false);
					writer.write(data);
		            writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		public static void add(String s, int min, int max){
			String [] vr= s.split(" ");
			int a=0 ;
			int b=0;
			for (int i =min +500; i<=max;i++){
        		  if(Memory[i]!=null &&Memory[i].getName().equals(vr[1])){
					 // prin(min);
					  System.out.println("Index:"+i);
					  System.out.println(Memory[i].getO().toString());
        			  a=Integer.parseInt(Memory[i].getO().toString());
        		  }
        		  if(Memory[i]!=null &&Memory[i].getName().equals(vr[2])){
					  //prin(min);
					  System.out.println("Index:"+i);
					  System.out.println(Memory[i].getO().toString());
          			  b=Integer.parseInt(Memory[i].getO().toString());
				  }
			}
			int c= a+b;

			for (int i =min+500; i<=max;i++){
				if(Memory[i]!=null &&Memory[i].getName().equals(vr[1])){
				//	prin(min);
					System.out.println("Index:"+i);
					System.out.println(c);
					Memory[i]=new Variable(vr[1],c);
				}
			}
		}
		public static void main(String[] args) {
			sysCall s= new sysCall();
			s.parser("Program 1");
			s.parser("Program 2");
			s.parser("Program 3");
			s.scheduler();
		}
}
