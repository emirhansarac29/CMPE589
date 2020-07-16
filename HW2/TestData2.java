package hw_one;
/*
 * Class which contains the method to analyze
 */
public class TestData {

	public int k = 3;
	
    public int liveVarTest(int p) {
		int x = 0; // DEAD CODE
		int y = 1;
		int z = 2;
		x = y + 1;  // DEAD CODE
		if(p != 0) 
			p = z - 1;  // DEAD CODE
		else
			z = 5;
		x = z + y + 1; 
		return x;
	}
   
    public void test() {
    	int x = 4;
    	k = x + 7;
    }
    

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
            x = 6; // DEAD CODE
        } else {
            x = 7; // DEAD CODE
        }
    }

    public int IfElseIf(int y) {

        int x = 1;

        if (x == y) {
            y = 6; // DEAD CODE
        } else
        if (x == 8) {
            System.out.println("x == 7");
        } else {
            y = 1; // DEAD CODE
        }

        return x;
    }

    public int ManyIfElse(int y) {

        int x = 1;

        if (x == y) {
            y = 6; // DEAD CODE
        } else
        if (x == 8) {
            y = 7; // DEAD CODE
        } else
        if (x == 6) {
            y = 1; // DEAD CODE
        } else
        if (x == 5) {
            y = 4; // DEAD CODE
        }

        return x;
    }

    public void ForTest(){

        int x = 3;

        for(int i = 0;
            i < x;
            i++){

            System.out.println("i");

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

    public void DeadCode() {
        int x = 3;
        int y = 1; // DEAD CODE
        int z = 0; // DEAD CODE

        if(x > 3) {
            y = 2; // DEAD CODE
            z = 4;
        } else {
            z = 5;
        }
        x = z;
        System.out.println(x);
    }

    public int doWhile(){
        int b = 0; // DEAD CODE
        int c = 0;
        int a = 0;
        do  {
            b = a + 1;
            c = c + b;
            a = b * 2;
        }while(a < 9);
        return c;
    }

    public int deadFor() {
    	int x = 3;
    	for(int i = 0 ; i < 3 ; i++) {
    		x++;
    	}
    	return x;
    }
}
