/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author marwe
 */
public class appointments {
    public int id ;
    public String title;
    public String description;
    public String start_date;
    public String end_date;

    public appointments(int id, String title, String description, String start_date, String end_date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }

   

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "appointments{" + "id=" + id + ", title=" + title + ", description=" + description + ", start_date=" + start_date + ", end_date=" + end_date + '}';
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   

   
}
