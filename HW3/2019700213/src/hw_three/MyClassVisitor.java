package hw_three;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class MyClassVisitor extends ClassVisitor{
	
    public MyClassVisitor(ClassWriter cv) {
        super(Opcodes.ASM7, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
            String signature, String[] exceptions) {

        MethodVisitor mv= super.visitMethod(access, name, desc, signature, exceptions);
        MyMethodAdapter mvw=new MyMethodAdapter(mv, name);
        return mvw;
    }
     
     
     
}