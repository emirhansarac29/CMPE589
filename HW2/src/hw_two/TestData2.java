package hw_two;
/*
 * Class which contains the method to analyze
 */
public class TestData2 {

    public void test1(){
        int x = 3;
        int y = 5;
        
        if(x <= 2){
            if(y >= 4){
                System.out.println("x <=2 and y >= 4");
            }else{
                System.out.println("x <= 2 and y < 4");
            }
        }else{
            if(y >= 4){
                System.out.println("x > 2 and y >= 4");
            }else{
                System.out.println("x < 2 and y < 4");
            }
        }
    }



    public void test2(){
        int x = 4;
        int y = 4;
        
        if(x < y){
            System.out.println("x < y");
        }else
        if(y < x){
            System.out.println("y < x");
        }else{
            System.out.println("x = y");
        }
    }



    public void test3(){
        int x = 5;
        int y = 5;
        while(x < y){   	
            System.out.println("x < y");
            ++x;
        }
    }

    public void test4(){
        int x = 4;
        int y = 5;

        while(x < y){
            System.out.println("x < y");
            ++x;
        }
    }

    public void test5(){
        int x = 3;
        int y = 5;

        while(x < y){
            System.out.println("x < y");
            ++x;
        }
    }

    public void IfElse(int x) {
        if (x < 3) {
            x = 6;
        } else {
            x = 7;
        }
    }

    public int IfElseIf(int y) {

        int x = 1;

        if (x == y) {
            y = 6;
        } else
        if (x == 8) {
            System.out.println("x == 7");
        } else {
            y = 1;
        }

        return x;
    }

    public int ManyIfElse(int y) {

        int x = 1;

        if (x == y) {
            y = 6;
        } else 
        if (x == 8) {
            y = 7;
        } else
        if (x == 6) {
            y = 1;
        } else
        if (x == 5) {
            y = 4;
        }

        return x;
    }


    public void ForTest(){

        int x = 3;

        for(int i = 0; i < x; i++){
            System.out.println("i");

        }

        if(x > 11) {
        	
        }
    }

 public void BreakConds(int x, int y) {
	 begin: 
	 while(x > 0) {
		 System.out.println("HELLO");
		 while(y>0) {
			 System.out.println("MERHABA");
			 if(y == 1) {
				 break;
			 }else
			 if(y == 4) {
				 break begin;
			 }
			 y--;
		 }
		 if(x == 1) {
			 break;
		 }
		 x--;
	 }
 }
 
	 public void MoreComplex() {
		 int x = 5;
		 String str = "";
		 for (int i = 0; i < 10; i++, x++) {
			 if (x % 2 == 0) {
				 str += "E";
			 } else {
				 str += "O";
			 }
			 int y = 0;
		 
			 while (y < 5) {
				 str += y;
				 int z = 0;
				 	while (z < 10) {
				 		z *= z;
				 		str += z;
				 	}
			 }
		 }
	 }

  public void Complex() {
        int x= 2;
        int y= 3;

        for(int a = 1 , b = 3; a < 4 && b < 4; a++, b++) {
            x++;
            y++;
        }
        
        while(x > 0 || y < 0) {
            x--;
        }
    }


}