package org.example.forms;

import javax.ws.rs.FormParam;

/**
 * Created by hadesgames on 7/3/13.
 */
public class FileUploadForm {
    @FormParam("filename") public String filename;
    @FormParam("file") public byte[] file;
}
