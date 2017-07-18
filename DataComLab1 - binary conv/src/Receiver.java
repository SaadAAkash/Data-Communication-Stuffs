
public class Receiver {
	String str;

    String app_lay(String s7){
        return s7.substring(3);
    }
    String pre_lay(String s2){
        return s2.substring(3);
    }
    String ses_lay(String s3){
        return s3.substring(3);
    }
    String trns_lay(String s4){
        return s4.substring(3);
    }
    String net_lay(String s5){
        return s5.substring(3);
    }
    String dat_lay(String s6){
        return s6.substring(3);
    }
    String phy_lay(String s7){
        return s7.substring(4);

    }
    void get_str(String s){
        str=s;

    }

}
