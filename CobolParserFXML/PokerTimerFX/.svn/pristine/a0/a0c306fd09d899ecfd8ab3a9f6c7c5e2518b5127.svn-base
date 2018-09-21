/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ListModel;

import javafx.collections.ObservableList;

/**
 *
 * @author eduardo.sawabe
 */
public class MailResultContent {

    private ObservableList<String> rebuy;
    private ObservableList<String> players;
    private ObservableList<String> outs;
    private String resume;
    private String arrecadado;
    private String premio1;
    private String premio2;
    private String premio3;
    private String premio4;
    private String premio5;
    private String roundFinal;
    private int rebuys;

    public void updateRebuys(){
	    for (int i = 0; i < rebuy.size(); i++) {
	    	if(!rebuy.get(i).contains("BREAK"))
	    		rebuys++;
		}
    }

    /**
     * @return the rebuy
     */
    public ObservableList<String> getRebuy() {
        return rebuy;
    }

    /**
     * @param rebuy the rebuy to set
     */
    public void setRebuy(ObservableList<String> rebuy) {
        this.rebuy = rebuy;
    }

    /**
     * @return the players
     */
    public ObservableList<String> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(ObservableList<String> players) {
        this.players = players;
    }

    /**
     * @return the outs
     */
    public ObservableList<String> getOuts() {
        return outs;
    }

    /**
     * @param outs the outs to set
     */
    public void setOuts(ObservableList<String> outs) {
        this.outs = outs;
    }

    /**
     * @return the resume
     */
    public String getResume() {
        return resume;
    }

    /**
     * @param resume the resume to set
     */
    public void setResume(String resume) {
        this.resume = resume;
    }

    public String toStringCssHtml()
    {
    	updateRebuys();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        String rebuyList = new String();
        String outsList = new String();

        for (int i = 0; i < rebuy.size(); i++) {
        	if(!rebuy.get(i).contains("BREAK"))
        		rebuyList = rebuyList + "<li>" + rebuy.get(i) + "</li>";
        }

        for (int i = 0; i < outs.size(); i++) {
            outsList = outsList + "<li>" + outs.get(i) + "</li>";
        }

        return ""
        + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "\n" +
        "<html>\n" +
        "<head>\n" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
        "<title>Resultados Poker</title>\n" +
        "<style>\n" +
        "*{margin:0; padding:0; font-family: 'Pontano Sans', ; font-size:14px; color:#424044;font-weight: bold;}\n" +
        "a{text-decoration:none;}\n" +
        "image{border:none;}\n" +
        ".clearing{clear:both;}\n" +
        "\n" +
        ".header-wrapper{width:100%; height:auto; background:#222156; overflow:hidden;}\n" +
        ".header{width:960px; margin:0 auto; margin-top:1px; overflow:hidden;}\n" +
        "\n" +
        ".banner{width:100%; height:30px; margin:0 auto;}\n" +
        ".banner-content{width:100%; float:left; padding-top:10px;}\n" +
        ".banner-content h2{font-size:26px; font-weight:normal; color:#22a8f0;}\n" +
        ".banner-content h1{font-family: 'Playfair Display', serif; font-size:38px; font-weight:normal; color:#ffc103; padding-bottom:10px; text-align:center}\n" +
        ".banner-content ul{list-style-type: square;}\n" +
        ".banner-content li{text-align:left;}\n" +
        ".banner-content li a{display:block; width:151px; height:39px; background:url(../images/btn-banner.gif) no-repeat; font-family: 'Playfair Display', serif; font-size:16px; font-weight:normal; color:#ffffff; text-align:center; line-height:38px; text-transform:uppercase; padding-left:10px;}\n" +
        ".banner-content li a:hover{display:block; width:151px; height:39px; background:url(../images/btn-banner-h.gif) no-repeat; font-family: 'Playfair Display', serif; font-size:16px; font-weight:normal; color:#222156; text-align:center; line-height:38px;}\n" +
        "\n" +
        "\n" +
        ".panel-wrapper{width:100%; height:auto; background:#222156; overflow:hidden;}\n" +
        ".panel-content{width:100%; margin:0 auto; margin-top:20px;}\n" +
        ".panel{width:500px; height:auto; margin-left:40px; float:left; overflow:hidden; position:relative; background:#ffffff; border-width:3px; border-style:solid; border-radius: 25px;}\n" +
        ".panel-center{width:300px; height:auto; float:center; background:#ffffff; overflow:hidden; position:relative; }\n" +
        ".panel-center h1{font-family: 'Playfair Display', serif; font-size:22px; font-weight:normal; color:#22a8f0; padding-left:30px; padding-right:30px; padding-top:5px;}\n" +
        ".panel-bottom{width:300px; height:10px; background:#ffffff; bottom no-repeat; padding-bottom:20px;}\n" +
        ".panel h1{font-family: 'Playfair Display', serif; font-size:22px; font-weight:normal; color:#22a8f0; padding-left:30px; padding-right:30px; padding-top:10px;}\n" +
        ".panel p{line-height:20px; padding-left:30px; padding-right:30px; padding-top:20px;}\n" +
        ".panel table{padding-left:30px; padding-right:30px; padding-top:2px;}\n" +
        ".panel table tr td{padding-left:0px; padding-right:30px; padding-top:7px;}\n" +
        ".panel ol{line-height:20px; padding-left:70px; padding-right:30px; padding-top:5px; list-style-type:decimal; }\n" +
        ".panel ol li{padding-left:0px; padding-right:30px; padding-top:2px;}\n" +
        "a.more1{width:90px; height:30px; line-height:30px; font-family: 'Playfair Display', serif; font-size:14px; color:#ffffff; display:block;text-align:center; background:url(../images/btn-more-n.gif) no-repeat; margin-left:30px; margin-top:30px; }\n" +
        "a.more1:hover{width:90px; height:30px; line-height:30px; font-family: 'Playfair Display', serif; font-size:14px; color:#ffffff; display:block;text-align:center; }\n" +
        ".marRight{margin-right:30px;background:#FFFFFF}\n" +
        "</style>\n" +
        "<link href='http://fonts.googleapis.com/css?family=Playfair+Display|Pontano+Sans' rel='stylesheet' type='text/css'>\n" +
        "</head>\n" +
        "<body>\n" +
        "<div class=\"header-wrapper\">\n" +
        "	<div class=\"header\">    	       \n" +
        "        <div class=\"banner\">\n" +
        "        	<div class=\"banner-content\">\n" +
        "				 <h1>Poker Etapa "+dateFormat.format(date)+"</h1>\n" +
        "            </div>\n" +
        "        </div>        \n" +
        "    </div>\n" +
        "</div>\n" +
        "\n" +
        "<div class=\"clearing\"></div>\n" +
        "<div class=\"panel-wrapper\">\n" +
        "	<div class=\"panel-content\">\n" +
        "		<div class=\"panel marRight\">\n" +
        "			<h1>Resultados</h1>\n" +
        "			    <table>\n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Jogadores\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+outs.size()+"\n" +
        "					   </td>\n" +
        "				   </tr>\n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Rebuys\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+rebuys+"\n" +
        "					   </td>				   \n" +
        "				   </tr>\n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Total Arrecadado\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+arrecadado+"\n" +
        "					   </td>				   \n" +
        "				   </tr>	\n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Premiação 1º\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+getPremio1()+"\n" +
        "					   </td>				   \n" +
        "				   </tr>					   \n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Premiação 2º\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+premio2+"\n" +
        "					   </td>				   \n" +
        "				   </tr>					   \n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Premiação 3º\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+premio3+"\n" +
        "					   </td>				   \n" +
        "				   </tr>					   \n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Premiação 4º\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+premio4+"\n" +
        "					   </td>				   \n" +
        "				   </tr>					   \n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Premiação 5º\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+premio5+"\n" +
        "					   </td>				   \n" +
        "				   </tr>\n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Ultima Rodada\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+getRoundFinal()+"\n" +
        "					   </td>				   \n" +
        "				   </tr>				   					   \n" +
        "				</table>\n" +
        "			<div class=\"panel-bottom\"></div>			\n" +
        "		</div>\n" +
        "		<div class=\"panel\">\n" +
        "			<h1>Rebuys</h1>\n" +
        "			<ol>\n" +
        "                          "+rebuyList+
        "			</ol>	\n" +
        "			<div class=\"panel-bottom\"></div>		\n" +
        "		</div>		\n" +
        "		<div class=\"panel\">\n" +
        "			<h1>Eliminaçõe</h1>\n" +
        "			<ol>\n" +
        "                          "+outsList+
        "			</ol>	\n" +
        "            <div class=\"panel-bottom\">\n" +
        "			</div>\n" +
        "		</div>				\n" +
        "	</div>\n" +
        "			\n" +
        "</div>\n" +
        "</body>\n" +
        "</html>";
    }

    public String toStringCssHtml2()
    {
    	updateRebuys();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        String rebuyList = new String();
        String outsList = new String();

        for (int i = 0; i < rebuy.size(); i++) {
        	if(!rebuy.get(i).contains("BREAK"))
        		rebuyList = rebuyList + "<li>" + rebuy.get(i) + "</li>";
        }

        for (int i = 0; i < outs.size(); i++) {
            outsList = outsList + "<li>" + outs.get(i) + "</li>";
        }

        return ""
        + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "\n" +
        "<html>\n" +
        "<head>\n" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
        "<title>Resultados Poker</title>\n" +
        "<style>\n" +
        "*{margin:0; padding:0; font-family: 'Pontano Sans', ; font-size:14px; color:#424044;font-weight: bold;}\n" +
        "a{text-decoration:none;}\n" +
        "image{border:none;}\n" +
        ".clearing{clear:both;}\n" +
        "\n" +
        ".header-wrapper{width:100%; height:auto; background:#222156; overflow:hidden;}\n" +
        ".header{width:960px; margin:0 auto; margin-top:1px; overflow:hidden;}\n" +
        "\n" +
        ".banner{width:100%; height:30px; margin:0 auto;}\n" +
        ".banner-content{width:100%; float:left; padding-top:10px;}\n" +
        ".banner-content h2{font-size:26px; font-weight:normal; color:#22a8f0;}\n" +
        ".banner-content h1{font-family: 'Playfair Display', serif; font-size:38px; font-weight:normal; color:#ffc103; padding-bottom:10px; text-align:center}\n" +
        ".banner-content ul{list-style-type: square;}\n" +
        ".banner-content li{text-align:left;}\n" +
        ".banner-content li a{display:block; width:151px; height:39px; background:url(../images/btn-banner.gif) no-repeat; font-family: 'Playfair Display', serif; font-size:16px; font-weight:normal; color:#ffffff; text-align:center; line-height:38px; text-transform:uppercase; padding-left:10px;}\n" +
        ".banner-content li a:hover{display:block; width:151px; height:39px; background:url(../images/btn-banner-h.gif) no-repeat; font-family: 'Playfair Display', serif; font-size:16px; font-weight:normal; color:#222156; text-align:center; line-height:38px;}\n" +
        "\n" +
        "\n" +
        ".panel-wrapper{width:100%; height:auto; background:#222156; overflow:hidden;}\n" +
        ".panel-content{width:100%; margin:0 auto; margin-top:20px;}\n" +
        ".panel{width:500px; height:auto; margin-left:40px; float:left; overflow:hidden; position:relative; background:#ffffff; border-width:3px; border-style:solid; border-radius: 25px;}\n" +
        ".panel-center{width:300px; height:auto; float:center; background:#ffffff; overflow:hidden; position:relative; }\n" +
        ".panel-center h1{font-family: 'Playfair Display', serif; font-size:22px; font-weight:normal; color:#22a8f0; padding-left:30px; padding-right:30px; padding-top:5px;}\n" +
        ".panel-bottom{width:300px; height:10px; background:#ffffff; bottom no-repeat; padding-bottom:20px;}\n" +
        ".panel h1{font-family: 'Playfair Display', serif; font-size:22px; font-weight:normal; color:#22a8f0; padding-left:30px; padding-right:30px; padding-top:10px;}\n" +
        ".panel p{line-height:20px; padding-left:30px; padding-right:30px; padding-top:20px;}\n" +
        ".panel table{padding-left:30px; padding-right:30px; padding-top:2px;}\n" +
        ".panel table tr td{padding-left:0px; padding-right:30px; padding-top:7px;}\n" +
        ".panel ol{line-height:20px; padding-left:70px; padding-right:30px; padding-top:5px; list-style-type:decimal; }\n" +
        ".panel ol li{padding-left:0px; padding-right:30px; padding-top:2px;}\n" +
        "a.more1{width:90px; height:30px; line-height:30px; font-family: 'Playfair Display', serif; font-size:14px; color:#ffffff; display:block;text-align:center; background:url(../images/btn-more-n.gif) no-repeat; margin-left:30px; margin-top:30px; }\n" +
        "a.more1:hover{width:90px; height:30px; line-height:30px; font-family: 'Playfair Display', serif; font-size:14px; color:#ffffff; display:block;text-align:center; }\n" +
        ".marRight{margin-right:30px;background:#FFFFFF}\n" +
        "</style>\n" +
        "<link href='http://fonts.googleapis.com/css?family=Playfair+Display|Pontano+Sans' rel='stylesheet' type='text/css'>\n" +
        "</head>\n" +
        "<body>\n" +
        "<div class=\"header-wrapper\">\n" +
        "	<div class=\"header\">    	       \n" +
        "        <div class=\"banner\">\n" +
        "        	<div class=\"banner-content\">\n" +
        "				 <h1>Poker Etapa "+dateFormat.format(date)+"</h1>\n" +
        "            </div>\n" +
        "        </div>        \n" +
        "    </div>\n" +
        "</div>\n" +
        "\n" +
        "<div class=\"clearing\"></div>\n" +
        "<div class=\"panel-wrapper\">\n" +
        "	<div class=\"panel-content\">\n" +
        "		<div class=\"panel marRight\">\n" +
        "			<h1>Resultados</h1>\n" +
        "			    <table>\n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Jogadores\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+outs.size()+"\n" +
        "					   </td>\n" +
        "				   </tr>\n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Rebuys\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+rebuys+"\n" +
        "					   </td>				   \n" +
        "				   </tr>\n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Total Arrecadado\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+arrecadado+"\n" +
        "					   </td>				   \n" +
        "				   </tr>	\n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Premiação 1º\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+getPremio1()+"\n" +
        "					   </td>				   \n" +
        "				   </tr>					   \n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   PremiaÃ§Ã£o 2Âº\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+premio2+"\n" +
        "					   </td>				   \n" +
        "				   </tr>					   \n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   PremiaÃ§Ã£o 3Âº\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+premio3+"\n" +
        "					   </td>				   \n" +
        "				   </tr>					   \n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   PremiaÃ§Ã£o 4Âº\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+premio4+"\n" +
        "					   </td>				   \n" +
        "				   </tr>					   \n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   PremiaÃ§Ã£o 5Âº\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+premio5+"\n" +
        "					   </td>				   \n" +
        "				   </tr>\n" +
        "				   <tr>\n" +
        "				   	   <td>\n" +
        "					   Ultima Rodada\n" +
        "					   </td>\n" +
        "					   <td>\n" +
        "					   "+getRoundFinal()+"\n" +
        "					   </td>				   \n" +
        "				   </tr>				   					   \n" +
        "				</table>\n" +
        "			<div class=\"panel-bottom\"></div>			\n" +
        "		</div>\n" +
        "		<div class=\"panel\">\n" +
        "			<h1>Rebuys</h1>\n" +
        "			<ol>\n" +
        "                          "+rebuyList+
        "			</ol>	\n" +
        "			<div class=\"panel-bottom\"></div>		\n" +
        "		</div>		\n" +
        "		<div class=\"panel\">\n" +
        "			<h1>EliminaÃ§Ãµes</h1>\n" +
        "			<ol>\n" +
        "                          "+outsList+
        "			</ol>	\n" +
        "            <div class=\"panel-bottom\">\n" +
        "			</div>\n" +
        "		</div>				\n" +
        "	</div>\n" +
        "			\n" +
        "</div>\n" +
        "</body>\n" +
        "</html>";
    }

    public String toStringHTML(){
        String retContent, ret;
        int r = 0;

        updateRebuys();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
ret =       "<p>===================== Etapa "+ dateFormat.format(date) +"==================================</P>";
        ret = ret + "<P>*********************************************************</P>";
        ret = ret + "<P>Jogadores           : " + outs.size() + "</P>";
        ret = ret + "<P>Rebuys              : " + rebuys + "</P>";
        ret = ret + "<P>Total Arrecadado    : " + arrecadado + "</P>";
        ret = ret + "<P>Premiação 1o        : " + getPremio1() + "</P>";
        ret = ret + "<P>PremiaÃ§Ã£o 2o        : " + premio2 + "</P>";
        ret = ret + "<P>PremiaÃ§Ã£o 3o        : " + premio3 + "</P>";
        ret = ret + "<P>PremiaÃ§Ã£o 4o        : " + premio4 + "</P>";
        ret = ret + "<P>PremiaÃ§Ã£o 5o        : " + premio5 + "</P>";
        ret = ret + "<P>Ultimo Round        : " + getRoundFinal() + "</P>";
        ret = ret + "<P>*********************************************************</P>";
        ret = ret + "<P>Lista de Rebuys     : " + "</P>";
        for (int i = 0; i < rebuy.size(); i++) {
        	if(!rebuy.get(i).contains("BREAK")){
        		ret = ret + "<P>   - " + (r+1) +" "+ rebuy.get(i) + "</P>";
        		r++;
        	}
        }
        ret = ret + "<P>*********************************************************</P>";
        ret = ret + "<P>Lista de EliminaÃ§Ãµes: " + "</P>";
        for (int i = 0; i < outs.size(); i++) {
            ret = ret + "<P>   - " + (i+1) +" "+ outs.get(i) + "</P>";
        }
        ret = ret + "<P>*********************************************************</P>";
        ret = ret + "<P>==================================================================</P>";

        retContent = "<!DOCTYPE html>\n" +
"<html>\n" +
"<head>\n" +
"<title>Page Title</title>\n" +
"</head>\n" +
"<body>\n" +
                "<style>\n" +
"body {\n" +
"    background-color: #g0e8fe;\n" +
"}\n" +
"\n" +
"h1 {\n" +
"    color: orange;\n" +
"    text-align: center;\n" +
"}\n" +
"\n" +
"p {\n" +
"    font-family: \"Courier New\";\n" +
"    font-size: 12px;\n" +
"}\n" +
"</style>"+
"\n" +
ret +
"\n" +
"</body>\n" +
"</html>";



        return retContent;
    }

    public String toString(){
        String retContent, ret;
        int r = 0;

        updateRebuys();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
ret =       "===================== Etapa "+ dateFormat.format(date) +"==================================\n";
        ret = ret + "*********************************************************\n";
        ret = ret + "Jogadores           : " + outs.size() + "\n";
        ret = ret + "Rebuys              : " + rebuys + "\n";
        ret = ret + "Total Arrecadado    : " + arrecadado + "\n";
        ret = ret + "Premiação 1o        : " + getPremio1() + "\n";
        ret = ret + "Premiação 2o        : " + premio2 + "\n";
        ret = ret + "Premiação 3o        : " + premio3 + "\n";
        ret = ret + "Premiação 4o        : " + premio4 + "\n";
        ret = ret + "Premiação 5o        : " + premio5 + "\n";
        ret = ret + "Ultimo Round        : " + getRoundFinal() + "\n";
        ret = ret + "*********************************************************\n";
        ret = ret + "Lista de Rebuys     : " + "\n";
        for (int i = 0; i < rebuy.size(); i++) {
        	if(!rebuy.get(i).contains("BREAK")){
        		ret = ret + "   - " + (r+1) +" "+ rebuy.get(i) + "\n";
        		r++;
        	}
        }
        ret = ret + "*********************************************************\n";
        ret = ret + "Lista de Eliminações: " + "\n";
        for (int i = 0; i < outs.size(); i++) {
            ret = ret + "   - " + (i+1) +" "+ outs.get(i) + "\n";
        }
        ret = ret + "*********************************************************\n";
        ret = ret + "==================================================================\n";

        return ret;
    }

    /**
     * @return the arrecadado
     */
    public String getArrecadado() {
        return arrecadado;
    }

    /**
     * @param arrecadado the arrecadado to set
     */
    public void setArrecadado(String arrecadado) {
        this.arrecadado = arrecadado;
    }

    /**
     * @return the premio2
     */
    public String getPremio2() {
        return premio2;
    }

    /**
     * @param premio2 the premio2 to set
     */
    public void setPremio2(String premio2) {
        this.premio2 = premio2;
    }

    /**
     * @return the premio3
     */
    public String getPremio3() {
        return premio3;
    }

    /**
     * @param premio3 the premio3 to set
     */
    public void setPremio3(String premio3) {
        this.premio3 = premio3;
    }

    /**
     * @return the premio4
     */
    public String getPremio4() {
        return premio4;
    }

    /**
     * @param premio4 the premio4 to set
     */
    public void setPremio4(String premio4) {
        this.premio4 = premio4;
    }

    /**
     * @return the premio5
     */
    public String getPremio5() {
        return premio5;
    }

    /**
     * @param premio5 the premio5 to set
     */
    public void setPremio5(String premio5) {
        this.premio5 = premio5;
    }

    /**
     * @return the roundFinal
     */
    public String getRoundFinal() {
        return roundFinal;
    }

    /**
     * @param roundFinal the roundFinal to set
     */
    public void setRoundFinal(String roundFinal) {
        this.roundFinal = roundFinal;
    }

    /**
     * @return the premio1
     */
    public String getPremio1() {
        return premio1;
    }

    /**
     * @param premio1 the premio1 to set
     */
    public void setPremio1(String premio1) {
        this.premio1 = premio1;
    }
}
