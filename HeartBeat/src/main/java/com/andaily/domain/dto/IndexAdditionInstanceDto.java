package com.andaily.domain.dto;

/**
 * @author Shengzhao Li
 */
public class IndexAdditionInstanceDto extends AbstractDto {


    private String additionData;

    //yyyy-mm-dd HH:mm:ss
    private String lastLogDate;


    public IndexAdditionInstanceDto() {
    }

    public String getAdditionData() {
        return additionData;
    }

    public void setAdditionData(String additionData) {
        this.additionData = additionData;
    }

    public String getLastLogDate() {
        return lastLogDate;
    }

    public void setLastLogDate(String lastLogDate) {
        this.lastLogDate = lastLogDate;
    }
}