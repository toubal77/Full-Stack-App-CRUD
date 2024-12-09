package categorie.DTO;

import java.util.Date;

public class ParentDTO {
    private Long id;
    private String name;
    private Date creationDate;
    private boolean ifRacine;
    private Integer nbrChildrens;

    public ParentDTO(Long id, String name, Date creationDate,boolean ifRacine, Integer nbrChildrens) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.ifRacine = ifRacine;
        this.nbrChildrens = nbrChildrens;
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

	public Integer getNbrChildrens() {
		return nbrChildrens;
	}

	public void setNbrChildrens(Integer nbrChildrens) {
		this.nbrChildrens = nbrChildrens;
	}
}
