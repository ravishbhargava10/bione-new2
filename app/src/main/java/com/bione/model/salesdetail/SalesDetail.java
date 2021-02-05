package com.bione.model.salesdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesDetail {

@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("sales_details")
@Expose
private Boolean salesDetails;
@SerializedName("data")
@Expose
private Data data;

public Integer getCode() {
return code;
}

public void setCode(Integer code) {
this.code = code;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Boolean getSalesDetails() {
return salesDetails;
}

public void setSalesDetails(Boolean salesDetails) {
this.salesDetails = salesDetails;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

}