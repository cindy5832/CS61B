package bomb;

import common.IntList;

public class BombMain {
    public static void main(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }

        Bomb b = new Bomb();
        if (phase >= 0) {
            b.phase0("39291226");
        }
        if (phase >= 1) {
            b.phase1(IntList.of(0,9,3,0,8)); // Figure this out too
        }

        StringBuffer sb = new StringBuffer();
        int i = 0;
        while(i <= 1336){
            sb.append(i).append(" ");
            i++;
        }
        sb.append(-81201430);

        if (phase >= 2) {
            b.phase2(sb.toString());
        }
    }
}
