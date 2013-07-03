package org.example.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FragmentAnswer {
    private String filepath;
    private long id;
    private int page;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getId() { return id; }

    public String getFilepath() { return filepath; }
    public int getPage() { return page; }

    public void setId(long i) { id = i; }
    public void setFilepath(String path) { filepath = path; }
    public void setPage(int p) { page = p; }

}
