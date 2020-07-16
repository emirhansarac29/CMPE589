package hw_three;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public class ClassTrace extends ClassNode { 
	public ClassTrace() {
          super(Opcodes.ASM7);
    }

	/*
	 * Overriden in order to override MethodNode
	 */
	@Override 
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		MethodTrace method = new MethodTrace(access, name, descriptor, signature, exceptions);
		methods.add(method);
		return method;
	} 
}