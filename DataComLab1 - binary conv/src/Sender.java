
public class Sender {
	String str;

    void app_lay(String s1){
        s1="A-H"+s1;
        
        pre_lay(s1);
        
    }
    void pre_lay(String s2){
        s2="P-H"+s2;
        
        ses_lay(s2);
        
    }
    void ses_lay(String s3){
        s3="S-H"+s3;
        trns_lay(s3);
        
    }
    void trns_lay(String s4){
        s4="T-H"+s4;
        net_lay(s4);
        
    }
    void net_lay(String s5){
        s5="N-H"+s5;
        dat_lay(s5);
        
    }
    void dat_lay(String s6){
        s6="D-H"+s6;
        phy_lay(s6);
        
    }
    void phy_lay(String s7){
        s7="PH-H"+s7;
        get_str(s7);

    }
    void get_str(String s){
        str=s;
        
    }

}
