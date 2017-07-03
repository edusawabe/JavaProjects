package application;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import factory.CalendarCellFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.MarcacaoCSV;
import model.MarcacaoLinhaTV;
import processors.CSVReader;
import processors.FeriadosReader;
import processors.MarcacaoManualFileManager;
import util.DateUtil;
import util.HorasUtil;

public class AuxiliarPontoController implements Initializable{
	@FXML
	private Label lbTotalMes;
	@FXML
	private Label lbTotalRealizadoMes;
	@FXML
	private Label lbDataAtual;
	@FXML
	private Label lbHoraEntrada;
	@FXML
	private Label lbHoraSaida;
	@FXML
	private Label lbHoraAtual;
	@FXML
	private ComboBox<String> cbDatasPendentes;
	private ObservableList<String> olDatasPendentes = FXCollections.observableArrayList();
	@FXML
	private TextField tfEntrada;
	@FXML
	private TextField tfSaida;
	@FXML
	private Button btRealizaMarcacao;
	@FXML
	private TableView<MarcacaoLinhaTV> tvHorasMarcadas;
	private ObservableList<MarcacaoLinhaTV> olHorasMarcadas = FXCollections.observableArrayList();
	@FXML
	private TableColumn<MarcacaoLinhaTV, String> tcDom;
	@FXML
	private TableColumn<MarcacaoLinhaTV, String> tcSeg;
	@FXML
	private TableColumn<MarcacaoLinhaTV, String> tcTer;
	@FXML
	private TableColumn<MarcacaoLinhaTV, String> tcQua;
	@FXML
	private TableColumn<MarcacaoLinhaTV, String> tcQui;
	@FXML
	private TableColumn<MarcacaoLinhaTV, String> tcSex;
	@FXML
	private TableColumn<MarcacaoLinhaTV, String> tcSab;

	private CSVReader csvReader;
	private FeriadosReader feriadosReader;
	private MarcacaoManualFileManager manualManager;
	private Timeline timer;
	private String hora;
	private String hh;
	private String MM;
	private String ss;
	private int qtdeHorasMes;

	@SuppressWarnings("deprecation")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hoursField(tfEntrada);
		hoursField(tfSaida);
		tfEntrada.setPromptText("HH:MM");
		tfSaida.setPromptText("HH:MM");
		recarregar();
		timer = new Timeline();
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.getKeyFrames().add(new KeyFrame(Duration.millis(500), new EventHandler() {
			// KeyFrame event handler
			@Override
			public void handle(Event event) {
				if(LocalDateTime.now().getHour() < 10)
					hh = "0" + LocalDateTime.now().getHour();
				else
					hh = "" + LocalDateTime.now().getHour();

				if(LocalDateTime.now().getMinute() < 10)
					MM = "0" + LocalDateTime.now().getMinute();
				else
					MM = "" + LocalDateTime.now().getMinute();

				if(LocalDateTime.now().getSecond() < 10)
					ss = "0" + LocalDateTime.now().getSecond();
				else
					ss = "" + LocalDateTime.now().getSecond();
				lbHoraAtual.setText( hh + ":" + MM + ":" + ss);

				hora = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
				requestFocus(event);
			}
		}));
		timer.playFromStart();
	}
	private void requestFocus(Event event) {
		if(olDatasPendentes.isEmpty()){
			btRealizaMarcacao.setDisable(true);
			tfEntrada.setDisable(true);
			tfSaida.setDisable(true);
			cbDatasPendentes.setDisable(true);
		}else{
			btRealizaMarcacao.setDisable(false);
			tfEntrada.setDisable(false);
			tfSaida.setDisable(false);
			cbDatasPendentes.setDisable(false);
		}
		if(hora.equals(lbHoraSaida.getText())){
			Platform.runLater(()-> lbDataAtual.getScene().getWindow().requestFocus());
			System.out.println("Foco solicitado");
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void recarregar(){
		String dd, mm = null, yyyy = null, dateS, horas, horasDia;
		MarcacaoCSV horasDiaManual;
		int dayOfWeek;
		int dia;
		int mes;
		int year;
		Calendar c = Calendar.getInstance();
		Date dtAtual = DateUtil.dataAtual();
		Date dt;
		SimpleDateFormat df  = new SimpleDateFormat("dd/MM/yyyy");
		MarcacaoLinhaTV ml = null;
		dd = new String();
		qtdeHorasMes = 0;
		lbTotalRealizadoMes.setText("00:00");

		olDatasPendentes.clear();
		olHorasMarcadas.clear();

		tcDom.setCellValueFactory(new PropertyValueFactory<>("horasDom"));
		tcSeg.setCellValueFactory(new PropertyValueFactory<>("horasSeg"));
		tcTer.setCellValueFactory(new PropertyValueFactory<>("horasTer"));
		tcQua.setCellValueFactory(new PropertyValueFactory<>("horasQua"));
		tcQui.setCellValueFactory(new PropertyValueFactory<>("horasQui"));
		tcSex.setCellValueFactory(new PropertyValueFactory<>("horasSex"));
		tcSab.setCellValueFactory(new PropertyValueFactory<>("horasSab"));

		tcDom.setCellFactory(new CalendarCellFactory());
		tcSeg.setCellFactory(new CalendarCellFactory());
		tcTer.setCellFactory(new CalendarCellFactory());
		tcQua.setCellFactory(new CalendarCellFactory());
		tcQui.setCellFactory(new CalendarCellFactory());
		tcSex.setCellFactory(new CalendarCellFactory());
		tcSab.setCellFactory(new CalendarCellFactory());

		csvReader = new CSVReader();
		csvReader.setFile(new File("./ponto.csv"));
		feriadosReader = new FeriadosReader();
		manualManager = new MarcacaoManualFileManager();
		csvReader.readFile(false);
		feriadosReader.readFile();
		manualManager.readFile();

		lbDataAtual.setText(df.format(dtAtual));
		dateS = df.format(dtAtual);
		String[] dtSplit = dateS.split("/");
		dia = Integer.parseInt(dtSplit[0]);
		mes = Integer.parseInt(dtSplit[1]);
		year = Integer.parseInt(dtSplit[2]);
		yyyy = "" + year;

		//Preenchendo a Tabela
		for (int i = 0; i < 32; i++) {
			if((i+1) < 10)
				dd = "0" + (i+1);
			else
				dd = "" + (i+1);
			if(mes < 10)
				mm = "0" + mes;

			dt = new Date(year-1900, (mes-1), (i+1));
			dateS = df.format(dt);
			if(!DateUtil.isDateValid((dd + "/" + mm + "/" + yyyy))){
				olHorasMarcadas.add(ml);
				break;
			}
			c.setTime(dt);
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			if(i > 0 && dayOfWeek == 1){
				olHorasMarcadas.add(ml);
				ml = new MarcacaoLinhaTV();
			}
			if(i == 0)
				ml = new MarcacaoLinhaTV();
			horasDia = "00:00";
			horasDia = csvReader.getQtdeHorasDia(dateS);
			horasDiaManual = manualManager.getMarcacaoManual(df.format(dt));
			if (horasDiaManual != null)
				horasDia = horasDiaManual.getQtdeHoras();
			horas = dd + "\nHoras: " + horasDia;
			lbTotalRealizadoMes.setText(HorasUtil.addHours(lbTotalRealizadoMes.getText(), horasDia));
			switch (dayOfWeek) {
				case 1:
					ml.setHorasDom(horas);
					ml.setDateDom(df.format(dt));
					break;
				case 2:
					ml.setHorasSeg(horas);
					ml.setDateSeg(df.format(dt));
					if(!feriadosReader.getlFeriados().isFeriado(dateS)){
						qtdeHorasMes = qtdeHorasMes + 9;
						if (horasDia.equals("00:00"))
							if(dia > (i+1))
								olDatasPendentes.add(df.format(dt));
					}
					break;
				case 3:
					ml.setHorasTer(horas);
					ml.setDateTer(df.format(dt));
					if(!feriadosReader.getlFeriados().isFeriado(dateS)){
						qtdeHorasMes = qtdeHorasMes + 9;
						if (horasDia.equals("00:00"))
							if(dia > (i+1))
								olDatasPendentes.add(df.format(dt));
					}
					break;
				case 4:
					ml.setHorasQua(horas);
					ml.setDateQua(df.format(dt));
					if(!feriadosReader.getlFeriados().isFeriado(dateS)){
						qtdeHorasMes = qtdeHorasMes + 9;
						if (horasDia.equals("00:00"))
							if(dia > (i+1))
								olDatasPendentes.add(df.format(dt));
					}
					break;
				case 5:
					ml.setHorasQui(horas);
					ml.setDateQui(df.format(dt));
					if(!feriadosReader.getlFeriados().isFeriado(dateS)){
						qtdeHorasMes = qtdeHorasMes + 9;
						if (horasDia.equals("00:00"))
							if(dia > (i+1))
								olDatasPendentes.add(df.format(dt));
					}
					break;
				case 6:
					ml.setHorasSex(horas);
					ml.setDateSex(df.format(dt));
					if(!feriadosReader.getlFeriados().isFeriado(dateS)){
						qtdeHorasMes = qtdeHorasMes + 9;
						if (horasDia.equals("00:00"))
							if(dia > (i+1))
								olDatasPendentes.add(df.format(dt));
					}
					break;
				case 7:
					ml.setHorasSab(horas);
					ml.setDateSab(df.format(dt));
					break;
				default:
					break;
			}
		}

		lbTotalMes.setText("" + qtdeHorasMes + ":00");
		if(csvReader.getMarcacaoCSV(df.format(dtAtual))!= null){
			lbHoraEntrada.setText(csvReader.getMarcacaoCSV(df.format(dtAtual)).getEntrada());
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date d1 = null;

			try {
				d1 = format.parse(lbDataAtual.getText() + " " + lbHoraEntrada.getText() + ":00");
				Calendar sum = Calendar.getInstance();
				sum.setTimeInMillis(0);
				sum.setTime(d1);
				sum.add(Calendar.HOUR_OF_DAY,  9);
				format = new SimpleDateFormat("HH:mm");
				lbHoraSaida.setText(format.format(sum.getTime()));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cbDatasPendentes.setItems(olDatasPendentes);
		tvHorasMarcadas.setItems(olHorasMarcadas);

		tvHorasMarcadas.getSelectionModel().setCellSelectionEnabled(true);

		for (int j = 0; j < olHorasMarcadas.size(); j++) {
			if(olHorasMarcadas.get(j).getDateDom() != null && olHorasMarcadas.get(j).getDateDom().equals(df.format(dtAtual))){
				tvHorasMarcadas.getSelectionModel().select(j,tcDom);
				break;
			}
			if(olHorasMarcadas.get(j).getDateSeg() != null && olHorasMarcadas.get(j).getDateSeg().equals(df.format(dtAtual))){
				tvHorasMarcadas.getSelectionModel().select(j,tcSeg);
				break;
			}
			if(olHorasMarcadas.get(j).getDateTer() != null && olHorasMarcadas.get(j).getDateTer().equals(df.format(dtAtual))){
				tvHorasMarcadas.getSelectionModel().select(j,tcTer);
				break;
			}
			if(olHorasMarcadas.get(j).getDateQua() != null && olHorasMarcadas.get(j).getDateQua().equals(df.format(dtAtual))){
				tvHorasMarcadas.getSelectionModel().select(j,tcQua);
				break;
			}
			if(olHorasMarcadas.get(j).getDateQui() != null && olHorasMarcadas.get(j).getDateQui().equals(df.format(dtAtual))){
				tvHorasMarcadas.getSelectionModel().select(j,tcQui);
				break;
			}
			if(olHorasMarcadas.get(j).getDateSex() != null && olHorasMarcadas.get(j).getDateSex().equals(df.format(dtAtual))){
				tvHorasMarcadas.getSelectionModel().select(j,tcSex);
				break;
			}
			if(olHorasMarcadas.get(j).getDateSab() != null && olHorasMarcadas.get(j).getDateSab().equals(df.format(dtAtual))){
				tvHorasMarcadas.getSelectionModel().select(j,tcSab);
				break;
			}
		}
	}

	@FXML
	private void btRealizarMarcacaoAction(Event event){
		MarcacaoCSV marcacao = new MarcacaoCSV();
		marcacao.setData(olDatasPendentes.get(cbDatasPendentes.getSelectionModel().getSelectedIndex()));
		marcacao.setEntrada(tfEntrada.getText());
		marcacao.setSaida(tfSaida.getText());
		marcacao.setQtdeHoras("09:00");
		this.manualManager.writeLine(marcacao);
		recarregar();
	}

    /**
     * Monta a mascara para Data (dd/MM/yyyy).
     *
     * @param textField TextField
     */
    public static void hoursField(final TextField textField) {
        maxField(textField, 5);
        textField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() < 5) {
                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    try {
                    	value = value.replaceFirst("([01][0-9]|2[0-3])([0-5][0-9])", "$1:$2");
                        textField.setText(value);
                        positionCaret(textField);
					} catch (IllegalArgumentException e) {
						// TODO: handle exception
					}
                }
            }
        });
    }

    /**
     * @param textField TextField.
     * @param length    Tamanho do campo.
     */
    private static void maxField(final TextField textField, final Integer length) {
        textField.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                if (((String) newValue).length() > length)
                    textField.setText((String) oldValue);
			}
        });
    }

    /**
     * Devido ao incremento dos caracteres das mascaras eh necessario que o cursor sempre se posicione no final da string.
     *
     * @param textField TextField
     */
    private static void positionCaret(final TextField textField) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Posiciona o cursor sempre a direita.
                textField.positionCaret(textField.getText().length());
            }
        });
    }
}
