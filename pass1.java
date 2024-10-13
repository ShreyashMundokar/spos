import java.util.*;
import java.io.*;

public class pass1 {
    static int address = 0;
    static int sadd[] = new int[10];
    static int ladd[] = new int[10];
    
    public static void main(String args[]) {
        BufferedReader br;
        OutputStream oo;
        String input = null;
        String IS[] = {"ADD", "SUB", "MUL", "MOV"};
        String UserReg[] = {"AREG", "BREG", "CREG", "DREG"};
        String AD[] = {"START", "END"};
        String DL[] = {"DC", "DS"};
        int lc = 0;
        int scount = 0, lcount = 0;
        int flag = 0, flag2 = 0, stored = 0;
        String tokens[] = new String[30];
        String tt = null;
        String sv[] = new String[10];
        String lv[] = new String[10];

        try {
            br = new BufferedReader(new FileReader("initial.txt"));
            File f = new File("IM.txt");
            File f1 = new File("ST.txt");
            File f2 = new File("LT.txt");
            PrintWriter p = new PrintWriter(f);
            PrintWriter p1 = new PrintWriter(f1);
            PrintWriter p2 = new PrintWriter(f2);

            int k = 0, l = 0;
            while ((input = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(input, " ");
                while (st.hasMoreTokens()) {
                    tt = st.nextToken();
                    
                    // Check if it's a number with more than two digits
                    if (tt.matches("\\d*") && tt.length() > 2) {
                        lc = Integer.parseInt(tt);
                        p.println(lc);
                        address = lc - 1;
                    } else {
                        // Check for assembler directives
                        for (int i = 0; i < AD.length; i++) {
                            if (tt.equals(AD[i])) {
                                p.print("AD " + (i + 1) + " ");
                            }
                        }

                        // Check for instructions
                        for (int i = 0; i < IS.length; i++) {
                            if (tt.equals(IS[i])) {
                                p.print("IS " + (i + 1) + " ");
                            }
                        }

                        // Check for user registers
                        for (int i = 0; i < UserReg.length; i++) {
                            if (tt.equals(UserReg[i])) {
                                p.print((i + 1) + " ");
                                flag = 1;
                            }
                        }

                        // Check for declarative statements
                        for (int i = 0; i < DL.length; i++) {
                            if (tt.equals(DL[i])) {
                                p.print("DL " + (i + 1) + " ");
                            }
                        }

                        // If it's a single character and no more tokens
                        if (tt.length() == 1 && !st.hasMoreTokens() && flag == 1) {
                            if (Arrays.asList(sv).contains(tt)) {
                                for (int i = 0; i < scount; i++) {
                                    if (sv[i].equals(tt)) {
                                        p.print("S" + i);
                                        flag2 = 1;
                                    } else {
                                        flag2 = 0;
                                    }
                                }
                            } else {
                                p.print("S" + scount);
                                sv[scount] = tt;
                                flag2 = 1;
                                scount++;
                            }
                        }

                        // If it's a label or symbol
                        if (tt.length() == 1 && st.hasMoreTokens()) {
                            p.print(tt + " ");
                            sadd[k] = address;
                            k++;
                        }

                        // If it's a literal
                        if (tt.charAt(0) == '=') {
                            p.print("L" + lcount);
                            lv[lcount] = tt;
                            lcount++;
                        }

                        // End of a line
                        if (!st.hasMoreTokens()) {
                            p.println();
                        }

                        // Handle declarative statements with DS
                        if (tt.equals("DS")) {
                            int a = Integer.parseInt(st.nextToken());
                            address = address + a - 1;
                            p.println();
                        }
                    }
                }
                address++;
            }

            p.close();
            address--;

            // Store literals
            for (int i = 0; i < lcount; i++) {
                ladd[i] = address;
                address++;
            }

            // Store symbols in symbol table
            for (int i = 0; i < scount; i++) {
                p1.println(i + "\t" + sv[i] + "\t" + sadd[i]);
            }
            p1.close();

            // Store literals in literal table
            for (int i = 0; i < lcount; i++) {
                p2.println(i + "\t" + lv[i] + "\t" + ladd[i]);
            }
            p2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
