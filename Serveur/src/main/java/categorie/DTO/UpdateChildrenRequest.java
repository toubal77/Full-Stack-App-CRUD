package categorie.DTO;
import java.util.ArrayList;
import java.util.List;
public class UpdateChildrenRequest {
    private Long id;
    private List<Long> childrens = new ArrayList<>();
    private List<Long> childrensRemoved = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Long> childrens) {
        this.childrens = childrens;
    }

	public List<Long> getChildrensRemoved() {
		return childrensRemoved;
	}

	public void setChildrensRemoved(List<Long> childrensRemoved) {
		this.childrensRemoved = childrensRemoved;
	}
}
