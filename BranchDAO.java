// src/BranchDAO.java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BranchDAO {
    private List<Branch> branches = new ArrayList<>();

    public Optional<Branch> get(String branchCode) {
        return branches.stream()
                .filter(b -> b.getCode().equals(branchCode))
                .findFirst();
    }

    public List<Branch> getAll() {
        return new ArrayList<>(branches);
    }

    public boolean save(Branch branch) {
        if (get(branch.getCode()).isPresent()) {
            return false;
        }
        return branches.add(branch);
    }

    public boolean update(Branch branch) {
        for (int i = 0; i < branches.size(); i++) {
            if (branches.get(i).getCode().equals(branch.getCode())) {
                branches.set(i, branch);
                return true;
            }
        }
        return false;
    }

    public boolean delete(String branchCode) {
        return branches.removeIf(b -> b.getCode().equals(branchCode));
    }
}