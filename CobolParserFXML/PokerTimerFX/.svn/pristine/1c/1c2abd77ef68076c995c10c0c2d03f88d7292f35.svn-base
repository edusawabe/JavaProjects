package application;

import java.util.LinkedList;

import model.Round;
import util.Constants;

public class RoundManager {
	private LinkedList<Round> roundList;

	public RoundManager(){

	}

	public void setRoundListValues(){
		Round round;

		for (int i = 0; i < Constants.MAX_ROUNDS; i++) {
	            round = new Round();
	            switch (i) {
	                case 0:
	                    round.setSmallBlind(10);
	                    round.setBigBlind(20);
	                    round.setAnte(0);
	                    round.setBreakRound(false);
	                    break;
	                case 1:
	                    round.setSmallBlind(20);
	                    round.setBigBlind(40);
	                    round.setAnte(0);
	                    round.setBreakRound(false);
	                    break;
	                case 2:
	                    round.setSmallBlind(30);
	                    round.setBigBlind(60);
	                    round.setAnte(0);
	                    round.setBreakRound(false);
	                    break;
	                case 3:
	                    round.setSmallBlind(40);
	                    round.setBigBlind(80);
	                    round.setAnte(0);
	                    round.setBreakRound(false);
	                    break;
	                case 4:
	                    round.setSmallBlind(0);
	                    round.setBigBlind(0);
	                    round.setAnte(0);
	                    round.setBreakRound(true);
	                    break;
	                case 5:
	                    round.setSmallBlind(40);
	                    round.setBigBlind(80);
	                    round.setAnte(10);
	                    round.setBreakRound(false);
	                    break;
	                case 6:
	                    round.setSmallBlind(50);
	                    round.setBigBlind(100);
	                    round.setAnte(10);
	                    round.setBreakRound(false);
	                    break;
	                case 7:
	                    round.setSmallBlind(70);
	                    round.setBigBlind(140);
	                    round.setAnte(20);
	                    round.setBreakRound(false);
	                    break;
	                case 8:
	                    round.setSmallBlind(100);
	                    round.setBigBlind(200);
	                    round.setAnte(30);
	                    round.setBreakRound(false);
	                    break;
	                case 9:
	                    round.setSmallBlind(0);
	                    round.setBigBlind(0);
	                    round.setAnte(0);
	                    round.setBreakRound(true);
	                    break;
	                case 10:
	                    round.setSmallBlind(150);
	                    round.setBigBlind(300);
	                    round.setAnte(30);
	                    round.setBreakRound(false);
	                    break;
	                case 11:
	                    round.setSmallBlind(200);
	                    round.setBigBlind(400);
	                    round.setAnte(50);
	                    round.setBreakRound(false);
	                    break;
	                case 12:
	                    round.setSmallBlind(300);
	                    round.setBigBlind(600);
	                    round.setAnte(70);
	                    round.setBreakRound(false);
	                    break;
	                case 13:
	                    round.setSmallBlind(500);
	                    round.setBigBlind(1000);
	                    round.setAnte(100);
	                    round.setBreakRound(false);
	                    break;
	                case 14:
	                    round.setSmallBlind(0);
	                    round.setBigBlind(0);
	                    round.setAnte(0);
	                    round.setBreakRound(true);
	                    break;
	                case 15:
	                    round.setSmallBlind(700);
	                    round.setBigBlind(1400);
	                    round.setAnte(200);
	                    round.setBreakRound(false);
	                    break;
	                case 16:
	                    round.setSmallBlind(1000);
	                    round.setBigBlind(2000);
	                    round.setAnte(300);
	                    round.setBreakRound(false);
	                    break;
	                case 17:
	                    round.setSmallBlind(1500);
	                    round.setBigBlind(3000);
	                    round.setAnte(400);
	                    round.setBreakRound(false);
	                    break;
	                case 18:
	                    round.setSmallBlind(2000);
	                    round.setBigBlind(4000);
	                    round.setAnte(500);
	                    round.setBreakRound(false);
	                    break;
	                case 19:
	                    round.setSmallBlind(0);
	                    round.setBigBlind(0);
	                    round.setAnte(0);
	                    round.setBreakRound(true);
	                    break;
	                case 20:
	                    round.setSmallBlind(3000);
	                    round.setBigBlind(6000);
	                    round.setAnte(800);
	                    round.setBreakRound(false);
	                    break;
	                case 21:
	                    round.setSmallBlind(4000);
	                    round.setBigBlind(8000);
	                    round.setAnte(1000);
	                    round.setBreakRound(false);
	                    break;
	                default:
	                    round.setSmallBlind(6000);
	                    round.setBigBlind(12000);
	                    round.setAnte(2000);
	                    round.setBreakRound(false);
	                    break;
	            }
	            if (round.isBreakRound()) {
	                round.setRoundName("BREAK");
	            } else {
	                round.setRoundName("Rodada " + (i + 1) + ": " + round.getSmallBlind() + "/" + round.getBigBlind() + " - Ante: " + round.getAnte());
	            }
	            roundList.add(round);
	}
	}

	public LinkedList<Round> getRoundList() {
		return roundList;
	}

	public void setRoundList(LinkedList<Round> roundList) {
		this.roundList = roundList;
	}
}
