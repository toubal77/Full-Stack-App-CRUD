package categorie.DTO;

import java.util.Date;
import java.util.List;

import categorie.entities.Category;

public class ResponseDTO {
    private Long id;
    private String name;
    private Date creationDate;
    private ParentDTO parent;
    private List<ChildDTO> childrens;
    private boolean ifRacine;
    private Integer nbrChildrens;

    public ResponseDTO(Long id, String name, Date creationDate, boolean ifRacine, ParentDTO parent, List<ChildDTO> childrens, Integer nbrChildrens) {
        this.setId(id);
        this.setName(name);
        this.setCreationDate(creationDate);
        this.setIfRacine(ifRacine);
        this.setParent(parent);
        this.setChildrens(childrens);
        this.setNbrChildrens(nbrChildrens);
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

	public List<ChildDTO> getChildrens() {
	 	return childrens;
	}

	public void setChildrens(List<ChildDTO> childrens) {
		this.childrens = childrens;
	}

	public boolean isIfRacine() {
		return ifRacine;
	}

	public void setIfRacine(boolean ifRacine) {
		this.ifRacine = ifRacine;
	}

	public Integer getNbrChildrens() {
		return nbrChildrens;
	}

	public void setNbrChildrens(Integer nbrChildrens) {
		this.nbrChildrens = nbrChildrens;
	}

}
