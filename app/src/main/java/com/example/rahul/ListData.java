package com.example.rahul;

public class ListData {
    String id;
    String Title;
    String Author;
    String Units;
    String username;
    String phoneno;
    String Returndate;
    String Currentdate;

    ListData() {

    }

    public ListData(String id, String title, String author, String units, String username, String phoneno, String returndate, String currentdate) {
        this.id = id;
        Title = title;
        Author = author;
        Units = units;
        this.username = username;
        this.phoneno = phoneno;
        Returndate = returndate;
        Currentdate = currentdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String units) {
        Units = units;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getReturndate() {
        return Returndate;
    }

    public void setReturndate(String returndate) {
        Returndate = returndate;
    }

    public String getCurrentdate() {
        return Currentdate;
    }

    public void setCurrentdate(String currentdate) {
        Currentdate = currentdate;
    }
}


