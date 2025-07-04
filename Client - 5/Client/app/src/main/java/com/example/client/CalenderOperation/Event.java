package com.example.client.CalenderOperation;


public class Event {
 public  String subject,class2,date2,time2, calender_t, calender_S, cont;

    public String getCalender_t() {
        return calender_t;
    }

    public void setCalender_t(String calender_t) {
        this.calender_t = calender_t;
    }

    public String getCalender_S() {
        return calender_S;
    }

    public void setCalender_S(String calender_S) {
        this.calender_S = calender_S;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public Event(String subject, String class2, String date2, String time2, String calender_t, String calender_S, String cont) {
        this.subject = subject;
        this.class2 = class2;
        this.date2 = date2;
        this.time2 = time2;
        this.calender_t = calender_t;
        this.calender_S = calender_S;
        this.cont = cont;
    }

    public Event(String subject, String class2, String date2, String time2, String text, String calender_t, String calender_S, String cont) {
        this.subject = subject;
        this.class2 = class2;
        this.date2 = date2;
        this.time2 = time2;
        this.text = text;
        this.calender_t = calender_t;
        this.calender_S = calender_S;
        this.cont = cont;

    }

    public String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Event(String subject, String class2, String date2, String time2, String status) {
        this.subject = subject;
        this.class2 = class2;
        this.date2 = date2;
        this.time2 = time2;
       // this.pending = pending;
        text = status;
    }

    public  boolean pending;

    public String getSubject() {
        return subject;
    }





    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClass2() {
        return class2;
    }

    public void setClass2(String class2) {
        this.class2 = class2;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public Event(String subject, String class2, String date2, String time2) {
        this.subject = subject;
        this.class2 = class2;
        this.date2 = date2;
        this.time2 = time2;
    }

    public  boolean isPending(){
        return  "Pending".equals(getText());
    }
    public void add(Event event) {
    }



}