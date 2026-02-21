package com.isa.brtaeasyfill.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "FORMS_DATA")
@NoArgsConstructor
@AllArgsConstructor
public class BefModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_seq_gen")
    @SequenceGenerator(name = "form_seq_gen", sequenceName = "FORM_SEQ", allocationSize = 1)
    private Long id;

    // Vehicle information
    private String regAuth;
    private String viType;
    private String viRegNum;
    private String viChassisNo;
    private String viEnginNo;
    private String viManufacturer;
    private String viRegYear;
    private String viModel;

    @Column(name = "VI_CC")
    private String viCC;

    private String viPrevRegNo;
    private String viSellPrice;
    private String viBankName;

    // Seller information
    private String seName;
    private String seFatherName;
    private String seMotherName;

    @Column(name = "SE_NID")
    private String seNID;

    @Column(name = "SE_TIN")
    private String seTIN;

    private String sePermenentAdd;
    private String sePresentAdd;
    private String seNationality;
    private String seRelegion;
    private String seProfession;
    private String sePhone;
    private String seSpouse;

    // Buyer information
    private String buName;
    private String buFatherName;
    private String buMotherName;
    private String buSpouse;
    private String buGuardianName;
    private String buGender;
    private String buDoB;

    @Column(name = "BU_NID")
    private String buNID;

    @Column(name = "BU_TIN")
    private String buTIN;

    private String buPermenentAdd;
    private String buPresentAdd;
    private String buNationality;
    private String buRelegion;
    private String buProfession;
    private String buPhone;


 @Column(name = "created_by")
 private String createdByName;

 public String getCreatedByName() {
     return createdByName;
 }

 public void setCreatedByName(String createdByName) {
     this.createdByName = createdByName;
 }


    // --- Getters & Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Vehicle getters and setters
    public String getRegAuth() { return regAuth; }
    public void setRegAuth(String regAuth) { this.regAuth = regAuth; }

    public String getViType() { return viType; }
    public void setViType(String viType) { this.viType = viType; }

    public String getViRegNum() { return viRegNum; }
    public void setViRegNum(String viRegNum) { this.viRegNum = viRegNum; }

    public String getViChassisNo() { return viChassisNo; }
    public void setViChassisNo(String viChassisNo) { this.viChassisNo = viChassisNo; }

    public String getViEnginNo() { return viEnginNo; }
    public void setViEnginNo(String viEnginNo) { this.viEnginNo = viEnginNo; }

    public String getViManufacturer() { return viManufacturer; }
    public void setViManufacturer(String viManufacturer) { this.viManufacturer = viManufacturer; }

    public String getViRegYear() { return viRegYear; }
    public void setViRegYear(String viRegYear) { this.viRegYear = viRegYear; }

    public String getViModel() { return viModel; }
    public void setViModel(String viModel) { this.viModel = viModel; }

    public String getViCC() { return viCC; }
    public void setViCC(String viCC) { this.viCC = viCC; }

    public String getViPrevRegNo() { return viPrevRegNo; }
    public void setViPrevRegNo(String viPrevRegNo) { this.viPrevRegNo = viPrevRegNo; }

    public String getViSellPrice() { return viSellPrice; }
    public void setViSellPrice(String viSellPrice) { this.viSellPrice = viSellPrice; }

    public String getViBankName() { return viBankName; }
    public void setViBankName(String viBankName) { this.viBankName = viBankName; }

    // Seller getters and setters
    public String getSeName() { return seName; }
    public void setSeName(String seName) { this.seName = seName; }

    public String getSeFatherName() { return seFatherName; }
    public void setSeFatherName(String seFatherName) { this.seFatherName = seFatherName; }

    public String getSeMotherName() { return seMotherName; }
    public void setSeMotherName(String seMotherName) { this.seMotherName = seMotherName; }

    public String getSeNID() { return seNID; }
    public void setSeNID(String seNID) { this.seNID = seNID; }

    public String getSeTIN() { return seTIN; }
    public void setSeTIN(String seTIN) { this.seTIN = seTIN; }

    public String getSePermenentAdd() { return sePermenentAdd; }
    public void setSePermenentAdd(String sePermenentAdd) { this.sePermenentAdd = sePermenentAdd; }

    public String getSePresentAdd() { return sePresentAdd; }
    public void setSePresentAdd(String sePresentAdd) { this.sePresentAdd = sePresentAdd; }

    public String getSeNationality() { return seNationality; }
    public void setSeNationality(String seNationality) { this.seNationality = seNationality; }

    public String getSeRelegion() { return seRelegion; }
    public void setSeRelegion(String seRelegion) { this.seRelegion = seRelegion; }

    public String getSeProfession() { return seProfession; }
    public void setSeProfession(String seProfession) { this.seProfession = seProfession; }

    public String getSePhone() { return sePhone; }
    public void setSePhone(String sePhone) { this.sePhone = sePhone; }

    public String getSeSpouse() { return seSpouse; }
    public void setSeSpouse(String seSpouse) { this.seSpouse = seSpouse; }

    // Buyer getters and setters
    public String getBuName() { return buName; }
    public void setBuName(String buName) { this.buName = buName; }

    public String getBuFatherName() { return buFatherName; }
    public void setBuFatherName(String buFatherName) { this.buFatherName = buFatherName; }

    public String getBuMotherName() { return buMotherName; }
    public void setBuMotherName(String buMotherName) { this.buMotherName = buMotherName; }

    public String getBuSpouse() { return buSpouse; }
    public void setBuSpouse(String buSpouse) { this.buSpouse = buSpouse; }

    public String getBuGuardianName() { return buGuardianName; }
    public void setBuGuardianName(String buGuardianName) { this.buGuardianName = buGuardianName; }

    public String getBuGender() { return buGender; }
    public void setBuGender(String buGender) { this.buGender = buGender; }

    public String getBuDoB() { return buDoB; }
    public void setBuDoB(String buDoB) { this.buDoB = buDoB; }

    public String getBuNID() { return buNID; }
    public void setBuNID(String buNID) { this.buNID = buNID; }

    public String getBuTIN() { return buTIN; }
    public void setBuTIN(String buTIN) { this.buTIN = buTIN; }

    public String getBuPermenentAdd() { return buPermenentAdd; }
    public void setBuPermenentAdd(String buPermenentAdd) { this.buPermenentAdd = buPermenentAdd; }

    public String getBuPresentAdd() { return buPresentAdd; }
    public void setBuPresentAdd(String buPresentAdd) { this.buPresentAdd = buPresentAdd; }

    public String getBuNationality() { return buNationality; }
    public void setBuNationality(String buNationality) { this.buNationality = buNationality; }

    public String getBuRelegion() { return buRelegion; }
    public void setBuRelegion(String buRelegion) { this.buRelegion = buRelegion; }

    public String getBuProfession() { return buProfession; }
    public void setBuProfession(String buProfession) { this.buProfession = buProfession; }

    public String getBuPhone() { return buPhone; }
    public void setBuPhone(String buPhone) { this.buPhone = buPhone; }
}
