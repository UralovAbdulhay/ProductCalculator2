package sample.Moodles;

import sample.Controllers.ControllerTable;

import static sample.Moodles.TovarZakaz.zakUsdUsz;

public class HelperClass {

    private Stavkalar stavkalar;


    public double zakazHisobla(TovarZakaz tovarZakaz, ControllerTable controllerTable) {

        this.stavkalar = new Stavkalar();

        tovarZakaz.setZakazSummaExw((tovarZakaz.getTovarNarxi() * tovarZakaz.getZakazSoni()));                   //  n ta tovar summasi
        tovarZakaz.setZakazTransProNatija(tovarZakaz.getTovarTransportNarxi() * tovarZakaz.getStTrans());       //  this.tovar ning natijaviy trans. stavkasi
        tovarZakaz.setZakazTransNarxi(tovarZakaz.getTovarNarxi() * tovarZakaz.getZakazTransProNatija());        //  har bir tovar uchun trans narxi
        tovarZakaz.setZakazTransSumm((tovarZakaz.getZakazSummaExw() * tovarZakaz.getZakazTransProNatija()));               //  berilgan zakaz uchun trans summasi
        tovarZakaz.setZakazTransLiNarx((tovarZakaz.getTovarNarxi() + tovarZakaz.getZakazTransNarxi()));          //  har bir tovar uchun transport bilan  birgalikdagi Narxi
        tovarZakaz.setZakazTransLiSumma((tovarZakaz.getZakazTransLiNarx() * tovarZakaz.getZakazSoni()));                     //  berilgan zakaz uchun transport bilan birgalikdagi Summa

        /* private maydonlar  */

        tovarZakaz.setZakazCIPNarxiUSD(tovarZakaz.getZakazTransLiNarx() / stavkalar.getStCIP());                     // CIP Narxi   USD
        tovarZakaz.setZakazCIPSummUSD((tovarZakaz.getZakazCIPNarxiUSD() * tovarZakaz.getZakazSoni()));                   // CIP summasi USD

//        this.zakazCIPNarxiUSZ = (this.zakazCIPNarxiUSD * stavkalar.getStUSD_USZ());                   // CIP Narxi UZS
        tovarZakaz.setZakazCIPNarxiUSZ(tovarZakaz.getZakazCIPNarxiUSD() * zakUsdUsz);                   // CIP Narxi UZS

        double a = tovarZakaz.getZakazCIPNarxiUSZ();


        tovarZakaz.setZakazBojYigini(a * stavkalar.getStBojxona());                                     // bojxona yiginlari UZS
        double b = tovarZakaz.getZakazBojYigini();

        tovarZakaz.setZakazPoshlinaSumm(a * tovarZakaz.getTovarPoshlina());                    // Poshlina summasi UZS
        double c = tovarZakaz.getZakazPoshlinaSumm();

        tovarZakaz.setZakazAksizSumm(a * tovarZakaz.getTovarAksiz());                   //   Aksiz summasi UZS
        double d = tovarZakaz.getZakazAksizSumm();


        tovarZakaz.setZakazNDS1Narxi(((a + b + c + d) * stavkalar.getStNDS1Bez()));                             //  NDS1  miqdori


        double k = tovarZakaz.getZakazNDS1Narxi();

        tovarZakaz.setZakazKelishNarxi(a + b + c + d + k);                                       // bitta tovarning yetib kelish narxi
        tovarZakaz.setZakazKelishSumm(tovarZakaz.getZakazKelishNarxi() * tovarZakaz.getZakazSoni());                   // tovarning berilgan  miqdoridagi kelish summasi

        /*    pivate  maydon oxiri  */


        tovarZakaz.setZakazDDPnarxi(tovarZakaz.getZakazKelishNarxi() / tovarZakaz.getTovarDDP());                                 // DDP narxi
        tovarZakaz.setZakazDDPsumm(tovarZakaz.getZakazDDPnarxi() * tovarZakaz.getZakazSoni());                                //  DDP summasi
        tovarZakaz.setZakazNDS2liNarxi(tovarZakaz.getZakazDDPnarxi() + (tovarZakaz.getZakazDDPnarxi() * stavkalar.getStNDS2()));        // NDS2 li narx
        tovarZakaz.setZakazNDS2liSumm(tovarZakaz.getZakazNDS2liNarxi() * tovarZakaz.getZakazSoni());                // NDS2 li summa


        controllerTable.summaHisobla();

        return tovarZakaz.getZakazDDPsumm();
    }

}
