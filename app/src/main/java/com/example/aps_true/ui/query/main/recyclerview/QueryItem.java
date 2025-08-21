package com.example.aps_true.ui.query.main.recyclerview;

public class QueryItem {
    private int serial;
    private String ordernumber;
    private String source;
    private String number;
    private String number2;
    private String number3;
    private String number4;
    private String time;
    private String process;
    private String check;


    public QueryItem(Integer serial, String ordernumber,String source, String number, String number2,
                     String number3, String number4, String time, String process, String check) {
        this.serial = serial;
        this.ordernumber = ordernumber;
        this.source = source;
        this.number = number;
        this.number2 = number2;
        this.number3 = number3;
        this.number4 = number4;
        this.time = time;
        this.process = process;
        this.check = check;
    }

    public Integer getSerial() { return serial; }
    public String getOrderNumber() { return ordernumber; }
    public String getSource() { return source; }
    public String getNumber() { return number; }
    public String getNumber2() { return number2; }
    public String getNumber3() { return number3; }
    public String getNumber4() { return number4; }
    public String getTime() { return  time; }
    public String getProcess() { return  process; }
    public String getCheck() { return check; }
}
