package infoExtractor;
import javafx.beans.property.SimpleStringProperty;

	public class PDCTableLine {
		private SimpleStringProperty arquivo;
		private SimpleStringProperty processoPDC;
		private SimpleStringProperty fluxo;
		private boolean found;

		public PDCTableLine() {
			arquivo = new SimpleStringProperty();
			processoPDC = new SimpleStringProperty();
			fluxo = new SimpleStringProperty();
			setFound(false);
		}

		public String getArquivo() {
			return arquivo.get();
		}
		public void setArquivo(String arquivo) {
			this.arquivo.set(arquivo);
		}
		public String getProcessoPDC() {
			return processoPDC.get();
		}
		public void setProcessoPDC(String processoPDC) {
			this.processoPDC.set(processoPDC);
		}
		public String getFluxo() {
			return fluxo.get();
		}
		public void setFluxo(String fluxo) {
			this.fluxo.set(fluxo);
		}

		public boolean isFound() {
			return found;
		}

		public void setFound(boolean found) {
			this.found = found;
		}
	}

