import java.util.concurrent.RecursiveAction;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

/**
 * MaxWithFJ is based on a version provided by Brian Goetz at 
 * http://www.ibm.com/developerworks/java/library/j-jtp11137/index.html
 * Java theory and practice: Stick a fork in it, Part 1.
 * Some things have changed since the actual release of Java 7
 *
 * @author      Miguel Mendes Ruiz
 * @version     1.0~may2012
 * @since       1.7
 */
public class MaxWithFJ extends RecursiveAction {
  private final int threshold;
  private final SelectMaxProblem problem;
  private int result;

  public MaxWithFJ(SelectMaxProblem problem, int threshold) {
    this.problem = problem;
    this.threshold = threshold;
  }

  protected void compute() {
    if (problem.size() < threshold) {
      result = problem.solveSequentially();
    } else {
      int midpoint = problem.size()/2;
      MaxWithFJ left = new MaxWithFJ(problem.subproblem(0, midpoint), threshold);
      MaxWithFJ right = new MaxWithFJ(problem.subproblem(midpoint + 1, problem.size()), threshold);
      invokeAll(left, right);
      result = Math.max(left.result(), right.result());
    }
  }

  public int result() {
    return this.result;
  }

/**
 * @throws InterruptedException  If an interrupt signal is caught
 */
  public static void main (String[] args) throws InterruptedException {
    long time = System.currentTimeMillis();

    RandomNumberArrayGenerator gen = new RandomNumberArrayGenerator(10000);
    int[] values = gen.getNumbers();
    SelectMaxProblem problem = new SelectMaxProblem(values, 0, values.length);
    int threshold = 50;
    MaxWithFJ mfj = new MaxWithFJ(problem, threshold);
    ForkJoinPool fjPool = new ForkJoinPool();

    fjPool.invoke(mfj);
    int result = mfj.result();

    time = System.currentTimeMillis() - time;
    System.out.println((Runtime.getRuntime()).availableProcessors() + " processors available.");
    System.out.println("Threshold: " + threshold);
    System.out.println("Time elapsed: " + time);
    System.out.println("Result: " + result);
    // Thread.sleep(3000l);
    // System.out.println("Original array: " + Arrays.toString(values));
  }
}
