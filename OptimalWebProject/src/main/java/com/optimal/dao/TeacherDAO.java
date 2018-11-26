/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dao;

import com.opitmal.hibernate.HibernateUtil;
import com.optimal.dbmodel.Teacher;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author root
 */
public class TeacherDAO {

    public void saveTeacher(Teacher teacher) {

        Session se = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = se.beginTransaction();
        try {
            se.save(teacher);
            se.getTransaction().commit();

        } catch (RuntimeException ex) {

            if (trans != null) {

                trans.rollback();
            }
            ex.printStackTrace();
        } finally {
            se.flush();
            se.close();

        }
    }

    // general to delet or update
    public void executedTeacher(String HQL) {

        Transaction trans = null;

        Session se = HibernateUtil.getSessionFactory().openSession();

        try {

            trans = se.beginTransaction();

            se.createQuery(HQL).executeUpdate();

            se.getTransaction().commit();

        } catch (RuntimeException ex) {

            if (trans != null) {

                trans.rollback();
            }
            ex.printStackTrace();
        } finally {
            se.flush();
            se.close();

        }
    }

    public void deletdTeacher(int id) {

        String hql = "delete Teacher t where t.id ='" + id + "'";
        executedTeacher(hql);

    }

    public void updateTeacher(int id, String newName, String newGender, Date newBirthDate, String newCountery, String newCity, String newStreet, String newHabbits, String newImagePath) {

        String hql = "update Teacher t set t.name ='" + newName + "' , t.gender ='"
                + newGender + /*"' , t.birthdate ='" + newBirthDate +*/ "' , t.countery ='" + newCountery
                + "' , t.city ='" + newCity + "' , t.street ='" + newStreet + "' , t.habits ='" + newHabbits
                + "' , t.imagePath ='" + newImagePath + "'where t.id ='" + id + "'";

        executedTeacher(hql);

    }

    public void updateTeacherObject(Teacher teacher) {

        Session se = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = se.beginTransaction();
        try {

          //  Teacher get = (Teacher) se.get(Teacher.class, teacher.getId());
            Teacher load = (Teacher) se.load(Teacher.class, teacher.getId());

            load.setName(teacher.getName());
            load.setGender(teacher.getGender());
            load.setBirthdate(teacher.getBirthdate());
            load.setCountery(teacher.getCountery());
            load.setCity(teacher.getCity());
            load.setStreet(teacher.getStreet());
            load.setHabits(teacher.getHabits());
            load.setImageName(teacher.getImageName());

            se.saveOrUpdate(load);

            se.getTransaction().commit();

        } catch (RuntimeException ex) {

            if (trans != null) {

                trans.rollback();
            }
            ex.printStackTrace();
        } finally {
            se.flush();
            se.close();

        }
    }

    // genral
    public List list(String HQL) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            session.beginTransaction();
            List l = session.createQuery(HQL).list();
            session.getTransaction().commit();
            return l;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List getAllTeachers() {
        String hql = "from Teacher t";
        return list(hql);
    }

    //get search teacher
    public List searchTeacher(int id) {

        String hql = "from Teacher t  where  t.id  ='" + id + "'";

        return list(hql);
    }

    public List getAllCounteries() {
        String hql = "t.countery from Teacher t";
        return list(hql);
    }

    public Teacher getImageNamebyTeacherId(int teacherId) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Object uniqueResult = null;
        try {

            session.beginTransaction();
            uniqueResult = session.createQuery("from Teacher t  where  t.id  ='" + teacherId + "'").uniqueResult();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return (Teacher) uniqueResult;
    }

}
