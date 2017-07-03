package factory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.MarcacaoLinhaTV;
import processors.FeriadosReader;
import util.DateUtil;

@SuppressWarnings("rawtypes")
public class CalendarCellFactory implements Callback<TableColumn<MarcacaoLinhaTV, String>, TableCell<MarcacaoLinhaTV, String>> {
	private FeriadosReader feriadosReader;

	public CalendarCellFactory() {
		feriadosReader = new FeriadosReader();
		feriadosReader.readFile();
	}

	@Override
	public TableCell call(TableColumn param) {
		return new TableCell<TableRow, String>() {
			@SuppressWarnings("deprecation")
			public void updateItem(String item, boolean empty) {
                SimpleDateFormat df = new SimpleDateFormat("/MM/yyyy");
                Date dtAtual = DateUtil.dataAtual();
                Date dt;
                String d = df.format(dtAtual);
                Calendar c = Calendar.getInstance();
                int dayOfWeek = 0;

				super.updateItem(item, empty);
                if (!isEmpty() && item != null) {
                	if(item.contains("\n")){
                		String[] aux = item.split("\n");
                		String[] dtS = d.split("/");
                		dt = new Date(Integer.parseInt(dtS[2]) - 1900, Integer.parseInt(dtS[1]) - 1, Integer.parseInt(aux[0]));
                		c.setTime(dt);
            			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            			if(dayOfWeek == 1 || dayOfWeek == 7){
            				this.setTextFill(Color.DARKBLUE);
            				this.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));
            			}
                		if(feriadosReader.getlFeriados().isFeriado(aux[0] + d)){
                			this.setTextFill(Color.DARKGREEN);
                			this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
                		}
                		else{
	                		if(item.contains("Horas: 00:00") && !(dayOfWeek == 1 || dayOfWeek == 7)){
	                			this.setTextFill(Color.WHITE);
	            				this.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
	                		}
                		}
                	}
                    setText(item);
                }
            }
		};
	}
}
