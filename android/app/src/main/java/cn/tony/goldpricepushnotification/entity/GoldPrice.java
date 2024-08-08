package cn.tony.goldpricepushnotification.entity;

import java.io.Serializable;
import java.util.Date;

public class GoldPrice implements Serializable {
    Date updateDate;
    Double bid;
    Double sell;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }
}
