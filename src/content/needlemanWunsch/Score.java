package content.needlemanWunsch;

import java.util.List;

public class Score {

    private final List<Character> first;
    private final double[][] scores;

    /**
     * @param characters elements of sequences e.g nucleotides or amino acids
     * @param scores scores(i,j) is score of matching element i with element j
     */
    public Score(List<Character> characters, double[][] scores) {
        this.first = characters;
        this.scores = scores;
    }

    /**
     * @param t name of first element
     * @param s name of second element
     * @return score of matching element t and s
     */
    public double getScore(char t, char s) {
        int i = first.indexOf(t);
        int j = first.indexOf(s);
        return scores[i][j];
    }
}
