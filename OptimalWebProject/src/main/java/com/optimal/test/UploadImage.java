/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import static org.hibernate.internal.util.io.StreamCopier.BUFFER_SIZE;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author root
 */
@ManagedBean(name = "fileUploadController")
@SessionScoped
public class UploadImage {

    //https://dzone.com/articles/how-upload-primefaces-32-under
    public void handleFileUpload(FileUploadEvent event) {

        ExternalContext extContext
                = FacesContext.getCurrentInstance().getExternalContext();
        File result = new File(extContext.getRealPath("/WEB-INF/" + event.getFile().getFileName()));

        System.out.println("check =====> " + extContext.getRequestContextPath() + "/target/OptimalWebProject-1.0-SNAPSHOT/WEB-INF");

        System.out.println(extContext.getRealPath(event.getFile().getFileName()));

        System.out.println(extContext.getRealPath("/WEB-INF/files/"));

        System.out.println(extContext.getRealPath("/WEB-INF/files/" + event.getFile().getFileName()));

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(result);

            byte[] buffer = new byte[BUFFER_SIZE];

            int bulk;
            InputStream inputStream = event.getFile().getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            FacesMessage msg
                    = new FacesMessage("File Description", "file name: "
                            + event.getFile().getFileName() + "<br/>file size: "
                            + event.getFile().getSize() / 1024
                            + " Kb<br/>content type: "
                            + event.getFile().getContentType()
                            + "<br/><br/>The file was uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (IOException e) {
            e.printStackTrace();

            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "The files were not uploaded!", "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }

    private StreamedContent bStatus;

}
