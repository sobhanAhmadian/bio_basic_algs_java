package data;

import java.util.ArrayList;
import java.util.List;

public class Sequences {

    public static final Character[] NUCLEOTIDES = new Character[]{
        'A', 'C', 'G', 'T'
    };

    public static final List<String> NF_KB = new ArrayList<String>() {
        public ArrayList<String> make() {
            add("TCGGGGGTTTTT");
            add("CCGGTGACTTAC");
            add("ACGGGGATTTTC");
            add("TTGGGGACTTTT");
            add("AAGGGGACTTCC");
            add("TTGGGGACTTCC");
            add("TCGGGGATTCAT");
            add("TCGGGGATTCCT");
            add("TAGGGGAACTAC");
            add("TCGGGTATAACC");
            return this;
        }
     }.make();

    public static final String CONSENSUS_OF_NF_KB = "TCGGGGATTTCC";

}
