public class Poimodel {
    //内容
    public String content;
    //上一行同一位置内容
    public String oldContent;
    //行标
    public int rowIndex;
    //列标
    public int cellIndex;

    public Poimodel() {
    }

    public Poimodel(String content, String oldContent, int rowIndex, int cellIndex) {
        this.content = content;
        this.oldContent = oldContent;
        this.rowIndex = rowIndex;
        this.cellIndex = cellIndex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOldContent() {
        return oldContent;
    }

    public void setOldContent(String oldContent) {
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
