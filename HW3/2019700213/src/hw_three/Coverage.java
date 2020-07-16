package hw_three;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

import javafx.util.Pair;

public class Coverage {

	public static int NUMBER_OF_LINES = 0;
	
	public static ArrayList<Integer> LINES = new ArrayList<Integer>();
	public static ArrayList<Integer> C_LINES = new ArrayList<Integer>();
	
	public static ArrayList<String> FEEDBACKS = new ArrayList<String>();
	public static HashSet<Integer> VISITED_LINES = new HashSet<Integer>();
	
	public static HashSet<Pair<String, String>> LABEL_TO_LABEL = new HashSet<Pair<String,String>>();
	public static HashSet<Pair<String, String>> VISITED_BRANCHES = new HashSet<Pair<String,String>>();
	public static String prevLabel = "";
	
	public static HashSet<Pair<String, String>> S_LABEL_TO_LABEL = new HashSet<Pair<String,String>>();
	
	public static HashSet<Pair<String, String>> JUMP_LABEL_TO_LABEL = new HashSet<Pair<String,String>>();
	
	public static HashSet<Pair<String, String>> ALL_CONDITION_COVERAGE = new HashSet<Pair<String,String>>();
	public static HashSet<Pair<String, String>> FOUND_CONDITION_COVERAGE = new HashSet<Pair<String,String>>();
	
	public static String CURRENT_INDEX = "0";
	
	public static void traverse(String label) {
		if(prevLabel.compareTo("") != 0) {
			Pair<String, String> pair = new Pair<String, String>(prevLabel, label);
			VISITED_BRANCHES.add(pair);
		}
		prevLabel = label;
	}
	
	public static void findBranch(String label) {
		if(ALL_CONDITION_COVERAGE.contains(new Pair<String, String>(CURRENT_INDEX,label))) {
			FOUND_CONDITION_COVERAGE.add(new Pair<String, String>(CURRENT_INDEX, label));
		}
	}
	
	public static void main(String[] args) throws IOException {
	
		// Read class file
		FileInputStream in = new FileInputStream("bin/hw_three/Calculator.class");
		ClassReader cr1= new ClassReader(in);
        ClassNode classNode = new ClassTrace();
        cr1.accept(classNode, ClassReader.EXPAND_FRAMES);
        
        List<MethodNode> methods = classNode.methods;

        for(int i = 0 ; i < methods.size() ; i++) {
        	MethodNode currentMethod = methods.get(i);
        	InsnList instructions= currentMethod.instructions;
        	/*
        	if(currentMethod.name.toString().compareTo("<init>") == 0)
        		continue;
        		*/
        	System.out.println(currentMethod.name);
        	// Finds initial line number of each method
        	for (int j = 0; j < instructions.size(); j++) {
        		AbstractInsnNode inst = instructions.get(j);
        		if(inst instanceof LineNumberNode) {
        			int lline = ((LineNumberNode) inst).line;
        			if(!LINES.contains(lline)) {
        				LINES.add(lline);
            			C_LINES.add(lline);
        			}
        			
        		}
        	}  
        }
    
        
        NUMBER_OF_LINES = LINES.size();
        /*
        for(int i = 0 ; i< NUMBER_OF_LINES; i++) {
        	System.out.println(LINES.get(i));
        }
		*/
        
		FileInputStream is = new FileInputStream("bin/hw_three/Calculator.class");
		ClassReader classReader= new ClassReader(is);
		ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		MyClassVisitor mcw=new MyClassVisitor(cw);
		classReader.accept(mcw, ClassReader.EXPAND_FRAMES);
		

		FileOutputStream fos = new FileOutputStream("bin/hw_three/Calculator.class");
		fos.write(cw.toByteArray());
		fos.close();
		
		
		Result result = JUnitCore.runClasses(CalculatorTest.class);
		for (Failure failure : result.getFailures()) {
			FEEDBACKS.add(failure.toString());
		}
		
		double LINE_COVERAGE = 100*(VISITED_LINES.size()/(double)NUMBER_OF_LINES);
		System.out.printf("LINE COVERAGE IS --> %.2f%%\t", LINE_COVERAGE);
		System.out.println(VISITED_LINES.size() + " out of " + NUMBER_OF_LINES);
		/*
		int transition_size = LABEL_TO_LABEL.size();
		int counter = 0 ;
		for(Pair<String, String> ppp : VISITED_BRANCHES) {
			if(LABEL_TO_LABEL.contains(ppp)) {
				counter++;
			}
		}
		double BRANCH_COVERAGE = 100*(counter/(double)transition_size);
		System.out.printf("BRANCH COVERAGE IS --> %.2f\n", BRANCH_COVERAGE);
		*/
		/*
		System.out.println(LABEL_TO_LABEL.size());
        for(Pair<String, String> p : LABEL_TO_LABEL) {
        	System.out.println(p);
        }
        System.out.println(VISITED_BRANCHES.size());
		System.out.println("NEXTTT");
		for(Pair<String, String> p : VISITED_BRANCHES) {
        	System.out.println(p);
        }
        */
		
		for(Pair<String, String> p : LABEL_TO_LABEL) {
			for(Pair<String, String> l : LABEL_TO_LABEL) {
	        	if(p.getKey().compareTo(l.getKey()) == 0 && p.getValue().compareTo(l.getValue()) != 0) {
	        		S_LABEL_TO_LABEL.add(p);
	        		break;
	        	}
	        }
        }
	
		int transition_size = S_LABEL_TO_LABEL.size();
		int counter = 0 ;
		for(Pair<String, String> ppp : VISITED_BRANCHES) {
			if(S_LABEL_TO_LABEL.contains(ppp)) {
				counter++;
			}
		}
		double BRANCH_COVERAGE = 100*(counter/(double)transition_size);
		System.out.printf("BRANCH COVERAGE IS --> %.2f%%\t", BRANCH_COVERAGE);
		System.out.println(counter + " out of " + transition_size);
		
		int cnd_size = ALL_CONDITION_COVERAGE.size();
		int cnd_counter = FOUND_CONDITION_COVERAGE.size();
		double CONDITION_COVERAGE = 100*(cnd_counter/(double)cnd_size);
		System.out.printf("CONDITION COVERAGE IS --> %.2f%%\t", CONDITION_COVERAGE);
		System.out.println(cnd_counter + " out of " + cnd_size);
		
	}

}