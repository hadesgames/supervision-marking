package org.example.models;


import org.apache.commons.io.FilenameUtils;
import org.example.SessionFactoryManager;
import org.example.pdfTools.PdfManip;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.File;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "FullAnswer")
public class FullAnswer {
   private String filepath;
   private int id;
   private String name;
   private Set<FragmentAnswer> fragments;

   public FullAnswer() {}

   public FullAnswer(String fp) {
       filepath = fp;
       name = FilenameUtils.getBaseName(FilenameUtils.getBaseName(filepath));
   }

   @Id
   @GeneratedValue(generator="increment")
   @GenericGenerator(name="increment", strategy="increment")
   public int getId() { return id; }

   @OneToMany(fetch=FetchType.EAGER, mappedBy = "parentAnswer")
   public Set<FragmentAnswer> getFragments() { return fragments; }

   public String getName() { return name; }
   public String getFilepath() { return filepath; }

   public void setId(int i) { id = i; }
   public void setFilepath(String path) { filepath = path; }
   public void setFragments(Set<FragmentAnswer> frags) { fragments = frags; }
   public void setName(String n) { name = n; }

   public void generateFragments() {
       Session session = SessionFactoryManager.getInstance().openSession();
       session.beginTransaction();

       List<String> files = PdfManip.splitPdf(getFilepath(), getId());

       int pageCounter = 1;

       for (String file: files) {
           FragmentAnswer fragment = new FragmentAnswer(file, pageCounter, this);
           session.save(fragment);
           pageCounter ++;
       }

       session.getTransaction().commit();
       session.close();
   }


   public void delete(Session session) {
       File f = new File(getFilepath());

       f.delete();
       System.out.println("Am inceput sa sterg");
       System.out.println(getFragments().size());
       //Session session = SessionFactoryManager.getInstance().openSession();
       session.beginTransaction();

       for (FragmentAnswer fragment: getFragments()) {
           System.out.println(fragment.getPage());
           fragment.delete();
           session.delete(fragment);
       }

       session.getTransaction().commit();
       //session.close();
       System.out.println("AM terminat de sters ?!");
   }

}
