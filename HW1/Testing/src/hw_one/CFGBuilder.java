package hw_one;

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
         
        // Write to .dot file
        for (String methodName : METHODNAME_TO_EDGES.keySet()) {
        	 ArrayList<Pair<Integer, Integer>> edges = METHODNAME_TO_EDGES.get(methodName);
        	 Set<Integer> usedLines = new HashSet<Integer>();
        	 File file_out = new File (methodName + ".dot");
        	 PrintWriter out = new PrintWriter (file_out);
        	 out.println("digraph ast {");
        	 out.println("START [label=\"START - " + methodName + "\",width=0,height=0];");
        	 for (int i = 0; i < edges.size(); i++) {
        		 int begin = edges.get(i).getKey();
        		 int end = edges.get(i).getValue();
        		 if(!usedLines.contains(begin)) {
        			 out.println("L" + begin + " [label=\"" + JAVA_LINE.get(begin) + "\",width=0,height=0];");
        			 usedLines.add(begin);
        		 }
        		 if(!usedLines.contains(end)) {
        			 out.println("L" + end + " [label=\"" + JAVA_LINE.get(end) + "\",width=0,height=0];");
        			 usedLines.add(end);
        		 }
        	 }
        	 out.println("START -> L" + FUNCTION_INIT_LINE.get(methodName));
        	 for (int i = 0; i < edges.size(); i++) {
        		 int begin = edges.get(i).getKey();
        		 int end = edges.get(i).getValue();
        		 out.println("L" + begin + " -> L" + end);
        	 }
        	 out.println ("}");
        	 out.close ();
		}
       
        /*
        for (String methodName : METHODNAME_TO_EDGES.keySet()) {
	        Process p = Runtime.getRuntime().exec("sh toImage.sh");
		    p.waitFor();
        }
        */
        
	}
}