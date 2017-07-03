package application;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import br.org.util.validator.MaskFieldUtil;
import br.org.util.validator.MaskTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
		MaskFieldUtil.hoursField(tfEntrada);
		MaskFieldUtil.hoursField(tfSaida);
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
		Date dtAtual = new Date();
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
					ml.setDate(df.format(dt));
					break;
				case 2:
					ml.setHorasSeg(horas);
					ml.setDate(df.format(dt));
					if(!feriadosReader.getlFeriados().isFeriado(dateS)){
						qtdeHorasMes = qtdeHorasMes + 9;
						if (horasDia.equals("00:00"))
							if(dia > (i+1))
								olDatasPendentes.add(df.format(dt));
					}
					break;
				case 3:
					ml.setHorasTer(horas);
					ml.setDate(df.format(dt));
					if(!feriadosReader.getlFeriados().isFeriado(dateS)){
						qtdeHorasMes = qtdeHorasMes + 9;
						if (horasDia.equals("00:00"))
							if(dia > (i+1))
								olDatasPendentes.add(df.format(dt));
					}
					break;
				case 4:
					ml.setHorasQua(horas);
					ml.setDate(df.format(dt));
					if(!feriadosReader.getlFeriados().isFeriado(dateS)){
						qtdeHorasMes = qtdeHorasMes + 9;
						if (horasDia.equals("00:00"))
							if(dia > (i+1))
								olDatasPendentes.add(df.format(dt));
					}
					break;
				case 5:
					ml.setHorasQui(horas);
					ml.setDate(df.format(dt));
					if(!feriadosReader.getlFeriados().isFeriado(dateS)){
						qtdeHorasMes = qtdeHorasMes + 9;
						if (horasDia.equals("00:00"))
							if(dia > (i+1))
								olDatasPendentes.add(df.format(dt));
					}
					break;
				case 6:
					ml.setHorasSex(horas);
					ml.setDate(df.format(dt));
					if(!feriadosReader.getlFeriados().isFeriado(dateS)){
						qtdeHorasMes = qtdeHorasMes + 9;
						if (horasDia.equals("00:00"))
							if(dia > (i+1))
								olDatasPendentes.add(df.format(dt));
					}
					break;
				case 7:
					ml.setHorasSab(horas);
					ml.setDate(df.format(dt));
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
}
