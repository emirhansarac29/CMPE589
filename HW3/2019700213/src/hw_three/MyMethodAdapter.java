package hw_three;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

import javafx.util.Pair;

public class MyMethodAdapter extends MethodVisitor{

	private String methodName;
	private String lastLabelNode ="";	// Last LabelNode to find initial location of jump
	private boolean isGOTO = false;	// Special case for GOTO
	private boolean isCOND = false;
	private static int COND_VAL = 0;
	private static int lastVar = 0;
	
	private int INDEX = 1;

	public void Jjump() {
		super.visitLdcInsn("" + this.INDEX);
		super.visitFieldInsn(179, "hw_three/Coverage", "CURRENT_INDEX", "Ljava/lang/String;");
		this.INDEX++;
	}
	public void assignJump() {
		super.visitLdcInsn("" + this.INDEX);
		super.visitMethodInsn(184, "hw_three/Coverage", "findBranch", "(Ljava/lang/String;)V", false);
	}
	public void staticJUMP() {
		if(isCOND) {
			Coverage.ALL_CONDITION_COVERAGE.add(new Pair<String, String>("" + COND_VAL, "" + this.INDEX));
			isCOND = false;
		}
	}
	public MyMethodAdapter(MethodVisitor mv, String methodName) {
		super(Opcodes.ASM7, mv);
		this.methodName=methodName;
	}

	//This is the point we insert the code. Note that the instructions are added right after
	//the visitCode method of the super class. This ordering is very important.
	@SuppressWarnings("deprecation")
	@Override
	public void visitCode() {
		
		super.visitCode();
		staticJUMP();
		assignJump();
		Jjump();
	}  

	@Override
	public void visitLineNumber(final int line, final Label start) {
		staticJUMP();
		assignJump();
		super.visitLineNumber(line, start);
		if(Coverage.C_LINES.contains(line)) {
			Coverage.C_LINES.remove(Coverage.C_LINES.indexOf(line));
			/*
			super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			super.visitLdcInsn("LINE_NUMBER_ITERATED_" + line);
			super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
			*/
			super.visitFieldInsn(Opcodes.GETSTATIC, "hw_three/Coverage", "VISITED_LINES", "Ljava/util/HashSet;");
			super.visitIntInsn(17, line);
			super.visitMethodInsn(184, "java/lang/Integer", "valueOf","(I)Ljava/lang/Integer;", false);
			super.visitMethodInsn(182, "java/util/HashSet", "add", "(Ljava/lang/Object;)Z", false);
			super.visitInsn(87);
		}
		Jjump();
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {

		Pair<String, String> pair = new Pair<String, String>(lastLabelNode, label.toString());
		Coverage.LABEL_TO_LABEL.add(pair);
		if(opcode == Opcodes.GOTO) {
			isGOTO = true;
		}else {
			isCOND = true;
			COND_VAL = this.INDEX;
			Coverage.ALL_CONDITION_COVERAGE.add(new Pair<String, String>("" + this.INDEX, label.toString()));
			//super.visitInsn(89);
			Coverage.JUMP_LABEL_TO_LABEL.add(new Pair<String, String>(lastLabelNode, label.toString()));
			//System.out.println(lastLabelNode + " **** " + label.toString()); 
			
		}
		assignJump();
		Jjump();
		
		
		super.visitJumpInsn(opcode, label);
		
		if(opcode != Opcodes.GOTO) {
			//super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			//super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Z)V", false);
			
			
		}
		
		
		
	}
	
	@Override
	public void visitLabel(final Label label) {
		String st_label = label.toString();
		if(lastLabelNode.compareTo("") != 0) {
			Pair<String, String> pair = new Pair<String, String>(lastLabelNode, st_label);
			if(!isGOTO) {
				Coverage.LABEL_TO_LABEL.add(pair);
			}else{
				Coverage.LABEL_TO_LABEL.add(pair);
				isGOTO = false;	// Special case for next
			}
		}
		lastLabelNode = st_label;
		super.visitLabel(label);
		
		if(isCOND) {
			Coverage.ALL_CONDITION_COVERAGE.add(new Pair<String, String>("" + COND_VAL, st_label));
			isCOND = false;
		}
		
		super.visitLdcInsn(st_label);
		super.visitMethodInsn(184, "hw_three/Coverage", "findBranch", "(Ljava/lang/String;)V", false);
		super.visitLdcInsn(st_label);
		super.visitFieldInsn(179, "hw_three/Coverage", "CURRENT_INDEX", "Ljava/lang/String;");
		
		super.visitLdcInsn(label.toString());
		super.visitMethodInsn(184, "hw_three/Coverage", "traverse", "(Ljava/lang/String;)V", false);
		

	}
	
	
	@Override
	public void visitVarInsn(final int opcode, final int var) {
		
	    super.visitVarInsn(opcode, var);
	    staticJUMP();
	    assignJump();
	    Jjump();
	}
	
	@Override
	public void visitInsn(final int opcode) {
	
	    super.visitInsn(opcode);
	    staticJUMP();
	    assignJump();
	    Jjump();
	}
	@Override
	public void visitParameter(final String name, final int access) {
	    super.visitParameter(name, access);
	    staticJUMP();
	    assignJump();
	    Jjump();
	}
	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		staticJUMP();
		assignJump();
		Jjump();
	    return super.visitAnnotationDefault();
	  }
	@Override
	public AnnotationVisitor visitAnnotation(final String descriptor, final boolean visible) {
		staticJUMP();
		assignJump();
		Jjump();
	    return super.visitAnnotation(descriptor, visible);
	  }
	
	@Override
	public AnnotationVisitor visitTypeAnnotation(
	      final int typeRef, final TypePath typePath, final String descriptor, final boolean visible) {
		staticJUMP();
		assignJump();
		Jjump();
	    return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
	  }

	@Override
	  public void visitAnnotableParameterCount(final int parameterCount, final boolean visible) {
		staticJUMP();
		assignJump();
		Jjump();
	    super.visitAnnotableParameterCount(parameterCount, visible);
	  }

	  @Override
	  public AnnotationVisitor visitParameterAnnotation(
	      final int parameter, final String descriptor, final boolean visible) {
		  staticJUMP();
		  assignJump();
		  Jjump();
	    return super.visitParameterAnnotation(parameter, descriptor, visible);
	  }

	  @Override
	  public void visitAttribute(final Attribute attribute) {
	   super.visitAttribute(attribute);
	   staticJUMP();
	   assignJump();
	   Jjump();
	  }

	  @Override
	  public void visitFrame(
	      final int type,
	      final int numLocal,
	      final Object[] local,
	      final int numStack,
	      final Object[] stack) {
		  
	    super.visitFrame(type, numLocal, local, numStack, stack);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }

	  @Override
	  public void visitIntInsn(final int opcode, final int operand) {
	    super.visitIntInsn(opcode, operand);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }


	  @Override
	  public void visitTypeInsn(final int opcode, final String type) {

	    super.visitTypeInsn(opcode, type);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }

	  @Override
	  public void visitFieldInsn(
	      final int opcode, final String owner, final String name, final String descriptor) {
		
	    super.visitFieldInsn(opcode, owner, name, descriptor);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }
	  
	  @Override
	  public void visitMethodInsn(
	      final int opcode,
	      final String owner,
	      final String name,
	      final String descriptor,
	      final boolean isInterface) {

	    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }

	  @Override
	  public void visitInvokeDynamicInsn(
	      final String name,
	      final String descriptor,
	      final Handle bootstrapMethodHandle,
	      final Object... bootstrapMethodArguments) {
	
	   super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
	   staticJUMP();
	   assignJump();
	   Jjump();
	  }


	  @Override
	  public void visitLdcInsn(final Object value) {
		 
	    super.visitLdcInsn(value);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }

	  @Override
	  public void visitIincInsn(final int var, final int increment) {
	    super.visitIincInsn(var, increment);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }

	  @Override
	  public void visitTableSwitchInsn(
	      final int min, final int max, final Label dflt, final Label... labels) {
		  
	    super.visitTableSwitchInsn(min, max, dflt, labels);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }

	  @Override
	  public void visitLookupSwitchInsn(final Label dflt, final int[] keys, final Label[] labels) {
	    super.visitLookupSwitchInsn(dflt, keys, labels);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }

	  @Override
	  public void visitMultiANewArrayInsn(final String descriptor, final int numDimensions) {
	    super.visitMultiANewArrayInsn(descriptor, numDimensions);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }

	  @Override
	  public AnnotationVisitor visitInsnAnnotation(
	      final int typeRef, final TypePath typePath, final String descriptor, final boolean visible) {
		  staticJUMP();
		  assignJump();
		  Jjump();
	   return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
	  }

	  @Override
	  public void visitTryCatchBlock(
	      final Label start, final Label end, final Label handler, final String type) {
	    super.visitTryCatchBlock(start, end, handler, type);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }

		@Override
	  public AnnotationVisitor visitTryCatchAnnotation(
	      final int typeRef, final TypePath typePath, final String descriptor, final boolean visible) {
			staticJUMP();
			assignJump();
			Jjump();
	    return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
	  }

		@Override
	  public void visitLocalVariable(
	      final String name,
	      final String descriptor,
	      final String signature,
	      final Label start,
	      final Label end,
	      final int index) {
	    super.visitLocalVariable(name, descriptor, signature, start, end, index);
	    staticJUMP();
	    assignJump();
	    Jjump();
	  }

		@Override
	  public AnnotationVisitor visitLocalVariableAnnotation(
	      final int typeRef,
	      final TypePath typePath,
	      final Label[] start,
	      final Label[] end,
	      final int[] index,
	      final String descriptor,
	      final boolean visible) {
			staticJUMP();
			assignJump();
			Jjump();
	    return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, descriptor, visible);
	  }
	
}