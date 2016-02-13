package view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import model.RankingLine;

public class ColumnFactory implements Callback<TableColumn.CellDataFeatures<RankingLine, String>, ObservableValue<String>> {
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<RankingLine, String> data) {
        return new ReadOnlyObjectWrapper(data.getValue());
    }
}
