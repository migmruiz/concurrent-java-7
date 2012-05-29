/**
 * SelectMaxProblem a copy of a version provided by Brian Goetz at 
 * http://www.ibm.com/developerworks/java/library/j-jtp11137/index.html
 * Java theory and practice: Stick a fork in it, Part 1.
 * Nothing new here, only some obvious implementations done.
 *
 * @author      Miguel Mendes Ruiz
 * @version     1.0~may2012
 * @since       1.7
 */
public class SelectMaxProblem {
  private final int[] numbers;
  private final int start, end, size;

  public SelectMaxProblem(int[] numbers, int start, int end) {
    this.numbers = numbers;
    this.start = start;
    this.end = end;
    this.size = end - start;
  }

  public int size() {
    return this.size;
  }

  public int solveSequentially() {
    int max = Integer.MIN_VALUE;
    for (int i = start; i < end; i++) {
      int n = numbers[i];
      if (n > max)
        max = n;
    }
    return max;
  }
  
  public SelectMaxProblem subproblem(int subStart, int subEnd) {
    return new SelectMaxProblem(numbers, start + subStart, start + subEnd);
  }
}
