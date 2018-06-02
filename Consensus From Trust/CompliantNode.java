import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

// start forgetting
/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {

	private Set<Transaction> pendingTransactions;
	private List<Integer> followees;
	private Set<Candidate> possibleTxCandidates;
	private double expectedFollowees;
	private double expectedCompliantFollowees;
	private double expectedTxNumbers;
	private double p_graph;
	private double p_txDistribution;
	private double p_malicious;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
		followees = new ArrayList<>();
		pendingTransactions = new HashSet<Transaction>();
		possibleTxCandidates = new HashSet<Candidate>();
		this.p_graph = p_graph;
		this.p_malicious = p_malicious;
		this.p_txDistribution = p_txDistribution;
		//set num value!
    }

    public void setFollowees(boolean[] followees) {
		int c=0;        
		for (boolean i : followees) {
			if (i==true){
				this.followees.add(c);			
			}
			c++;
		}
		expectedFollowees = 99*p_graph;
		expectedCompliantFollowees = expectedFollowees*(1-p_malicious);
		expectedTxNumbers = expectedCompliantFollowees*p_txDistribution;
		return;
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
        // IMPLEMENT THIS
		for (Transaction tx : pendingTransactions) {
			this.pendingTransactions.add(tx);
		}
		return;
    }

    public Set<Transaction> sendToFollowers() {
        // IMPLEMENT THIS
		return pendingTransactions;
    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
        // IMPLEMENT THIS
		for (Candidate candidate : candidates) {			
			if (followees.contains(candidate.sender)) {
				if (!pendingTransactions.contains(candidate.tx)) {
//					pendingTransactions.add(candidate.tx);
					int count = 0;
					for (Candidate candidate2 : candidates) {
						if (candidate.tx.equals(candidate2.tx)) {
							count++;
						}
					}
					if (count >= expectedTxNumbers) {
						pendingTransactions.add(candidate.tx);
					}
				}
			}
		}
		return;
    }
}
