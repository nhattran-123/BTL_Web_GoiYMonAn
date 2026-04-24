package com.model.bean;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String password;
    
    private String gender;
    private int age;
    private float weight;
    private float height;
    private float desired_weight;
    private float desired_height;
    private String role;
    
    private int is_activate; 
    private String createdAt;
    

    public User() {
    }

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }

    public float getDesired_weight() { return desired_weight; }
    public void setDesired_weight(float desired_weight) { this.desired_weight = desired_weight; }

    public float getDesired_height() { return desired_height; }
    public void setDesired_height(float desired_height) { this.desired_height = desired_height; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getIs_activate() {
        return is_activate;
    }

    public void setIs_activate(int is_activate) {
        this.is_activate = is_activate;
    }

    

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}