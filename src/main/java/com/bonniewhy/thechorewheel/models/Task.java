package com.bonniewhy.thechorewheel.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 200)
    private String name;

    private boolean checked;

    // [ ] TODO: Figure out how to automatically grab the date and add it as "lastFinished" when a user checks a box to finish the task.
    // private Date lastFinished;

    @ManyToOne
    private Room room;

    @ManyToOne
    private User user;

    // Constructors
    public Task() { }

    public Task(String name, boolean checked) {
        this.name = name;
        this.checked = false;
        //this.lastFinished = lastFinished;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getChecked() {
        return checked;
    }

//    public Date getLastFinished() {
//        return lastFinished;
//    }

    public Room getRoom() {
        return room;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCheckedTrue(boolean checked) {
        this.checked = true;
    }

    public void setCheckedFalse(boolean checked) {
        this.checked = false;
    }

//    public void setLastFinished(Date lastFinished) {
//        this.lastFinished = lastFinished;
//    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
