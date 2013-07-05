package org.example.models;


import org.hibernate.annotations.GenericGenerator;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.File;

/**
 * Created by hadesgames on 7/3/13.
 */

@Entity
@Table(name = "FullAnswer")
public class FullAnswer {
   private String filepath;
   private int id;

   public FullAnswer() {}

   public FullAnswer(String fp) {
       filepath = fp;
   }

   @Id
   @GeneratedValue(generator="increment")
   @GenericGenerator(name="increment", strategy="increment")
   public int getId() { return id; }


   public String getFilepath() { return filepath; }

   public void setId(int i) { id = i; }
   public void setFilepath(String path) { filepath = path; }


   public void delete() {
       File f = new File(getFilepath());
       f.delete();

       //TODO delete fragments
   }

}
