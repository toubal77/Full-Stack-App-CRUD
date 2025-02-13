package categorie.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date creationDate;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Category> children = new ArrayList<>();

    private boolean ifRacine;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int nbrChildrends = 0;

    public Category() {
        this.creationDate = new Date();
    }

    public Category(String name, Category parent, boolean ifRacine) {
        this.name = name;
        this.parent = parent;
        this.ifRacine = ifRacine;
        this.creationDate = new Date();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getChildren() {
        return this.children;
    }

    public void setChildren(Category child) {
        this.children.add(child);
    }

    public void setChildrens(List<Category> children) {
        this.children = children;
    }

    public Category getParent() {
        return this.parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isIfRacine() {
        return this.ifRacine;
    }

    public void setIfRacine(boolean ifRacine) {
        this.ifRacine = ifRacine;
    }

    public Integer getNbrChildrends() {
        return this.nbrChildrends;
    }

    public void setNbrChildrends(Integer nbrChildrends) {
        this.nbrChildrends = nbrChildrends;
    }
}