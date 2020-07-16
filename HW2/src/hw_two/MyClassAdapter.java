package hw_two;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public class MyClassAdapter extends ClassNode { 
	public MyClassAdapter() {
          super(Opcodes.ASM7);
    }

	/*
	 * Overriden in order to override MethodNode
	 */
	@Override 
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		MyMethodAdapter method = new MyMethodAdapter(access, name, descriptor, signature, exceptions);
		methods.add(method);
		return method;
	} 
}