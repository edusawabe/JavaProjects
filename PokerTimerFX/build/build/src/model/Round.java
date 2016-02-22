/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author eduardo.sawabe
 */
public class Round {
    private String roundName;
    private int smallBlind;
    private int bigBlind;
    private int ante;
    private boolean breakRound;
    
    public Round (){
        smallBlind = 0;
        bigBlind = 0;
        ante = 0;
        breakRound = false;
        roundName = new String();
    }

    /**
     * @return the smallBlind
     */
    public int getSmallBlind() {
        return smallBlind;
    }

    /**
     * @param smallBlind the smallBlind to set
     */
    public void setSmallBlind(int smallBlind) {
        this.smallBlind = smallBlind;
    }

    /**
     * @return the bigBlind
     */
    public int getBigBlind() {
        return bigBlind;
    }

    /**
     * @param bigBlind the bigBlind to set
     */
    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }

    /**
     * @return the ante
     */
    public int getAnte() {
        return ante;
    }

    /**
     * @param ante the ante to set
     */
    public void setAnte(int ante) {
        this.ante = ante;
    }

    /**
     * @return the breakRound
     */
    public boolean isBreakRound() {
        return breakRound;
    }

    /**
     * @param breakRound the breakRound to set
     */
    public void setBreakRound(boolean breakRound) {
        this.breakRound = breakRound;
    }

    /**
     * @return the roundName
     */
    public String getRoundName() {
        return roundName;
    }

    /**
     * @param roundName the roundName to set
     */
    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }
    
}
