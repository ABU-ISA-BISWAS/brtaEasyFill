package com.isa.brtaeasyfill.controller;



public  class ChangePasswordRequest {
    private String username;
    private String currentPassword;
    private String newPassword;


    public String getUsername() { return username; }
    public String getCurrentPassword() { return currentPassword; }
    public String getNewPassword() { return newPassword; }

    public void setUsername(String username) { this.username = username; }
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
