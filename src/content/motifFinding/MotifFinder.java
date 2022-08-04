package content.motifFinding;

import java.util.List;

public interface MotifFinder {

    List<String> findMotif(List<String> sequences, int k);
}
