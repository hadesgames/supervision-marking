package org.example.forms;

import javax.ws.rs.FormParam;

/**
 * Created by hadesgames on 7/3/13.
 */
public class FileUploadForm {
    //@FormParam("name") public String name;
    @FormParam("file") public byte[] file;
}
