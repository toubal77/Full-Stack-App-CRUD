package categorie.DTO;

import java.util.Date;
import java.util.List;

import categorie.entities.Category;

public class ResponseDTO {
    private Long id;
    private String name;
    private Date creationDate;
    private ParentDTO parent;
    private List<Category> childrens;
    private boolean ifRacine;

    public ResponseDTO(Long id, String name, Date creationDate, boolean ifRacine, ParentDTO parent, List<Category> childrens) {
        this.setId(id);
        this.setName(name);
        this.setCreationDate(creationDate);
        this.setIfRacine(ifRacine);
        this.setParent(parent);
        this.setChildrens(childrens);
    }

	public ParentDTO getParent() {
		return parent;
	}

	public void setParent(ParentDTO parent) {
		this.parent = parent;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Category> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Category> childrens) {
		this.childrens = childrens;
	}

	public boolean isIfRacine() {
		return ifRacine;
	}

	public void setIfRacine(boolean ifRacine) {
		this.ifRacine = ifRacine;
	}

}