package com.bluehorntech.muslimsislamicapp.dateconversion;

import java.util.Calendar;
/**
 * @author http://www.cepmuvakkit.com
 */
public class HijriCalendar {

    private Calendar cal;
    private double MJD;
    private String ismiSuhiri[] = {
        "MUHARRAM", "SAFAR", "REBIULAVVAL", "REBIULAHIR",
        "JAMIZIALAVVAL", "JAMIZIALAHIR", "RAJAB", "SHABAN",
        "RAMADHAN", "SHAVVAL", "ZILKADE", "ZILHICCE"};
    private int Lunation;
    private int hijriYear, hijriMonth, hijriDay;
    private boolean[] isFound;
    private double newMoonMoment;      //Calculated time  for the New Moon in ModifiedJulianDays UTC
    private double crescentMoonMoment; // Calculated time for the Crescent Moon in ModifiedJulianDays UTC
    final double synmonth = 29.530588861;// Synodic Month Period
    final double dT = 7.0 / 36525.0;           // Step (1 week)
    final double dTc = 3.0 / 36525.0;          // Step (3 days)
    final double Acc = (0.5 / 1440.0) / 36525.0;  // Desired Accuracy (0.5 min)
    final double MJD_J2000 = 51544.5;
    final double MLunatBase = 23435.90347;
    //  Modified Base date for E. W. Brown's numbered  series of
    //  lunations (1923 January 16 02:41) 2423436-2400000.5=23435.5
    //  2423436.40347   23435,9034699998
    //  which is solved according to the 8 degrees elongation angle.
    //  private double timeDifferenceforET_UT;  // Correction variable due to  difference of ephemeris time and universal time
    public HijriCalendar() {
    	
    }
    public HijriCalendar(int Year, int Month, int Day) {

        this.MJD = APC_Time.Mjd(Year, Month, Day, 0, 0, 0);
        cal = APC_Time.CalDat(MJD);
        double Tnow, T0, T1, TNewMoon, TCrescent; // Time( Ephemeris:disabled) in Julian centuries since J2000
        double D0, D1;
        Tnow = (MJD - MJD_J2000) / 36525.0;
        T1 = Tnow;
        T0 = T1 - dT;  // decrease 1 week
        isFound = new boolean[1];
        isFound[0] = false;
        // Search for phases   bracket desired phase event
        MoonPhases newMoon = new NewMoon();
        MoonPhases crescentMoon = new CrescentMoon();
        D1 = newMoon.calculatePhase(T1);
        D0 = newMoon.calculatePhase(T0);
        while ((D0 * D1 > 0.0) || (D1 < D0)) {
            T1 = T0;
            D1 = D0;
            T0 -= dT;
            D0 = newMoon.calculatePhase(T0);//Finds correct week for iteration
        }
        // Iterate NewMoon time
        TNewMoon = APC_Math.Pegasus(newMoon, T0, T1, Acc, isFound);
        // Correct for difference of ephemeris time and universal time currently disabled
       //  ETminUT ( TPhase, ET_UT, valid );
        newMoonMoment = (TNewMoon * 36525.0 + MJD_J2000);// - ET_UT/86400.0;
        Lunation = (int) Math.floor((newMoonMoment + 7 - MLunatBase) / synmonth) + 1;
        // 1341 (29 CemuzuyelEvvel) is the hicri day for the 17 January 1923
        // which is the start day of the Brown's Lunation Number;
        hijriYear = (Lunation + 4) / 12 + 1341;
        // Returns 1 for Muharrem 2 for Safer  .... 12 for Zilhicce
        hijriMonth = (Lunation + 4) % 12 + 1;
        if (isFound[0]) {
            TCrescent = APC_Math.Pegasus(crescentMoon, TNewMoon, TNewMoon + dTc, Acc, isFound);
            crescentMoonMoment = TCrescent * 36525.0 + MJD_J2000;
        }
        hijriDay = (int) (MJD - Math.round(crescentMoonMoment + 0.279166666666667)) + 1;
        //0.279166666666667 comes from the hours 5:18 am
        if (hijriDay == 0) {
            hijriDay = 30;
            hijriMonth--;
            if (hijriMonth == 0) {
                hijriMonth = 12;
            }
        }


    }
    public void gergrionToHijri(int Year, int Month, int Day) {

        this.MJD = APC_Time.Mjd(Year, Month, Day, 0, 0, 0);
        cal = APC_Time.CalDat(MJD);

        double Tnow, T0, T1, TNewMoon, TCrescent; // Time( Ephemeris:disabled) in Julian centuries since J2000
        double D0, D1;
        Tnow = (MJD - MJD_J2000) / 36525.0;
        T1 = Tnow;
        T0 = T1 - dT;  // decrease 1 week
        isFound = new boolean[1];
        isFound[0] = false;
        // Search for phases   bracket desired phase event
        MoonPhases newMoon = new NewMoon();
        MoonPhases crescentMoon = new CrescentMoon();
        D1 = newMoon.calculatePhase(T1);
        D0 = newMoon.calculatePhase(T0);
        while ((D0 * D1 > 0.0) || (D1 < D0)) {
            T1 = T0;
            D1 = D0;
            T0 -= dT;
            D0 = newMoon.calculatePhase(T0);//Finds correct week for iteration
        }
        // Iterate NewMoon time
        TNewMoon = APC_Math.Pegasus(newMoon, T0, T1, Acc, isFound);
        // Correct for difference of ephemeris time and universal time currently disabled
        // ETminUT ( TPhase, ET_UT, valid );
        newMoonMoment = (TNewMoon * 36525.0 + MJD_J2000);// - ET_UT/86400.0;
        Lunation = (int) Math.floor((newMoonMoment + 7 - MLunatBase) / synmonth) + 1;
        // 1341 (29 CemuzuyelEvvel) is the hicri day for the 17 January 1923
        // which is the start day of the Brown's Lunation Number;
        hijriYear = (Lunation + 4) / 12 + 1341;
        // Returns 1 for Muharrem 2 for Safer  .... 12 for Zilhicce
        hijriMonth = (Lunation + 4) % 12 + 1;
        if (isFound[0]) {
            TCrescent = APC_Math.Pegasus(crescentMoon, TNewMoon, TNewMoon + dTc, Acc, isFound);
            crescentMoonMoment = TCrescent * 36525.0 + MJD_J2000;
        }
        hijriDay = (int) (MJD - Math.round(crescentMoonMoment + 0.279166666666667)) + 1;
        //0.279166666666667 comes from the hours 5:18 am
        if (hijriDay == 0) {
            hijriDay = 30;
            hijriMonth--;
            if (hijriMonth == 0) {
                hijriMonth = 12;
            }
        }


    }
    public int getHijriYear() {
       return hijriYear;
    }

    public String getHijriMonthName() {
        return ismiSuhiri[(hijriMonth - 1)];
    }

    public int getHijriMonth() {

        return hijriMonth;
    }

    public int getHijriDay() {
        return hijriDay;
    }

    public String getHicriTakvim() {
        return getHijriDay() + " " + getHijriMonthName() + " " + getHijriYear();
    }

    /**
     * 1 Muharrem=Hijri New Year
     * 10 Muharrem= Day of Ashura
     * 11/12 Rebiulevvel= Mawlid-al Nabi
     * 1 Recep=Start of Holy Months
     * 1st Cuma day on Recep= Lailatul-Raghaib
     * 27 Recep=Lailatul-Me'rac
     * 14/15 Nisfu-Sha'aban
     * 1 Ramadhan=1. Day of Ramadhan
     * 27 Ramadhan= Lailatul-Qadr
     * 1 Sevval=1. Day of Eid-al-Fitr
     * 2 Sevval=2. Day of Eid-al-Fitr
     * 3 Sevval=3. Day of Eid-al-Fitr
     * 9 ZiLHiCCE= A'rafa
     * 10 Zilhicce= 1. Day of Eid-al-Adha
     * 11 Zilhicce= 2. Day of Eid-al-Adha
     * 12 Zilhicce= 3. Day of Eid-al-Adha
     * 13 Zilhicce= 4. Day of Eid-al-Adha
     * @return
     */
    public String checkIfHolyDay() {
        String holyDay = "";
        switch (hijriMonth) {
            case 1:
                if (hijriDay == 1) {
                    holyDay = "NEWYEAR";
                } else if (hijriDay == 10) {
                    holyDay = "ASHURA";
                }
                break;
            case 3:
                if ((hijriDay == 11) || (hijriDay == 12)) {
                    holyDay = "MAWLID";
                }
                break;
            case 7:
                if ((hijriDay == 1) && (hijriMonth == 7)) {
                    holyDay = "HOLYMONTHS";
                }
                if ((cal.get(Calendar.DAY_OF_WEEK) == 6) && (hijriDay < 7)) {
                    holyDay = "RAGHAIB";
                }
                if (hijriDay == 27) {
                    holyDay = "MERAC";
                }
                break;
            case 8:
                if (/*(hijriDay==14)||*/(hijriDay == 15)) {
                    holyDay = "BARAAT";
                }
                break;
            case 9:
                if ((hijriDay == 27)) {
                    holyDay = "QADR";
                }
                break;
            case 10:
                if ((hijriDay == 1) || (hijriDay == 2) || (hijriDay == 3)) {
                    holyDay = hijriDay + "DAYOFEIDFITR";
                }
                break;

            case 12:
                if (hijriDay == 9) {
                    holyDay = "AREFE";
                }
                if ((hijriDay == 10) || (hijriDay == 11) || (hijriDay == 12) || (hijriDay == 13)) {
                    holyDay = (hijriDay - 9) + "DAYOFEIDAHDA";
                }
                break;
        }

        return holyDay;


    }

    public String getDay() {
        String daysName[] = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        return daysName[cal.get(Calendar.DAY_OF_WEEK) - 1];

    }
     


    /*
    //------------------------------------------------------------------------------
    //
    // ETminUT: Difference ET-UT of ephemeris time and universal time
    //
    // Input:
    //
    //   T         Time in Julian centuries since J2000
    //
    // Output:
    //
    //   DTsec     ET-UT in [s]
    //   valid     Flag indicating T in domain of approximation
    //
    // Notes: The approximation spans the years from 1825 to 2005
    //
    //------------------------------------------------------------------------------
    void getETDifferenceInMinUT (double T, double& DTsec, bool& valid)
    {
    //
    // Variables
    //
    int    i = (int) floor(T/0.25);
    double t = T-i*0.25;


    if ( (T<-1.75) || (0.05<T) ) {
    valid = false;
    DTsec = 0.0;
    }
    else {
    valid = true;
    switch (i) {
    case -7: DTsec=10.4+t*(-80.8+t*( 413.9+t*( -572.3))); break; // 1825-
    case -6: DTsec= 6.6+t*( 46.3+t*(-358.4+t*(   18.8))); break; // 1850-
    case -5: DTsec=-3.9+t*(-10.8+t*(-166.2+t*(  867.4))); break; // 1875-
    case -4: DTsec=-2.6+t*(114.1+t*( 327.5+t*(-1467.4))); break; // 1900-
    case -3: DTsec=24.2+t*( -6.3+t*(  -8.2+t*(  483.4))); break; // 1925-
    case -2: DTsec=29.3+t*( 32.5+t*(  -3.8+t*(  550.7))); break; // 1950-
    case -1: DTsec=45.3+t*(130.5+t*(-570.5+t*( 1516.7))); break; // 1975-
    case  0: t+=0.25;
    DTsec=45.3+t*(130.5+t*(-570.5+t*( 1516.7)));        // 2000-
    }                                                              // 2005
    }
    }*/
}
