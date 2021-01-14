
public class Poimodel {
    //内容
    Object content;
    //上一行同一位置内容
    Object oldContent;
    //行标
    int rowIndex;
    //列标
    int cellIndex;

    public Poimodel(Object content, Object oldContent, int rowIndex, int cellIndex) {
        this.content = content;
        this.oldContent = oldContent;
        this.rowIndex = rowIndex;
        this.cellIndex = cellIndex;
    }

    public Poimodel() {
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getOldContent() {
        return oldContent;
    }

    public void setOldContent(Object oldContent) {
        this.oldContent = oldContent;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getCellIndex() {
        return cellIndex;
    }

    public void setCellIndex(int cellIndex) {
        this.cellIndex = cellIndex;
    }
}
