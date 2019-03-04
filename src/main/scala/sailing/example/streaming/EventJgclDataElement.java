package main.scala.sailing.example.streaming;

import org.apache.solr.schema.LatLonType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//import org.apache.lucene.spatial.util.GeoHashUtils;

public class EventJgclDataElement implements Serializable,
        Comparable<EventJgclDataElement> {

    private static final long serialVersionUID = 1L;


    private String JLBH;
    private String HPHM;
    private String HPZL;

    private String JGSJ;
    private String CLSD;
    private String HPYS;

    private String CLLX;
    private String HDSJ;

    private String BZWZDM;

    private String SBBH;


    private String CDBH;
    private String FXBH;

    private String CCLX;
    private String BZ;
    private String CSYS;

    private Date XRSJ;
    private String XZQH;


    public String getJLBH() {
        return JLBH;
    }

    public void setJLBH(String JLBH) {
        this.JLBH = JLBH;
    }

    public String getHPHM() {
        return HPHM;
    }

    public void setHPHM(String HPHM) {
        this.HPHM = HPHM;
    }

    public String getHPZL() {
        return HPZL;
    }

    public void setHPZL(String HPZL) {
        this.HPZL = HPZL;
    }



    public String getJGSJ() {
        return JGSJ;
    }

    public void setJGSJ(String JGSJ) {
        this.JGSJ = JGSJ;
    }

    public String getCLSD() {
        return CLSD;
    }

    public void setCLSD(String CLSD) {
        this.CLSD = CLSD;
    }

    public String getHPYS() {
        return HPYS;
    }

    public void setHPYS(String HPYS) {
        this.HPYS = HPYS;
    }



    public String getCLLX() {
        return CLLX;
    }

    public void setCLLX(String CLLX) {
        this.CLLX = CLLX;
    }

    public String getHDSJ() {
        return HDSJ;
    }

    public void setHDSJ(String HDSJ) {
        this.HDSJ = HDSJ;
    }



    public String getBZWZDM() {
        return BZWZDM;
    }

    public void setBZWZDM(String BZWZDM) {
        this.BZWZDM = BZWZDM;
    }



    public String getSBBH() {
        return SBBH;
    }

    public void setSBBH(String SBBH) {
        this.SBBH = SBBH;
    }





    public String getCDBH() {
        return CDBH;
    }

    public void setCDBH(String CDBH) {
        this.CDBH = CDBH;
    }

    public String getFXBH() {
        return FXBH;
    }

    public void setFXBH(String FXBH) {
        this.FXBH = FXBH;
    }



    public String getCCLX() {
        return CCLX;
    }

    public void setCCLX(String CCLX) {
        this.CCLX = CCLX;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getCSYS() {
        return CSYS;
    }

    public void setCSYS(String CSYS) {
        this.CSYS = CSYS;
    }



    public Date getXRSJ() {
        return XRSJ;
    }

    public void setXRSJ(Date XRSJ) {
        this.XRSJ = XRSJ;
    }

    public String getXZQH() {
        return XZQH;
    }

    public void setXZQH(String XZQH) {
        this.XZQH = XZQH;
    }




    public EventJgclDataElement() {
        super();
    }

    public EventJgclDataElement(String JLBH, String HPHM, String HPZL,  String JGSJ, String CLSD, String HPYS, String CLLX, String HDSJ, String BZWZDM,  String SBBH, String CDBH, String FXBH, String CCLX, String BZ, String CSYS,  Date XRSJ, String XZQH) {
        super();


        this.JLBH = JLBH;
        this.HPHM = HPHM;
        this.HPZL = HPZL;

        this.JGSJ = JGSJ;
        this.CLSD = CLSD;
        this.HPYS = HPYS;

        this.CLLX = CLLX;
        this.HDSJ = HDSJ;

        this.BZWZDM = BZWZDM;

        this.SBBH = SBBH;

        this.CDBH = CDBH;
        this.FXBH = FXBH;

        this.CCLX = CCLX;
        this.BZ = BZ;
        this.CSYS = CSYS;

        this.XRSJ = XRSJ;
        this.XZQH = XZQH;



    }


    @Override
    public int compareTo(EventJgclDataElement o) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy HH:mm");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = this.XRSJ;
            date2 = o.getXRSJ();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getXRSJ().compareTo(o.getXRSJ());
    }

}
