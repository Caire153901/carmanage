package com.wmt.carmanage.constant;

import java.io.Serializable;
import java.util.List;

/**
 * 分页组件处理（为处理easyui表格问题）
 */
public class EUDataGridResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private long total;

    private List<?> rows;

    public EUDataGridResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public EUDataGridResult() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
