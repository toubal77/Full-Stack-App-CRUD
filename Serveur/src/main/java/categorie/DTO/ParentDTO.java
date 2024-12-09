package categorie.DTO;

import java.util.Date;

public class ParentDTO {
    private Long id;
    private String name;
    private Date creationDate;
    private boolean ifRacine;

    public ParentDTO(Long id, String name, Date creationDate,boolean ifRacine) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.ifRacine = ifRacine;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

	public boolean isIfRacine() {
		return ifRacine;
	}

	public void setIfRacine(boolean ifRacine) {
		this.ifRacine = ifRacine;
	}
}