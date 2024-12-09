package categorie.DTO;

import java.util.List;

import categorie.entities.Category;

public class UpdateDTO {
    private Long id;
    private String name;
    private Category parent;
    private boolean ifRacine;
    private List<Category> children; 
    
	public List<Category> getChildren() {
		return children;
	}
	
	public void setChildren(List<Category> children) {
		this.children = children;
	}
	
	public boolean isIfRacine() {
		return ifRacine;
	}
	
	public void setIfRacine(boolean ifRacine) {
		this.ifRacine = ifRacine;
	}
	
	public Category getParent() {
		return parent;
	}
	
	public void setParent(Category parent) {
		this.parent = parent;
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

}
