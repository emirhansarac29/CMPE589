package hw_three;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

public class MethodTrace extends MethodNode {
	
	
	
	public MethodTrace(int access, String name, String desc,String signature, String[] exceptions) { 
		super(Opcodes.ASM7, access, name, desc, signature, exceptions);
	}
	
	
	@Override 
	public void visitLabel(Label label) {
		
        super.visitLabel(label);
	}
	

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		//JumpInsnNode jump = new JumpInsnNode(opcode, getLabelNode(label));
		
		
		super.visitJumpInsn(opcode, label);
	}
	
	
}

