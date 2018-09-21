package application;

import java.util.LinkedList;

import model.Round;
import util.Constants;

public class RoundManager {
	private LinkedList<Round> roundList;

	public RoundManager(){

	}

	public void setRoundListValues() {
		Round round;
		int lastBreak = 0;
		int roundNum = 0;

		for (int i = 0; i < Constants.MAX_ROUNDS; i++) {
			round = new Round();
			switch (i) {
			case 0:
				round.setSmallBlind(10);
				round.setBigBlind(20);
				round.setAnte(0);
				round.setBreakRound(false);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_20);
				break;
			case 1:
				round.setSmallBlind(20);
				round.setBigBlind(40);
				round.setAnte(0);
				round.setBreakRound(false);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_20);
				break;
			case 2:
				round.setSmallBlind(30);
				round.setBigBlind(60);
				round.setAnte(0);
				round.setBreakRound(false);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_20);
				break;
			case 3:
				round.setSmallBlind(40);
				round.setBigBlind(80);
				round.setAnte(0);
				round.setBreakRound(false);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_20);
				break;
			case 4:
				round.setSmallBlind(0);
				round.setBigBlind(0);
				round.setAnte(0);
				round.setBreakRound(true);
				round.setBreakRoundNumber(1);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_BREAK);
				round.setMinutesToBreak(Constants.MAX_MINUTES_BREAK);
				break;
			case 5:
				round.setSmallBlind(40);
				round.setBigBlind(80);
				round.setAnte(10);
				round.setBreakRound(false);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_20);
				break;
			case 6:
				round.setSmallBlind(50);
				round.setBigBlind(100);
				round.setAnte(10);
				round.setBreakRound(false);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_20);
				break;
			case 7:
				round.setSmallBlind(70);
				round.setBigBlind(140);
				round.setAnte(10);
				round.setBreakRound(false);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_20);
				break;
			case 8:
				round.setSmallBlind(90);
				round.setBigBlind(180);
				round.setAnte(20);
				round.setBreakRound(false);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_20);
				break;
			case 9:
				round.setSmallBlind(0);
				round.setBigBlind(0);
				round.setAnte(0);
				round.setBreakRound(true);
				round.setBreakRoundNumber(2);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_BREAK);
				round.setMinutesToBreak(Constants.MAX_MINUTES_BREAK);
				break;
			case 10:
				round.setSmallBlind(120);
				round.setBigBlind(240);
				round.setAnte(20);
				round.setBreakRound(false);
				round.setRebuyAllowed(true);
				round.setMinutes(Constants.MAX_MINUTES_15);
				break;
			case 11:
				round.setSmallBlind(150);
				round.setBigBlind(300);
				round.setAnte(30);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_15);
				break;
			case 12:
				round.setSmallBlind(220);
				round.setBigBlind(440);
				round.setAnte(40);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_15);
				break;
			case 13:
				round.setSmallBlind(300);
				round.setBigBlind(600);
				round.setAnte(60);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_15);
				break;
			case 14:
				round.setSmallBlind(400);
				round.setBigBlind(800);
				round.setAnte(80);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_15);
				break;
			case 15:
				round.setSmallBlind(0);
				round.setBigBlind(0);
				round.setAnte(0);
				round.setBreakRound(true);
				round.setMinutes(Constants.MAX_MINUTES_BREAK);
				round.setMinutesToBreak(Constants.MAX_MINUTES_BREAK);
				break;
			case 16:
				round.setSmallBlind(500);
				round.setBigBlind(1000);
				round.setAnte(100);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_15);
				break;
			case 17:
				round.setSmallBlind(600);
				round.setBigBlind(1200);
				round.setAnte(100);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_15);
				break;
			case 18:
				round.setSmallBlind(800);
				round.setBigBlind(1600);
				round.setAnte(100);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_10);
				break;
			case 19:
				round.setSmallBlind(1000);
				round.setBigBlind(2000);
				round.setAnte(200);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_10);
				break;
			case 20:
				round.setSmallBlind(1200);
				round.setBigBlind(2400);
				round.setAnte(200);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_10);
				break;
			case 21:
				round.setSmallBlind(0);
				round.setBigBlind(0);
				round.setAnte(0);
				round.setBreakRound(true);
				round.setMinutes(Constants.MAX_MINUTES_BREAK);
				round.setMinutesToBreak(Constants.MAX_MINUTES_BREAK);
				break;
			case 22:
				round.setSmallBlind(1500);
				round.setBigBlind(3000);
				round.setAnte(300);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_10);
				break;
			case 23:
				round.setSmallBlind(2000);
				round.setBigBlind(4000);
				round.setAnte(400);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_10);
				break;
			case 24:
				round.setSmallBlind(3000);
				round.setBigBlind(6000);
				round.setAnte(600);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_5);
				break;
			case 25:
				round.setSmallBlind(4000);
				round.setBigBlind(8000);
				round.setAnte(800);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_5);
				break;
			default:
				round.setSmallBlind(5000);
				round.setBigBlind(10000);
				round.setAnte(1000);
				round.setBreakRound(false);
				round.setMinutes(Constants.MAX_MINUTES_5);
				break;
			}
			if (round.isBreakRound()) {
				round.setRoundName("BREAK");
			} else {
				roundNum++;
				round.setRoundName("Rodada " + roundNum + ": " + round.getSmallBlind() + "/" + round.getBigBlind()
						+ " - Ante: " + round.getAnte());
			}
			roundList.add(round);
		}
		for (int i = 0; i < Constants.MAX_ROUNDS; i++){
			if(roundList.get(i).isBreakRound()){
				while (lastBreak < i) {
					for (int j = lastBreak; j < i; j++) {
						roundList.get(lastBreak).setMinutesToBreak(roundList.get(lastBreak).getMinutesToBreak() + roundList.get(j).getMinutes());
					}
					lastBreak++;
				}
				lastBreak++;
			}
		}
	}

	public LinkedList<Round> getRoundList() {
		return roundList;
	}

	public void setRoundList(LinkedList<Round> roundList) {
		this.roundList = roundList;
	}
}
