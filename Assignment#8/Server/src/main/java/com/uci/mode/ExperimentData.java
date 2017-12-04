package com.uci.mode;

/**
 * Created by junm5 on 10/14/17.
 */
public class ExperimentData {
    private Long timeStamp;
    private String value;
    private DataType dataType;

    public Long getTimeStamp() {
        return timeStamp;
    }

    public ExperimentData setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ExperimentData setValue(String value) {
        this.value = value;
        return this;
    }

    public DataType getDataType() {
        return dataType;
    }

    public ExperimentData setDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    @Override
    public String toString() {
        return "ExperimentData{" +
                "timeStamp=" + timeStamp +
                ", value='" + value + '\'' +
                ", dataType=" + dataType +
                '}';
    }
}
