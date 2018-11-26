/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opitmal.teacerbean;

import com.optimal.dao.TeacherDAO;
import com.optimal.dbmodel.Teacher;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import static org.hibernate.internal.util.io.StreamCopier.BUFFER_SIZE;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author root
 */
@ManagedBean(name = "teacher")
@SessionScoped
public class TeacherBean {

    private int id;
    private String name;
    private String gender;
    private Date birthdate;
    private String countery;
    private String city;
    private String street;
    private List<String> habits;
    private String imageName;

    FacesContext facesContext;

    // list of all teacher records from DBTable
    List<Teacher> teacherTabl;

    private boolean buttomVisable;

    //SelectItem to store itemlable  ,  itemvalue
    private List<SelectItem> optionlist;

    //used to uploade image
    public TeacherBean() {
    }

    public TeacherBean(String name, String gender, Date birthdate, String countery, String city, String street, List<String> habits, String imagePath, Part ImagePart) {
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.countery = countery;
        this.city = city;
        this.street = street;
        this.habits = habits;
        this.imageName = imagePath;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountery() {
        return countery;
    }

    public void setCountery(String countery) {
        this.countery = countery;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<String> getHabits() {
        return habits;
    }

    public void setHabits(List<String> habits) {
        this.habits = habits;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Teacher> getTeacherTabl() {
        return teacherTabl;
    }

    public List<SelectItem> getOptionlist() {
        return optionlist;
    }

    public boolean isButtomVisable() {
        return buttomVisable;
    }

    public void setButtomVisable(boolean buttomVisable) {
        this.buttomVisable = buttomVisable;
    }

    //Messge date 
    public void onDateSelect(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
        //displayMessage("Selected Date : " + format.format(event.getObject()));
    }

    public void saveTeacher() {

        if (!validate()) {
            return;
        }

        if (uplodeValidate == false) {
            displayMessage("Select Your Image Please !");

        } else {
            // System.out.println("image name " + imageName);
            Teacher teacher = new Teacher();
            teacher.setName(name);
            teacher.setGender(gender);
            teacher.setBirthdate(birthdate);
            teacher.setCountery(countery);
            teacher.setCity(city);
            teacher.setStreet(street);

            StringBuilder habitsBulider = new StringBuilder();

            for (String habit : habits) {

                habitsBulider.append((String) habit + ",");

            }

            teacher.setHabits(habitsBulider.toString());

            teacher.setImageName(uploadeImage());

            TeacherDAO tdao = new TeacherDAO();
            tdao.saveTeacher(teacher);

            clearTeacherForm();
        }
    }

    ExternalContext extContext;
    InputStream inputStreamAssigned;
    boolean uplodeValidate;
    boolean uplodeValidateUpdate;

    // getEvent when select the image
    public void handleFileUpload(FileUploadEvent event) {

        uplodeValidate = true;
        uplodeValidateUpdate = true;

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");

        imageName = dateFormat.format(date) + "_" + event.getFile().getFileName();

        extContext = FacesContext.getCurrentInstance().getExternalContext();

        displayMessage("File Description : "
                + "file name: " + event.getFile().getFileName()
                + "file size: " + event.getFile().getSize() / 1024
                + "content type: " + event.getFile().getContentType());

        try {

            inputStreamAssigned = event.getFile().getInputstream();

        } catch (IOException ex) {
            Logger.getLogger(TeacherBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // upload image on MY PROJECT
    ///home/shady/(1)Optimal/(3)Work/OldNetBeansProjects/JavaApplication5/OptimalWebProject/target/OptimalWebProject-1.0-SNAPSHOT/WEB-INF
    public String uploadeImage() {

        //System.out.println("handleFileUpload === > " + imageName);
        File result = new File(extContext.getRealPath("/WEB-INF/" + imageName));

        // System.out.println("check =====> " + extContext.getRequestContextPath() + "/target/OptimalWebProject-1.0-SNAPSHOT/WEB-INF");
        //System.out.println(extContext.getRealPath(event.getFile().getFileName()));
        System.out.println(" Path of uploaded image :: " + extContext.getRealPath("/WEB-INF/" + imageName));

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(result);

            byte[] buffer = new byte[BUFFER_SIZE];

            int bulk;

            //InputStream inputStream = new FileInputStream(new File(extContext.getRealPath("/WEB-INF/" + imageName)));
            while (true) {
                bulk = inputStreamAssigned.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStreamAssigned.close();

//         
        } catch (IOException e) {
            e.printStackTrace();

            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "The files were not uploaded!", "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }

        uplodeValidate = false;

        return imageName;
    }

    public StreamedContent getDisplayTeacherImage() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {

            int teacherID_Param = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("teacherID"));

            System.out.println("teacherID_Param ===================>  " + teacherID_Param);

            imageName = new TeacherDAO().getImageNamebyTeacherId(teacherID_Param).getImageName();

            System.out.println("getimage :: =========> " + context.getExternalContext().getRealPath("/WEB-INF/" + imageName));

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ///home/shady/Desktop/index.jpeg
            BufferedImage img = ImageIO.read(new File(context.getExternalContext().getRealPath("/WEB-INF/" + imageName)));

            Image resizedImage = img.getScaledInstance(300, 200, BufferedImage.TYPE_INT_ARGB);

            BufferedImage bi = new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.getGraphics();

            g.drawImage(resizedImage, 0, 0, null);

            ImageIO.write(bi, "png", bos);

            return new DefaultStreamedContent(new ByteArrayInputStream(bos.toByteArray()), "image/png");

        }
    }

    public void searchTeacher() {

        TeacherDAO tdao = new TeacherDAO();

        //  System.out.println("searchTeacer @ :: " + name);
        if (name.isEmpty()) {

            displayMessage("You must Enter Id of Teacher to find his Informarion");

        } else if (name.matches("[0-9]*")) {

            List<Teacher> foundedTeacher = tdao.searchTeacher(Integer.parseInt(name));

            for (Teacher teacher : foundedTeacher) {

                name = teacher.getName();
                gender = teacher.getGender();
                birthdate = teacher.getBirthdate();
                countery = teacher.getCountery();
                city = teacher.getCity();
                street = teacher.getStreet();

                habits = Arrays.asList(teacher.getHabits().split(","));
                System.out.println("habits list" + habits);

                imageName = teacher.getImageName();
            }

            if (foundedTeacher.isEmpty()) {

                displayMessage("Not Found Teacher");
            }

        } else {

            displayMessage("You must Enter Id Number of Teacher to find his Informarion");

        }

    }

    // done
    public void displayTeachers() {

        TeacherDAO tdao = new TeacherDAO();
        teacherTabl = tdao.getAllTeachers();

    }

    Teacher currentTeacher;

    // done
    public void displayCurrentTeacher(Teacher teacher) {

        currentTeacher = teacher;

        id = teacher.getId();
        name = teacher.getName();
        gender = teacher.getGender();
        birthdate = teacher.getBirthdate();
        countery = teacher.getCountery();
        city = teacher.getCity();
        street = teacher.getStreet();
        imageName = teacher.getImageName();
        habits = Arrays.asList(teacher.getHabits().split(","));

        buttomVisable = true;
    }

    // done
    public void deleteCurrentTeacher(Teacher teacher) {

        TeacherDAO tdao = new TeacherDAO();
        tdao.deletdTeacher(teacher.getId());

        /// code to delet old image
        System.out.println("==============================> old image (muste be deleted) : " + imageName);
        //System.out.println("==============================> old image (muste be deleted) : " +extContext.getRealPath( "/WEB-INF/" + teacher.getImageName()));

        //File oldImage = new File(extContext.getRealPath("/WEB-INF/" + teacher.getImageName()));
        //  oldImage.delete();  // not hase privalge
        clearTeacherForm();

    }

    // done
    public void updateCurrentTeacher() {

        if (!validate()) {
            return;
        } else if (currentTeacher == null) {
            displayMessage("Select item that u need to update please !");
        } else {

            currentTeacher.setName(name);
            currentTeacher.setGender(gender);
            currentTeacher.setBirthdate(birthdate);
            currentTeacher.setCountery(countery);
            currentTeacher.setCity(city);
            currentTeacher.setStreet(street);

            StringBuilder habitsBulider = new StringBuilder();

            for (String habit : habits) {

                habitsBulider.append((String) habit + ",");

            }

            currentTeacher.setHabits(habitsBulider.toString());

            if (uplodeValidateUpdate == true) {

                /// code to delet old image
                System.out.println("==============================> old image (muste be deleted) : " + imageName);
                File oldImage = new File(extContext.getRealPath("/WEB-INF/" + imageName));
                oldImage.delete();  // not hase privalge

                currentTeacher.setImageName(uploadeImage());
                System.out.println("==============================> updated image : " + currentTeacher.getImageName());
                uplodeValidateUpdate = false;

            } else {
                currentTeacher.setImageName(imageName);
                System.out.println("==============================> Same image : " + currentTeacher.getImageName());

            }

            TeacherDAO tdao = new TeacherDAO();
            tdao.updateTeacherObject(currentTeacher);

            clearTeacherForm();
            buttomVisable = false;
        }
    }

    // form inputs validation
    private boolean validate() {

        String formatedBirthdate = null;

        facesContext = FacesContext.getCurrentInstance();
        boolean validte = true;
        String message = "";

        if (birthdate == null) {
        } else {
            Format formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatedBirthdate = formatter.format(birthdate);
            //  System.out.println("dddddddddddddddd : " + formatedBirthdate);
        }

        if (name.isEmpty()) {
            message = "Enter Your Name Please !";
            validte = false;
        } else if (gender.isEmpty()) {
            message = "Select Your Gender Please !";
            validte = false;
        } else if (birthdate == null) {
            message = "Enter Your Date Please !";
            validte = false;

        } else if (!(formatedBirthdate.matches("[0-9]{1,30}/[0-9]{1,12}/\\d{4}"))) {
            message = "Check Your DateFormate Please ! D/M/Y ";
            validte = false;
        } else if (countery.isEmpty()) {
            message = "Select Your Countery Please !";
            validte = false;
        } else if (city.isEmpty()) {
            message = "Select Your City Please !";
            validte = false;
        } else if ((street.isEmpty())) {
            message = "Enter Your Street !";
            validte = false;
        } else if (habits.isEmpty()) {
            message = "Select Your Habits Please !";
            validte = false;
//        } else if (uplodeValidate == false) {
//            message = "Select Your Image Please !";
//            validte = false;
//        } else {

        }

        if (!validte) {
            displayMessage(message);
        }

        return validte;
    }

    //
    public void clearTeacherForm() {

        id = 0;
        name = null;
        gender = null;
        birthdate = null;
        countery = null;
        city = null;
        street = null;
        habits = null;
        imageName = null;

        buttomVisable = false;

    }

    // dinamic city
    public void handleCities() {

        this.optionlist = new ArrayList<>();

        if (countery.equals("Palestine")) {
            System.out.println("Palestine ====>>>" + countery);

            //JOptionPane.showMessageDialog(null, "Palestine ====>>>" + countery);
            SelectItem option = new SelectItem();

            option = new SelectItem();
            option.setLabel("Palestine 1");
            option.setValue("Palestine 1");
            optionlist.add(option);

            option = new SelectItem();
            option.setLabel("Palestine 2");
            option.setValue("Palestine 2");
            optionlist.add(option);

            option = new SelectItem();
            option.setLabel("Palestine 3");
            option.setValue("Palestine 3");
            optionlist.add(option);

        } else if (countery.equals("Egypt")) {

            // JOptionPane.showMessageDialog(null, "Palestine ====>>>" + countery);
            System.out.println("Egypt ====>>>" + countery);

            SelectItem egyptOption = new SelectItem();
            egyptOption = new SelectItem();
            egyptOption.setLabel("Cairo");
            egyptOption.setValue("Cairo");
            optionlist.add(egyptOption);

            egyptOption = new SelectItem();
            egyptOption.setLabel("Alx");
            egyptOption.setValue("Alx");
            optionlist.add(egyptOption);

            egyptOption = new SelectItem();
            egyptOption.setLabel("El Menufia");
            egyptOption.setValue("El Menufia");
            optionlist.add(egyptOption);

        }
    }

    //show messags
    void displayMessage(String Message) {

        facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message, ""));

    }

//    
//    
//    public void setFilterCountery(List<String> filterCountery) {
//        this.filterCountery = filterCountery;
//
//        for (Teacher teacher : teacherTabl) {
//            filterCountery.add(teacher.getCountery());
//        }
//
//    }
//
//    public List<String> getFilterCountery() {
//
//        System.out.println("===========================>" + filterCountery);
//        return filterCountery;
//    }
//    private List<String> filterrCountery;
//
//    public List<String> getFilterCounterys() {
//
//        filterrCountery = new ArrayList<>();
//
//        for (Teacher teacher : teacherTabl) {
//            filterrCountery.add(teacher.getCountery());
//        }
//
//        System.out.println("list ============>" + filterrCountery);
//
//        return filterrCountery;
//    }
}
