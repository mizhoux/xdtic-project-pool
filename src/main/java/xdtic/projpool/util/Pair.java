package xdtic.projpool.util;

/**
 * 二元组
 *
 * @author Michael Chow <mizhoux@gmail.com>
 * @param <L> Left element
 * @param <R> right element
 */
public class Pair<L, R> {

    private final L left;
    private final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    /**
     * 返回二元组中左边的元素
     *
     * @return 二元组中左边的元素
     */
    public L left() {
        return left;
    }

    /**
     * 返回二元组中右边的元素
     *
     * @return 二元组中右边的元素
     */
    public R right() {
        return right;
    }
}
