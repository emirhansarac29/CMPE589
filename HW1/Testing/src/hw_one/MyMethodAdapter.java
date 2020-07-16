package hw_one;

import java.util.ArrayList;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import javafx.util.Pair;

public class MyMethodAdapter extends MethodNode {
	
	private String lastLabelNode;	// Last LabelNode to find initial location of jump
	private boolean isGOTO = false;	// Special case for GOTO
	private int prevLine = -1;		// To find next instruction
	
	public MyMethodAdapter(int access, String name, String desc,String signature, String[] exceptions) { 
		super(Opcodes.ASM7, access, name, desc, signature, exceptions);
	}
	
	/*
	 * Assigns each LabelNode to its method.
	 * Collects all LabelNodes
	 */
	@Override 
	public void visitLabel(Label label) {
		String st_label = getLabelNode(label).getLabel().toString();
		if(CFGBuilder.FUNCTION_TO_LABELS.get(this.name) == null) {
			ArrayList<String> funct_labels = new ArrayList<String>();
			funct_labels.add(st_label);
			CFGBuilder.FUNCTION_TO_LABELS.put(this.name, funct_labels);
		}else {
			CFGBuilder.FUNCTION_TO_LABELS.get(this.name).add(st_label);
		}
		CFGBuilder.LABELNODES.add(st_label);
		lastLabelNode = st_label;
        super.visitLabel(label);
	}
	
	/*
	 * Collects beginning and end of jump LabelNodes of each method.
	 */
	@Override
	public void visitJumpInsn(int opcode, Label label) {
		//JumpInsnNode jump = new JumpInsnNode(opcode, getLabelNode(label));
		if(CFGBuilder.METHODNAME_TO_JUMP_LABEL_TO_LABEL.get(this.name) == null) {
			ArrayList<Pair<String, String>> met_labels = new ArrayList<Pair<String, String>>();
			Pair<String, String> pair = new Pair<String, String>(lastLabelNode, getLabelNode(label).getLabel().toString());
			met_labels.add(pair);
			CFGBuilder.METHODNAME_TO_JUMP_LABEL_TO_LABEL.put(this.name, met_labels);
		}else {
			Pair<String, String> pair = new Pair<String, String>(lastLabelNode, getLabelNode(label).getLabel().toString());
			CFGBuilder.METHODNAME_TO_JUMP_LABEL_TO_LABEL.get(this.name).add(pair);
		}
		if(opcode == Opcodes.GOTO) {
			isGOTO = true;
		}
		super.visitJumpInsn(opcode, label);
	}
	
	/*
	 * Collects initial label of each line.
	 * Collects next from each line.
	 */
	@Override
	public void visitLineNumber(int line, Label start) {
		if(!isGOTO && prevLine != -1) {
			// Collects next line
			if(CFGBuilder.METHODNAME_TO_EDGES.get(this.name) == null) {
				ArrayList<Pair<Integer, Integer>> met_labels = new ArrayList<Pair<Integer, Integer>>();
				Pair<Integer, Integer> pair = new Pair<Integer, Integer>(prevLine, line);
				if(prevLine != line) {
					met_labels.add(pair);
				}
				CFGBuilder.METHODNAME_TO_EDGES.put(this.name, met_labels);
			}else {
				Pair<Integer, Integer> pair = new Pair<Integer, Integer>(prevLine, line);
				if(CFGBuilder.METHODNAME_TO_EDGES.get(this.name).contains(pair) == false && prevLine != line) {
					CFGBuilder.METHODNAME_TO_EDGES.get(this.name).add(pair);
				}
			}
		}else if (isGOTO) {
			isGOTO = false;	// Special case for next
		}
		prevLine = line;
		// Initial label
		CFGBuilder.LABELNODE_TO_STARTLINE.put(getLabelNode(start).getLabel().toString(), line);
	    super.visitLineNumber(line, start);
	}
}
