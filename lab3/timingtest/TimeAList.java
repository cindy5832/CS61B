package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> a = new AList();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        a.addLast(1000);
        a.addLast(2000);
        a.addLast(4000);
        a.addLast(8000);
        a.addLast(16000);
        a.addLast(32000);
        a.addLast(64000);
        a.addLast(128000);
        for(int i =0; i < a.size(); i++){
            Integer N = a.get(i);
            AList<Integer> Ns = new AList();
            Stopwatch sw = new Stopwatch();
            for(int j=1; j <= N; j++){
                Ns.addLast(j);
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
            opCounts.addLast(Ns.size());
        }
        printTimingTable(a, times,opCounts);
    }
}
