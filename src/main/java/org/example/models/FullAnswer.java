package org.example.models;

import org.hibernate.annotations.GenericGenerator;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by hadesgames on 7/3/13.
 */

@Entity
public class FullAnswer {
   private String filepath;
   private long id;

   @Id
   @GeneratedValue(generator="increment")
   @GenericGenerator(name="increment", strategy="increment")
   public long getId() { return id; }

   public String getFilepath() { return filepath; }

   public void setId(long i) { id = i; }
   public void setFilepath(String path) { filepath = path; }

}
