package application;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import factory.CalendarCellFactory;
import factory.HourUnaryOperator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
	private Label lbTotalRealizadoMes;
	@FXML
	private Label lbTotalDeveriamRealizarAteHoje;
	@FXML
	private Label lbDifHoras;
	@FXML
	private Label lbTotalMes;
	@FXML
	private Label lbDataAtual;
	@FXML
	private Label lbHoraEntrada;
	@FXML
	private Label lbHoraAtual;
	@FXML
	private Label lbHoraSaida;
	@FXML
	private TextField tfHoraSaidaManual;
	@FXML
	private Label lbDifAposHoje;
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
	private int qtdeHorasQueDeveriamAteHoje;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//hoursField(tfEntrada);
		//hoursField(tfSaida);
		tfEntrada.setTextFormatter(new TextFormatter<>(new HourUnaryOperator()));
		tfSaida.setTextFormatter(new TextFormatter<>(new HourUnaryOperator()));
		tfHoraSaidaManual.setTextFormatter(new TextFormatter<>(new HourUnaryOperator()));
		tfEntrada.setPromptText("HH:MM");
		tfSaida.setPromptText("HH:MM");
		tfHoraSaidaManual.setPromptText("HH:MM");
		recarregar();
		timer = new Timeline();
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.getKeyFrames().add(new KeyFrame(Duration.millis(500), event -> {
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
			tratarTela(event);
		}));
		timer.playFromStart();
	}
	private void tratarTela(Event event) {
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
		calculaDiferencaAposHoje();
		if(!tfHoraSaidaManual.getText().isEmpty() && hora.equals(tfHoraSaidaManual.getText())){
			requestFocus();
		}
		else{
			if(hora.equals(lbHoraSaida.getText())){
				requestFocus();
			}
		}
	}

	private void requestFocus() {
		Platform.runLater(() -> lbDataAtual.getScene().getWindow().requestFocus());
		System.out.println("Foco solicitado");
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
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
		qtdeHorasQueDeveriamAteHoje = 0;
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

			dt = new Date(year-1900, mes-1, i+1);
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
			switch (dayOfWeek) {
				case 1:
					ml.setHorasDom(horas);
					ml.setDateDom(df.format(dt));
					break;
				case 2:
					ml.setHorasSeg(horas);
					ml.setDateSeg(df.format(dt));
					tratarHorasDia(dateS, horasDia, i, df, dt, dia);
					break;
				case 3:
					ml.setHorasTer(horas);
					ml.setDateTer(df.format(dt));
					tratarHorasDia(dateS, horasDia, i, df, dt, dia);
					break;
				case 4:
					ml.setHorasQua(horas);
					ml.setDateQua(df.format(dt));
					tratarHorasDia(dateS, horasDia, i, df, dt, dia);
					break;
				case 5:
					ml.setHorasQui(horas);
					ml.setDateQui(df.format(dt));
					tratarHorasDia(dateS, horasDia, i, df, dt, dia);
					break;
				case 6:
					ml.setHorasSex(horas);
					ml.setDateSex(df.format(dt));
					tratarHorasDia(dateS, horasDia, i, df, dt, dia);
					break;
				case 7:
					ml.setHorasSab(horas);
					ml.setDateSab(df.format(dt));
					break;
				default:
					break;
			}
		}

		if(qtdeHorasQueDeveriamAteHoje < 10)
			lbTotalDeveriamRealizarAteHoje.setText("0" + qtdeHorasQueDeveriamAteHoje + ":00");
		else
			lbTotalDeveriamRealizarAteHoje.setText("" + qtdeHorasQueDeveriamAteHoje + ":00");

		lbTotalMes.setText("" + qtdeHorasMes + ":00");

		if(csvReader.getMarcacaoCSV(df.format(dtAtual))!= null){
			formatarDiferencaTotalHoras(dtAtual, df);
		}else{
			Alert al = new Alert(AlertType.ERROR);
			al.setTitle("Erro Hora de Entrada");
			al.setContentText("Favor Carregar o arquivo CSV com a hora de entrada da Data de Hoje.");
			al.show();
			lbHoraEntrada.setText("08:00");
		}
		cbDatasPendentes.setItems(olDatasPendentes);
		tvHorasMarcadas.setItems(olHorasMarcadas);

		calculaDiferencaAposHoje();
		selecionarDataAtual(dtAtual, df);
	}

	private void selecionarDataAtual(Date dtAtual, SimpleDateFormat df) {
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
	private void formatarDiferencaTotalHoras(Date dtAtual, SimpleDateFormat df) {
		String horaSaida = null, difHoras = null;

		lbHoraEntrada.setText(csvReader.getMarcacaoCSV(df.format(dtAtual)).getEntrada());
		//Soma 09:00 horas na hora de entrada, para obter a hora de saida padrao
		horaSaida = HorasUtil.operateHoursCalendar(csvReader.getMarcacaoCSV(df.format(dtAtual)).getEntrada(), "09:00", "+");

		//se as horas acumuladas são maiores que as horas que deveriam ter sido realizadas, subtrair a diferenca a hora de saida padrao
		if(lbTotalDeveriamRealizarAteHoje.getText().compareTo(lbTotalRealizadoMes.getText()) < 0){
			difHoras  = HorasUtil.subTractHours(lbTotalRealizadoMes.getText(), lbTotalDeveriamRealizarAteHoje.getText());
			if(difHoras.compareTo("09:00") > 0)
				horaSaida = HorasUtil.operateHoursCalendar(csvReader.getMarcacaoCSV(df.format(dtAtual)).getEntrada(), "09:00", "+");
			else
				horaSaida = HorasUtil.subTractHours(horaSaida, difHoras);
			lbDifHoras.setText(difHoras);
		}
		//se as horas acumuladas são menores que as horas que deveriam ter sido realizadas, somar a diferenca na hora de saida padrao
		if(lbTotalDeveriamRealizarAteHoje.getText().compareTo(lbTotalRealizadoMes.getText()) > 0){
			difHoras  = HorasUtil.subTractHours(lbTotalDeveriamRealizarAteHoje.getText(), lbTotalRealizadoMes.getText());
			if(difHoras.compareTo("09:00") > 0)
				horaSaida = HorasUtil.operateHoursCalendar(csvReader.getMarcacaoCSV(df.format(dtAtual)).getEntrada(), "09:00", "+");
			else
				horaSaida = HorasUtil.addHours(horaSaida, difHoras);
			lbDifHoras.setText(difHoras);
		}
		lbHoraSaida.setText(horaSaida);
	}

	private void tratarHorasDia(String dateS, String horasDia, int i, SimpleDateFormat df, Date dt, int dia){
		if(!feriadosReader.getlFeriados().isFeriado(dateS)){
			qtdeHorasMes = qtdeHorasMes + 9;
			if(dia > (i+1)){
				if (horasDia.equals("00:00")){
					olDatasPendentes.add(df.format(dt));
				}else{
					lbTotalRealizadoMes.setText(HorasUtil.addHours(lbTotalRealizadoMes.getText(), horasDia));
				}
				qtdeHorasQueDeveriamAteHoje = qtdeHorasQueDeveriamAteHoje + 9;
			}
		}
	}

	@FXML
	private void btRealizarMarcacaoAction(Event event){
		MarcacaoCSV marcacao = new MarcacaoCSV();
		if(cbDatasPendentes.getSelectionModel().getSelectedIndex() < 0) {
			Alert al = new Alert(AlertType.ERROR);
			al.setTitle("Erro - Selecione a Data");
			al.setContentText("Favor Selecionar a Data da Marcação Manual");
			al.show();
			return;
		}
		if(tfEntrada.getText().compareTo(tfSaida.getText()) >= 0) {
			Alert al = new Alert(AlertType.ERROR);
			al.setTitle("Erro - Horarios");
			al.setContentText("Horario de Entrada não pode ser maior ou igual ao horario de saida");
			al.show();
			return;
		}
		marcacao.setData(olDatasPendentes.get(cbDatasPendentes.getSelectionModel().getSelectedIndex()));
		marcacao.setEntrada(tfEntrada.getText());
		marcacao.setSaida(tfSaida.getText());
		marcacao.setQtdeHoras(HorasUtil.operateHoursCalendar(tfSaida.getText(), tfEntrada.getText(), "-"));
		this.manualManager.writeLine(marcacao);
		recarregar();
	}

	private void calculaDiferencaAposHoje(){
		String difTotal = null;
		String difHoje = null;

		if(tfHoraSaidaManual.getText().length() == 5){
			difHoje = HorasUtil.subTractHours(tfHoraSaidaManual.getText(), lbHoraEntrada.getText());
			difHoje = HorasUtil.subTractHours(difHoje, "09:00");
			difTotal = tratarHoras(difHoje, lbDifHoras.getText());
			lbDifAposHoje.setText(difTotal);
		} else {
			difHoje = HorasUtil.subTractHours(lbHoraSaida.getText(), lbHoraEntrada.getText());
			difHoje = HorasUtil.subTractHours(difHoje, "09:00");
			difTotal = tratarHoras(difHoje, lbDifHoras.getText());
			lbDifAposHoje.setText(difTotal);
		}
	}

	private String tratarHoras(String hora1, String hora2) {
		if (hora1.startsWith("-") && hora2.startsWith("-")) {
			return "-" + HorasUtil.addHours(hora1.replaceAll("-", ""), hora2.replaceAll("-", ""));
		}

		if (!hora1.startsWith("-") && !hora2.startsWith("-")) {
			return HorasUtil.addHours(hora1, hora2);
		}

		if (hora1.startsWith("-") && !hora2.startsWith("-")) {
			return HorasUtil.subTractHours(hora2, hora1.replaceAll("-", ""));
		}

		if (!hora1.startsWith("-") && hora2.startsWith("-")) {
			return HorasUtil.subTractHours(hora1, hora2.replaceAll("-", ""));
		}
		return null;
	}
}
