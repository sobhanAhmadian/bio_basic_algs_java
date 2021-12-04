package util;

public final class NucleotideUtility {

    private NucleotideUtility() {

    }

    /**
     * @param n name of nucleotide
     * @return complement of nucleotide
     */
    public static char complement(char n) {
        switch (n) {
            case 'A':
                return 'T';
            case 'T':
                return 'A';
            case 'C':
                return 'G';
            case 'G':
                return 'C';
            case 'a':
                return 't';
            case 't':
                return 'a';
            case 'c':
                return 'g';
            case 'g':
                return 'c';
            default:
                return 'n';
        }
    }
}
