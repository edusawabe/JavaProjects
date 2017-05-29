package infoExtractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.draw.binding.CTTransform2D;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import util.Constants;
import util.FileUtil;
import util.InfoExtractor;

public class MainGUIController implements Initializable{
	@FXML
	private MenuItem miOpen;
	@FXML
	private MenuItem miClose;
	@FXML
	private Label lbPDCDir;
	@FXML
	private Label lbProjectDir;
	@FXML
	private Label lbTelaComponenteDir;
	@FXML
	private TableView<PDCTableLine> tvProcessosPDC;
	@FXML
	private TableColumn<PDCTableLine, String> tcArquivo;
	@FXML
	private TableColumn<PDCTableLine, String> tcProcessoPDC;
	@FXML
	private TableColumn<PDCTableLine, String> tcFluxo;
	@FXML
	private Label lbProcessados;
	@FXML
	private ProgressBar pbProcessados;

	private ObservableList<PDCTableLine> olTvProcessosPDC = FXCollections.observableArrayList();
	private File dir;
	private Timeline oPenProcess;
	private Timeline exportProcess;
	private int indFile;
	private ArrayList<File> pdcFiles;
	private ArrayList<File> beanFilesProjeto;
	private ArrayList<File> beanFilesTelaComponente;
	private Double di = new Double("0");
	private Double ds = new Double("0");
	private int totalFiles;
    final static Logger logger = Logger.getLogger(MainGUIController.class);
	private HSSFWorkbook workbook = new HSSFWorkbook();
	private HSSFSheet spreadsheet = workbook.createSheet("Processos PDC");
	private HSSFRow row;
	private HSSFCell cell;
	private int irow;
	private int indProjeto;
	private int indComponente;
	private Workbook wb;
	private XSSFSheet sheet;
	private XSSFTable table;
	private CTTable cttable;
	private ExcelManager excelManager = new ExcelManager();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.info("Inicializando!");
		tcArquivo.setCellValueFactory(new PropertyValueFactory<PDCTableLine, String>("arquivo"));
		tcProcessoPDC.setCellValueFactory(new PropertyValueFactory<PDCTableLine, String>("processoPDC"));
		tcFluxo.setCellValueFactory(new PropertyValueFactory<PDCTableLine, String>("fluxo"));
		tvProcessosPDC.getItems().setAll(olTvProcessosPDC);
		pdcFiles = new ArrayList<File>();
		beanFilesProjeto = new ArrayList<File>();
		beanFilesTelaComponente = new ArrayList<File>();
		logger.info("Inicializado!");
	}

	@FXML
	private void abrirPastaProjeto(Event event){
		beanFilesProjeto.clear();
		DirectoryChooser dch = new DirectoryChooser();
		dch.setInitialDirectory(new File("C:\\workspace_4.5.1\\RFIN"));
		dir = dch.showDialog(lbProjectDir.getScene().getWindow());
		indFile = 0;

		if (dir == null)
			return;
		else
			lbProjectDir.setText(Constants.DIR_PROJETO + " " + dir.getAbsolutePath());

		logger.info("Inicio Análise do Projeto: " + dir.getAbsolutePath());
		FileUtil.listf(dir.getAbsolutePath(), beanFilesProjeto, Constants.PDC_FILES);
		logger.info("Pasta do Projeto Avaliada: "+ dir.getAbsolutePath() + " ! - " + beanFilesProjeto.size() + " Arquivos Bean Encontrados");
	}

	@FXML
	private void abrirPastaTelaComponente(Event event){
		beanFilesTelaComponente.clear();
		DirectoryChooser dch = new DirectoryChooser();
		dch.setInitialDirectory(new File("C:\\workspace_4.5.1\\RFIN_COMPONENTE"));
		dir = dch.showDialog(lbTelaComponenteDir.getScene().getWindow());
		indFile = 0;

		if (dir == null)
			return;
		else
			lbTelaComponenteDir.setText(Constants.DIR_TELA_COMPONENTE+ " " + dir.getAbsolutePath());

		logger.info("Inicio Análise Tela Componente: " + dir.getAbsolutePath());
		FileUtil.listf(dir.getAbsolutePath(), beanFilesTelaComponente, Constants.PDC_FILES);
		logger.info("Pasta Tela Componente Avaliada: "+ dir.getAbsolutePath() + " ! - " + beanFilesTelaComponente.size() + " Arquivos Bean Encontrados");
	}

	@FXML
	private void abrirPastaPDC(Event event){
		olTvProcessosPDC.clear();
		pdcFiles.clear();
		DirectoryChooser dch = new DirectoryChooser();
		dch.setInitialDirectory(new File("C:\\workspace_4.5.1\\RFIN_PDC\\processos"));
		dir = dch.showDialog(lbPDCDir.getScene().getWindow());
		indFile = 0;

		if (dir == null)
			return;
		else
			logger.info("Abrindo Pasta PDC: "+ dir.getAbsolutePath() + " !");

		lbPDCDir.setText(Constants.DIR_PDC + " " + dir.getAbsolutePath());
		FileUtil.listf(dir.getAbsolutePath(), pdcFiles, Constants.PDC_FILE);
		logger.info("Fim - Pasta PDC: " + dir.getAbsolutePath() + " Processos PDC Encontrados: "+ pdcFiles.size());

		totalFiles = pdcFiles.size();

		oPenProcess = new Timeline();
		oPenProcess.setCycleCount(Timeline.INDEFINITE);
		oPenProcess.getKeyFrames().add(new KeyFrame(Duration.seconds(0.005), new EventHandler() {
			// KeyFrame event handler
			@Override
			public void handle(Event event) {
				try {
					doOpenDir();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}));
		oPenProcess.playFromStart();
	}

	private void doOpenDir() throws Exception {
		if (indFile >= totalFiles) {
			oPenProcess.stop();
		} else {
			PDCTableLine pdcTableLine = new PDCTableLine();
			pdcTableLine.setArquivo(pdcFiles.get(indFile).getAbsolutePath());
			pdcTableLine.setProcessoPDC(pdcFiles.get(indFile).getName().replace(".pdc", ""));
			pdcTableLine.setFluxo(InfoExtractor.getFluxoFrwkPDC(pdcFiles.get(indFile).getAbsolutePath()));
			olTvProcessosPDC.add(pdcTableLine);
			tvProcessosPDC.setItems(olTvProcessosPDC);

			ds = Double.parseDouble("" + totalFiles);
			di = Double.parseDouble("" + indFile);
			di = di + 1;
			pbProcessados.setProgress(di / ds);
			lbProcessados.setText("Carregando Arquivos:" + (indFile + 1) + "/" + (totalFiles));
			tvProcessosPDC.scrollTo(indFile);
			indFile++;
		}
	}

	@FXML
	private void btnReport(ActionEvent event) {
		excelManager.setFileName("c:\\export\\testExport.xlsx");
		excelManager.setSheetName("Processos PDC");
		excelManager.getlConlumns().add("Processo PDC");
		excelManager.getlConlumns().add("Fluxo");
		excelManager.getlConlumns().add("Projeto");
		excelManager.getlConlumns().add("Bean");

		excelManager.getlRowValue().clear();

		exportProcess = new Timeline();
		exportProcess.setCycleCount(Timeline.INDEFINITE);
		exportProcess.getKeyFrames().add(new KeyFrame(Duration.seconds(0.005), new EventHandler() {
			// KeyFrame event handler
			@Override
			public void handle(Event event) {
				try {
					doExportProcessList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}));
		exportProcess.playFromStart();
	}



	private void doExportProcess() {
		String ret = null;
		File parent = null;
		String oldParent = null;
		int end = 0;
		String processo = null;
		String fluxo = null;
		boolean notFound;

	    try {
	    	if(indFile < totalFiles){
	    		indFile++;
				if (indProjeto < beanFilesProjeto.size()) {
					logger.info("Verificando Arquivo Projeto(" + indProjeto + ")"
							+ beanFilesProjeto.get(indProjeto).getAbsolutePath());
					for (int i = 0; i < olTvProcessosPDC.size(); i++) {
						processo = ((PDCTableLine) olTvProcessosPDC.get(i)).getProcessoPDC();
						fluxo = ((PDCTableLine) olTvProcessosPDC.get(i)).getFluxo();
						ret = InfoExtractor.getPDCProcessCall(beanFilesProjeto.get(indProjeto).getAbsolutePath(),
								processo);
						if (ret != null) {
							irow++;
							olTvProcessosPDC.get(i).setFound(true);
							row = spreadsheet.createRow(irow);
							cell = row.createCell(1);
							cell.setCellValue(processo);
							cell = row.createCell(2);
							cell.setCellValue(fluxo);
							logger.info("Arquivo com chamada PDC Identificado(" + indProjeto + "): Processo: "
									+ processo + " Arquivo: " + beanFilesProjeto.get(indProjeto).getAbsolutePath());
							cell = row.createCell(3);
							parent = beanFilesProjeto.get(indProjeto).getParentFile();
							end = beanFilesProjeto.get(indProjeto).getPath().indexOf('\\');
							end = beanFilesProjeto.get(indProjeto).getPath().indexOf('\\', end + 1);
							end = beanFilesProjeto.get(indProjeto).getPath().indexOf('\\', end + 1);
							cell.setCellValue(beanFilesProjeto.get(indProjeto).getPath().substring(0, end));
							cell = row.createCell(4);
							cell.setCellValue(ret);
						}
					}
					indProjeto++;
				} else{
					if (indComponente < beanFilesTelaComponente.size()){
						logger.info("Verificando Arquivo Tela Componente(" + indComponente + ")"
								+ beanFilesTelaComponente.get(indComponente).getAbsolutePath());
						for (int i = 0; i < olTvProcessosPDC.size(); i++) {
							processo = ((PDCTableLine) olTvProcessosPDC.get(i)).getProcessoPDC();
							fluxo = ((PDCTableLine) olTvProcessosPDC.get(i)).getFluxo();
							ret = InfoExtractor.getPDCProcessCall(
									beanFilesTelaComponente.get(indComponente).getAbsolutePath(), processo);
							if (ret != null) {
								irow++;
								olTvProcessosPDC.get(i).setFound(true);
								row = spreadsheet.createRow(irow);
								cell = row.createCell(1);
								cell.setCellValue(processo);
								cell = row.createCell(2);
								cell.setCellValue(fluxo);
								logger.info("Arquivo com chamada PDC Identificado(" + indComponente + "): Processo: "
										+ processo + " Arquivo: " + beanFilesTelaComponente.get(indComponente).getAbsolutePath());
								cell = row.createCell(3);
								parent = beanFilesTelaComponente.get(indComponente).getParentFile();
								end = beanFilesTelaComponente.get(indComponente).getPath().indexOf('\\');
								end = beanFilesTelaComponente.get(indComponente).getPath().indexOf('\\', end + 1);
								end = beanFilesTelaComponente.get(indComponente).getPath().indexOf('\\', end + 1);
								cell.setCellValue(
										beanFilesTelaComponente.get(indComponente).getPath().substring(0, end));
								cell = row.createCell(4);
								cell.setCellValue(ret);
							}
						}
						indComponente++;
					}
				}
				ds = Double.parseDouble("" + totalFiles);
				di = Double.parseDouble("" + indFile);
				di = di + 1;
				pbProcessados.setProgress(di / ds);
				lbProcessados.setText("Processos Verificados: " + (indFile + 1) + "/" + totalFiles);
				tvProcessosPDC.scrollTo(indFile);
			}
	    	else {
	    		exportProcess.stop();
		    	for (int i = 0; i < olTvProcessosPDC.size(); i++) {
					if (!((PDCTableLine) olTvProcessosPDC.get(i)).isFound()) {
						irow++;
						processo = ((PDCTableLine) olTvProcessosPDC.get(i)).getProcessoPDC();
						fluxo = ((PDCTableLine) olTvProcessosPDC.get(i)).getFluxo();
						row = spreadsheet.createRow(irow);
						cell = row.createCell(1);
						cell.setCellValue(processo);
						cell = row.createCell(2);
						cell.setCellValue(fluxo);
					}
				}
		        FileOutputStream out = new FileOutputStream(new File("C:\\export\\ProcessosPDC.xls"));
		        workbook.write(out);
		        out.close();
			    Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Exportação");
			    alert.setContentText("Exportação Realizada com Sucesso!\n\nArquivo disponível em:\nC:\\export\\ProcessosPDC.xlsx");
			    alert.show();
		        logger.info("Processos Exportados com Sucesso");
	        }
	    } catch (Exception e) {
	    	logger.error("Erro na Exportação", e);
	    	exportProcess.stop();
	    }
	}

	private void doExportProcessList() {
		String ret = null;
		File parent = null;
		String oldParent = null;
		int end = 0;
		String processo = null;
		String fluxo = null;
		boolean notFound;

	    try {
	    	if(indFile < totalFiles){
	    		indFile++;
				if (indProjeto < beanFilesProjeto.size()) {
					logger.info("Verificando Arquivo Projeto(" + indProjeto + ")"
							+ beanFilesProjeto.get(indProjeto).getAbsolutePath());
					for (int i = 0; i < olTvProcessosPDC.size(); i++) {
						processo = ((PDCTableLine) olTvProcessosPDC.get(i)).getProcessoPDC();
						fluxo = ((PDCTableLine) olTvProcessosPDC.get(i)).getFluxo();
						ret = InfoExtractor.getPDCProcessCall(beanFilesProjeto.get(indProjeto).getAbsolutePath(),
								processo);
						if (ret != null) {
							irow++;
							olTvProcessosPDC.get(i).setFound(true);
							excelManager.getlRowValue().add(new LinkedList<String>());
							excelManager.getlRowValue().getLast().add(olTvProcessosPDC.get(i).getProcessoPDC());
							excelManager.getlRowValue().getLast().add(olTvProcessosPDC.get(i).getFluxo());
							logger.info("Arquivo com chamada PDC Identificado(" + indProjeto + "): Processo: "
									+ processo + " Arquivo: " + beanFilesProjeto.get(indProjeto).getAbsolutePath());
							parent = beanFilesProjeto.get(indProjeto).getParentFile();
							end = beanFilesProjeto.get(indProjeto).getPath().indexOf('\\');
							end = beanFilesProjeto.get(indProjeto).getPath().indexOf('\\', end + 1);
							end = beanFilesProjeto.get(indProjeto).getPath().indexOf('\\', end + 1);
							excelManager.getlRowValue().getLast().add(beanFilesProjeto.get(indProjeto).getPath().substring(0, end));
							excelManager.getlRowValue().getLast().add(ret);
						}
					}
					indProjeto++;
				} else{
					if (indComponente < beanFilesTelaComponente.size()){
						logger.info("Verificando Arquivo Tela Componente(" + indComponente + ")"
								+ beanFilesTelaComponente.get(indComponente).getAbsolutePath());
						for (int i = 0; i < olTvProcessosPDC.size(); i++) {
							processo = ((PDCTableLine) olTvProcessosPDC.get(i)).getProcessoPDC();
							fluxo = ((PDCTableLine) olTvProcessosPDC.get(i)).getFluxo();
							ret = InfoExtractor.getPDCProcessCall(
									beanFilesTelaComponente.get(indComponente).getAbsolutePath(), processo);
							if (ret != null) {
								irow++;
								olTvProcessosPDC.get(i).setFound(true);

								irow++;
								olTvProcessosPDC.get(i).setFound(true);
								excelManager.getlRowValue().add(new LinkedList<String>());
								excelManager.getlRowValue().getLast().add(olTvProcessosPDC.get(i).getProcessoPDC());
								excelManager.getlRowValue().getLast().add(olTvProcessosPDC.get(i).getFluxo());
								logger.info("Arquivo com chamada PDC Identificado(" + indComponente + "): Processo: "
										+ processo + " Arquivo: " + beanFilesTelaComponente.get(indComponente).getAbsolutePath());
								parent = beanFilesTelaComponente.get(indComponente).getParentFile();
								end = beanFilesTelaComponente.get(indComponente).getPath().indexOf('\\');
								end = beanFilesTelaComponente.get(indComponente).getPath().indexOf('\\', end + 1);
								end = beanFilesTelaComponente.get(indComponente).getPath().indexOf('\\', end + 1);
								excelManager.getlRowValue().getLast().add(beanFilesTelaComponente.get(indComponente).getPath().substring(0, end));
								excelManager.getlRowValue().getLast().add(ret);
							}
						}
						indComponente++;
					}
				}
				ds = Double.parseDouble("" + totalFiles);
				di = Double.parseDouble("" + indFile);
				di = di + 1;
				pbProcessados.setProgress(di / ds);
				lbProcessados.setText("Processos Verificados: " + (indFile + 1) + "/" + totalFiles);
				tvProcessosPDC.scrollTo(indFile);
			}
	    	else {
	    		exportProcess.stop();
	    		excelManager.generateExcelFile();
	    		Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Exportação");
			    alert.setContentText("Exportação Realizada com Sucesso!\n\nArquivo disponível em:\n"+excelManager.getFileName());
			    alert.show();
		        logger.info("Processos Exportados com Sucesso");
	        }
	    } catch (Exception e) {
	    	logger.error("Erro na Exportação", e);
	    	exportProcess.stop();
	    }
	}

}
