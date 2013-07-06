package org.example.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "FragmentAnswer")
public class FragmentAnswer {
    private String filepath;
    private long id;
    private int page;
    private FullAnswer parentAnswer;

    public FragmentAnswer() {}
    public FragmentAnswer(String fp, int pg, FullAnswer answer) {
        filepath = fp;
        page = pg;
        parentAnswer = answer;
    }


    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getId() { return id; }

    @ManyToOne
    public FullAnswer getParentAnswer() { return parentAnswer; }

    public String getFilepath() { return filepath; }
    public int getPage() { return page; }

    public void setId(long i) { id = i; }
    public void setFilepath(String path) { filepath = path; }
    public void setPage(int p) { page = p; }
    public void setParentAnswer(FullAnswer answer) { parentAnswer = answer; }

    public void delete() {
        (new File(getFilepath())).delete();
    }

}
