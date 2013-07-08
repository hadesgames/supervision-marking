package org.example.listeners;

import org.example.helpers.PathHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class FolderStructureListener implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent bla) {

    }
    public void contextInitialized(ServletContextEvent bla) {
        if (!PathHelper.temp.exists())
            PathHelper.temp.mkdir();

        if (!PathHelper.pdf.exists())
            PathHelper.pdf.mkdir();

        if (!PathHelper.fragments.exists())
            PathHelper.fragments.mkdir();

    }
}
