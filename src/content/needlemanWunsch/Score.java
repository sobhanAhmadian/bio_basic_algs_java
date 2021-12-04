package content.needlemanWunsch;

import java.util.List;

public class Score {

    private final List<Character> first;
    private final List<Character> second;
    private final double[][] scores;

    public Score(List<Character> first, List<Character> second, double[][] scores) {
        this.first = first;
        this.second = second;
        this.scores = scores;
    }

    public double getScore(char t, char s) {
        int i = first.indexOf(t);
        int j = second.indexOf(s);
        return scores[i][j];
    }
}
