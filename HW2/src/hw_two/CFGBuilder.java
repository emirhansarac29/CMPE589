package hw_two;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import javafx.util.Pair;

public class CFGBuilder {

	// Keeps labels of each method
	public static Map<String, ArrayList<String>> FUNCTION_TO_LABELS = new HashMap<String, ArrayList<String>>();
	// Keeps initial line of each method
	public static Map<String, Integer> FUNCTION_INIT_LINE = new HashMap<String, Integer>();
	// Keeps initial label of instruction lines
	public static Map<String, Integer> LABELNODE_TO_STARTLINE = new HashMap<String, Integer>();
	// Keeps all labels
	public static ArrayList<String> LABELNODES = new ArrayList<String>();
	// Map from label to line number
	public static Map<String, Integer> LABELNODE_TO_LINE = new HashMap<String, Integer>();
	// Keeps label to label jumps of each method
	public static Map<String, ArrayList<Pair<String, String>>> METHODNAME_TO_JUMP_LABEL_TO_LABEL = new HashMap<String, ArrayList<Pair<String, String>>>();
	// Keeps line to line jumps and next line of each method
	public static Map<String, ArrayList<Pair<Integer, Integer>>> METHODNAME_TO_EDGES = new HashMap<String, ArrayList<Pair<Integer, Integer>>>();
	// Keeps line of each line at the java file(ltrim)
	public static ArrayList<String> JAVA_LINE = new ArrayList<String>();
	
	public static Map<String, HashSet<Integer>> LABEL_TO_DEF = new HashMap<String, HashSet<Integer>>();
	public static Map<String, HashSet<Integer>> LABEL_TO_USE = new HashMap<String, HashSet<Integer>>();
	
	public static Map<Integer, HashSet<Integer>> LINE_TO_DEF = new HashMap<Integer, HashSet<Integer>>();
	public static Map<Integer, HashSet<Integer>> LINE_TO_USE = new HashMap<Integer, HashSet<Integer>>();
	
	public static Map<Integer, HashSet<Integer>> LINE_TO_LIVE_IN = new HashMap<Integer, HashSet<Integer>>();
	public static Map<Integer, HashSet<Integer>> LINE_TO_LIVE_OUT = new HashMap<Integer, HashSet<Integer>>();
	
	public static ArrayList<Pair<Integer, Integer>> LINE_TO_LINE = new ArrayList<Pair<Integer, Integer>>();
	
	public static HashSet<Integer> LINES = new HashSet<Integer>();
	public static HashSet<Integer> CHANGED_LINES = new HashSet<Integer>();
	
	public static ArrayList<Integer> getPredecessors(int line) {
		ArrayList<Integer> preds = new ArrayList<Integer>();
		for(int i = 0 ; i < LINE_TO_LINE.size() ; i++) {
			if(LINE_TO_LINE.get(i).getValue() == line) {
				preds.add(LINE_TO_LINE.get(i).getKey());
			}
		}
		return preds;
	}
	
	public static ArrayList<Integer> getSuccessors(int line) {
		ArrayList<Integer> succs = new ArrayList<Integer>();
		for(int i = 0 ; i < LINE_TO_LINE.size() ; i++) {
			if(LINE_TO_LINE.get(i).getKey() == line) {
				succs.add(LINE_TO_LINE.get(i).getValue());
			}
		}
		return succs;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// Read java file
		File file = new File("src/hw_one/TestData.java"); 
		Scanner sc = new Scanner(file);
		JAVA_LINE.add("DUMMY");
		while (sc.hasNextLine()) {
		     String line = sc.nextLine().replaceAll("^\\s+","");
		     line = line.replace("\"", "\\\"");
		     JAVA_LINE.add("L#" + JAVA_LINE.size() + " " + line);    
		} 
	
		// Read class file
		InputStream in= CFGBuilder.class.getResourceAsStream("/hw_one/TestData.class");
		ClassReader cr= new ClassReader(in);
        ClassNode classNode = new MyClassAdapter();
        cr.accept(classNode, ClassReader.EXPAND_FRAMES);
        
        // Assign each label a line number
        Stack<String> inter_labels = new Stack<String>();
        int currentLine = 0;
        for (int i = 0; i < LABELNODES.size(); i++) {
			if(LABELNODE_TO_STARTLINE.get(LABELNODES.get(i)) != null) {
				for (int j = 0; j < inter_labels.size(); j++) {
					LABELNODE_TO_LINE.put(inter_labels.pop(), currentLine);
				}
				currentLine = LABELNODE_TO_STARTLINE.get(LABELNODES.get(i));
				LABELNODE_TO_LINE.put(LABELNODES.get(i), currentLine);
			}else {
				inter_labels.push(LABELNODES.get(i));
			}
		}
        for (int j = 0; j < inter_labels.size(); j++) {
			LABELNODE_TO_LINE.put(inter_labels.pop(), currentLine);
		}
        
        // Retrieve all methods
        List<MethodNode> methods = classNode.methods;
        
        for(int i = 0 ; i < methods.size() ; i++) {
        	MethodNode currentMethod = methods.get(i);
        	//System.out.println("METHOD NAME --> " + currentMethod.name);
        	InsnList instructions= currentMethod.instructions;
        	
        	// Finds initial line number of each method
	        for (int j = 0; j < instructions.size(); j++) {
	        	AbstractInsnNode inst = instructions.get(j);
	        	if(j==0 && inst instanceof LabelNode) {
	        		FUNCTION_INIT_LINE.put(currentMethod.name, LABELNODE_TO_LINE.get(((LabelNode)inst).getLabel().toString()));
	        	}
	        }  
	    
	        ArrayList<Pair<String, String>> jumps = METHODNAME_TO_JUMP_LABEL_TO_LABEL.get(currentMethod.name);

	        // Add jumps to edges for each method
	        for (int j = 0; jumps != null && j < jumps.size(); j++) {
	        	if(CFGBuilder.METHODNAME_TO_EDGES.get(currentMethod.name) == null) {
					ArrayList<Pair<Integer, Integer>> met_labels = new ArrayList<Pair<Integer, Integer>>();
					int begin = LABELNODE_TO_LINE.get(jumps.get(j).getKey());
					int next = LABELNODE_TO_LINE.get(jumps.get(j).getValue());
					Pair<Integer, Integer> pair = new Pair<Integer, Integer>(begin, next);
					if(begin != next) {
						met_labels.add(pair);
					}
					CFGBuilder.METHODNAME_TO_EDGES.put(currentMethod.name, met_labels);
				}else {
					int begin = LABELNODE_TO_LINE.get(jumps.get(j).getKey());
					int next = LABELNODE_TO_LINE.get(jumps.get(j).getValue());
					Pair<Integer, Integer> pair = new Pair<Integer, Integer>(begin, next);
					if(METHODNAME_TO_EDGES.get(currentMethod.name).contains(pair) == false && begin != next) {
						CFGBuilder.METHODNAME_TO_EDGES.get(currentMethod.name).add(pair);
					}
				}
			}
        }
         
        for (String methodName : METHODNAME_TO_EDGES.keySet()) {
        	 ArrayList<Pair<Integer, Integer>> edges = METHODNAME_TO_EDGES.get(methodName);
        	 for (int i = 0; i < edges.size(); i++) {
        		 int begin = edges.get(i).getKey();
        		 int end = edges.get(i).getValue();
        		 Pair<Integer, Integer> pair = new Pair<Integer, Integer>(begin, end);
        		 LINE_TO_LINE.add(pair);
        		 LINES.add(begin);
        		 LINES.add(end);
        	 }
		}
        
        // Retrieves Defs for each line from labels
        for(String defLabel : LABEL_TO_DEF.keySet()) {
        	int lineNumber = LABELNODE_TO_LINE.get(defLabel);
        	if(LINE_TO_DEF.get(lineNumber) == null) {
        		HashSet<Integer> temp = new HashSet<Integer>();
	        	for(int variable : LABEL_TO_DEF.get(defLabel)) {
	        		temp.add(variable);
	        	}
	        	if(temp.size() != 0) {
	        		LINE_TO_DEF.put(lineNumber, temp);
	        	}
        	}else {
        		for(int variable : LABEL_TO_DEF.get(defLabel)) {
        			LINE_TO_DEF.get(lineNumber).add(variable);
	        	}
        	}
        }
        
        // Retrieves Uses for each line from labels        
        for(String defLabel : LABEL_TO_USE.keySet()) {
        	int lineNumber = LABELNODE_TO_LINE.get(defLabel);
        	if(LINE_TO_USE.get(lineNumber) == null) {
        		HashSet<Integer> temp = new HashSet<Integer>();
	        	for(int variable : LABEL_TO_USE.get(defLabel)) {
	        		temp.add(variable);
	        	}
	        	if(temp.size() != 0) {
	        		LINE_TO_USE.put(lineNumber, temp);
	        	}
        	}else {
        		for(int variable : LABEL_TO_USE.get(defLabel)) {
        			LINE_TO_USE.get(lineNumber).add(variable);
	        	}
        	}
        }
        
        // Initialize live-in and live-outs
        for(int line : LINES) {
        	LINE_TO_LIVE_IN.put(line, new HashSet<Integer>());
        	LINE_TO_LIVE_OUT.put(line, new HashSet<Integer>());
        	CHANGED_LINES.add(line);
        }
        
        // Worklist Algorithm
        while(!CHANGED_LINES.isEmpty()) {
        	int line = CHANGED_LINES.iterator().next();
        	CHANGED_LINES.remove(line);
        	
        	ArrayList<Integer> successors = getSuccessors(line);
        	for(int succs : successors) {
        		for(int succ_in : LINE_TO_LIVE_IN.get(succs)) {
        			LINE_TO_LIVE_OUT.get(line).add(succ_in);
        		}
        	}
        	
        	HashSet<Integer> old_live_in = (HashSet<Integer>) LINE_TO_LIVE_IN.get(line).clone();
        	HashSet<Integer> transformed_live_out = (HashSet<Integer>) LINE_TO_LIVE_OUT.get(line).clone();
        	if(LINE_TO_DEF.get(line) != null) {
        		for(int line_def : LINE_TO_DEF.get(line)) {
        			transformed_live_out.remove(line_def);
            	}
        	}
        	if(LINE_TO_USE.get(line) != null) {
        		for(int line_use : LINE_TO_USE.get(line)) {
        			transformed_live_out.add(line_use);
            	}
        	}
        	LINE_TO_LIVE_IN.put(line, transformed_live_out);
        	
        	if(!old_live_in.equals(LINE_TO_LIVE_IN.get(line))) {
        		ArrayList<Integer> predecessors = getPredecessors(line);
            	for(int preds : predecessors) {
            		CHANGED_LINES.add(preds);
            	}
        	}
        }
        
        /*
        for(int line : LINES) {
        	System.out.println("LINE " + line);
        	System.out.print("IN = ");
        	for (int var : LINE_TO_LIVE_IN.get(line)) {
				System.out.print(var + " ");
			}
        	System.out.println();
        	System.out.print("OUT = ");
        	for (int var : LINE_TO_LIVE_OUT.get(line)) {
				System.out.print(var + " ");
			}
        	System.out.println();
        }
        */
   	 
        // Finding Dead code line by looking at which lines 
        // are defining variable and doesn't have that def in their
        // respective live-out
   	 	HashSet<Integer> DEAD_CODE = new HashSet<Integer>();
        for(int line : LINES) {
        	HashSet<Integer> live_out = LINE_TO_LIVE_OUT.get(line);
        	if(LINE_TO_DEF.get(line) != null) {
        		for(int def_var : LINE_TO_DEF.get(line)) {
            		if(!live_out.contains(def_var)) {
            			DEAD_CODE.add(line);
            		}
            	}
        	}
        }
        
        File file_out = new File ("TestData2.java");
   	 	PrintWriter out = new PrintWriter (file_out);
   	 	
        File file_in = new File("src/hw_one/TestData.java"); 
		Scanner sc_in = new Scanner(file_in);
		int lineNumber = 0;
		while (sc_in.hasNextLine()) {
			lineNumber++;
		    String textLine = sc_in.nextLine();
		    if(DEAD_CODE.contains(lineNumber)) {
		    	textLine = textLine + " // DEAD CODE";
		    }
		   out.println(textLine);
		}
		
		out.close();
		
        /*
        for(int a1 : LINE_TO_USE.keySet()) {
        	System.out.println("LINE USE " + a1);
        	for(int a2 : LINE_TO_USE.get(a1)) {
        		System.out.println(a2);
        	}
        }
        for(int a1 : LINE_TO_DEF.keySet()) {
        	System.out.println("LINE DEF " + a1);
        	for(int a2 : LINE_TO_DEF.get(a1)) {
        		System.out.println(a2);
        	}
        }
        
        for(int i = 0; i<LINE_TO_LINE.size() ; i++) {
        	System.out.println(LINE_TO_LINE.get(i).getKey() + " -> " + LINE_TO_LINE.get(i).getValue());
        }
       */
        /*
        for (String methodName : METHODNAME_TO_EDGES.keySet()) {
	        Process p = Runtime.getRuntime().exec("sh toImage.sh");
		    p.waitFor();
        }
        */
        
	}
}