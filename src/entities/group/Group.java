/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.group;

/**
 *
 * @author moez
 */
public class Group 
{
    public int id;
    public String name;
    public String description;
    
    public Group ()
    {
        
    }
    
    public Group (String name, String description)
    {
     this.name=name;
     this.description=description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
