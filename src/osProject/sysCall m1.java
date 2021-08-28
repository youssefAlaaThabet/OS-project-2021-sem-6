package osProject;
import java.io.BufferedReader;
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
	
	public static void parser(String s){
		String r="src/osProject/files/"+s+".txt";
		try {
			String [] t = Execute(r);
			for (int i=0;i<t.length;i++){
				String [] u;
				if(t[i]==null){
					
					break;
				}
				else{
					u= t[i].split(" ");

				}
				if (u[0].equals("print")){
					print(t[i]);
				}
				if (u[0].equals("add")){
					add(t[i]);
				}
				if (u[0].equals("assign")){
					assign(t[i]);
				}
				if (u[0].equals("writeFile")){
					writeFile(t[i]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		
		public static String[] Execute(String filepath) throws IOException
		{
			File file = new File(filepath); 

			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));
				 String [] strings = new String[6] ; 
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
		public static void print(String s){
			String [] vr= s.split(" ");
				boolean flag =false; 
        	  for (int i =0 ; i<v.size();i++){
        		  if(v.get(i).getName().equals(vr[1])){
        			  System.out.println(v.get(i).getO().toString());
        			  flag =true ;
        			  
        		  }
        		  
        	  }
        	  if(vr[1].equals("readFile")){
        		  try {
					System.out.println(readFile(vr[2].toString()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	  }
        	  if(!flag){
        		  System.out.println(vr[1]);
        	  }
        	  
		}
		public static void assign(String s){
			String [] vr= s.split(" ");
			if (vr[2].equals("readFile")){
				try {
					String k="src/osProject/files/";
					boolean Flag=true;
				
					for (int i =0 ; i<v.size();i++){
		        		  if(v.get(i).getName().equals(vr[3])){
		        			  k=k+(String) v.get(i).getO()+".txt";
		        		  }
		        			  
		        		  }
					String  x=  readFile(k);
					boolean flag=false;
					for (int i =0 ; i<v.size();i++){
						if (v.get(i).getName().equals(vr[1])){
							v.get(i).setO(x);
							flag =true;
						}
					}
					if(!flag){
						Variable vt= new Variable(vr[1],x);
						v.add(vt);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				Scanner in = new Scanner(System.in);
				String name = in.nextLine();

				boolean flag=false;
				for (int i =0 ; i<v.size();i++){
					if (v.get(i).getName().equals(vr[1])){
						v.get(i).setO(name);
						flag =true;
					}
				}
				if(!flag){
					Variable vt= new Variable(vr[1],name);
					v.add(vt);
				}

			}
			

		}
		public static void writeFile(String s){
			String [] vr= s.split(" ");
              //boolean flag = false;
              String filename ="src/osProject/files/";
              String data = "";
			for (int i =0 ; i<v.size();i++){
        		  if(v.get(i).getName().equals(vr[1])){
        			 // flag = true;
        			  filename=filename+v.get(i).getO().toString()+".txt";
        		  }
        		  if(v.get(i).getName().equals(vr[2])){
        			  data=v.get(i).getO().toString()+"\n";
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
		public static void add(String s){
			String [] vr= s.split(" ");
			int a=0 ;
			int b=0;
			for (int i =0 ; i<v.size();i++){
        		  if(v.get(i).getName().equals(vr[1])){
        			  a=Integer.parseInt(v.get(i).getO().toString());
        		  }
        		  if(v.get(i).getName().equals(vr[2])){
          			  b=Integer.parseInt(v.get(i).getO().toString());
          		  }
			}
			int c= a+b;
			for (int i =0 ; i<v.size();i++){
				if (v.get(i).getName().equals(vr[1])){
					v.get(i).setO(c);
				}
			}
		}
		
		
		public static void main(String[] args) {
			parser("Program 1");

		}			

	
}
