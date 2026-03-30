package com.nilemobile.backend.dto;

public class AddressDTO {
    private Long addressId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String addressLine;
    private String ward;
    private String district;
    private String province;
    private Boolean isDefault;

    public AddressDTO(Long addressId, String firstName, String lastName, String phoneNumber, String addressLine, String ward, String district, String province, Boolean isDefault) {
        this.addressId = addressId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.addressLine = addressLine;
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.isDefault = isDefault;
    }

    public AddressDTO() {}

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }

    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

}